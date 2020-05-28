package com.example.readinglist.controller;

import com.example.readinglist.dto.Book;
import com.example.readinglist.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/")
public class BookController {
    @Autowired
    private BookService bookService;

    @RequestMapping(value = "/addBook", method = RequestMethod.POST)
    @ResponseBody
    @Transactional
    public String addBook(Book book) {
        System.out.println("添加book："+book.toString());
        if (bookService.addBook(book) == 1){
            return "success";
        }
        return "error";
    }

    //事务测试(rollbackfor参数默认遇到runtime exception或error时回滚
    // propagation参数默认为required,即支持当前事务，如果不存在就回滚)
    @RequestMapping(value = "/addBookTest", method = RequestMethod.POST)
    @Transactional
    public String addBookTest(Book book) {
        log.info("#########  info  #########");
        System.out.println("添加book："+book.toString());
        if (bookService.addBook(book) == 1){
            return "success";
        }
        return "error";
    }

//    RestController与controller注解的区别
    //@Controller注解放类之前，然后若类中某个方法需要返回json数据，则需在该方法前添加@ResponseBody注解
//    @RequestMapping(value = "/allBook", method = RequestMethod.GET)
//    public List<Book> getAllBook(Model model) {
//        List<Book> readingList = bookService.getAllBook();
//        if (readingList != null) {
//            model.addAttribute("books", readingList);
//        }
//        return readingList;
//    }

    @RequestMapping(value = "/allBook", method = RequestMethod.GET)
    public String getAllBook(Model model) {
        List<Book> readingList = bookService.getAllBook();
        if (readingList != null) {
            model.addAttribute("books", readingList);
        }
        return "readingList";
    }

    @RequestMapping(value = "/searchBook", method = RequestMethod.POST)
    @ResponseBody
    public List<Book> getAllBookByReader(Integer readerId) {
        List<Book> readingList = bookService.getAllBookByReader(readerId);
        return readingList;
    }

    @RequestMapping(value = "/getOneBookWithTags", method = RequestMethod.POST)
    @ResponseBody
    public Book getOneBookWithTags(Integer id) {
        Book book = bookService.getOneBookWithTags(id);
        System.out.println("根据id获取书籍和标签"+book);
        return book;
    }

    @RequestMapping(value = "/delBook", method = RequestMethod.POST)
    @ResponseBody
    @Transactional
    public String deleteBook(Integer id) {
        if (bookService.deleteBook(id) == 1){
            return "success";
        }
        return "error";
    }

    @RequestMapping(value = "/updateBook", method = RequestMethod.POST)
    @ResponseBody
    @Transactional
    public String updateBook(Book book) {
        if (bookService.updateBook(book) == 1){
            return "success";
        }
        return "error";
    }

    //导出excel
    @RequestMapping(value = "UserExcelDownloads", method = RequestMethod.GET)
    public void userExcelDownloads(HttpServletResponse response) throws IOException {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("书本表");

        List<Book> bookList = bookService.getAllBook();

        String fileName = "bookinf"  + ".xls";//设置要导出的文件的名字
        //新增数据行，并且设置单元格数据

        int rowNum = 1;

        String[] headers = { "book_name", "author", "pub_time", "reader_id"};
        //headers表示excel表中第一行的表头

        HSSFRow row = sheet.createRow(0);
        //在excel表中添加表头

        for(int i=0;i<headers.length;i++){
            HSSFCell cell = row.createCell(i);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }

        //在表中存放查询到的数据放入对应的列
        System.out.println(bookList);
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        for (Book book : bookList) {
            HSSFRow row1 = sheet.createRow(rowNum);
            String pubTime = "";
            if (book.getPubTime() != null){
                pubTime = format.format(book.getPubTime());
            }
            row1.createCell(0).setCellValue(book.getBookName());
            row1.createCell(1).setCellValue(book.getAuthor());
            row1.createCell(2).setCellValue(pubTime);
            row1.createCell(3).setCellValue(book.getReaderId());
            rowNum++;
        }

        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
        response.flushBuffer();
        workbook.write(response.getOutputStream());
    }

    //导入excel
    @RequestMapping(value = "ExcelUploads", method = RequestMethod.GET)
    @ResponseBody
    @Transactional
    public String excelUploads() throws IOException {
        System.out.println("开始导入-------------------");
        int flag = 1;
        //获取要导入的文件
        HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(new File("D://Google下载//bookinf.xls")));
        //获取工作簿
        HSSFSheet sheet = workbook.getSheet("书本表");

        System.out.println("读取数据-------------------");
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            //获取行数据
            HSSFRow row = sheet.getRow(i);

            //将表格中的行数据封装到banner对象中
            Book book = new Book();
            book.setBookName(row.getCell(0).getStringCellValue());
            book.setAuthor(row.getCell(1).getStringCellValue());
            book.setPubTime(new Date());
            book.setReaderId(0);


            //调用插入方法
            if (bookService.addBook(book) != 1){
                return "error";
            }
        }
        System.out.println("导入结束-------------------");
        return "success";
    }
}
