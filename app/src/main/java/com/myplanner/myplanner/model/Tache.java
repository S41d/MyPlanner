package com.myplanner.myplanner.model;


public class Tache {

    private int id;
    private String titreTache;
    private String descriptionTache;
    private String jourTache;
    private String heureTache;
    private int idUser;

    public Tache(int id, String titreTache, String descriptionTache, String jourTache, String heureTache, int idUser) {
        this.id = id;
        this.titreTache = titreTache;
        this.descriptionTache = descriptionTache;
        this.jourTache = jourTache;
        this.heureTache = heureTache;
        this.idUser = idUser;
    }

    public Tache(String titreTache, String descriptionTache, String jourTache, String heureTache, int idUser) {
        this.titreTache = titreTache;
        this.descriptionTache = descriptionTache;
        this.jourTache = jourTache;
        this.heureTache = heureTache;
        this.idUser = idUser;
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

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }
}
