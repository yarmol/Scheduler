package com.yvalera.scheduler.server.BaseUnits.Point;

import org.joda.time.Interval;

/*
 * This point must be start and end on defined term
 */
public class LimitedTermPoint extends AbstractPoint {
	private Interval interval;
	private int necessaryTime;

	public void setInterval(Interval interval){
		this.interval = interval;
	}

	public Interval getInterval(){
		return interval;
	}

	public int getNecessaryTime() {
		return necessaryTime;
	}

	public void setNecessaryTime(int necessaryTime) {
		this.necessaryTime = necessaryTime;
	}
}
