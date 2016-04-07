package com.topeastic.test;

import com.topeastic.bean.AirconStatus;

public class BehaviorTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		AirconStatus[] Air = new AirconStatus[3];
		String[] val = new String[3];
//		val[0]="isRunning=true, model=0001, temperature=26, speed=0000100, ligth=0000100, isUpDownWind=false, isLeftRightWind=false, isTimeToUp=false, airTimeUp=00000000000, isTimeToDown=false, airTimeDown=00000000000, isSleep=true, isHot=false, isStrong=false, isQuiet=false";
		val[0] = "isRunning=true, model=0001, temperature=32, speed=auto, ligth=0000001, isUpDownWind=false, isLeftRightWind=true, isTimeToUp=false, airTimeUp=00000000000, isTimeToDown=false, airTimeDown=00000000000, isSleep=true, isHot=true, isStrong=true, isQuiet=false";
		val[1] = "isRunning=true, model=0001, temperature=32, speed=auto, ligth=0000100, isUpDownWind=true, isLeftRightWind=false, isTimeToUp=false, airTimeUp=00000000000, isTimeToDown=false, airTimeDown=00000000000, isSleep=true, isHot=true, isStrong=false, isQuiet=false";
		val[2] = "isRunning=false, model=0001, temperature=32, speed=auto, ligth=0000000, isUpDownWind=true, isLeftRightWind=false, isTimeToUp=false, airTimeUp=00000000000, isTimeToDown=false, airTimeDown=00000000000, isSleep=true, isHot=false, isStrong=false, isQuiet=false";
		Air[0] = getAir(val[0]);
		Air[1] = getAir(val[1]);
		Air[2] = getAir(val[2]);
//		System.out.println(Air[0].toString());
		run(Air);
	}

	public static AirconStatus getAir(String val) {
		AirconStatus air = new AirconStatus();
		String[] value = val.split(",");
		air.setRunning("true".equals(value[0].split("=")[1]) ? true : false);
		air.setModel(value[1].split("=")[1]);
		air.setTemperature(Integer.parseInt(value[2].split("=")[1]));
		air.setSpeed(value[3].split("=")[1]);
		air.setLight(value[4].split("=")[1]);
		air.setUpDownWind("true".equals(value[5].split("=")[1]) ? true : false);
		air.setLeftRightWind("true".equals(value[6].split("=")[1]) ? true
				: false);
		air.setTimeToUp("true".equals(value[7].split("=")[1]) ? true : false);
		air.setAirTimeUp(value[8].split("=")[1]);
		air.setTimeToDown("true".equals(value[9].split("=")[1]) ? true : false);
		air.setAirTimeDown(value[10].split("=")[1]);
		air.setSleep("true".equals(value[11].split("=")[1]) ? true : false);
		air.setHot("true".equals(value[12].split("=")[1]) ? true : false);
		air.setStrong("true".equals(value[13].split("=")[1]) ? true : false);
		air.setQuiet("true".equals(value[14].split("=")[1]) ? true : false);
		return air;
	}

	public static void run(AirconStatus[] Air) {
		AirconStatus last_status = null;
		AirconStatus now_status = new AirconStatus();
		// 开关机
		int up = 0;
		int down = 0;
		// 制热
		int warm = 0;
		// 制冷
		int cold = 0;
		// 送风
		int songfeng = 0;
		// 除湿
		int wet = 0;
		// 自动
		int auto_model = 0;
		// 温度
		int temperature = 0;
		// 风速
		int low_speed = 0;
		int mid_speed = 0;
		int high_speed = 0;
		int auto_speed = 0;
		// 静音
		int quiet = 0;
		// 亮度
		int light = 0;
		// 上下风
		int up_down = 0;
		// 左右风
		int left_right = 0;
		// 定时开机
		int time_up = 0;
		// 定时关机
		int time_down = 0;
		// 睡眠模式
		int sleep = 0;
		// 电热
		int hot = 0;
		// 强力
		int strong = 0;
		int num = 0;
		for (AirconStatus airStatus : Air) {

			num++;

			// 判断
			now_status = airStatus;
			AirconStatus tmp_status = AirconStatus.initAir(airStatus);
//			System.out.println(now_status==tmp_status);
			if (null != last_status) {
				System.out.println(now_status==last_status);
				// 比对2条数据
				// 上条记录为开机
				if (last_status.isRunning()) {
					// 现在为开机 即开->开
					if (now_status.isRunning()) {

						if (now_status.isUpDownWind() != last_status
								.isUpDownWind()) {
							up_down++;
						}
						if (now_status.isLeftRightWind() != last_status
								.isLeftRightWind()) {
							left_right++;
						}
						if (now_status.isTimeToUp() != last_status.isTimeToUp()) {
							time_up++;
						}
						if (now_status.isTimeToDown() != last_status
								.isTimeToDown()) {
							time_down++;
						}
						if (now_status.isSleep() != last_status.isSleep()) {
							sleep++;
						}
						if (!last_status.isStrong() && now_status.isStrong()) {
							strong++;
						}
						if (last_status.isStrong() && !now_status.isStrong()
								&& tmp_status.getSpeed().equals("auto")) {
							strong++;
						}
						if (now_status.isHot() != last_status.isHot()) {
							hot++;
						}
						// 模式的比较
						if (now_status.getModel()
								.equals(last_status.getModel())) {

							if (now_status.getTemperature() != last_status
									.getTemperature()) {
								temperature++;
							}
							// 根据强力开关比较风速
							if ((!now_status.isStrong())
									&& !(now_status.getSpeed().equals(last_status
											.getSpeed()))) {
								switch (now_status.getSpeed()) {
								case "auto":
									auto_speed++;
									break;
								case "0000010":
									low_speed++;
									break;
								case "0000011":
									mid_speed++;
									break;
								case "0000100":
									high_speed++;
									break;
								case "0000001":
									quiet++;
									break;
								default:
									break;
								}
							}
							if ((!now_status.isSleep())
									&& (!now_status.getLight().equals(
											last_status.getLight()))) {
								light++;
							}

						} else {
							// 模式发生了变化
							switch (now_status.getModel()) {
							// 送风
							case "0000":
								songfeng++;
								break;
							// 制热
							case "0001":
								warm++;
								break;
							// 制冷
							case "0010":
								cold++;
								break;
							// 除湿
							case "0011":
								wet++;
								break;
							// 自动送风
							// 自动制热
							// 自动制冷
							// 自动除湿
							default:
								auto_model++;
								break;
							}
							// 当前状态跟切换后初始化的状态比较
							if (now_status.getTemperature() != tmp_status
									.getTemperature()) {
								temperature++;
							}
							if ((!now_status.isStrong())
									&& !(now_status.getSpeed().equals(last_status
											.getSpeed()))) {
								switch (now_status.getSpeed()) {
								case "auto":
									auto_speed++;
									System.out.println("2");
									break;
								case "0000010":
									low_speed++;
									break;
								case "0000011":
									mid_speed++;
									break;
								case "0000100":
									high_speed++;
									break;
								case "0000001":
									quiet++;
									break;
								default:
									break;
								}
							}
							if ((!now_status.isSleep())
									&& (!now_status.getLight().equals(
											last_status.getLight()))) {
								light++;
							}
						}

					} else {
						// 开 -> 关
						// 判断是否为定时关 ： 判断定时关的剩余时间是否为0
						if (last_status.isTimeToDown()
								&& "00000000000".equals(now_status
										.getAirTimeDown())) {
							time_down++;
						} else {
							down++;
						}
						// 其他比较
						if (last_status.isTimeToUp() != now_status.isTimeToUp()) {
							time_up++;
						}
						if (now_status.getModel()
								.equals(last_status.getModel())) {
							if (now_status.getTemperature() != last_status
									.getTemperature()) {
								temperature++;
							}
						} else {
							switch (now_status.getModel()) {
							// 送风
							case "0000":
								songfeng++;
								break;
							// 制热
							case "0001":
								warm++;
								break;
							// 制冷
							case "0010":
								cold++;
								break;
							// 除湿
							case "0011":
								wet++;
								break;
							// 自动送风
							// 自动制热
							// 自动制冷
							// 自动除湿
							default:
								auto_model++;
								break;
							}
							if (now_status.getTemperature() != tmp_status
									.getTemperature()) {
								temperature++;
							}
						}

					}
				} else {
					// 现在为开机 关->开
					if (now_status.isRunning()) {
						// 判断是否为定时开 ：判断定时开的声音时间是否为0
						if (last_status.isTimeToUp()
								&& "00000000000".equals(now_status
										.getAirTimeUp())) {
							time_up++;
						} else {
							up++;
						}

						// 其他比较
						if (now_status.isTimeToUp() != tmp_status.isTimeToUp()) {
							time_up++;
						}
						if (now_status.isTimeToDown() != tmp_status
								.isTimeToDown()) {
							time_down++;
						}
						if (now_status.isUpDownWind() != last_status
								.isUpDownWind()) {
							up_down++;
						}
						if (now_status.isLeftRightWind() != tmp_status
								.isLeftRightWind()) {
							left_right++;
						}
						if (now_status.isSleep() != last_status.isSleep()) {
							sleep++;
						}
						if (!last_status.isStrong() && now_status.isStrong()) {
							strong++;
						}
						if (last_status.isStrong() && !now_status.isStrong()
								&& tmp_status.getSpeed().equals("auto")) {
							strong++;
						}
						if (now_status.isHot() != last_status.isHot()) {
							hot++;
						}
						if (now_status.getModel()
								.equals(last_status.getModel())) {
							if (now_status.getTemperature() != last_status
									.getTemperature()) {
								temperature++;
							}
						} else {
							switch (now_status.getModel()) {
							// 送风
							case "0000":
								songfeng++;
								break;
							// 制热
							case "0001":
								warm++;
								break;
							// 制冷
							case "0010":
								cold++;
								break;
							// 除湿
							case "0011":
								wet++;
								break;
							// 自动送风
							// 自动制热
							// 自动制冷
							// 自动除湿
							default:
								auto_model++;
								break;
							}
							if (now_status.getTemperature() != tmp_status
									.getTemperature()) {
								temperature++;
							}
						}
						if ((!now_status.isStrong())
								&& !(now_status.getSpeed().equals(last_status
										.getSpeed()))) {
							switch (now_status.getSpeed()) {
							case "auto":
								auto_speed++;
								System.out.println("3");
								break;
							case "0000010":
								low_speed++;
								break;
							case "0000011":
								mid_speed++;
								break;
							case "0000100":
								high_speed++;
								break;
							case "0000001":
								quiet++;
								break;
							default:
								break;
							}
						}

					} else {
						// 关 -> 关
						if (now_status.getModel()
								.equals(last_status.getModel())) {
							if (now_status.getTemperature() != last_status
									.getTemperature()) {
								temperature++;
							}
						} else {
							switch (now_status.getModel()) {
							// 送风
							case "0000":
								songfeng++;
								break;
							// 制热
							case "0001":
								warm++;
								break;
							// 制冷
							case "0010":
								cold++;
								break;
							// 除湿
							case "0011":
								wet++;
								break;
							// 自动送风
							// 自动制热
							// 自动制冷
							// 自动除湿
							default:
								auto_model++;
								break;
							}
							if (now_status.getTemperature() != tmp_status
									.getTemperature()) {
								temperature++;
							}
						}
						if (last_status.isTimeToUp() != now_status.isTimeToUp()) {
							time_up++;
						}
					}
				}

			}
			last_status = now_status;
		}
		// 只有一条记录
		if (num == 1) {
			AirconStatus tmp = AirconStatus.initAir(now_status);
			if (now_status.isRunning()) {
				up++;
				if (now_status.isHot()) {
					hot++;
				}
				if (now_status.isLeftRightWind()) {
					left_right++;
				}
				if (now_status.isQuiet()) {
					quiet++;
				}
				if (now_status.isSleep()) {
					sleep++;
				}
				if (now_status.isStrong()) {
					strong++;
				}
				if (now_status.isTimeToDown()) {
					time_down++;
				}
				if (now_status.isTimeToUp()) {
					time_up++;
				}
				if (now_status.isUpDownWind()) {
					up_down++;
				}
				if (now_status.getTemperature() != tmp.getTemperature()) {
					temperature++;
				}
				if (!now_status.getSpeed().equals(tmp.getSpeed())) {
					switch (now_status.getSpeed()) {
					case "auto":
						auto_speed++;
						System.out.println("4");
						break;
					case "0000010":
						low_speed++;
						break;
					case "0000011":
						mid_speed++;
						break;
					case "0000100":
						high_speed++;
						break;
					case "0000001":
						quiet++;
						break;
					default:
						break;
					}
				}

			} else {
				down++;
				if (now_status.getTemperature() != tmp.getTemperature()) {
					temperature++;
				}
				if (now_status.isTimeToUp()) {
					time_up++;
				}
			}
		}
		String behavior = "开机次数：" + up + "|关机次数：" + down + "|制热次数：" + warm
				+ "|制冷次数：" + cold + "|送风次数：" + songfeng + "|除湿次数：" + wet
				+ "|自动次数：" + auto_model + "|温度调节次数：" + temperature + "|小风次数："
				+ low_speed + "|中风次数：" + mid_speed + "|高风次数：" + high_speed
				+ "|自动风次数：" + auto_speed + "|静音次数：" + quiet + "|亮度调节次数："
				+ light + "|上下风设置次数：" + up_down + "|左右风设置次数：" + left_right
				+ "|定时开机设置次数：" + time_up + "|定时关机设置次数：" + time_down
				+ "|睡眠设置次数：" + sleep + "|电热设置次数：" + hot + "|强力设置次数：" + strong;
		System.out.println(behavior);
	}

}
