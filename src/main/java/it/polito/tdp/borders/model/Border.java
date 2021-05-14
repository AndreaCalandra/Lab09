package it.polito.tdp.borders.model;

public class Border {
	
	private Country s1;
	private Country s2;
	private int anno;
	
	public Border(Country s1, Country s2, int anno) {
		super();
		this.s1 = s1;
		this.s2 = s2;
		this.anno = anno;
	}

	public Country getS1() {
		return s1;
	}

	public void setS1(Country s1) {
		this.s1 = s1;
	}

	public Country getS2() {
		return s2;
	}

	public void setS2(Country s2) {
		this.s2 = s2;
	}

	public int getAnno() {
		return anno;
	}

	public void setAnno(int anno) {
		this.anno = anno;
	}
	
	

}
