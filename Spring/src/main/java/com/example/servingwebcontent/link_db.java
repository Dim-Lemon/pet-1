package com.example.servingwebcontent;

import javax.persistence.*;

@Entity
public class link_db {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String link;
    @Column(name = "abbName")
    private String abbName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getAbbName() {
        return abbName;
    }

    public void setAbbName(String abbName) {
        this.abbName = abbName;
    }

    public link_db() {
    }

    public link_db(String link, String abbName) {
        this.link = link;
        this.abbName = abbName;
    }
}
