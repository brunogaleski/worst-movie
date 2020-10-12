package com.brunogaleski.worstmovie.model;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.util.List;

@Entity
public class Nominee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private int year;

    @Column
    private String title;

    @Column
    private String studios;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name="nominee_producer",
            joinColumns= { @JoinColumn(name="nominee_id") },
            inverseJoinColumns= { @JoinColumn(name="producer_id")})
    private List<Producer> producers;

    @Column
    private boolean winner;

    public Nominee() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStudios() {
        return studios;
    }

    public void setStudios(String studios) {
        this.studios = studios;
    }

    public List<Producer> getProducers() {
        return producers;
    }

    public void setProducers(List<Producer> producers) {
        this.producers = producers;
    }

    public boolean isWinner() {
        return winner;
    }

    public void setWinner(boolean winner) {
        this.winner = winner;
    }
}
