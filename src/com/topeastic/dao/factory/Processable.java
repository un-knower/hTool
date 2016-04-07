package com.topeastic.dao.factory;

import java.util.List;
import java.util.TreeSet;

public interface Processable {
	public void run(List<Long> targetTime,List<Long> baseTime,
			int openPoint,boolean isFind,Object...param);
}
