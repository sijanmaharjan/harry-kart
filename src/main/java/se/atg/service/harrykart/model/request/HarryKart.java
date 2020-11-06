package se.atg.service.harrykart.model.request;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

@JacksonXmlRootElement(localName = "harryKart")
public class HarryKart {
	@JacksonXmlProperty(localName =  "numberOfLoops")
	private int numberOfLoops;
	@JacksonXmlElementWrapper(localName =  "startList", useWrapping = true)
	private List<Participant> startList;
	@JacksonXmlElementWrapper(localName =  "powerUps", useWrapping = true)
	private List<Loop> powerUps;

	public HarryKart() {
	}

	public int getNumberOfLoops() {
		return numberOfLoops;
	}

	public void setNumberOfLoops(int numberOfLoops) {
		this.numberOfLoops = numberOfLoops;
	}

	public List<Participant> getStartList() {
		return startList;
	}

	public void setStartList(List<Participant> startList) {
		this.startList = startList;
	}

	public List<Loop> getPowerUps() {
		return powerUps;
	}

	public void setPowerUps(List<Loop> powerUps) {
		this.powerUps = powerUps;
	}
}
