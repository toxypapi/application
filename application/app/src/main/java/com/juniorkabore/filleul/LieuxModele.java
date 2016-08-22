package com.juniorkabore.filleul;

public class LieuxModele {
	int id;
	String univ;
	String idFacebook;
	
	
	public LieuxModele() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public LieuxModele(int id, String univ,String idFacebook) {
		this.id = id;
		this.univ = univ;
		this.idFacebook = idFacebook;
	}
	
	
	public LieuxModele( String univ,String idFacebook) {
		this.univ = univ;
		this.idFacebook = idFacebook;
	}
	
	public LieuxModele(LieuxModele lm) {
		this.univ = lm.univ;
		this.idFacebook = lm.idFacebook;
	}


	public int getId() {
		return id;
	}


	public String getIdFacebook() {
		return idFacebook;
	}


	public void setIdFacebook(String idFacebook) {
		this.idFacebook = idFacebook;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getUniv() {
		return univ;
	}


	public void setUniv(String univ) {
		this.univ = univ;
	}
	
	
	
	

}
