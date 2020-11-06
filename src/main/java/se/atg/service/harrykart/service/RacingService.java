package se.atg.service.harrykart.service;

import org.springframework.stereotype.Service;
import se.atg.service.harrykart.model.request.HarryKart;
import se.atg.service.harrykart.model.request.Lane;
import se.atg.service.harrykart.model.request.Loop;
import se.atg.service.harrykart.model.request.Participant;
import se.atg.service.harrykart.model.response.RaceResult;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * provides racing service
 */
@Service
public class RacingService {

    /**
     * total steps in loop or distance of loop
     */
    final public static int LOOP_LENGTH = 1000;

    /**
     * collection of lanes
     * each thread represents a lane
     */
    private final List<Thread> lanes = new ArrayList<>();

    /**
     * invoke to start Racing
     * @param kart {@link HarryKart}
     * @param onComplete {@link OnComplete} event callback - invoked when race completes
     * @return {@link RaceResult}
     */
    public RaceResult startRace(HarryKart kart, OnComplete onComplete) {
        init(kart);
        setupRaceTrack(kart);
        whistleToStartRace();
        waitUntilRaceCompletes(kart);
        clearLanes();
        return onComplete.onRaceComplete();
    }

    /**
     * inits race by sorting different lists from request parameters:<br>
     *     - orders participators by lane ASC<br>
     *     - orders loops by number ASC<br>
     *     - orders lane by number ASC<br>
     * @param kart
     */
    private void init(HarryKart kart) {
        kart.getStartList().sort(Comparator.comparingInt(Participant::getLane));    //sorts participators by lane number
        kart.getPowerUps().sort(Comparator.comparingInt(Loop::getNumber));          //sorts loops by its number
        kart.getPowerUps().forEach(loop -> {                                        //sorts all lanes within loops by its number
            loop.getLanes().sort(Comparator.comparingInt(Lane::getNumber));
        });
    }

    /**
     * prepares race track by setting up {@link #lanes}<br>
     * and checks if race is completed by each participants
     * @param kart
     */
    private void setupRaceTrack(HarryKart kart) {
        int numberOfLoops = kart.getNumberOfLoops();
        setupLanesForEach(kart.getStartList(), (index, participant) -> {    //creates lane (thread) for all participants
            try{                                                            //then on each lanes created listener gets invoked
                int speed = participant.getBaseSpeed();                     //inside of thread created for participant, gets basespeed of participant
                for(int loop=0; loop<numberOfLoops; loop++) {
                    speed += getPower(loop, index, kart.getPowerUps());     //calculates speed for each loops
                    runParticipant(speed, participant);                     //let participant run to trace its race time
                }
            }finally {
                participant.setCrossedFinishLine(true);                     //marks race completion for participant
            }
        });
    }

    /**
     * invoked to actually start race
     */
    private void whistleToStartRace() {
        lanes.forEach(Thread::start);   //starts all created threads, ie starts race operation in lane
    }

    /**
     * invoked to wait for all threads to complete <br>
     * ie waits for race to complete
     * @param kart
     */
    private void waitUntilRaceCompletes(HarryKart kart) {
        WAIT: while(true) {                                             //blocks program flow until race completes
            for(Participant participant: kart.getStartList()) {
                if(!participant.isCrossedFinishLine()) continue WAIT;   //checks if race completed by each participants
            }
            break;
        }
    }

    /**
     * clears dirty lane by removing previously created threads
     */
    private void clearLanes() {
        lanes.removeAll(lanes);     //removes lanes ie threads from list
    }

    /**
     * creates lane ie thread for each participants, and the listener gets called when lane are assigned to participants.
     * @param participants list of participant
     * @param onLane {@link OnLane} listener
     */
    private void setupLanesForEach(List<Participant> participants, OnLane onLane) {
        int totalParticipant = participants.size();
        for(int laneIndex=0; laneIndex <totalParticipant; laneIndex++) {
            final int index = laneIndex;
            Thread lane = new Thread( () -> onLane.performOnLane(index, participants.get(index)) );
            lanes.add(lane);
        }
    }

    /**
     * invoked to read participant's power to calculate & update speed on each loop
     * @param loop loopIndex
     * @param participantId participant's index on list
     * @param powerUpInfo list of loop that is received from request
     * @return power
     */
    private int getPower(int loop, int participantId, List<Loop> powerUpInfo) {
        return (loop == 0) ? 0 : powerUpInfo.get(loop-1).getLanes().get(participantId).getPower();  //for first loop power = 0, so the calculation of speed gets base speed
                                                                                                    //but for later loops, power information is retrieved from provided list
    }

    /**
     * invoked to let participants run step by step with their updated speed upto {@link #LOOP_LENGTH}<br>
     * it traces out the race time of each participants
     * @param speed updated speed of participant
     * @param participant participant who is going to run
     */
    private void runParticipant(final int speed, Participant participant){
        for(int step = 0; step < LOOP_LENGTH; step += speed){
            participant.setRaceTime(participant.getRaceTime() + 1);     //calculation and tracing of race time
        }
    }

    /**
     * This is a event listener interface for race completion event
     */
    @FunctionalInterface
    public interface OnComplete {
        /**
         * gets invoked when race is completed to list out Result
         * @return {@link RaceResult}
         */
        RaceResult onRaceComplete();
    }

    /**
     * This is a event listener interface for lane creation event
     */
    @FunctionalInterface
    public interface OnLane {
        /**
         * gets invoked when lane is created and to let participant run over the created lane
         * @param index lane index
         * @param participant participant who is going to run
         */
        void performOnLane(int index, Participant participant);
    }
}
