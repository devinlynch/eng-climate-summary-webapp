package com.dlynch.climate.controllers.api;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dlynch.climate.exceptions.NotFoundException;
import com.dlynch.climate.models.Station;
import com.dlynch.climate.services.StationsService;

@RestController
@RequestMapping("/api/stations")
public class StationsController {
	@Autowired
	private StationsService stationsService;
	private Log log = LogFactory.getLog(getClass());

	@GetMapping
	public Page<Station> getStations(Pageable pageable,
			@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateRangeStart,
			@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateRangeEnd) {

		log.info("/api/stations is called with dateRangeStart=[" + dateRangeStart + "], dateRangeEnd=[" + dateRangeEnd
				+ "], pageable=[" + pageable + "] ");
		return getStationsService().getStations(pageable, dateRangeStart, dateRangeEnd);
	}

	@GetMapping("/{id}")
	public Station getStation(@PathVariable Integer id) throws NotFoundException {
		log.info("/api/stations/"+id+" is called");
		
		Station station = getStationsService().getStation(id);

		if (station == null) {
			log.info("No station was found with ID ["+id+"]");
			throw new NotFoundException();
		}

		return station;
	}

	public StationsService getStationsService() {
		return stationsService;
	}

	public void setStationsService(StationsService stationsService) {
		this.stationsService = stationsService;
	}
}
