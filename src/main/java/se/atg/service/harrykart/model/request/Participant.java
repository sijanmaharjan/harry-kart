package se.atg.service.harrykart.model.request;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class Participant {
	@JacksonXmlProperty(localName =  "lane")
	private int lane;
	@JacksonXmlProperty(localName =  "name")
	private String name;
	@JacksonXmlProperty(localName =  "baseSpeed")
	private int baseSpeed;

	private int raceTime = 0;
	private boolean crossedFinishLine = false;

	public Participant() {
	}

	public Participant(int lane, String name, int baseSpeed, int raceTime) {
		this.lane = lane;
		this.name = name;
		this.baseSpeed = baseSpeed;
		this.raceTime = raceTime;
	}

	public int getLane() {
		return lane;
	}

	public void setLane(int lane) {
		this.lane = lane;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getBaseSpeed() {
		return baseSpeed;
	}

	public void setBaseSpeed(int baseSpeed) {
		this.baseSpeed = baseSpeed;
	}

	public int getRaceTime() {
		return raceTime;
	}

	public void setRaceTime(int raceTime) {
		this.raceTime = raceTime;
	}

	public boolean isCrossedFinishLine() {
		return crossedFinishLine;
	}

	public void setCrossedFinishLine(boolean crossedFinishLine) {
		this.crossedFinishLine = crossedFinishLine;
	}
}
