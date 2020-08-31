package com.dlynch.climate.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.dlynch.climate.models.Station;
import com.dlynch.climate.repositories.StationsRepository;

/**
 * 
 * Service for interacting with Stations.  At the moment implemented as a straight
 * pass through to the repository.  Put in place for future work where more business
 * logic may need to be implemented.
 * 
 * 
 * @author devinlynch
 *
 */
@Service
public class StationsService {
	@Autowired
	private StationsRepository repository;

	public Page<Station> getStations(Pageable pageable, Date dateRangeStart, Date dateRangeEnd) {
		return getRepository().getStations(pageable, dateRangeStart, dateRangeEnd);
	}

	public Station getStation(int id) {
		return getRepository().getStationById(id);
	}

	public StationsRepository getRepository() {
		return repository;
	}

	public void setRepository(StationsRepository repository) {
		this.repository = repository;
	}

}
