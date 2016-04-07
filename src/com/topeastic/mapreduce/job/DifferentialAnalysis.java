package com.topeastic.mapreduce.job;


import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.TreeSet;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Reducer.Context;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

import com.topeastic.hadoop.hdfs.HdfsUtils;
import com.topeastic.hadoop.utils.ConstantParam;
import com.topeastic.hadoop.utils.DateUtils;
import com.topeastic.hadoop.utils.PropertiesUtils;
import com.topeastic.hadoop.utils.StringUtils;
import com.topeastic.hadoop.utils.ToolsOther;
import com.topeastic.mapreduce.job.vo.DeviceStatus;
import com.topeastic.mapreduce.job.vo.FrostPair;
import com.topeastic.mapreduce.job.vo.TemperaturePair;
import com.topeastic.mapreduce.job.vo.TimePair;

public class DifferentialAnalysis {
	public static String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
   public static String plotResultPath = PropertiesUtils.getConfigProperty("local_plot_result");
//	public static String routinBurstResult = PropertiesUtils.getConfigProperty("routin_burst_result");
//	public static String operator_id = PropertiesUtils.getConfigProperty("operator_id");
	public static class Map extends Mapper<Object, Text, Text, Text> {
		// 输入数据格式：1,1422806402340,AC,AIH-W401-2059A0FCB36B,F4F50140490100FE01010101006600010400281A138080808080000000000000000000000000000000FFFFFF0000000000000080008000000000000000000000000000000000000000000000000008CAF4FB
		String[] inputArray = null;
		double electric = 0;// 工作电流
		double insideTemp = 0; // 室内温度--制冷时使用
		double insidePadTemp = 0; // 室内盘管温度--制冷时使用
		double outTemp = 0; // 室外温度--制热时使用
		double outPadTemp = 0; // 室外盘管温度--制热时使用
		double exhaustTemp = 0;//排气温度
		double setTemp = 0;//设定温度
		double dsh = 0;//排气温差
		Long timeValue = 0l;//时间戳值
		String[] hexArray = null;
		String deviceType = null;
		String workModel  = null;
		String shellTempDefend = null;
		String removeFrost = null;
		String isRrunning = null;
		
		public void map(Object key, Text value, Context context) throws IOException, InterruptedException {			
			//String pathName = ((FileSplit) context.getInputSplit()).getPath().toString();
			inputArray = value.toString().split(",");
			//检验输入的数据是否合法
			if (inputArray.length != 5) {
				return;
			}
			//过滤掉微分周期外的数据 --微分周期为20天
			if(!DateUtils.isPlotPeriod(-20,String.valueOf(inputArray[1]))){
				return;
			}
			//过滤掉用户操作时产生的日志
			if(!inputArray[0].equals(inputArray[2])){
				return;
			}
			//检验输入的指令是否合法
			if(inputArray[4].length()<162){
				return;
			}
			//获取设备类型  01--空调
			deviceType =inputArray[4].substring(18,20);
			if(!"01".equals(deviceType)){
				return;
			}
			//将结果F4F5开头的指令传转成16进制的字符串数组数据
			hexArray = StringUtils.getHexString(inputArray[4]);
			
			//判断是否开机，静音，睡眠状态，三者满足其一，此条数据过滤掉
			if(StringUtils.isRunning(hexArray[2])){
				isRrunning = "y";
			}else{
				isRrunning = "n";
			}
			removeFrost = hexArray[46];//除霜标志位
			if(!StringUtils.isStrongWind(hexArray[0])&&StringUtils.isRunning(hexArray[2])){
				return;
			}
			if(StringUtils.isSleep(hexArray[1])
					||StringUtils.isMute(hexArray[20])){
				return;
			}
			//获取工作模式，制冷还是制热
			workModel = StringUtils.getWoreMode(hexArray[2]);
			if(workModel.length() !=4){
				return;
			}else{
				workModel = workModel.substring(2,workModel.length());
			}
			System.out.println("removeFrost    ="+removeFrost);
			System.out.println("removeFrost    ="+StringUtils.isRemoveFrost(removeFrost));
			//判断制冷模式下
			//workModel="10";
			if("10".equals(workModel)||"01".equals(workModel)){
				timeValue = Long.parseLong(inputArray[1]);
				electric = StringUtils.getIabElectric(hexArray[39]);
				insideTemp = StringUtils.getInsideActualTemperature(hexArray[4]); //获取实际室内温度
				insidePadTemp = StringUtils.getInsidePadTemperature(hexArray[5]);//获取室内盘管温度
				
				outTemp = StringUtils.getOutsideEnviTemperature(hexArray[28],workModel);//获取实际室外温度
				outPadTemp = StringUtils.getOutsideCodenTemperature(hexArray[29],workModel);//获取实际室外冷凝器温度
				exhaustTemp = StringUtils.getCompressorExhaustTemperature(hexArray[30]);//获取排气温度
				setTemp = StringUtils.getDeviceSetTemperature(hexArray[3]);//获取设定温度
				shellTempDefend = hexArray[51];
				
				double insideTempRange =  StringUtils.calWencha(insideTemp,insidePadTemp,workModel);//室内温差
				double outTempRange =  StringUtils.calWencha(outPadTemp,outTemp,workModel);//室外温差
				double setTempRange =StringUtils.calWencha(insideTemp,setTemp,workModel);//计算设定温差--设定温度-室内温度
//				System.out.println("insideTempRange    ="+insideTempRange);
//				System.out.println("outTempRange    ="+outTempRange);
				//对制冷模式下进行分组
				System.out.println("workModel    ="+workModel);
				System.out.println("outTemp    ="+outTemp);
				System.out.println("outPadTemp    ="+outPadTemp);
				
				String c = null;
				if("10".equals(workModel)){
					//开始分组， 制冷情况下
					if(outTemp<24||outTemp>51){
						return;
					}
					c = StringUtils.getCoolGroups(outTemp);
					dsh =  StringUtils.calExhaustWencha(exhaustTemp,insidePadTemp,outPadTemp,workModel);
					context.write(new Text(inputArray[3] + ";" + workModel+";"+c+"|"),
							new Text(electric + "," + insideTempRange + ","+outTempRange+","
										+setTempRange+","+dsh+","+shellTempDefend+","+workModel+","+timeValue+","+isRrunning+","+removeFrost+","+outTemp));
				}
				//对制热模式下进行分组
				if("01".equals(workModel)){
					dsh =  StringUtils.calExhaustWencha(exhaustTemp,insidePadTemp,outPadTemp,workModel);
					//开始分组， 制热情况下
					if(outTemp<-16||outTemp>18){
						return;
					}
					c = StringUtils.getHotGroups(outTemp);
					context.write(new Text(inputArray[3] + ";" + workModel+";"+c+"|"),
								new Text(electric + "," + insideTempRange + ","+outTempRange+","
											+setTempRange+","+dsh+","+shellTempDefend+","+workModel+","+timeValue+","+isRrunning+","+removeFrost+","+outTemp));					
				}
			}else{
				System.out.println("other model    ="+workModel);
			}
		}
	}

	public static class Reduce extends Reducer<Text, Text, Text, Text> {
		// reduce解析map输出，将value中数据按照左右表分别保存，然后求笛卡尔积，输出
		//@SuppressWarnings({ "rawtypes", "unused" })
		public void reduce(Text key, Iterable<Text> values, Context context)
				throws IOException, InterruptedException {
			double electric = 0; // 记录工作电流--取平均值
			double inTempRang = 0; // 记录室内温差
			double outTempRang = 0; // 记录室外时的温差
			double setTempRang = 0; // 记录设定的温差
			double exhaustTemp = 0; // 记录排气
			String workModel = "";
			Integer count = 0;
			TreeSet<TemperaturePair> ts = new TreeSet<TemperaturePair>();
			TreeSet<TimePair> tr = new TreeSet<TimePair>();
			TreeSet<FrostPair> tf = new TreeSet<FrostPair>();
			
			List<DeviceStatus> dStatusList  = new ArrayList<DeviceStatus>();
			String[] valArray=null;
			for (Text val : values) {
				valArray = val.toString().split(",");
				if(valArray.length !=11){
					return;
				}
				workModel = valArray[6];
					//如果是制热，需要找出除霜标志位
				if("01".equals(workModel)){
					tf.add(new FrostPair(Long.valueOf(valArray[7].trim()), StringUtils.isRemoveFrost(valArray[9]),valArray[8]));
				}
				DeviceStatus ds = new DeviceStatus();
				ds.setElectric(Double.parseDouble(valArray[0]));
				ds.setExhaustTemp(Double.parseDouble(valArray[4]));
				ds.setInTempRang(Double.parseDouble(valArray[1]));
				ds.setOutTempRang(Double.parseDouble(valArray[2]));
				ds.setSetTempRang(Double.parseDouble(valArray[3]));
				ds.setShellTempDefend(StringUtils.compressorShellTempDefend(valArray[5]));
				ds.setWorkModel(valArray[6]);
				ds.setTime( Long.parseLong(valArray[7]));
				dStatusList.add(ds);
				tr.add(new TimePair(Long.parseLong(valArray[7]), valArray[8]));
				
			}
			
			//进行是制热还是制冷的判断
			List<Long> targetTime = new ArrayList<Long>();
			List<Long> baseTime = new ArrayList<Long>();
			if("01".equals(workModel)){
				List<FrostPair> fpList = new ArrayList<FrostPair>();
				Iterator<FrostPair> iter = tf.iterator();
				 while(iter.hasNext()){
					 FrostPair frostPair = iter.next();
					 fpList.add(frostPair);
				 }
				 if(fpList.size()<=1){
					 return;
				 }
				 
				 for(int i=0;i<fpList.size()-1;i++){
					 FrostPair currentFrostPair = fpList.get(i);
					 FrostPair nextFrostPair = fpList.get(i+1);
				
					 /**
					  * 如果前一次是关机状态，后一次是开机状态，
					  * 那么从后一次开始遍历有没开机时间达到30分钟，或者4小时
					  */
					 if(((currentFrostPair.getIsRun().equals("n"))&&
							 nextFrostPair.getIsRun().equals("y")) ){
						 System.out.println("制热时开机时间点 i=   ("+i+")：  time = "+DateUtils.longConvert2DateString(nextFrostPair.getTimeValue(), TIME_FORMAT));												 
						 //判断是否开机半小时
						 boolean isHalf = ToolsOther.isRunningHalfHours(i, fpList);
						 //如果不超过半小时，该条数据过滤掉
						 if(!isHalf){
							 continue;
						 }
						//判断是否开机4小时
						 boolean isFourHour = ToolsOther.isRunningFourHours(i,fpList);
						 List<Integer> inList = ToolsOther.getRemoveFrost(i,fpList);
						 if(isFourHour){
							 //如果没检测到除霜标志位
							 if(inList.size()==0){
								 int cnt =0;
								 for(int k=i+6;k<fpList.size()-1;k++){
									 if(fpList.get(k).getIsRun().equals("y")){
										 targetTime.add(fpList.get(k).getTimeValue());
//										 if(cnt<=3){
//											 baseTime.add(fpList.get(k).getTimeValue());
//										 }
//										 cnt++;
									 }
								 }
							 }else{
								 for(int m=0;m<inList.size()-1;m++){
									 int p =inList.get(m)+5;
									 if(p<fpList.size()-1){
										 if(fpList.get(p).getIsRun().equals("y")){
											 targetTime.add(fpList.get(p).getTimeValue());
										 }
									 }
								 }
								 //baseTime.add(targetTime.get(0));
							 }
						 }else{
							 //开机时间不满4小时，且无除霜标志位
							 if(inList.size()==0){
								 for(int n=i+6;n<fpList.size()-1;n++){
									 if(fpList.get(n).getIsRun().equals("y")){
										 targetTime.add(fpList.get(n).getTimeValue());
									 }
								 }
								 
							 }else{
								 for(int s=0;s<inList.size()-1;s++){
									 int q = inList.get(s)+5;
									 if(q<fpList.size()-1&&(fpList.get(q).getIsRun().equals("y"))){
										targetTime.add(fpList.get(q).getTimeValue());
									 }
								 }
							 }
						 }
					 }
				 }
				
			}else{
				//制冷开机时间点
				List<TimePair> trList = new ArrayList<TimePair>();
				Iterator<TimePair> iter = tr.iterator();
				 while(iter.hasNext()){
					 TimePair timePair = iter.next();
					 trList.add(timePair);
				 }
				 if(trList.size()<=1){
					 return;
				 }
				boolean isBase = false;
				for(int i=0;i<trList.size()-1;i++){
					TimePair currentTimePair = trList.get(i);
					TimePair nextPair = trList.get(i+1);
					//如果前一个是关机状态，下一个是开机状态，
					//遍历连续7个采样周期是否都是开机状态
					
					if(currentTimePair.getStatus().equals("n")&&
							nextPair.getStatus().equals("y")){
						System.out.println("制冷时开机时间点：  moduleNO = "+key.toString());
						//System.out.println("制冷时开机时间点：  timeCurrent = "+DateUtils.longConvert2DateString(currentTimePair.getTimeValue(), TIME_FORMAT));
						System.out.println("制冷时开机时间点：  timeNext = "+DateUtils.longConvert2DateString(nextPair.getTimeValue(), TIME_FORMAT));
						if(i+6<(trList.size()-1)){
							int cnt = 0;
							for(int k=i+1;k<i+6;k++){
								if(trList.get(k).getStatus().equals("n")){
									cnt++;
									break;
								}
							}
							if(cnt==0){
								if(trList.get(i+7).getStatus().equals("y")){
									//targetTime.add(trList.get(i+7).getTimeValue());
									isBase = true;
									continue;
								}
								
							}
						}
					}
					//如果前一个是
					if(isBase){
						if(currentTimePair.getStatus().equals("y")&&
								nextPair.getStatus().equals("y")){
							if((i+6<trList.size()-1)&&trList.get(i+6).getStatus().equals("y")){
								targetTime.add(trList.get(i+6).getTimeValue());
							}
						}
					}
					
					//如果前一个是开机状态，后一周期为关机状态，那么过滤掉
					if(currentTimePair.getStatus().equals("y")&&
							nextPair.getStatus().equals("n")){
						continue;
					}
					//如果连续两个周期都是关机状态，那么过滤掉
					if(currentTimePair.getStatus().equals("n")&&
							nextPair.getStatus().equals("n")){
						continue;
					}
				}
			}
			
			//如果没有目标采集数据直接返回
			if(targetTime.size()==0){
				return;
			}
		
			//遍历采集的数据
			for(DeviceStatus d:dStatusList){
				Long _time = d.getTime();
				//计算基准
				if(targetTime.contains(_time)){
					electric = electric + d.getElectric();
					inTempRang = inTempRang+ d.getInTempRang();
					outTempRang = outTempRang + d.getOutTempRang();
					setTempRang= setTempRang+d.getSetTempRang();
					exhaustTemp = exhaustTemp + d.getExhaustTemp();
					count++;
					ts.add(new TemperaturePair(_time,inTempRang));
				}
			}
			double inTempRangAvg = Math.round(inTempRang / count);
			SimpleDateFormat sf = new SimpleDateFormat(TIME_FORMAT);
			//系统当前时间
	    	Date d = new Date();
	    	String s = sf.format(d);
	    	Long ls = DateUtils.stringDateConvert2long(s,TIME_FORMAT);
			context.write(key, new Text(String.valueOf(ls) + ";"+inTempRangAvg+";"+workModel+"\r\n"));
		}
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		Configuration conf = new Configuration();
		String[] otherArgs = new GenericOptionsParser(conf, args)
				.getRemainingArgs();
		if (otherArgs.length != 2) {
			System.err.println("Usage DifferentialAnalysis <int> <out>");
			System.exit(2);
		}
		Job job = new Job(conf, "DifferentialAnalysis");
		job.setJarByClass(DifferentialAnalysis.class);
		job.setMapperClass(Map.class);
		job.setReducerClass(Reduce.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		FileInputFormat.addInputPath(job, new Path(otherArgs[0]));
		FileOutputFormat.setOutputPath(job, new Path(otherArgs[1]));
		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
	
	
	public void run() throws Exception {
		Configuration conf = new Configuration();

		String inputPath = "hdfs://192.168.7.56:9000/user/hadoop/input3";
		String outputPath = "hdfs://192.168.7.56:9000/user/hadoop/output3";
		HdfsUtils.checkAndDel(outputPath, conf);
		String[] otherArgs = { inputPath, outputPath };
		if (otherArgs.length != 2) {
			System.err.println("Usage DifferentialAnalysis <int> <out>");
			System.exit(2);
		}
		Job job = new Job(conf, "DifferentialAnalysis");
		job.setJarByClass(DifferentialAnalysis.class);
		job.setMapperClass(Map.class);
		// job.setCombinerClass(Reduce.class);
		job.setReducerClass(Reduce.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		FileInputFormat.addInputPath(job, new Path(otherArgs[0]));
		FileOutputFormat.setOutputPath(job, new Path(otherArgs[1]));
		boolean i = job.waitForCompletion(true);
		if (i) {
			
				//logger.debug("start to get file to local   ");
				System.out.println("start to get file to local   ");
				boolean isGet = HdfsUtils.getFromHDFS(outputPath + "/part-r-00000", plotResultPath,conf);
				System.out.println("isGet   is   "+isGet);
				//logger.debug("isGet   is   "+isGet);
				//下载 完成后，解析结果文件，将结果存入mysql 数据库中
				String dst = "hdfs://192.168.0.234:9000/user/hadoop/input2";
				if(isGet){
					File resultFile = new File( plotResultPath+File.separator+"part-r-00000"); 
					SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
			        String str = sdf.format(new Date()).replace("-", "").replace(" ", "").replace(":", "");
			        File newFile =  new File(resultFile.getAbsoluteFile()+"_"+str);
			        if(resultFile.renameTo(newFile)){
			        	HdfsUtils.put2HDFS(newFile.getAbsolutePath(), dst, conf);
			        }
					//AirconStatusInfoDao dao = (AirconStatusInfoDao) DAOFactory.getAirconCtrlInstance();
				}
			}
		
	}
	
}
