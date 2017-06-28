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
    private int personid;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int skid;

}
