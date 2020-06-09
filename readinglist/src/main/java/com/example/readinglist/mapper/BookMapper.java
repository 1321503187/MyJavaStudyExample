package com.example.readinglist.mapper;

import com.example.readinglist.dto.Book;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
@MapperScan("com.example.readinglist.mapper")
public interface BookMapper {

    public Book getBookById(@Param("id") Integer id);

    public List<Book> getAllBook();

    public List<Book> selectBook(Book book);

    public Page<Book> findByPage();

    public Book getOneBookWithTags(@Param("id") Integer id);

    public List<Book> getAllBookByReader(@Param("readerId") Integer readerId);

    public Integer addBook(Book book);

    public Integer updateBook(Book book);

    public Integer deleteBook(@Param("id") Integer id);
}
