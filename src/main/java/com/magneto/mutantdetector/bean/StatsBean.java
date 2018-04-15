package com.magneto.mutantdetector.bean;

import lombok.Value;

@Value
public class StatsBean {
	private int mutantDnaCount;
	private int humanDnaCount;

	public float getRatio() {
		return humanDnaCount == 0 ? 0f : (float) mutantDnaCount / humanDnaCount;
	}
}
