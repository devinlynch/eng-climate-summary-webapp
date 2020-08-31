package com.dlynch.climate.controllers.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dlynch.climate.exceptions.NotFoundException;
import com.dlynch.climate.models.Station;
import com.dlynch.climate.services.StationsService;

@Controller
@RequestMapping("/stations")
public class StationDetailsController {

	@Autowired
	private StationsService service;

	@GetMapping
	@RequestMapping("/{id}")
	public String getHomePage(Model model, @PathVariable Integer id) throws NotFoundException {
		Station station = getService().getStation(id);
		if(station == null) {
			throw new NotFoundException();
		}
		
		model.addAttribute("station", station);
		return "stationDetails";
	}

	public StationsService getService() {
		return service;
	}

	public void setService(StationsService service) {
		this.service = service;
	}

}
