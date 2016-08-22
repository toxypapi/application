package com.juniorkabore.filleul;

/**
 * Created by juniorkabore on 04/06/15.
 */
public class AttributionModele {
    int id;
    String parrainIdFacebook;
    String filleulIdFacebook;

    public AttributionModele() {
    }

    public AttributionModele(int id, String filleulIdFacebook, String parrainIdFacebook) {
        this.id = id;
        this.filleulIdFacebook = filleulIdFacebook;
        this.parrainIdFacebook = parrainIdFacebook;
    }

    public AttributionModele(String filleulIdFacebook, String parrainIdFacebook){
        this.filleulIdFacebook = filleulIdFacebook;
        this.parrainIdFacebook = parrainIdFacebook;
    }

    public AttributionModele(AttributionModele am){
        this.filleulIdFacebook = am.filleulIdFacebook;
        this.parrainIdFacebook = am.parrainIdFacebook;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getParrainIdFacebook() {
        return parrainIdFacebook;
    }

    public void setParrainIdFacebook(String parrainIdFacebook) {
        this.parrainIdFacebook = parrainIdFacebook;
    }

    public String getFilleulIdFacebook() {
        return filleulIdFacebook;
    }

    public void setFilleulIdFacebook(String filleulIdFacebook) {
        this.filleulIdFacebook = filleulIdFacebook;
    }


}
