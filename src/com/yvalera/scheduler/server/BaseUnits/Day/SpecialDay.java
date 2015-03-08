package com.yvalera.scheduler.server.BaseUnits.Day;

import org.joda.time.LocalDate;

public class SpecialDay extends Day{
	private LocalDate date;

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}
}
