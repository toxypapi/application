package com.juniorkabore.filleul;

public class BesoinModele {
	int id;
	String inte;
	String aide;
	String idFacebook;
	
	
	
	public BesoinModele() {
		super();
		// TODO Auto-generated constructor stub
	}



	public BesoinModele(int id, String inte, String aide, String idFacebook) {
		super();
		this.id = id;
		this.inte = inte;
		this.aide = aide;
		this.idFacebook = idFacebook;
	}
	
	public BesoinModele(String inte, String aide, String idFacebook) {
		this.inte = inte;
		this.aide = aide;
		this.idFacebook = idFacebook;
	}
	
	
	public BesoinModele(BesoinModele bm) {
		this.inte = bm.inte;
		this.aide = bm.aide;
		this.idFacebook = bm.idFacebook;
	}

	
	
	
	


	@Override
	public String toString() {
		return "BesoinModele [id=" + id + ", inte=" + inte + ", aide=" + aide
				+ ", idFacebook=" + idFacebook + "]";
	}



	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public String getInte() {
		return inte;
	}



	public void setInte(String inte) {
		this.inte = inte;
	}



	public String getAide() {
		return aide;
	}



	public void setAide(String aide) {
		this.aide = aide;
	}



	public String getIdFacebook() {
		return idFacebook;
	}



	public void setIdFacebook(String idFacebook) {
		this.idFacebook = idFacebook;
	}



	
	
	

}
