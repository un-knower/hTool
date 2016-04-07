package com.topeastic.hadoop.task.bean;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.WritableComparable;

public class TypeTime implements WritableComparable<TypeTime> {
	String type;
	String time;

	public String getType() {
		return type;
	}

	public void set(String type, String time) {
		this.type = type;
		this.time = time;
	}

	public String getTime() {
		return time;
	}

	@Override
	public void readFields(DataInput arg0) throws IOException {
		// TODO Auto-generated method stub
		type = arg0.readUTF();
		time = arg0.readUTF();
	}

	@Override
	public void write(DataOutput arg0) throws IOException {
		// TODO Auto-generated method stub
		arg0.writeUTF(type);
		arg0.writeUTF(time);
	}

	@Override
	public int compareTo(TypeTime o) {
		if (!type.equals(o.type)) {
			return type.compareTo(o.type);
		} else if (!time.equals(o.time)) {
			return time.compareTo(o.time);
		} else
			return 0;
	}

	@Override
	public int hashCode() {
		return type.hashCode() * 2 + time.hashCode();
	}

	@Override
	public boolean equals(Object other) {
		if (other == null) {
			return false;
		}
		if (this == other) {
			return true;
		}
		if (other instanceof TypeTime) {
			TypeTime some = (TypeTime) other;
			return some.type == type && some.time == time;
		} else {
			return true;
		}
	}

}
