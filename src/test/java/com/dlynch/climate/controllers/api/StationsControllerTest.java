package com.dlynch.climate.controllers.api;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.dlynch.climate.exceptions.NotFoundException;
import com.dlynch.climate.models.Station;
import com.dlynch.climate.repositories.StationsCSVRepository;

@SpringBootTest
public class StationsControllerTest {
	
	@Autowired
	StationsController controller;
	
	private StationsController getMockedController() throws ParseException {
		StationsCSVRepository mockRepo = Mockito.spy(StationsCSVRepository.class);
		List<Station> stubbedStations = new ArrayList<Station>();
		
		Date date =  new SimpleDateFormat("yyyy-MM-dd").parse("2020-01-01");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		
		// Populate 100 mocked stations, each will have a incremental date from 2020-01-01
		for(int i = 1; i <= 100; i++) {
			Station s = new Station();
			s.setStationId(i);
			s.setStationName("station-"+i);
			s.setDate(calendar.getTime());
			calendar.add(Calendar.DATE, 1);
			stubbedStations.add(s);
		}
		
		Mockito.when(mockRepo.loadStationsFromCsv()).thenReturn(stubbedStations);
		controller.getStationsService().setRepository(mockRepo);
		return controller;
	}
	
	@Test
	public void testGetStationsWithoutFilter() throws ParseException {
		StationsController controller = getMockedController();
		
		PageRequest pageRequest = PageRequest.of(0, 10);
		Page<Station> stations = controller.getStations(pageRequest, null, null);
		
		assertEquals(10, stations.getContent().size());
		assertEquals("station-1", stations.getContent().get(0).getStationName());
	}
	
	@Test
	public void testGetStationsWithFilter() throws ParseException {
		StationsController controller = getMockedController();

		PageRequest pageRequest = PageRequest.of(0, 100);
		
		Date startDate =  new SimpleDateFormat("yyyy-MM-dd").parse("2020-01-06");
		Date endDate =  new SimpleDateFormat("yyyy-MM-dd").parse("2020-01-08");
		
		Page<Station> stations = controller.getStations(pageRequest, startDate, endDate);
		
		// Expecting 3 dates because the filter start/end times are inclusive
		assertEquals(3, stations.getContent().size());
		assertEquals("station-6", stations.getContent().get(0).getStationName());
	}
	
	@Test
	public void testGetStationById() throws NotFoundException, ParseException {
		StationsController controller = getMockedController();

		Station station = controller.getStation(3);
		
		assertEquals("station-3", station.getStationName());
	}
	
	@Test
	public void testGetStationNotFound() throws ParseException {
		StationsController controller = getMockedController();

		assertThrows(NotFoundException.class, () -> {
			controller.getStation(2131231212);
		});
	}
	
}
