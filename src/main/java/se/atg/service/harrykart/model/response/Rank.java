package se.atg.service.harrykart.model.response;

public class Rank {
	private int position;
	private String horse;

	public Rank(int position, String horse) {
		this.position = position;
		this.horse = horse;
	}

	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	public String getHorse() {
		return horse;
	}
	public void setHorse(String horse) {
	this.horse = horse;
}
}
