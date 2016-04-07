package com.topeastic.dao.factory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

import com.topeastic.mapreduce.job.Tools;
import com.topeastic.mapreduce.job.vo.FrostPair;

public class WarmProcess implements Processable {

	@Override
	public void run(List<Long> targetTime, List<Long> baseTime, int openPoint,
			boolean isFind, Object... params) {
		List<FrostPair> fpList = new ArrayList<FrostPair>();
		@SuppressWarnings({ "unchecked", "rawtypes" })
		// 首先对params数据进行排序 按照时间
		Iterator<Object> iter = ((TreeSet) params[0]).iterator();
		while (iter.hasNext()) {
			FrostPair frostPair = (FrostPair) iter.next();
			fpList.add(frostPair);
		}
		if (fpList.size() <= 1) {
			return;
		}
		// 数据采集 标准10分钟
		for (int i = 0; i < fpList.size() - 1; i++) {
			FrostPair currentFrostPair = fpList.get(i);
			FrostPair nextFrostPair = fpList.get(i + 1);

			// 如果下一条数据减去前一条数据差值大于10分钟，那么认为开机点是当前时间点
			if (nextFrostPair.getTimeValue() - currentFrostPair.getTimeValue() > 600000) {
				openPoint = i;
				isFind = true;
				break;
			} else {
				continue;
			}
		}

		if (!isFind) {
			openPoint = 0;
		}
		// 判断是否开机半小时
		boolean isHalf = Tools.isRunningHalfHours(openPoint, fpList);
		// 如果不超过半小时，该条数据过滤掉
		if (!isHalf) {
			return;
		}
		// 判断是否开机4小时
		boolean isFourHour = Tools.isRunningFourHours(openPoint, fpList);
		// 标记4小时内有没除霜
		List<Integer> inList = Tools.getRemoveFrost(openPoint, fpList);
		//开机运行4小时以上
		if (isFourHour) {
			// 没有除霜标志
			if (inList.size() == 0) {
				int cnt = 0;
				for (int k = openPoint + 6; k <= fpList.size() - 1; k++) {
					if (fpList.get(k).getIsRun().equals("y")) {
						targetTime.add(fpList.get(k).getTimeValue());
						if (cnt < 3) {
							baseTime.add(fpList.get(k).getTimeValue());
						}
						cnt++;
					}
				}
			}
			// 有除霜标志
			else {
				//~~~~~~~~~
				//获取每个除霜标志后25~30min的那条单个数据
				for (int m = 0; m <= inList.size() - 1; m++) {
					int p = inList.get(m) + 5;
					if (p <= fpList.size() - 1) {
						if (fpList.get(p).getIsRun().equals("y")) {
							targetTime.add(fpList.get(p).getTimeValue());
						}
					}
				}
				int c = 0;
				// 如果只有1次除霜标志，以首次为基准
				if (inList.size() == 1) {
					c = inList.get(0);
				} else {
					c = inList.get(1);
				}
				for (int k = 1; k <= 3; k++) {
					baseTime.add(fpList.get(c + k).getTimeValue());
				}
			}
		}
		//开机运行小于4小时
		else {
			//没有除霜标志
			if (inList.size() == 0) {
				for (int n = openPoint + 6; n <= fpList.size() - 1; n++) {
					if (fpList.get(n).getIsRun().equals("y")) {
						targetTime.add(fpList.get(n).getTimeValue());
					}
				}
				// 如果没有除霜标志位
				if (targetTime.size() < 3) {
					return;
				}
				for (int t = 0; t < 3; t++) {
					baseTime.add(targetTime.get(t));
				}
			} 
			// 有除霜标志
			else {
				for (int s = 0; s <= inList.size() - 1; s++) {
					int q = inList.get(s) + 5;
					if (q < fpList.size() - 1
							&& (fpList.get(q).getIsRun().equals("y"))) {
						targetTime.add(fpList.get(q).getTimeValue());
					}
				}
				// 如果检测到有除霜标志位，那么以除霜结束后三组数据作为基准
				int d = 0;
				// 如果只有1次除霜标志，以首次为基准
				if (inList.size() == 1) {
					d = inList.get(0);
				} else {
					d = inList.get(1);
				}
				for (int k = 1; k <= 3; k++) {
					if (d + k <= fpList.size() - 1) {
						baseTime.add(fpList.get(d + k).getTimeValue());
					}
				}
			}
		}
	}
}
