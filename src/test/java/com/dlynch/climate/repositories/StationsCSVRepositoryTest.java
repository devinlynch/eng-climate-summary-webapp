package com.dlynch.climate.repositories;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.dlynch.climate.exceptions.NotFoundException;
import com.dlynch.climate.models.Station;

/**
 * Integration tests which run agains the actual CSV
 * 
 * @author devinlynch
 *
 */
class StationsCSVRepositoryTest {
	
	private StationsCSVRepository repository = new StationsCSVRepository();
	
	
	@Test
	public void testGetAllStations() throws NotFoundException {
		Page<Station> page = repository.getStations(PageRequest.of(0, 100000), null, null);
		assertEquals(1135, page.getContent().size());
	}
	
	@Test
	public void testGetStationsPageLogic() throws NotFoundException {
		Page<Station> page = repository.getStations(PageRequest.of(1, 10), null, null);
		assertEquals(10, page.getContent().size());
		assertEquals("RACE ROCKS", page.getContent().get(0).getStationName());
		assertEquals("CAMPBELL RIVER A", page.getContent().get(9).getStationName());
	}
	
	@Test
	public void testGetStationsFilterLogic() throws NotFoundException, ParseException {
		Date startDate =  new SimpleDateFormat("yyyy-MM-dd").parse("2018-01-06");
		Date endDate =  new SimpleDateFormat("yyyy-MM-dd").parse("2018-01-20");
		Page<Station> page = repository.getStations(PageRequest.of(0, 10000), startDate, endDate);
		assertEquals(80, page.getContent().size());
	}
	
	
	@Test
	public void testGetStationById() throws NotFoundException {
		Station station = repository.getStationById(3);
		assertEquals("DISCOVERY ISLAND", station.getStationName());
	}
	
	@Test
	public void testGetStationNotFound() {
		Station station = repository.getStationById(22312321);
		assertNull(station);
	}

}
