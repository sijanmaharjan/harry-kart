package se.atg.service.harrykart.model.request;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.List;

public class Loop {
    @JacksonXmlProperty(localName =  "number", isAttribute = true)
    private int number;
    @JacksonXmlElementWrapper(localName =  "lane", useWrapping = false)
    private List<Lane> lane;

    public Loop() {
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public List<Lane> getLanes() {
        return lane;
    }

    public void setLanes(List<Lane> lanes) {
        this.lane = lanes;
    }
}
