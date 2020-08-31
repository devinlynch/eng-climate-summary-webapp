package com.dlynch.climate.models;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvDate;

public class Station {

	
	private Integer stationId;
	
	@CsvBindByPosition(position = 0)
	private String stationName;
	
	@CsvBindByPosition(position = 1)
	private String province;
	
	@CsvDate("dd/MM/yyyy")
	@CsvBindByPosition(position = 2)
	@JsonFormat(pattern="dd/MM/yyyy")
	private Date date;
	
	@CsvBindByPosition(position = 3)
	private Double meanTemp;
	
	@CsvBindByPosition(position = 4)
	private Double highestMonthlyMaxTemp;
	
	@CsvBindByPosition(position = 5)
	private Double lowestMonthlyMinTemp;

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Double getMeanTemp() {
		return meanTemp;
	}

	public void setMeanTemp(Double meanTemp) {
		this.meanTemp = meanTemp;
	}

	public Double getHighestMonthlyMaxTemp() {
		return highestMonthlyMaxTemp;
	}

	public void setHighestMonthlyMaxTemp(Double highestMonthlyMaxTemp) {
		this.highestMonthlyMaxTemp = highestMonthlyMaxTemp;
	}

	public Double getLowestMonthlyMinTemp() {
		return lowestMonthlyMinTemp;
	}

	public void setLowestMonthlyMinTemp(Double lowestMonthlyMinTemp) {
		this.lowestMonthlyMinTemp = lowestMonthlyMinTemp;
	}

	public Integer getStationId() {
		return stationId;
	}

	public void setStationId(Integer stationId) {
		this.stationId = stationId;
	}

}
