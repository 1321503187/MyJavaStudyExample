package com.example.readinglist.dto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Tag {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer TId;
    private String TName;

    public Tag() {
    }

    public Tag(String TName) {
        this.TName = TName;
    }

    public Integer getTId() {
        return TId;
    }

    public void setTId(Integer TId) {
        TId = TId;
    }

    public String getTName() {
        return TName;
    }

    public void setTName(String TName) {
        this.TName = TName;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "TId=" + TId +
                ", TName='" + TName + '\'' +
                '}';
    }
}
