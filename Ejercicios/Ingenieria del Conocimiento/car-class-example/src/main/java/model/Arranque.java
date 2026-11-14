package model;

public enum Arranque {
 
	Gira,
	NoGira,
	NoInformado;
	
	public Boolean isa(Arranque arranque) {
		return this.equals(arranque);	
	}
}
