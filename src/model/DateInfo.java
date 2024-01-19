package model;

import java.io.Serializable;

public class DateInfo implements Serializable{
	
	private static final long serialVersionUID = -2181677688451435927L;
	private int startMonth,endMonth,startYear;

	public DateInfo(int startMonth, int startYear) {
		this.startMonth = startMonth;
		this.startYear = startYear;
		endMonth = (startMonth - 1  == 0) ? 12 : startMonth - 1;
	}

	public int getStartMonth() {
		return startMonth;
	}

	public void setStartMonth(int startMonth) {
		this.startMonth = startMonth;
	}

	public int getEndMonth() {
		return endMonth;
	}

	public void setEndMonth(int endMonth) {
		this.endMonth = endMonth;
	}

	public int getStartYear() {
		return startYear;
	}

	public void setStartYear(int startYear) {
		this.startYear = startYear;
	}

}
