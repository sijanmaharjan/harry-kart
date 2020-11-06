package se.atg.service.harrykart.service;

import org.springframework.stereotype.Service;
import se.atg.service.harrykart.model.request.HarryKart;
import se.atg.service.harrykart.model.request.Participant;
import se.atg.service.harrykart.model.response.RaceResult;
import se.atg.service.harrykart.model.response.Rank;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * provides ranking service
 */
@Service
public class ResultRankingService {

    /**
     * total participants to be selected from race
     */
    final public static Integer TOTAL_SELECTION = 3;

    /**
     * invoke to get result with rankings
     * @param kart
     * @return {@link RaceResult}
     */
    public RaceResult getResult(HarryKart kart) {
        RaceResult raceResult = new RaceResult();
        List<Rank> rankList = new ArrayList<>();
        List<Participant> participants = kart.getStartList();

        participants.sort(Comparator.comparingInt(Participant::getRaceTime));   //sorts participants by race time
                                                                                // #Note: participants with less race time wins
        participants.stream().limit(TOTAL_SELECTION).forEach(participant -> {   //selects winners from sorted list and performs foreach
            rankList.add(new Rank(                                              //for each winner, creates new Rank and adds to list
                    participants.indexOf(participant)+1,
                    participant.getName()
            ));
        });
        raceResult.setRanking(rankList);
        return raceResult;
    }
}
