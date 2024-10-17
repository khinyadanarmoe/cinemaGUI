package com.cinema.model;

public class Seat {
	private int id;
	private String title;
	private String seatType = "normal";
	private Theatre theatre;

	public int getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getSeatType() {
		return seatType;
	}



	public void setId(int id) {
		this.id = id;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setSeatType(String seatType) {
		this.seatType = seatType;
	}

	

	@Override
	public String toString() {
		return "Seat [id=" + id + ", title=" + title + ", seatType=" + seatType + ", threatre=" + theatre + "]";
	}

	public Theatre getTheatre() {
		return theatre;
	}

	public void setTheatre(Theatre theatre) {
		this.theatre = theatre;
	}

	public String[] toArray() {
		String[] arr = new String[3];
		arr[0] = this.id+"";
		arr[1] = this.title;
		arr[2] = this.getTheatre().getId()+"";
		return arr;
	}

	public boolean isNormal() {

		return this.seatType.equalsIgnoreCase("normal");
	}

	public boolean isVIP() {

		return this.seatType.equalsIgnoreCase("vip");
	}

}
