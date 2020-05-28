package com.example.readinglist.service;

import com.example.readinglist.dto.Book;

import java.util.List;

public interface BookService {
    public Book getBookById(Integer id);

    public List<Book> getAllBook();

    public Integer addBook(Book book);

    public Integer updateBook(Book book);

    public Integer deleteBook(Integer id);

    public List<Book> getAllBookByReader(Integer readerId);

    public Book getOneBookWithTags(Integer id);
}
