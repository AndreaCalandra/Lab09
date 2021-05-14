package it.polito.tdp.borders.model;

public class Country {
	
	private String abbreviazione;
	private int id;
	private String s;
	public Country(String abbreviazione, int id, String s) {
		super();
		this.abbreviazione = abbreviazione;
		this.id = id;
		this.s = s;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getS() {
		return s;
	}
	public void setS(String s) {
		this.s = s;
	}
	public String getAbbreviazione() {
		return abbreviazione;
	}
	public void setAbbreviazione(String abbreviazione) {
		this.abbreviazione = abbreviazione;
	}
	@Override
	public String toString() {
		return s;
	}
	

}
