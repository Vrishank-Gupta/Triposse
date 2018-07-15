package com.vrishankgupta.triposse.util;

public class Dob{
	private String date;
	private String month;
	private String year;

	public Dob(String date, String month, String year) {
		this.date = date;
		this.month = month;
		this.year = year;
	}

	public void setDate(String date){
		this.date = date;
	}

	public String getDate(){
		return date;
	}

	public void setMonth(String month){
		this.month = month;
	}

	public String getMonth(){
		return month;
	}

	public void setYear(String year){
		this.year = year;
	}

	public String getYear(){
		return year;
	}

	@Override
 	public String toString(){
		return 
			"Dob{" + 
			"date = '" + date + '\'' + 
			",month = '" + month + '\'' + 
			",year = '" + year + '\'' + 
			"}";
		}
}
