package com.juniorkabore.filleul;

public class Contact {
	public int id;
	public String idFacebook;
	public String name;

	
	
	//constructeur
	public Contact(int id,String idFacebook, String name){
		super();
		this.id = id;
		this.idFacebook = idFacebook;
		this.name = name;	
	}
	
	public Contact(String idFacebook, String name){
		this.idFacebook = idFacebook;
		this.name = name;
	}
	
	public Contact(Contact c){
		this.idFacebook = c.idFacebook;
		this.name = c.name;
	}
	
	public Contact(){
		
	}

	

	@Override
	public String toString() {
		return "Contact [id=" + id + ", idFacebook=" + idFacebook + ", name="
				+ name + "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getIdFacebook() {
		return idFacebook;
	}

	public void setIdFacebook(String idFacebook) {
		this.idFacebook = idFacebook;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	

}
