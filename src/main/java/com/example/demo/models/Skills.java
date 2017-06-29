package com.example.demo.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Size;

/**
 * Created by student on 6/28/17.
 */
@Entity
public class Skills {

    @Size(max=25)
    private String skill;
    @Size(max=25)
    private String proficiency;

    private int endorsements;

    private int personid;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int skid;

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public String getProficiency() {
        return proficiency;
    }

    public void setProficiency(String proficiency) {
        this.proficiency = proficiency;
    }

    public int getPersonid() {
        return personid;
    }

    public void setPersonid(int personid) {
        this.personid = personid;
    }

    public int getSkid() {
        return skid;
    }

    public void setSkid(int skid) {
        this.skid = skid;
    }

    public int getEndorsements() {
        return endorsements;
    }

    public void setEndorsements(int endorsements) {
        this.endorsements = endorsements;
    }
}
