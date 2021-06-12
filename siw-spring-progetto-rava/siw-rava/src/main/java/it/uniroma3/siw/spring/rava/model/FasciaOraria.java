package it.uniroma3.siw.spring.rava.model;

public enum FasciaOraria {
	
	DIECI("10-12"),
	DODICI("12-14"),
	QUATTORDICI("14-16"),
	DICIOTTO("18-20"),
	VENTI("20-22"),
	VENTIDUE("22-24");
	
	private String displayValue;

	private FasciaOraria(String string) {
		this.displayValue = string;
	}
	
	public String getValue() {
		return this.displayValue;
	}

}
