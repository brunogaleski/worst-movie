package com.brunogaleski.worstmovie.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.List;

@Entity
public class Producer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String name;

    @ManyToMany(mappedBy = "producers")
    private List<Nominee> nominees;

    public Producer() {
    }

    public Producer(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Nominee> getNominees() {
        return nominees;
    }

    public void setNominees(List<Nominee> nominees) {
        this.nominees = nominees;
    }
}
