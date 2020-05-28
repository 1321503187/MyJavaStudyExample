package com.example.readinglist.service.Impl;

import com.example.readinglist.dto.Book;
import com.example.readinglist.mapper.BookMapper;
import com.example.readinglist.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookMapper bookMapper;

    @Override
    public Book getBookById(Integer id) {
        return bookMapper.getBookById(id);
    }

    @Override
    public List<Book> getAllBook() {
        return bookMapper.getAllBook();
    }

    @Override
    public Integer addBook(Book book) {
        return bookMapper.addBook(book);
    }

    @Override
    public Integer updateBook(Book book) {
        return bookMapper.updateBook(book);
    }

    @Override
    public Integer deleteBook(Integer id) {
        return bookMapper.deleteBook(id);
    }

    @Override
    public List<Book> getAllBookByReader(Integer readerId) {
        return bookMapper.getAllBookByReader(readerId);
    }

    @Override
    public Book getOneBookWithTags(Integer id) {
        return bookMapper.getOneBookWithTags(id);
    }
}
