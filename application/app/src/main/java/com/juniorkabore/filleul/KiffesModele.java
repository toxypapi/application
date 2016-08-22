package com.juniorkabore.filleul;

public class KiffesModele {
	
	int id;
	String sport;
	String musique;
	String gaming;
	String danse;
	String virer;
	String culture;
	String idFacebook;
	
	
	public KiffesModele() {
		super();
		// TODO Auto-generated constructor stub
	}


	public KiffesModele(int id, String sport, String musique, String gaming,
			String danse, String virer, String culture, String idFacebook) {
		super();
		this.id = id;
		this.sport = sport;
		this.musique = musique;
		this.gaming = gaming;
		this.danse = danse;
		this.virer = virer;
		this.culture = culture;
		this.idFacebook = idFacebook;
	}
	
	public KiffesModele(String sport, String musique, String gaming,
			String danse, String virer, String culture, String idFacebook) {
		this.sport = sport;
		this.musique = musique;
		this.gaming = gaming;
		this.danse = danse;
		this.virer = virer;
		this.culture = culture;
		this.idFacebook = idFacebook;
	}
	
	
	public KiffesModele(KiffesModele km) {
		this.sport = km.sport;
		this.musique = km.musique;
		this.gaming = km.gaming;
		this.danse = km.danse;
		this.virer = km.virer;
		this.culture = km.culture;
		this.idFacebook = km.idFacebook;
	}

	
	
	

	public String getIdFacebook() {
		return idFacebook;
	}


	public void setIdFacebook(String idFacebook) {
		this.idFacebook = idFacebook;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getSport() {
		return sport;
	}


	public void setSport(String sport) {
		this.sport = sport;
	}


	public String getMusique() {
		return musique;
	}


	public void setMusique(String musique) {
		this.musique = musique;
	}


	public String getGaming() {
		return gaming;
	}


	public void setGaming(String gaming) {
		this.gaming = gaming;
	}


	public String getDanse() {
		return danse;
	}


	public void setDanse(String danse) {
		this.danse = danse;
	}


	public String getVirer() {
		return virer;
	}


	public void setVirer(String virer) {
		this.virer = virer;
	}


	public String getCulture() {
		return culture;
	}


	public void setCulture(String culture) {
		this.culture = culture;
	}
	
	
	
	
	
	

}
