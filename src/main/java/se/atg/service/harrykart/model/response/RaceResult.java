package se.atg.service.harrykart.model.response;

import java.util.List;

public class RaceResult {
	private List<Rank> ranking;

	public RaceResult() {
	}

	public List<Rank> getRanking() {
		return ranking;
	}

	public void setRanking(List<Rank> ranking) {
		this.ranking = ranking;
	}
	

}
