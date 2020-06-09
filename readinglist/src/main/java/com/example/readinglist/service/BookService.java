package com.example.readinglist.service;

import com.example.readinglist.dto.Book;
import com.github.pagehelper.Page;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;

public interface BookService {
    public Book getBookById(Integer id);

    public List<Book> getAllBook();

    public List<Book> selectBook(Book book);

    public Page<Book> findByPage(Integer pageNum, Integer pageSize);

    public Integer addBook(Book book);

    public Integer updateBook(Book book);

    public Integer deleteBook(Integer id);

    public List<Book> getAllBookByReader(Integer readerId);

    public Book getOneBookWithTags(Integer id);

    public void exportTxt(List<Book> books, File dir, File outFile);

    public void userExcelDownloads(HttpServletResponse response, List<Book> bookList) throws IOException;

    public List<Book> excelUploads() throws IOException;
}
