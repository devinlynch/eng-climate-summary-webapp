package com.dlynch.climate.repositories;

import java.io.IOException;
import java.io.Reader;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.dlynch.climate.models.Station;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.enums.CSVReaderNullFieldIndicator;

/**
 * 
 * CSV implementation of a repository for CRUD on {@link Station}
 * 
 * @author devinlynch
 *
 */
@Repository
public class StationsCSVRepository implements StationsRepository {
	private Log log = LogFactory.getLog(getClass());

	/**
	 * Get all stations in a paginated format.
	 * 
	 */
	@Override
	public Page<Station> getStations(Pageable pageable, Date dateRangeStart, Date dateRangeEnd) {
		
		// First load the stations from the CSV
		List<Station> stations = loadStationsFromCsv();
		
		// Filter out the dates if applicable
		filterStationsByDates(dateRangeStart, dateRangeEnd, stations);
		
		
		// Filter the list based on the page being viewed
		int page = pageable.getPageNumber();
		int size = pageable.getPageSize();
		int startIndex = page*size;
		
		List<Station> pageOfStations = new ArrayList<Station>();
		for(int i = startIndex; i < (startIndex+size); i++) {
			if(i > (stations.size() - 1)) {
				break;
			}
			
			pageOfStations.add(stations.get(i));
		}
		
		// Construct a response that has page information
		PageImpl<Station> response = new PageImpl<Station>(pageOfStations, pageable, stations.size());
		return response;
	}


	/**
	 * Filters a list of stations that have dates within the given range.
	 * Both start + end dates are inclusive
	 * 
	 * @param dateRangeStart
	 * @param dateRangeEnd
	 * @param stations
	 */
	private void filterStationsByDates(Date dateRangeStart, Date dateRangeEnd, List<Station> stations) {
		if(dateRangeStart != null || dateRangeEnd != null) {
			Iterator<Station> iterator = stations.iterator();
			while (iterator.hasNext()) {
				Station station = iterator.next();
				if(station.getDate() == null) {
					iterator.remove();
					continue;
				}
				
				if(dateRangeStart != null && station.getDate().getTime() < dateRangeStart.getTime()) {
					iterator.remove();
					continue;
				}
				
				if(dateRangeEnd != null && station.getDate().getTime() > dateRangeEnd.getTime()) {
					iterator.remove();
					continue;
				}
			}
		}
	}

	
	/**
	 * Gets a specific station by name
	 * 
	 */
	@Override
	public Station getStationById(int id) {
		List<Station> stations = loadStationsFromCsv();
		
		for(Station s: stations) {
			if(id == s.getStationId()) {
				return s;
			}
		}
		
		return null;
	}

	
	/**
	 * Gets the name of the CSV file.  The file is assumed to be on the classpath.
	 * 
	 */
	protected String getCsvFileName() {
		String fileName = System.getenv("CSV_FILE_NAME");
		if(fileName == null)
			return "eng-climate-summary.csv";
		return fileName;
	}
	
	/**
	 * Loads all stations from the CSV file into a List
	 * 
	 */
	public List<Station> loadStationsFromCsv() {
		Reader reader;
		try {
			reader = Files.newBufferedReader(Paths.get(ClassLoader.getSystemResource(getCsvFileName()).toURI()));
		} catch (IOException | URISyntaxException e) {
			log.error("Error reading CSV from ["+getCsvFileName()+"]", e);
			throw new RuntimeException(e);
		}
		
		CsvToBean<Station> csvToBean = new CsvToBeanBuilder<Station>(reader)
				.withType(Station.class)
				.withIgnoreLeadingWhiteSpace(true)
				.withSkipLines(1)
				.withFieldAsNull(CSVReaderNullFieldIndicator.BOTH)
				.build();
		
		List<Station> stations = csvToBean.parse();
		
		
		int i = 1;
		for(Station s: stations) {
			s.setStationId(i);
			i++;
		}
		
		log.debug("["+stations.size()+"] stations were loaded from ["+getCsvFileName()+"]");
		
		return stations;
	}
	
}
