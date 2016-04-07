package com.topeastic.hadoop.utils;

import org.junit.Test;

import com.topeastic.bean.AirconStatus;

/**
 * @deprecated 对log日志文件进行中文解读
 * @deprecated 共62个字节
 * @author root
 * 
 */
public class LogUtils {

	/**
	 * @deprecated 第一个字节 获取当前风速 及 是否自动 hexArray[0]
	 * @return
	 */
	public static String getWindStatus(String hexArray) {
		String temp = hexToBinaryString(hexArray);
		if (null != temp) {
			String value = temp.substring(0, 7);
			String wind = temp.substring(7);
			if ("0".equals(wind)) {
				wind = "手动风速|";
			} else {
				wind = "自动风速|";
			}
			if ("0000000".equals(value)) {
				return "风机停;" + wind;
			}
			if ("0000001".equals(value)) {
				return "静音风速;" + wind;
			}
			if ("0000010".equals(value)) {
				return "低风风速;" + wind;
			}
			if ("0000011".equals(value)) {
				return "中风风速;" + wind;
			}
			if ("0000100".equals(value)) {
				return "高风风速;" + wind;
			} else {
				return (Integer.parseInt(value, 2) - 8) + "挡风;" + wind;
			}
		} else {
			return "null|";
		}

	}

	/**
	 * @deprecated 获取空调的睡眠模式状态 hexArray[1]
	 * @return
	 */
	public static String getSleepModel(String hexArray) {
		String temp = hexToBinaryString(hexArray);
		if (null != temp) {
			String value = temp.substring(0, 7);
			if ("0000000".equals(value)) {
				return "关睡眠模式|";
			} else {
				return Integer.parseInt(value, 2) + "#睡眠模式|";
			}
		} else {
			return "null|";
		}
	}

	/**
	 * @deprecated 工作模式与开停机控制 、风向开停控制 hexArray[2]
	 * @param hexArray
	 * @return
	 */
	public static String getWordmodel(String hexArray) {
		String temp = hexToBinaryString(hexArray);
		if (null != temp) {
			String workmodel = temp.substring(0, 4);
			String iswork = temp.substring(4, 5);
			if ("0000".equals(workmodel)) {
				workmodel = "送风;";
			}
			if ("0001".equals(workmodel)) {
				workmodel = "制热;";
			}
			if ("0010".equals(workmodel)) {
				workmodel = "制冷;";
			}
			if ("0011".equals(workmodel)) {
				workmodel = "除湿;";
			}
			if ("0100".equals(workmodel)) {
				workmodel = "自动下的送风;";
			}
			if ("0101".equals(workmodel)) {
				workmodel = "自动下的制热;";
			}
			if ("0110".equals(workmodel)) {
				workmodel = "自动下的制冷;";
			}
			if ("0111".equals(workmodel)) {
				workmodel = "自动下的除湿;";
			}
			if ("0".equals(iswork)) {
				iswork = "停机|";
			} else {
				iswork = "开机|";
			}
			return workmodel + iswork;
		} else {
			return "null|";
		}
	}

	/**
	 * @deprecated 室内温度设定值 hexArray[3]
	 * @param hexArray
	 * @return
	 */
	public static String getSetRoomTemperature(String hexArray) {
		if (null != hexArray) {
			return "室内温度设定值为：" + knowComplement(hexArray) + "|";
		} else {
			return "null|";
		}
	}

	/**
	 * @deprecated 室内温度实际值 hexArray[4]
	 * @param hexArray
	 * @return
	 */
	public static String getActualRoomTemperature(String hexArray) {
		if (null != hexArray) {
			return "室内温度实际值为：" + knowComplement(hexArray) + "|";
		} else {
			return "null|";
		}
	}

	/**
	 * @deprecated 室内盘管温度值 hexArray[5]
	 * @param hexArray
	 * @return
	 */
	public static String getRoomCoilTemperature(String hexArray) {
		if (null != hexArray) {
			return "室内盘管温度值为：" + knowComplement(hexArray) + "|";
		} else {
			return "null|";
		}
	}

	/**
	 * @deprecated 室内湿度设定值 hexArray[6]
	 * @param hexArray
	 * @return
	 */
	public static String getRoomHumidity(String hexArray) {
		if (null != hexArray) {
			if ("80".equals(hexArray)) {
				return "室内湿度设定值:无此项|";
			} else {
				return "室内湿度设定值为：" + knowTrueForm(hexArray) + "|";
			}
		} else {
			return null;
		}
	}

	/**
	 * @deprecated 室内湿度实际值 hexArray[7]
	 * @param hexArray
	 * @return
	 */
	public static String getRoomActualHumidity(String hexArray) {
		if (null != hexArray) {
			if ("80".equals(hexArray)) {
				return "室内湿度实际值:无此项|";
			} else {
				return "室内湿度实际值为：" + knowTrueForm(hexArray) + "|";
			}
		} else {
			return null;
		}
	}

	/**
	 * @deprecated 收到的体感室内温度 hexArray[8]
	 * @param hexArray
	 * @return
	 */
	public static String getReceiveRoomTemperature(String hexArray) {
		if (null != hexArray) {
			if ("80".equals(hexArray)) {
				return "收到的体感室内温度：无此项|";
			} else {
				return "IPhone等收到的体感室内温度："
						+ ((double) knowComplement(hexArray) / 2) + "|";
			}
		} else {
			return null;
		}
	}

	/**
	 * @deprecated 体感室内温度补偿 hexArray[9]
	 * @param hexArray
	 * @return
	 */
	public static String getRoomTemperatureCorrection(String hexArray) {
		String temp = hexToBinaryString(hexArray);
		if (null != temp) {
			String flag = temp.substring(0, 1);
			String value = temp.substring(1, 5);
			if ("0".equals(flag)) {
				if ("0000".equals(value)) {
					value = "无补偿;";
				} else {
					value = "补偿+" + ((double) Integer.parseInt(value, 2) / 2)
							+ "°C;";
				}
			} else {
				if ("0000".equals(value)) {
					value = "无补偿;";
				} else {
					value = "补偿-" + ((double) Integer.parseInt(value, 2) / 2)
							+ "°C;";
				}
			}
			flag = temp.substring(6, 7);
			if ("0".equals(flag)) {
				flag = "不用体感控制|";
			} else {
				flag = "用体感控制温度|";
			}
			return value + flag;
		} else {
			return "null|";
		}
	}

	/**
	 * @deprecated 自动模式和除湿模式的温度补偿 hexArray[10]
	 * @param hexArray
	 * @return
	 */
	public static String getTemperatureCorrection(String hexArray) {
		String temp = hexToBinaryString(hexArray);
		if (null != temp) {
			String flag = temp.substring(0, 1);
			String value = temp.substring(1, 4);
			if ("0".equals(flag)) {
				if ("000".equals(value)) {
					value = "无补偿;";
				} else {
					value = "补偿+" + Integer.parseInt(value, 2) + "°C;";
				}
			} else {
				if ("0000".equals(value)) {
					value = "无补偿;";
				} else {
					value = "补偿-" + Integer.parseInt(value, 2) + "°C;";
				}
			}
			flag = temp.substring(6, 7);
			if ("0".equals(flag)) {
				flag = "显示摄氏温度|";
			} else {
				flag = "显示华氏温度|";
			}
			return value + flag;
		} else {
			return "null|";
		}
	}

	/**
	 * @deprecated 普通定时开关机 hexArray[11]
	 * @param hexArray
	 * @return
	 */
	public static String getNormalTimerSwitch(String hexArray) {
		String temp = hexToBinaryString(hexArray);
		if (null != temp) {
			String flag = temp.substring(0, 1);
			String last_time = temp.substring(1, 7);
			if ("0".equals(flag)) {
				flag = "普通定时无效;";
			} else {
				flag = "普通定时有效;";
			}
			double time = (double) Integer.parseInt(last_time, 2) / 2;
			if (time == 0) {
				last_time = "时间到|";
			} else {
				last_time = "剩余" + time + "h|";
			}
			return flag + last_time;
		} else {
			return "null|";
		}
	}

	/**
	 * @deprecated 空调的实时时钟小时值 hexArray[12]
	 * @param hexArray
	 * @return
	 */
	public static String getTCLHours(String hexArray) {
		String temp = hexToBinaryString(hexArray);
		if (null != temp) {
			String value = temp.substring(0, 6);
			String flag = temp.substring(7);
			if ("0".equals(flag)) {
				flag = "无此功能|";
			} else {
				flag = "有此功能|";
			}
			return "当前空调的时钟小时值：" + Integer.parseInt(value, 2) + ";" + flag;
		} else {
			return "null|";
		}
	}

	/**
	 * @deprecated 空调的实时时钟分钟值 hexArray[13]
	 * @param hexArray
	 * @return
	 */
	public static String getTCLMin(String hexArray) {
		String temp = hexToBinaryString(hexArray);
		if (null != temp) {
			String value = temp.substring(0, 6);
			String flag = temp.substring(7);
			if ("0".equals(flag)) {
				flag = "无此功能|";
			} else {
				flag = "有此功能|";
			}
			return "当前空调的时钟分钟值：" + Integer.parseInt(value, 2) + ";" + flag;
		} else {
			return "null|";
		}
	}

	/**
	 * @deprecated 实时时钟的开机小时值 hexArray[14]
	 * @param hexArray
	 * @return
	 */
	public static String getStartingUpClockHours(String hexArray) {
		String temp = hexToBinaryString(hexArray);
		if (null != temp) {
			String value = temp.substring(0, 5);
			String control = temp.substring(6, 7);
			String flag = temp.substring(7);
			if ("0".equals(control)) {
				control = "实时开机无效;";
			} else {
				control = "实时开机有效;";
			}
			if ("0".equals(flag)) {
				flag = "无此功能|";
			} else {
				flag = "有此功能|";
			}
			return "实时开机的小时值：" + Integer.parseInt(value, 2) + ";" + control
					+ flag;
		} else {
			return "null|";
		}
	}

	/**
	 * @deprecated 实时时钟的开机分钟值 hexArray[15]
	 * @param hexArray
	 * @return
	 */
	public static String getStartingUpClockMin(String hexArray) {
		String temp = hexToBinaryString(hexArray);
		if (null != temp) {
			String value = temp.substring(0, 6);
			String flag = temp.substring(7);
			if ("0".equals(flag)) {
				flag = "无此功能|";
			} else {
				flag = "有此功能|";
			}
			return "实时开机的分钟值：" + Integer.parseInt(value, 2) + ";" + flag;
		} else {
			return "null|";
		}
	}

	/**
	 * @deprecated 实时时钟的关机小时值 hexArray[16]
	 * @param hexArray
	 * @return
	 */
	public static String getShutdownClockHours(String hexArray) {
		String temp = hexToBinaryString(hexArray);
		if (null != temp) {
			String value = temp.substring(0, 5);
			String control = temp.substring(6, 7);
			String flag = temp.substring(7);
			if ("0".equals(control)) {
				control = "实时关机无效;";
			} else {
				control = "实时关机有效;";
			}
			if ("0".equals(flag)) {
				flag = "无此功能|";
			} else {
				flag = "有此功能|";
			}
			return "实时关机的小时值：" + Integer.parseInt(value, 2) + ";" + control
					+ flag;
		} else {
			return "null|";
		}
	}

	/**
	 * @deprecated 实时时钟的关机分钟值 hexArray[17]
	 * @param hexArray
	 * @return
	 */
	public static String getShutdownClockMin(String hexArray) {
		String temp = hexToBinaryString(hexArray);
		if (null != temp) {
			String value = temp.substring(0, 6);
			String flag = temp.substring(7);
			if ("0".equals(flag)) {
				flag = "无此功能|";
			} else {
				flag = "有此功能|";
			}
			return "实时关机的分钟值：" + Integer.parseInt(value, 2) + ";" + flag;
		} else {
			return "null|";
		}
	}

	/**
	 * @deprecated 除湿模式和风扇位置 hexArray[18]
	 * @param hexArray
	 * @return
	 */
	public static String getWindDoor(String hexArray) {
		String temp = hexToBinaryString(hexArray);
		if (null != temp) {
			String model = temp.substring(0, 3);
			String windDoor = temp.substring(4, 7);
			if ("000".equals(model)) {
				model = "自动除湿;";
			} else {
				model = Integer.parseInt(model, 2) + "#除湿模式;";
			}
			if ("000".equals(windDoor)) {
				windDoor = "扫掠|";
			} else if ("001".equals(windDoor)) {
				windDoor = "自动|";
			} else {
				windDoor = (Integer.parseInt(windDoor, 2) - 1) + "#角度|";
			}
			return model + windDoor;
		} else {
			return "null|";
		}
	}

	/**
	 * @deprecated 室内状态一 hexArray[19]
	 * @param hexArray
	 * @return
	 */
	public static String getRoomStatus1(String hexArray) {
		String temp = hexToBinaryString(hexArray);
		if (null != temp) {
			String status1 = temp.substring(0, 1);
			String status2 = temp.substring(1, 2);
			String status3 = temp.substring(2, 3);
			String status4 = temp.substring(3, 4);
			String status5 = temp.substring(4, 5);
			String status6 = temp.substring(5, 6);
			String status7 = temp.substring(6, 7);
			String status8 = temp.substring(7);
			if ("0".equals(status1)) {
				status1 = "上下风停;";
			} else {
				status1 = "上下风开;";
			}
			if ("0".equals(status2)) {
				status2 = "左右风停;";
			} else {
				status2 = "左右风开;";
			}
			if ("0".equals(status3)) {
				status3 = "自然风停;";
			} else {
				status3 = "自然风开;";
			}
			if ("0".equals(status4)) {
				status4 = "电热停;";
			} else {
				status4 = "电热开;";
			}
			if ("0".equals(status5)) {
				status5 = "节能停;";
			} else {
				status5 = "节能开;";
			}
			if ("0".equals(status6)) {
				status6 = "并用节能停;";
			} else {
				status6 = "并用节能开;";
			}
			if ("0".equals(status7)) {
				status7 = "高效（强力）停;";
			} else {
				status7 = "高效（强力）开;";
			}
			if ("0".equals(status8)) {
				status8 = "变频运行|";
			} else {
				status8 = "定频运行|";
			}
			return status1 + status2 + status3 + status4 + status5 + status6
					+ status7 + status8;
		} else {
			return "null|";
		}
	}

	/**
	 * @deprecated 室内状态二 hexArray[20]
	 * @param hexArray
	 * @return
	 */
	public static String getRoomStatus2(String hexArray) {
		String temp = hexToBinaryString(hexArray);
		if (null != temp) {
			String status1 = temp.substring(0, 1);
			String status2 = temp.substring(1, 2);
			String status3 = temp.substring(2, 3);
			String status4 = temp.substring(3, 4);
			String status5 = temp.substring(4, 5);
			String status6 = temp.substring(5, 6);
			String status7 = temp.substring(6, 7);
			String status8 = temp.substring(7);
			if ("0".equals(status1)) {
				status1 = "清新停;";
			} else {
				status1 = "清新开;";
			}
			if ("0".equals(status2)) {
				status2 = "换风停;";
			} else {
				status2 = "换风开;";
			}
			if ("0".equals(status3)) {
				status3 = "室内清洁停;";
			} else {
				status3 = "室内清洁开;";
			}
			if ("0".equals(status4)) {
				status4 = "室外清洁停;";
			} else {
				status4 = "室外清洁开;";
			}
			if ("0".equals(status5)) {
				status5 = "智慧眼停;";
			} else {
				status5 = "智慧眼开;";
			}
			if ("0".equals(status6)) {
				status6 = "静音模式停;";
			} else {
				status6 = "静音模式开;";
			}
			if ("0".equals(status7)) {
				status7 = "语音控制停;";
			} else {
				status7 = "语音控制开;";
			}
			if ("0".equals(status8)) {
				status8 = "除烟停|";
			} else {
				status8 = "除烟开|";
			}
			return status1 + status2 + status3 + status4 + status5 + status6
					+ status7 + status8;
		} else {
			return "null|";
		}
	}

	/**
	 * @deprecated 室内状态三 hexArray[21]
	 * @param hexArray
	 * @return
	 */
	public static String getRoomStatus3(String hexArray) {
		String temp = hexToBinaryString(hexArray);
		if (null != temp) {
			String status1 = temp.substring(0, 1);
			String status2 = temp.substring(1, 2);
			String status3 = temp.substring(2, 3);
			String status4 = temp.substring(3, 4);
			String status5 = temp.substring(4, 5);
			String status6 = temp.substring(5, 6);
			String status7 = temp.substring(6, 7);
			String status8 = temp.substring(7);
			if ("0".equals(status1)) {
				status1 = "背景灯停;";
			} else {
				status1 = "背景灯开;";
			}
			if ("0".equals(status2)) {
				status2 = "显示屏发光显示停;";
			} else {
				status2 = "显示屏发光显示开;";
			}
			if ("0".equals(status3)) {
				status3 = "LED指示灯停;";
			} else {
				status3 = "LED指示灯开;";
			}
			if ("0".equals(status4)) {
				status4 = "正常显示室内温度;";
			} else {
				status4 = "正常显示室外温度;";
			}
			if ("0".equals(status5)) {
				status5 = "室内过滤网清洁正常;";
			} else {
				status5 = "室内过滤网需清洁;";
			}
			if ("0".equals(status6)) {
				status6 = "左风摆停;";
			} else {
				status6 = "左风摆开;";
			}
			if ("0".equals(status7)) {
				status7 = "右风摆停;";
			} else {
				status7 = "右风摆开;";
			}
			if ("0".equals(status8)) {
				status8 = "没有室内电量板|";
			} else {
				status8 = "有室内电量板|";
			}
			return status1 + status2 + status3 + status4 + status5 + status6
					+ status7 + status8;
		} else {
			return "null|";
		}
	}

	/**
	 * @deprecated 室内状态四 hexArray[22]
	 * @param hexArray
	 * @return
	 */
	public static String getRoomStatus4(String hexArray) {
		String temp = hexToBinaryString(hexArray);
		if (null != temp) {
			String status7 = temp.substring(6, 7);
			String status8 = temp.substring(7);
			if ("0".equals(status7)) {
				status7 = "正常机型;";
			} else {
				status7 = "测试机型;";
			}
			if ("0".equals(status8)) {
				status8 = "不能室内EEPROM在线升级|";
			} else {
				status8 = "能室内EEPROM在线升级|";
			}
			return status7 + status8;
		} else {
			return "null|";
		}
	}

	/**
	 * @deprecated 室内警告状态一 hexArray[23]
	 * @param hexArray
	 * @return
	 */
	public static String getRoomWarnStatus1(String hexArray) {
		String temp = hexToBinaryString(hexArray);
		if (null != temp) {
			String status1 = temp.substring(0, 1);
			String status2 = temp.substring(1, 2);
			String status3 = temp.substring(2, 3);
			String status4 = temp.substring(3, 4);
			String status5 = temp.substring(4, 5);
			String status6 = temp.substring(5, 6);
			String status7 = temp.substring(6, 7);
			String status8 = temp.substring(7);
			if ("0".equals(status1)) {
				status1 = "室内温度传感器正常;";
			} else {
				status1 = "室内温度传感器故障;";
			}
			if ("0".equals(status2)) {
				status2 = "室内盘管温度传感器正常;";
			} else {
				status2 = "室内盘管温度传感器故障;";
			}
			if ("0".equals(status3)) {
				status3 = "室内湿度传感器正常;";
			} else {
				status3 = "室内湿度传感器故障;";
			}
			if ("0".equals(status4)) {
				status4 = "室内排水泵正常;";
			} else {
				status4 = "室内排水泵故障;";
			}
			if ("0".equals(status5)) {
				status5 = "室内风机电机运转正常;";
			} else {
				status5 = "室内风机电机运转异常;";
			}
			if ("0".equals(status6)) {
				status6 = "柜机格栅保护正常;";
			} else {
				status6 = "柜机格栅保护告警;";
			}
			if ("0".equals(status7)) {
				status7 = "室内电压过零检测正常;";
			} else {
				status7 = "室内电压过零检测故障;";
			}
			if ("0".equals(status8)) {
				status8 = "室内外通信正常|";
			} else {
				status8 = "室内外通信故障|";
			}
			return status1 + status2 + status3 + status4 + status5 + status6
					+ status7 + status8;
		} else {
			return "null|";
		}
	}

	/**
	 * @deprecated 室内警告状态二 hexArray[24]
	 * @param hexArray
	 * @return
	 */
	public static String getRoomWarnStatus2(String hexArray) {
		String temp = hexToBinaryString(hexArray);
		if (null != temp) {
			String status1 = temp.substring(0, 1);
			String status2 = temp.substring(1, 2);
			String status3 = temp.substring(2, 3);
			String status4 = temp.substring(3, 4);
			String status5 = temp.substring(4, 5);
			if ("0".equals(status1)) {
				status1 = "室内控制板与显示板通信正常;";
			} else {
				status1 = "室内控制板与显示板通信故障;";
			}
			if ("0".equals(status2)) {
				status2 = "室内控制板与按键板通信正常;";
			} else {
				status2 = "室内控制板与按键板通信故障;";
			}
			if ("0".equals(status3)) {
				status3 = "WIFI控制板与室内控制板通信正常;";
			} else {
				status3 = "WIFI控制板与室内控制板通信故障;";
			}
			if ("0".equals(status4)) {
				status4 = "室内控制板与室内电量板通信正常;";
			} else {
				status4 = "室内控制板与室内电量板通信故障;";
			}
			if ("0".equals(status5)) {
				status5 = "室内控制板EEPROM正常|";
			} else {
				status5 = "室内控制板EEPROM异常|";
			}
			return status1 + status2 + status3 + status4 + status5;
		} else {
			return "null|";
		}
	}

	/**
	 * @deprecated 压缩机运行频率 hexArray[25]
	 * @param hexArray
	 * @return
	 */
	public static String getCompressorRunFrequency(String hexArray) {
		if (null != hexArray) {
			return "压缩机运行频:" + Integer.parseInt(hexArray, 16) + "Hz|";
		} else {
			return "null|";
		}
	}

	/**
	 * @deprecated 压缩机目标频率 hexArray[26]
	 * @param hexArray
	 * @return
	 */
	public static String getCompressorTargetFrequency(String hexArray) {
		if (null != hexArray) {
			return "压缩机目标频率:" + Integer.parseInt(hexArray, 16) + "Hz|";
		} else {
			return "null|";
		}
	}

	/**
	 * @deprecated 发给驱动器的频率 hexArray[27]
	 * @param hexArray
	 * @return
	 */
	public static String getSendToDriverFrequency(String hexArray) {
		if (null != hexArray) {
			return "发给驱动器的频率:" + Integer.parseInt(hexArray, 16) + "Hz|";
		} else {
			return "null|";
		}
	}

	/**
	 * @deprecated 室外环境温度 hexArray[28]
	 * @param hexArray
	 * @return
	 */
	public static String getOutsideTemperature(String hexArray) {
		if (null != hexArray) {
			return "室外环境温度 :" + knowComplement(hexArray) + "|";
		} else {
			return "null|";
		}
	}

	/**
	 * @deprecated 室外冷疑器温度 hexArray[29]
	 * @param hexArray
	 * @return
	 */
	public static String getOutsideColdMacTemperature(String hexArray) {
		if (null != hexArray) {
			return "室外冷疑器温度 :" + knowComplement(hexArray) + "|";
		} else {
			return "null|";
		}
	}

	/**
	 * @deprecated 压缩机排气温度 hexArray[30]
	 * @param hexArray
	 * @return
	 */
	public static String getCompressorExhaustTemperature(String hexArray) {
		if (null != hexArray) {
			return "压缩机排气温度为：" + knowTrueForm(hexArray) + "|";
		} else {
			return "null|";
		}
	}

	/**
	 * @deprecated 目标排气温度 hexArray[31]
	 * @param hexArray
	 * @return
	 */
	public static String getExhaustTemperature(String hexArray) {
		if (null != hexArray) {
			return "目标排气温度为：" + knowTrueForm(hexArray) + "|";
		} else {
			return "null|";
		}
	}

	/**
	 * @deprecated 室外膨胀阀开度 hexArray[32]
	 * @param hexArray
	 * @return
	 */
	public static String getOutsideExpansionValve(String hexArray) {
		if (null != hexArray) {
			return "室外膨胀阀开度为：" + (Integer.parseInt(hexArray, 16) * 2) + "|";
		} else {
			return "null|";
		}
	}

	public static String getUab(String hexArray){
		return "Uab电压为："+knowTrueForm(hexArray)+"|";
	}
	
	/**
	 * @deprecated 单相电源电压或三相电源的Uab电压高位
	 * @param hexArray
	 * @return
	 */
	public static String getUabcaHighVoltage(String hexArray) {
		if (null != hexArray) {
			if("80".equals(hexArray)){
				return "Uabca为单相电源高位|";
			}
			return "单相电源电压或三相电源的Uabca电压高位为：" + knowTrueForm(hexArray) + "|";
		} else {
			return "null|";
		}
	}

	/**
	 * @deprecated 单相电源电压或三相电源的Uab电压低位
	 * @param hexArray
	 * @return
	 */
	public static String getUabcaLowVoltage(String hexArray) {
		if (null != hexArray) {
			if("00".equals(hexArray)){
				return "Uabca为单相电源低位|";
			}
			return "单相电源电压或三相电源的Uabca电压低位为：" + knowTrueForm(hexArray) + "|";
		} else {
			return "null|";
		}
	}

	/**
	 * @deprecated 单相电源电流或三相电源的Iab电流 hexArray[39]
	 * @param hexArray
	 * @return
	 */
	public static String getIabElectric(String hexArray) {
		if (null != hexArray) {
			return "单相电源电流或三相电源的Iab电流:" + ((double)knowTrueForm(hexArray) / 5) + "|";
		} else {
			return "null|";
		}
	}
	
	/**
	 * @deprecated 单相电源电流或三相电源的Iab电流 hexArray[39]
	 * @param hexArray
	 * @return
	 */
	public static String getIbcaElectric(String hexArray) {
		if (null != hexArray) {
			if("00".equals(hexArray)){
				return "单相电源电流|";
			}
			return "单相电源电流或三相电源的Ibca电流:" + ((double)knowTrueForm(hexArray) / 5) + "|";
		} else {
			return "null|";
		}
	}
	

	/**
	 * @deprecated 直流母线电压 hexArray[42] hexArray[43]
	 * @param hexArray
	 * @return
	 */
	public static String getBusbarVoltage(String hexArray) {
		if (null != hexArray) {
			return "直流母线电压:" + knowTrueForm(hexArray) + "|";
		} else {
			return "null|";
		}
	}

	/**
	 * @deprecated 室外机实际工作状态和室外风机运行状态 hexArray[45]
	 * @param hexArray
	 * @return
	 */
	public static String getInAndOutStatus(String hexArray) {
		String temp = hexToBinaryString(hexArray);
		if (null != temp) {
			String status1 = temp.substring(3, 4);
			String status2 = temp.substring(4, 5);
			String status3 = temp.substring(5, 8);
			if ("0".equals(status1)) {
				status1 = "四通阀状态:停;";
			} else {
				status1 = "四通阀状态:开;";
			}
			if ("0".equals(status2)) {
				status2 = "室外机制冷运行;";
			} else {
				status2 = "室外机制热运行;";
			}
			if ("000".equals(status3)) {
				status3 = "风机停|";
			} else if ("001".equals(status3)) {
				status3 = "风机微风|";
			} else if ("010".equals(status3)) {
				status3 = "风机低风|";
			} else if ("011".equals(status3)) {
				status3 = "风机中风|";
			} else if ("100".equals(status3)) {
				status3 = "风机高风|";
			} else {
				status3 = "风机间歇低风|";
			}

			return status1 + status2 + status3;
		} else {
			return "null|";
		}
	}

	/**
	 * @deprecated 室外工作状态1 hexArray[46]
	 * @param hexArray
	 * @return
	 */
	public static String getOutsideStatus1(String hexArray) {
		String temp = hexToBinaryString(hexArray);
		if (null != temp) {
			String status1 = temp.substring(0, 1);
			String status2 = temp.substring(1, 2);
			String status3 = temp.substring(2, 3);
			String status4 = temp.substring(3, 4);
			String status5 = temp.substring(4, 5);
			String status6 = temp.substring(5, 6);
			String status7 = temp.substring(6, 7);
			String status8 = temp.substring(7);
			if ("0".equals(status1)) {
				status1 = "室外机强制室内机风门位置正常;";
			} else {
				status1 = "室外机强制室内机风门位置强制;";
			}
			if ("0".equals(status2)) {
				status2 = "室外机强制室内机风速正常;";
			} else {
				status2 = "室外机强制室内机风速强制;";
			}
			if ("0".equals(status3)) {
				status3 = "室外机强制室内机停正常;";
			} else {
				status3 = "室外机强制室内机停强制停;";
			}
			if ("0".equals(status4)) {
				status4 = "温控关机关;";
			} else {
				status4 = "温控关机开;";
			}
			if ("0".equals(status5)) {
				status5 = "一拖一;";
			} else {
				status5 = "一拖多;";
			}
			if ("0".equals(status6)) {
				status6 = "除湿阀关;";
			} else {
				status6 = "除湿阀开;";
			}
			if ("0".equals(status7)) {
				status7 = "室外机化霜正常;";
			} else {
				status7 = "室外机化霜化霜;";
			}
			if ("0".equals(status8)) {
				status8 = "室外旁通化霜正常|";
			} else {
				status8 = "室外旁通化霜化霜|";
			}
			return status1 + status2 + status3 + status4 + status5 + status6
					+ status7 + status8;
		} else {
			return "null|";
		}
	}

	/**
	 * @deprecated 室外工作状态2 hexArray[47]
	 * @param hexArray
	 * @return
	 */
	public static String getOutsideStatus2(String hexArray) {
		String temp = hexToBinaryString(hexArray);
		if (null != temp) {
			String status1 = temp.substring(0, 1);
			String status2 = temp.substring(1, 2);
			String status3 = temp.substring(2, 3);
			String status4 = temp.substring(3, 4);
			String status5 = temp.substring(4, 5);
			String status6 = temp.substring(5, 6);
			String status7 = temp.substring(6, 7);
			if ("0".equals(status1)) {
				status1 = "回油标志正常;";
			} else {
				status1 = "回油标志回油;";
			}
			if ("0".equals(status2)) {
				status2 = "室外故障显示标志显示;";
			} else {
				status2 = "室外故障显示标志不显示;";
			}
			if ("0".equals(status3)) {
				status3 = "室外机EEPROM在线下载标志不允许;";
			} else {
				status3 = "室外机EEPROM在线下载标志允许;";
			}
			if ("0".equals(status4)) {
				status4 = "室外机电量板没有;";
			} else {
				status4 = "室外机电量板有;";
			}
			if ("0".equals(status5)) {
				status5 = "压缩机电热带停;";
			} else {
				status5 = "压缩机电热带开;";
			}
			if ("0".equals(status6)) {
				status6 = "压缩机预加热正常;";
			} else {
				status6 = "压缩机预加热预加热;";
			}
			if ("0".equals(status7)) {
				status7 = "补气增晗停|";
			} else {
				status7 = "补气增晗开|";
			}
			return status1 + status2 + status3 + status4 + status5 + status6
					+ status7;
		} else {
			return "null|";
		}
	}

	/**
	 * @deprecated 室外告警状态1 hexArray[48]
	 * @param hexArray
	 * @return
	 */
	public static String getOutsideWarnStatus1(String hexArray) {
		String temp = hexToBinaryString(hexArray);
		if (null != temp) {
			String status1 = temp.substring(0, 1);
			String status2 = temp.substring(1, 2);
			String status3 = temp.substring(2, 3);
			String status4 = temp.substring(3, 4);
			String status5 = temp.substring(4, 5);
			String status6 = temp.substring(5, 6);
			String status7 = temp.substring(6, 7);
			String status8 = temp.substring(7);
			if ("0".equals(status1)) {
				status1 = "室内外机模式正常;";
			} else {
				status1 = "室内外机模式冲突;";
			}
			if ("0".equals(status2)) {
				status2 = "室外EEPROM正常;";
			} else {
				status2 = "室外EEPROM出错;";
			}
			if ("0".equals(status3)) {
				status3 = "室外盘管温度传感器正常;";
			} else {
				status3 = "室外盘管温度传感器故障;";
			}
			if ("0".equals(status4)) {
				status4 = "排气温度传感器正常;";
			} else {
				status4 = "排气温度传感器故障;";
			}
			if ("0".equals(status5)) {
				status5 = "室外环境温度传感器正常;";
			} else {
				status5 = "室外环境温度传感器故障;";
			}
			if ("0".equals(status6)) {
				status6 = "电压变压器正常;";
			} else {
				status6 = "电压变压器故障;";
			}
			if ("0".equals(status7)) {
				status7 = "电流互感器正常;";
			} else {
				status7 = "电流互感器故障;";
			}
			if ("0".equals(status8)) {
				status8 = "室外控制与驱动通信正常|";
			} else {
				status8 = "室外控制与驱动通信故障|";
			}
			return status1 + status2 + status3 + status4 + status5 + status6
					+ status7 + status8;
		} else {
			return "null|";
		}
	}

	/**
	 * @deprecated 室外告警状态2 hexArray[49]
	 * @param hexArray
	 * @return
	 */
	public static String getOutsideWarnStatus2(String hexArray) {
		String temp = hexToBinaryString(hexArray);
		if (null != temp) {
			String status1 = temp.substring(0, 1);
			String status2 = temp.substring(1, 2);
			String status3 = temp.substring(2, 3);
			String status4 = temp.substring(3, 4);
			String status5 = temp.substring(4, 5);
			String status6 = temp.substring(5, 6);
			String status7 = temp.substring(6, 7);
			String status8 = temp.substring(7);
			if ("0".equals(status1)) {
				status1 = "IPM模块过流保护正常;";
			} else {
				status1 = "IPM模块过流;";
			}
			if ("0".equals(status2)) {
				status2 = "IPM模块过热保护;";
			} else {
				status2 = "IPM模块过热;";
			}
			if ("0".equals(status3)) {
				status3 = "交流电过电压保护;";
			} else {
				status3 = "交流电过电压;";
			}
			if ("0".equals(status4)) {
				status4 = "交流电欠电压保护;";
			} else {
				status4 = "交流电欠电压;";
			}
			if ("0".equals(status5)) {
				status5 = "母线电压过电压保护;";
			} else {
				status5 = "母线电压过电压;";
			}
			if ("0".equals(status6)) {
				status6 = "母线电压欠电压保护;";
			} else {
				status6 = "母线电压欠电压;";
			}
			if ("0".equals(status7)) {
				status7 = "PFC过电流保护;";
			} else {
				status7 = "PFC过电流;";
			}
			if ("0".equals(status8)) {
				status8 = "室外机最大电流保护正常|";
			} else {
				status8 = "室外机最大电流保护|";
			}
			return status1 + status2 + status3 + status4 + status5 + status6
					+ status7 + status8;
		} else {
			return "null|";
		}
	}

	/**
	 * @deprecated 室外告警状态3 hexArray[50]
	 * @param hexArray
	 * @return
	 */
	public static String getOutsideWarnStatus3(String hexArray) {
		String temp = hexToBinaryString(hexArray);
		if (null != temp) {
			String status1 = temp.substring(0, 1);
			String status2 = temp.substring(1, 2);
			String status3 = temp.substring(2, 3);
			String status4 = temp.substring(3, 4);
			String status5 = temp.substring(4, 5);
			String status6 = temp.substring(5, 6);
			String status7 = temp.substring(6, 7);
			String status8 = temp.substring(7);
			if ("0".equals(status1)) {
				status1 = "室外环境温度过低保护正常;";
			} else {
				status1 = "室外环境温度过低保护;";
			}
			if ("0".equals(status2)) {
				status2 = "排气温度过高保护正常;";
			} else {
				status2 = "排气温度过高保护;";
			}
			if ("0".equals(status3)) {
				status3 = "压缩机管壳温度保护正常;";
			} else {
				status3 = "压缩机管壳温度保护;";
			}
			if ("0".equals(status4)) {
				status4 = "室内防冻结或防过载保护正常;";
			} else {
				status4 = "室内防冻结或防过载保护;";
			}
			if ("0".equals(status5)) {
				status5 = "室外机PFC保护正常;";
			} else {
				status5 = "室外机PFC保护;";
			}
			if ("0".equals(status6)) {
				status6 = "压缩机启动正常;";
			} else {
				status6 = "压缩机启动失败;";
			}
			if ("0".equals(status7)) {
				status7 = "压缩机正常;";
			} else {
				status7 = "压缩机失步;";
			}
			if ("0".equals(status8)) {
				status8 = "室外风机正常|";
			} else {
				status8 = "室外风机堵转|";
			}
			return status1 + status2 + status3 + status4 + status5 + status6
					+ status7 + status8;
		} else {
			return "null|";
		}
	}

	/**
	 * @deprecated 室外告警状态4 hexArray[51]
	 * @param hexArray
	 * @return
	 */
	public static String getOutsideWarnStatus4(String hexArray) {
		String temp = hexToBinaryString(hexArray);
		if (null != temp) {
			String status1 = temp.substring(0, 1);
			String status2 = temp.substring(1, 2);
			String status3 = temp.substring(2, 3);
			String status4 = temp.substring(3, 4);
			String status5 = temp.substring(4, 5);
			String status6 = temp.substring(5, 6);
			if ("0".equals(status1)) {
				status1 = "室外盘管防正常;";
			} else {
				status1 = "室外盘管防过载保护;";
			}
			if ("0".equals(status2)) {
				status2 = "冷媒正常;";
			} else {
				status2 = "冷媒泄漏;";
			}
			if ("0".equals(status3)) {
				status3 = "压缩机型号匹配正常;";
			} else {
				status3 = "压缩机型号匹配错误;";
			}
			if ("0".equals(status4)) {
				status4 = "系统低频振动正常;";
			} else {
				status4 = "系统低频振动保护;";
			}
			if ("0".equals(status5)) {
				status5 = "室外散热器温度正常;";
			} else {
				status5 = "室外散热器温度过高保护;";
			}
			if ("0".equals(status6)) {
				status6 = "系统压力正常;";
			} else {
				status6 = "系统压力过高保护;";
			}
			return status1 + status2 + status3 + status4 + status5 + status6;
		} else {
			return "null|";
		}
	}

	/**
	 * @deprecated 室外告警状态5 hexArray[52]
	 * @param hexArray
	 * @return
	 */
	public static String getOutsideWarnStatus5(String hexArray) {
		String temp = hexToBinaryString(hexArray);
		if (null != temp) {
			String status1 = temp.substring(0, 1);
			String status2 = temp.substring(1, 2);
			String status3 = temp.substring(2, 3);
			String status4 = temp.substring(3, 4);
			String status5 = temp.substring(4, 5);
			String status6 = temp.substring(5, 6);
			String status7 = temp.substring(6, 7);
			String status8 = temp.substring(7);
			if ("0".equals(status1)) {
				status1 = "逆变器直流过电压正常;";
			} else {
				status1 = "逆变器直流过电压故障;";
			}
			if ("0".equals(status2)) {
				status2 = "逆变器直流低电压正常;";
			} else {
				status2 = "逆变器直流低电压故障;";
			}
			if ("0".equals(status3)) {
				status3 = "逆变器交流过电流正常;";
			} else {
				status3 = "逆变器交流过电流故障;";
			}
			if ("0".equals(status4)) {
				status4 = "失步正常;";
			} else {
				status4 = "失步检出;";
			}
			if ("0".equals(status5)) {
				status5 = "速度推定脉冲检出法检出欠相正常;";
			} else {
				status5 = "速度推定脉冲检出法检出欠相故障;";
			}
			if ("0".equals(status6)) {
				status6 = "电流推定脉冲检出法检出欠相正常;";
			} else {
				status6 = "电流推定脉冲检出法检出欠相故障;";
			}
			if ("0".equals(status7)) {
				status7 = "逆变器IPM边沿正常;";
			} else {
				status7 = "逆变器IPM边沿故障;";
			}
			if ("0".equals(status8)) {
				status8 = "逆变器IPM电平正常|";
			} else {
				status8 = "逆变器IPM电平故障|";
			}
			return status1 + status2 + status3 + status4 + status5 + status6
					+ status7 + status8;
		} else {
			return "null|";
		}
	}

	/**
	 * @deprecated 室外告警状态6 hexArray[53]
	 * @param hexArray
	 * @return
	 */
	public static String getOutsideWarnStatus6(String hexArray) {
		String temp = hexToBinaryString(hexArray);
		if (null != temp) {
			String status1 = temp.substring(0, 1);
			String status2 = temp.substring(1, 2);
			String status3 = temp.substring(2, 3);
			String status4 = temp.substring(3, 4);
			String status5 = temp.substring(4, 5);
			String status6 = temp.substring(5, 6);
			String status7 = temp.substring(6, 7);
			String status8 = temp.substring(7);
			if ("0".equals(status1)) {
				status1 = "逆变器IPM边沿正常;";
			} else {
				status1 = "逆变器IPM边沿故障;";
			}
			if ("0".equals(status2)) {
				status2 = "逆变器IPM电平正常;";
			} else {
				status2 = "逆变器IPM电平故障;";
			}
			if ("0".equals(status3)) {
				status3 = "PFC停电检出正常;";
			} else {
				status3 = "PFC停电检出故障;";
			}
			if ("0".equals(status4)) {
				status4 = "PFC过电流检出正常;";
			} else {
				status4 = "PFC过电流检出故障;";
			}
			if ("0".equals(status5)) {
				status5 = "直流电压检出正常;";
			} else {
				status5 = "直流电压检出异常;";
			}
			if ("0".equals(status6)) {
				status6 = "PFC低电压（有效值）检出正常;";
			} else {
				status6 = "PFC低电压（有效值）检出故障;";
			}
			if ("0".equals(status7)) {
				status7 = "AD偏置异常检出正常;";
			} else {
				status7 = "AD偏置异常检出故障;";
			}
			if ("0".equals(status8)) {
				status8 = "逆变器PWM逻辑设置正常|";
			} else {
				status8 = "逆变器PWM逻辑设置故障|";
			}
			return status1 + status2 + status3 + status4 + status5 + status6
					+ status7 + status8;
		} else {
			return "null|";
		}
	}

	/**
	 * @deprecated 室外告警状态7 hexArray[54]
	 * @param hexArray
	 * @return
	 */
	public static String getOutsideWarnStatus7(String hexArray) {
		String temp = hexToBinaryString(hexArray);
		if (null != temp) {
			String status1 = temp.substring(0, 1);
			String status2 = temp.substring(1, 2);
			String status3 = temp.substring(2, 3);
			String status4 = temp.substring(3, 4);
			String status5 = temp.substring(4, 5);
			String status6 = temp.substring(5, 6);
			String status7 = temp.substring(6, 7);
			String status8 = temp.substring(7);
			if ("0".equals(status1)) {
				status1 = "逆变器PWM初始化正常;";
			} else {
				status1 = "逆变器PWM初始化故障;";
			}
			if ("0".equals(status2)) {
				status2 = "PFC_PWM逻辑设置正常;";
			} else {
				status2 = "PFC_PWM逻辑设置故障;";
			}
			if ("0".equals(status3)) {
				status3 = "PFC_PWM初始化正常;";
			} else {
				status3 = "PFC_PWM初始化故障;";
			}
			if ("0".equals(status4)) {
				status4 = "温度正常;";
			} else {
				status4 = "温度异常;";
			}
			if ("0".equals(status5)) {
				status5 = "电流采样电阻不平衡调整正常;";
			} else {
				status5 = "电流采样电阻不平衡调整故障;";
			}
			if ("0".equals(status6)) {
				status6 = "电机参数设置正常;";
			} else {
				status6 = "电机参数设置故障;";
			}
			if ("0".equals(status7)) {
				status7 = "MCE正常;";
			} else {
				status7 = "MCE故障;";
			}
			if ("0".equals(status8)) {
				status8 = "驱动EEPROM正常|";
			} else {
				status8 = "驱动EEPROM故障|";
			}
			return status1 + status2 + status3 + status4 + status5 + status6
					+ status7 + status8;
		} else {
			return "null|";
		}
	}

	/**
	 * @deprecated 室外告警状态8 hexArray[55]
	 * @param hexArray
	 * @return
	 */
	public static String getOutsideWarnStatus8(String hexArray) {
		String temp = hexToBinaryString(hexArray);
		if (null != temp) {
			String status1 = temp.substring(0, 1);
			String status2 = temp.substring(1, 2);
			String status3 = temp.substring(2, 3);
			String status4 = temp.substring(3, 4);
			String status5 = temp.substring(4, 5);
			String status6 = temp.substring(5, 6);
			String status7 = temp.substring(6, 7);
			String status8 = temp.substring(7);
			if ("0".equals(status1)) {
				status1 = "室外盘管过载正常;";
			} else {
				status1 = "室外盘管过载禁升频;";
			}
			if ("0".equals(status2)) {
				status2 = "室外盘管过载正常;";
			} else {
				status2 = "室外盘管过载降频;";
			}
			if ("0".equals(status3)) {
				status3 = "室内盘管过载正常;";
			} else {
				status3 = "室内盘管过载禁升频;";
			}
			if ("0".equals(status4)) {
				status4 = "室内盘管过载正常;";
			} else {
				status4 = "室内盘管过载降频;";
			}
			if ("0".equals(status5)) {
				status5 = "压降排气过载正常;";
			} else {
				status5 = "压降排气过载禁升频;";
			}
			if ("0".equals(status6)) {
				status6 = "压降排气过载正常;";
			} else {
				status6 = "压降排气过载降频;";
			}
			if ("0".equals(status7)) {
				status7 = "室内盘管冻结正常;";
			} else {
				status7 = "室内盘管冻结禁升频;";
			}
			if ("0".equals(status8)) {
				status8 = "室内盘管冻结正常|";
			} else {
				status8 = "室内盘管冻结降频|";
			}
			return status1 + status2 + status3 + status4 + status5 + status6
					+ status7 + status8;
		} else {
			return "null|";
		}
	}

	/**
	 * @deprecated 室外告警状态9 hexArray[56]
	 * @param hexArray
	 * @return
	 */
	public static String getOutsideWarnStatus9(String hexArray) {
		String temp = hexToBinaryString(hexArray);
		if (null != temp) {
			String status1 = temp.substring(0, 1);
			String status2 = temp.substring(1, 2);
			String status3 = temp.substring(2, 3);
			String status4 = temp.substring(3, 4);
			String status5 = temp.substring(4, 5);
			String status6 = temp.substring(5, 6);
			String status7 = temp.substring(6, 7);
			String status8 = temp.substring(7);
			if ("0".equals(status1)) {
				status1 = "室内外通信正常;";
			} else {
				status1 = "室内外通信检降频;";
			}
			if ("0".equals(status2)) {
				status2 = "模块温度过载正常;";
			} else {
				status2 = "模块温度过载限频;";
			}
			if ("0".equals(status3)) {
				status3 = "变调率正常;";
			} else {
				status3 = "变调率限频;";
			}
			if ("0".equals(status4)) {
				status4 = "相电流正常;";
			} else {
				status4 = "相电流限频;";
			}
			if ("0".equals(status5)) {
				status5 = "并用节电保护正常;";
			} else {
				status5 = "并用节电保护禁升频;";
			}
			if ("0".equals(status6)) {
				status6 = "并用节电保护正常;";
			} else {
				status6 = "并用节电保护降频;";
			}
			if ("0".equals(status7)) {
				status7 = "过电流保护正常;";
			} else {
				status7 = "过电流保护禁升频;";
			}
			if ("0".equals(status8)) {
				status8 = "过电流保护正常|";
			} else {
				status8 = "过电流保护降频|";
			}
			return status1 + status2 + status3 + status4 + status5 + status6
					+ status7 + status8;
		} else {
			return "null|";
		}
	}

	/**
	 * @deprecated 室内风机转速数字表示 hexArray[57]
	 * @param hexArray
	 * @return
	 */
	public static String getRoomFanSpeed(String hexArray) {
		if (null != hexArray) {
			return "室内风机转速" + (Integer.parseInt(hexArray, 16) * 10) + "|";
		} else {
			return "null|";
		}
	}

	/**
	 * @deprecated 室外风机转速数字表示 hexArray[58]
	 * @param hexArray
	 * @return
	 */
	public static String getOutsideFanSpeed(String hexArray) {
		if (null != hexArray) {
			return "室外风机转速" + (Integer.parseInt(hexArray, 16) * 10) + "|";
		} else {
			return "null|";
		}
	}

	/**
	 * @deprecated 空气PM2.5质量等级表示 hexArray[59]
	 * @param hexArray
	 * @return
	 */
	public static String getAirLevel(String hexArray) {
		String temp = hexToBinaryString(hexArray);
		if (null != temp) {
			String flag = temp.substring(0, 7);
			String level = temp.substring(4, 8);
			if ("0".equals(flag)) {
				flag = "没有PM2.5检测功能;";
			} else {
				flag = "有PM2.5检测功能;";
			}
			if ("0000".equals(level)) {
				level = "PM2.5污染程度:优|";
			} else if ("0001".equals(level)) {
				level = "PM2.5污染程度:良|";
			} else if ("0010".equals(level)) {
				level = "PM2.5污染程度:轻度污染|";
			} else if ("0100".equals(level)) {
				level = "PM2.5污染程度:中轻度污染|";
			} else if ("0101".equals(level)) {
				level = "PM2.5污染程度:中度污染|";
			} else if ("0110".equals(level)) {
				level = "PM2.5污染程度:重度污染|";
			} else {
				level = "PM2.5污染程度:严重污染|";
			}
			return flag + level;
		} else {
			return "null|";
		}
	}

	/**
	 * @deprecated 空气PM2.5质量百分比表示 hexArray[60]
	 * @param hexArray
	 * @return
	 */
	public static String getAirPercent(String hexArray) {
		if (null != hexArray) {
			if ("80".equals(hexArray)) {
				return "空气PM2.5质量百分比:无此项|";
			} else {
				return "空气PM2.5质量百分比表示:" + Integer.parseInt(hexArray, 16) + "|";
			}
		} else {
			return null;
		}
	}

	/**
	 * @deprecated 显示屏亮度值 hexArray[61]
	 * @param hexArray
	 * @return
	 */
	public static String getScreen(String hexArray) {
		String temp = hexToBinaryString(hexArray);
		if (null != temp) {
			String value = temp.substring(0, 7);
			if ("0000000".equals(value)) {
				return "显示屏不亮";
			} else {
				return "显示屏亮度为：" + Integer.parseInt(value, 2) + "#亮度";
			}
		} else {
			return "null|";
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(knowTrueForm("00"));
		System.out.println(knowTrueForm("80"));
	}

	public static boolean isEmpty(String s) {
		return (null == s) || "".equals(s) ? true : false;
	}

	/**
	 * @Description 将16进制的字符流转成二进制的字符流
	 * @param hexString
	 * @return String
	 */
	public static String hexToBinaryString(String hexString) {
		String binaryString = null;
		if ((!isEmpty(hexString)) && (hexString.length() == 2)) {
			// 第一位16进制小于8的，需要在转成二进制时在前面补0
			int i1 = Integer.parseInt(hexString.substring(0, 1), 16);
			String m = Integer.toBinaryString(Integer.parseInt(
					hexString.substring(0, 1), 16));
			if (i1 < 8) {
				String s = "";
				for (int i = 0; i < 4 - m.length(); i++) {
					s = "0" + s;
				}
				m = s + m;
			}
			// 第二位18进制小于8的，需要在转成二进制时在前面补0
			int i2 = Integer.parseInt(hexString.substring(1, 2), 16);
			String n = Integer.toBinaryString(Integer.parseInt(
					hexString.substring(1, 2), 16));
			if (i2 < 8) {
				// 第二位16进制小于8的，需要在转成二进制时在前面补0
				String t = "";
				for (int i = 0; i < 4 - n.length(); i++) {
					t = "0" + t;
				}
				n = t + n;
			}
			binaryString = m + n;
		}
		return binaryString;
	}

	/**
	 * 已知16进制原码
	 * 
	 * @param str
	 * @return
	 */
	public static int knowTrueForm(String str) {
		String temp = hexToBinaryString(str);
		if ('0' == (temp.charAt(0))) {
			return Integer.parseInt(temp.substring(1, temp.length()), 2);
		} else {
			return -Integer.parseInt(temp.substring(1, temp.length()), 2);
		}
	}

	/**
	 * 已知16进制补码
	 * 
	 * @param str
	 * @return
	 */
	public static int knowComplement(String str) {
		String temp = hexToBinaryString(str);
		if ('0' == (temp.charAt(0))) {
			return Integer.parseInt(temp.substring(1, temp.length()), 2);
		} else {
			int value = Integer.parseInt(temp, 2) - 1;
			char[] chars = Integer.toBinaryString(value).toCharArray();
			for (int i = 1; i < chars.length; i++) {
				if ('1' == chars[i]) {
					chars[i] = '0';
				} else {
					chars[i] = '1';
				}
			}
			return -Integer.parseInt(
					new String(chars).substring(1, temp.length()), 2);
		}
	}

	@Test
	public void testLog() {
		String[] inputArray = null;
		String deviceType = null;
		String[] hexArray = null;
		String value="DRC_SERVER_96,1453216500308,DRC_SERVER_96,AIH-W401-2059A0B8BE0A,F4F50140490100FE01010101006600010100101A181780808000000000000101010102400000000000000000EBEC000000000080008000000000000000000000000000000000000000000080800007EBF4FB";
		inputArray = value.toString().split(",");
		// 检验输入的数据是否合法
		if (inputArray.length != 5) {
			return;
		}
		// 检验输入的指令是否合法
		if (inputArray[4].length() < 162) {
			return;
		}
		// 获取设备类型 01--空调
		deviceType = inputArray[4].substring(18, 20);
		if (!"01".equals(deviceType)) {
			return;
		}
		System.out.println(inputArray[4].length());
		hexArray = StringUtils.getHexString(inputArray[4]);
		System.out.println(hexArray.length);
		System.out.println(hexArray[8]);

		System.out.println(getWindStatus(hexArray[0])
				+ getSleepModel(hexArray[1]) + getWordmodel(hexArray[2])
				+ getSetRoomTemperature(hexArray[3])
				+ getActualRoomTemperature(hexArray[4])
				+ getRoomCoilTemperature(hexArray[5])
				+ getRoomHumidity(hexArray[6])
				+ getRoomActualHumidity(hexArray[7])
				+ getReceiveRoomTemperature(hexArray[8])
				+ getRoomTemperatureCorrection(hexArray[9])
				+ getTemperatureCorrection(hexArray[10])
				+ getNormalTimerSwitch(hexArray[11])
				+ getTCLHours(hexArray[12]) + getTCLMin(hexArray[13])
				+ getStartingUpClockHours(hexArray[14])
				+ getStartingUpClockMin(hexArray[15])
				+ getShutdownClockHours(hexArray[16])
				+ getShutdownClockMin(hexArray[17]) + getWindDoor(hexArray[18])
				+ getRoomStatus1(hexArray[19]) + getRoomStatus2(hexArray[20])
				+ getRoomStatus3(hexArray[21]) + getRoomStatus4(hexArray[22])
				+ getRoomWarnStatus1(hexArray[23])
				+ getRoomWarnStatus2(hexArray[24])
				+ getCompressorRunFrequency(hexArray[25])
				+ getCompressorTargetFrequency(hexArray[26])
				+ getSendToDriverFrequency(hexArray[27])
				+ getOutsideTemperature(hexArray[28])
				+ getOutsideColdMacTemperature(hexArray[29])
				+ getCompressorExhaustTemperature(hexArray[30])
				+ getExhaustTemperature(hexArray[31])
				+ getOutsideExpansionValve(hexArray[32])
				+ getUab(hexArray[33])
				+ getUab(hexArray[34])
				+ getUabcaHighVoltage(hexArray[35])
				+ getUabcaLowVoltage(hexArray[36])
				+ getUabcaHighVoltage(hexArray[37])
				+ getUabcaLowVoltage(hexArray[38]) + getIabElectric(hexArray[39])
				+ getIbcaElectric(hexArray[40]) + getIbcaElectric(hexArray[41])
				+ getBusbarVoltage(hexArray[42])
				+ getBusbarVoltage(hexArray[43]) + getIabElectric(hexArray[44])
				+ getInAndOutStatus(hexArray[45])
				+ getOutsideStatus1(hexArray[46])
				+ getOutsideStatus2(hexArray[47])
				+ getOutsideWarnStatus1(hexArray[48])
				+ getOutsideWarnStatus2(hexArray[49])
				+ getOutsideWarnStatus3(hexArray[50])
				+ getOutsideWarnStatus4(hexArray[51])
				+ getOutsideWarnStatus5(hexArray[52])
				+ getOutsideWarnStatus6(hexArray[53])
				+ getOutsideWarnStatus7(hexArray[54])
				+ getOutsideWarnStatus8(hexArray[55])
				+ getOutsideWarnStatus9(hexArray[56])
				+ getRoomFanSpeed(hexArray[57])
				+ getOutsideFanSpeed(hexArray[58]) + getAirLevel(hexArray[59])
				+ getAirPercent(hexArray[60]) + getScreen(hexArray[61]));
		
		AirconStatus air = AirconStatus.getAirStatus(hexArray);
		System.out.println(air.toString());
		System.out.println(AirconStatus.initAir(air).toString());
	}
}
