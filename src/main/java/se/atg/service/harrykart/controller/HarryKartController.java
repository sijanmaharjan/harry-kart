package se.atg.service.harrykart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.atg.service.harrykart.model.request.HarryKart;
import se.atg.service.harrykart.model.response.RaceResult;
import se.atg.service.harrykart.service.RacingService;
import se.atg.service.harrykart.service.ResultRankingService;

@RestController
@RequestMapping("/api")
public class HarryKartController {

	@Autowired
	private RacingService racingService;
	@Autowired
	private ResultRankingService resultRankingService;

    @PostMapping( value = "/play", consumes = MediaType.APPLICATION_XML_VALUE)
    public RaceResult playHarryKart(@RequestBody HarryKart kart) {
    	return racingService.startRace( kart, () -> resultRankingService.getResult(kart));
    }
}

