package com.example.readinglist.dto;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Book {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
    private String bookName;
    private String author;
    private Date pubTime;
    private Integer readerId;

    @Transient
    private Reader reader;
    @Transient
    private List<Tag> tags;

    public Book() {
    }

    public Book(String bookName, String author, Date pubTime, Integer readerId, Reader reader) {
        this.bookName = bookName;
        this.author = author;
        this.pubTime = pubTime;
        this.readerId = readerId;
        this.reader = reader;
    }

    public Book(String bookName, String author, Date pubTime, Integer readerId, Reader reader, List<Tag> tags) {
        this.bookName = bookName;
        this.author = author;
        this.pubTime = pubTime;
        this.readerId = readerId;
        this.reader = reader;
        this.tags = tags;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getPubTime() {
        return pubTime;
    }

    public void setPubTime(Date pubTime) {
        this.pubTime = pubTime;
    }

    public Integer getReaderId() {
        return readerId;
    }

    public void setReaderId(Integer readerId) {
        this.readerId = readerId;
    }

    public Reader getReader() {
        return reader;
    }

    public void setReader(Reader reader) {
        this.reader = reader;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", bookName='" + bookName + '\'' +
                ", author='" + author + '\'' +
                ", pubTime=" + pubTime +
                ", readerId=" + readerId +
                ", reader=" + reader +
                ", tags=" + tags +
                '}';
    }
}
