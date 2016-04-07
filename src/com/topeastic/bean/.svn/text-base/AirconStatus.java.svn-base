package com.topeastic.bean;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import com.topeastic.hadoop.utils.StringUtils;

@SuppressWarnings("deprecation")
public class AirconStatus {

	/**
	 * 是否关机 true 开机 false 关机
	 */
	private boolean isRunning;

	/**
	 * 空调的模式 制冷 制热 送风 除湿 自动
	 */
	private String model;

	/**
	 * 空调的温度
	 */
	private int temperature;

	/**
	 * 空调的风速 小风 中风 大风 自动 静音
	 */
	private String speed;

	/**
	 * 空调的亮度
	 */
	private String light;

	/**
	 * 是否上下风 true 扫风 false 定向
	 */
	private boolean isUpDownWind;

	/**
	 * 是否左右风 true 扫风 false 定向
	 */
	private boolean isLeftRightWind;

	/**
	 * 定时开机 null为未开启
	 */
	private boolean isTimeToUp;

	private String airTimeUp;

	/**
	 * 定时关机 null为未开启
	 */
	private boolean isTimeToDown;

	private String airTimeDown;
	/**
	 * 是否睡眠 true 开启 false 关闭
	 */
	private boolean isSleep;

	/**
	 * 是否电热 true 开启 false 关闭
	 */
	private boolean isHot;

	/**
	 * 是否强力 true 开启 false 关闭
	 */
	private boolean isStrong;
	
	/**
	 * 是否静音 true 开启 false 关闭
	 */
	private boolean isQuiet;

	private String who;

	public static AirconStatus getAirStatus(String[] hexArray) {
		AirconStatus air = new AirconStatus();
		air.setRunning(StringUtils.isRunning(hexArray[2]));
		air.setModel(StringUtils.hexToBinaryString(hexArray[2]).substring(0, 4));
		air.setTemperature(StringUtils.knowComplement(hexArray[3]));
		air.setSpeed("1".equals(StringUtils.hexToBinaryString(hexArray[0])
				.substring(7)) ? "auto" : StringUtils.hexToBinaryString(
				hexArray[0]).substring(0, 7));
		air.setQuiet("0000001".equals(StringUtils
				.hexToBinaryString(hexArray[0]).substring(0, 7)) ? true : false);
		air.setLight(StringUtils.hexToBinaryString(hexArray[61])
				.substring(0, 7));
		air.setTimeToUp(StringUtils.hexToBinaryString(hexArray[14])
				.substring(6, 7).equals("1"));
		air.setTimeToDown(StringUtils.hexToBinaryString(hexArray[16])
				.substring(6, 7).equals("1"));
		air.setUpDownWind("1".equals(StringUtils
				.hexToBinaryString(hexArray[19]).substring(0, 1)));
		air.setLeftRightWind("1".equals(StringUtils.hexToBinaryString(
				hexArray[19]).substring(1, 2)));
		air.setSleep("0000000".equals(StringUtils.hexToBinaryString(hexArray[1])
				.substring(0, 7)) ? false : true);
		air.setHot("1".equals(StringUtils.hexToBinaryString(hexArray[19])
				.substring(3, 4)) ? true : false);
		air.setStrong("1".equals(StringUtils.hexToBinaryString(hexArray[19])
				.substring(6, 7)) ? true : false);
		air.setAirTimeUp(StringUtils.hexToBinaryString(hexArray[14]).substring(
				0, 5)
				+ StringUtils.hexToBinaryString(hexArray[15]).substring(0, 6));
		air.setAirTimeDown(StringUtils.hexToBinaryString(hexArray[16])
				.substring(0, 5)
				+ StringUtils.hexToBinaryString(hexArray[17]).substring(0, 6));
		return air;

	}

	public static AirconStatus initAir(AirconStatus airStatus) {
		AirconStatus air = getVal(airStatus);
		
		// 模式对温度和风速的影响
		switch (airStatus.getModel()) {
		// 送风
		case "0000":
			air.setTemperature(25);
			air.setSpeed("0000011");
			break;
		// 制热
		case "0001":
			air.setTemperature(23);
			air.setSpeed("auto");
			break;
		// 制冷
		case "0010":
			air.setTemperature(26);
			air.setSpeed("auto");
			break;
		// 除湿
		case "0011":
			air.setTemperature(25);
			air.setSpeed("auto");
			break;
		// 自动送风
		// 自动制热
		// 自动制冷
		// 自动除湿
		default:
			air.setTemperature(25);
			air.setSpeed("auto");
			break;
		}
		// 开关机对空调的影响
		if (airStatus.isRunning()) {
			air.setTimeToDown(false);
			air.setTimeToUp(false);
			air.setLeftRightWind(false);
		} else {
			airStatus.setTimeToDown(false);
		}

		// 睡眠对灯光的影响
		if (airStatus.isSleep()) {
			air.setSpeed("0000010");
			air.setLight("0000000");
			air.setStrong(false);
		}
		
		// 强力对风速的影响
		if (airStatus.isStrong()) {
			air.setSpeed("auto");
		}
		
		if (airStatus.isQuiet()) {
			air.setSpeed("0000001");
		}

		return air;
	}
	
	public static boolean wrongStatus(AirconStatus airStatus){
		//根据相对应的状态将状态错误的信息过滤
		//强力开，风速不为自动
		if(airStatus.isStrong()&&!airStatus.getSpeed().equals("auto")){
			
			return true;
		}
		//睡眠开，背景灯不为关，风速不为低风,强力不为关
		if(airStatus.isSleep()&&!airStatus.getLight().equals("0000000")&&!airStatus.isStrong()){
			
			return true;
		}
		
		return false;
	}

	public static AirconStatus getVal(AirconStatus airStatus) {
		AirconStatus air = new AirconStatus();
		air.setRunning(airStatus.isRunning());
		air.setModel(airStatus.getModel());
		air.setTemperature(airStatus.getTemperature());
		air.setSpeed(airStatus.getSpeed());
		air.setLight(airStatus.getLight());
		air.setUpDownWind(airStatus.isUpDownWind());
		air.setLeftRightWind(airStatus.isLeftRightWind());
		air.setTimeToUp(airStatus.isTimeToUp());
		air.setAirTimeUp(airStatus.getAirTimeUp());
		air.setTimeToDown(airStatus.isTimeToDown());
		air.setAirTimeDown(airStatus.getAirTimeDown());
		air.setSleep(airStatus.isSleep());
		air.setHot(airStatus.isHot());
		air.setStrong(airStatus.isStrong());
		air.setQuiet(airStatus.isQuiet());
		air.setWho(airStatus.getWho());
		return air;
	}

	public String getAirTimeUp() {
		return airTimeUp;
	}

	public void setAirTimeUp(String airTimeUp) {
		this.airTimeUp = airTimeUp;
	}

	public String getAirTimeDown() {
		return airTimeDown;
	}

	public void setAirTimeDown(String airTimeDown) {
		this.airTimeDown = airTimeDown;
	}

	public boolean isRunning() {
		return isRunning;
	}

	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public boolean isQuiet() {
		return isQuiet;
	}

	public void setQuiet(boolean isQuiet) {
		this.isQuiet = isQuiet;
	}

	public int getTemperature() {
		return temperature;
	}

	public void setTemperature(int temperature) {
		this.temperature = temperature;
	}

	public String getSpeed() {
		return speed;
	}

	public void setSpeed(String speed) {
		this.speed = speed;
	}

	public String getLight() {
		return light;
	}

	public void setLight(String light) {
		this.light = light;
	}

	public boolean isUpDownWind() {
		return isUpDownWind;
	}

	public void setUpDownWind(boolean isUpDownWind) {
		this.isUpDownWind = isUpDownWind;
	}

	public boolean isLeftRightWind() {
		return isLeftRightWind;
	}

	public void setLeftRightWind(boolean isLeftRightWind) {
		this.isLeftRightWind = isLeftRightWind;
	}

	public boolean isTimeToUp() {
		return isTimeToUp;
	}

	public void setTimeToUp(boolean isTimeToUp) {
		this.isTimeToUp = isTimeToUp;
	}

	public boolean isTimeToDown() {
		return isTimeToDown;
	}

	public void setTimeToDown(boolean isTimeToDown) {
		this.isTimeToDown = isTimeToDown;
	}

	public boolean isSleep() {
		return isSleep;
	}

	public void setSleep(boolean isSleep) {
		this.isSleep = isSleep;
	}

	public boolean isHot() {
		return isHot;
	}

	public void setHot(boolean isHot) {
		this.isHot = isHot;
	}

	public boolean isStrong() {
		return isStrong;
	}

	public void setStrong(boolean isStrong) {
		this.isStrong = isStrong;
	}

	public String getWho() {
		return who;
	}

	public void setWho(String who) {
		this.who = who;
	}

	@Override
	public String toString() {
		return "isRunning=" + isRunning + ", model=" + model + ", temperature="
				+ temperature + ", speed=" + speed + ", ligth=" + light
				+ ", isUpDownWind=" + isUpDownWind + ", isLeftRightWind="
				+ isLeftRightWind + ", isTimeToUp=" + isTimeToUp
				+ ", airTimeUp=" + airTimeUp + ", isTimeToDown=" + isTimeToDown
				+ ", airTimeDown=" + airTimeDown + ", isSleep=" + isSleep
				+ ", isHot=" + isHot + ", isStrong=" + isStrong + ", isQuiet="
				+ isQuiet + ", who=" + who;
	}

	// @Override
	// public void readFields(DataInput paramDataInput) throws IOException {
	// // TODO Auto-generated method stub
	// isRunning = paramDataInput.readBoolean();
	// model = paramDataInput.readUTF();
	// temperature = paramDataInput.readInt();
	// speed = paramDataInput.readUTF();
	// light = paramDataInput.readUTF();
	// isUpDownWind = paramDataInput.readBoolean();
	// isLeftRightWind = paramDataInput.readBoolean();
	// isTimeToUp = paramDataInput.readBoolean();
	// airTimeUp = paramDataInput.readUTF();
	// isTimeToDown = paramDataInput.readBoolean();
	// airTimeDown = paramDataInput.readUTF();
	// isSleep = paramDataInput.readBoolean();
	// isHot = paramDataInput.readBoolean();
	// isStrong = paramDataInput.readBoolean();
	// isQuiet = paramDataInput.readBoolean();
	//
	// }
	//
	// @Override
	// public void write(DataOutput paramDataOutput) throws IOException {
	// // TODO Auto-generated method stub
	// paramDataOutput.writeBoolean(isRunning);
	// paramDataOutput.writeUTF(model);
	// paramDataOutput.writeInt(temperature);
	// paramDataOutput.writeUTF(speed);
	// paramDataOutput.writeUTF(light);
	// paramDataOutput.writeBoolean(isUpDownWind);
	// paramDataOutput.writeBoolean(isLeftRightWind);
	// paramDataOutput.writeBoolean(isTimeToUp);
	// paramDataOutput.writeUTF(airTimeUp);
	// paramDataOutput.writeBoolean(isTimeToDown);
	// paramDataOutput.writeUTF(airTimeDown);
	// paramDataOutput.writeBoolean(isSleep);
	// paramDataOutput.writeBoolean(isHot);
	// paramDataOutput.writeBoolean(isStrong);
	// paramDataOutput.writeBoolean(isQuiet);
	// }

	// @Override
	// public int compareTo(AirconStatus o) {
	// // TODO Auto-generated method stub
	// int result=this.hashCode()-o.hashCode();
	// if(result!=0){
	// return result;
	// }
	// return 0;
	// }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((airTimeDown == null) ? 0 : airTimeDown.hashCode());
		result = prime * result
				+ ((airTimeUp == null) ? 0 : airTimeUp.hashCode());
		result = prime * result + (isHot ? 1231 : 1237);
		result = prime * result + (isLeftRightWind ? 1231 : 1237);
		result = prime * result + (isQuiet ? 1231 : 1237);
		result = prime * result + (isRunning ? 1231 : 1237);
		result = prime * result + (isSleep ? 1231 : 1237);
		result = prime * result + (isStrong ? 1231 : 1237);
		result = prime * result + (isTimeToDown ? 1231 : 1237);
		result = prime * result + (isTimeToUp ? 1231 : 1237);
		result = prime * result + (isUpDownWind ? 1231 : 1237);
		result = prime * result + ((light == null) ? 0 : light.hashCode());
		result = prime * result + ((model == null) ? 0 : model.hashCode());
		result = prime * result + ((speed == null) ? 0 : speed.hashCode());
		result = prime * result + temperature;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AirconStatus other = (AirconStatus) obj;
		if (airTimeDown == null) {
			if (other.airTimeDown != null)
				return false;
		} else if (!airTimeDown.equals(other.airTimeDown))
			return false;
		if (airTimeUp == null) {
			if (other.airTimeUp != null)
				return false;
		} else if (!airTimeUp.equals(other.airTimeUp))
			return false;
		if (isHot != other.isHot)
			return false;
		if (isLeftRightWind != other.isLeftRightWind)
			return false;
		if (isQuiet != other.isQuiet)
			return false;
		if (isRunning != other.isRunning)
			return false;
		if (isSleep != other.isSleep)
			return false;
		if (isStrong != other.isStrong)
			return false;
		if (isTimeToDown != other.isTimeToDown)
			return false;
		if (isTimeToUp != other.isTimeToUp)
			return false;
		if (isUpDownWind != other.isUpDownWind)
			return false;
		if (light == null) {
			if (other.light != null)
				return false;
		} else if (!light.equals(other.light))
			return false;
		if (model == null) {
			if (other.model != null)
				return false;
		} else if (!model.equals(other.model))
			return false;
		if (speed == null) {
			if (other.speed != null)
				return false;
		} else if (!speed.equals(other.speed))
			return false;
		if (temperature != other.temperature)
			return false;
		return true;
	}

}
