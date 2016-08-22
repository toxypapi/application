package com.juniorkabore.filleul;

public class StatutModele {
	
	int id;
	String statut;
	String idFacebook;
	
	
	public StatutModele() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public StatutModele(int id, String statut, String idFacebook) {
		super();
		this.id = id;
		this.statut = statut;
		this.idFacebook = idFacebook;
	}
	
	public StatutModele( String statut, String idFacebook) {
		this.statut = statut;
		this.idFacebook = idFacebook;
	}
	
	public StatutModele(StatutModele sm) {
		this.statut = sm.statut;
		this.idFacebook = sm.idFacebook;
	}

	
	
	

	@Override
	public String toString() {
		return "StatutModele [id=" + id + ", statut=" + statut
				+ ", idFacebook=" + idFacebook + "]";
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getStatut() {
		return statut;
	}


	public void setStatut(String statut) {
		this.statut = statut;
	}


	public String getIdFacebook() {
		return idFacebook;
	}


	public void setIdFacebook(String idFacebook) {
		this.idFacebook = idFacebook;
	}
	
	
	
	
	
	
	
	
	

}
