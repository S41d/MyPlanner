package com.myplanner.myplanner.Model;


public class Tache {

    private int id;
    private String titreTache;
    private String descriptionTache;
    private String jourTache;
    private String heureTache;

    public Tache(int id, String titreTache, String descriptionTache, String jourTache, String heureTache) {
        this.id = id;
        this.titreTache = titreTache;
        this.descriptionTache = descriptionTache;
        this.jourTache = jourTache;
        this.heureTache = heureTache;
    }

    public Tache(String titreTache, String descriptionTache, String jourTache, String heureTache) {
        this.titreTache = titreTache;
        this.descriptionTache = descriptionTache;
        this.jourTache = jourTache;
        this.heureTache = heureTache;
    }

    public Tache() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitreTache() {
        return titreTache;
    }

    public void setTitreTache(String titreTache) {
        this.titreTache = titreTache;
    }

    public String getDescriptionTache() {
        return descriptionTache;
    }

    public void setDescriptionTache(String descriptionTache) {
        this.descriptionTache = descriptionTache;
    }

    public String getJourTache() {
        return jourTache;
    }

    public void setJourTache(String jourTache) {
        this.jourTache = jourTache;
    }

    public String getHeureTache() {
        return heureTache;
    }

    public void setHeureTache(String heureTache) {
        this.heureTache = heureTache;
    }

}
