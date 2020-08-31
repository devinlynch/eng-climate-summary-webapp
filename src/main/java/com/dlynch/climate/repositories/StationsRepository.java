package com.dlynch.climate.repositories;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dlynch.climate.models.Station;

/**
 * 
 * Repository for CRUD on {@link Station}
 * 
 * @author devinlynch
 *
 */
public interface StationsRepository {

	
	public Page<Station> getStations(Pageable pageable, Date dateRangeStart, Date dateRangeEnd);
	public Station getStationById(int id);
	
	
	
}
