package com.example.readinglist.controller;

import com.example.readinglist.dto.Book;
import com.example.readinglist.service.BookService;
import com.example.readinglist.utils.ExportFileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/")
public class BookController {
    @Autowired
    private BookService bookService;

    @RequestMapping(value = "/addBook", method = RequestMethod.POST)
    @ResponseBody
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
    public String deleteBook(Integer id) {
        if (bookService.deleteBook(id) == 1){
            return "success";
        }
        return "error";
    }

    @RequestMapping(value = "/updateBook", method = RequestMethod.POST)
    @ResponseBody
    public String updateBook(Book book) {
        if (bookService.updateBook(book) == 1){
            return "success";
        }
        return "error";
    }

    @GetMapping("/exportBookTxt")
    @ResponseBody
    public void exportBookTxt() {
        List<Book> books = bookService.getAllBook();
//        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        String today = dateFormat.format(new Date());
//        String pathName = "D:\\Work\\workSpace\\" + today;
//        String fileName = "JD-" + today + ".txt";
//        File dir = new File(pathName);
//        File outFile = new File(dir,fileName);

        if (books != null) {
            ExportFileUtil<Book> exportFileUtil = new ExportFileUtil<>();
            exportFileUtil.exportTxt("JD","D:\\Work\\workSpace\\", books);
        }
    }

    //导出excel
    @RequestMapping(value = "UserExcelDownloads", method = RequestMethod.GET)
    public void userExcelDownloads(HttpServletResponse response) {
        List<Book> bookList = bookService.getAllBook();
        if (bookList != null) {
            try {
                bookService.userExcelDownloads(response, bookList);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //导入excel
    @RequestMapping(value = "ExcelUploads", method = RequestMethod.GET)
    @ResponseBody
    public String excelUploads() {
        List<Book> books = new ArrayList<>();
        try {
            books = bookService.excelUploads();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //调用插入方法
        for (Book book : books){
            if (bookService.addBook(book) != 1){
                return "error";
            }
        }
        return "success";
    }
}
