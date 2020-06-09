package com.example.readinglist.service.Impl;

import com.example.readinglist.dto.Book;
import com.example.readinglist.mapper.BookMapper;
import com.example.readinglist.service.BookService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

    /**
     * 模糊查询实现
     * @param book
     * @return
     */
    @Override
    public List<Book> selectBook(Book book) {
        return bookMapper.selectBook(book);
    }

    /**
     * 通过pageHelper插件实现分页查询
     * @param pageNum 当前页
     * @param pageSize 每页的数量
     * @return
     */
    @Override
    public Page<Book> findByPage(Integer pageNum, Integer pageSize) {
        //用插件进行分页
        PageHelper.startPage(pageNum, pageSize);
        return bookMapper.findByPage();
    }

    @Override
    @Transactional
    public Integer addBook(Book book) {
        return bookMapper.addBook(book);
    }

    @Override
    @Transactional
    public Integer updateBook(Book book) {
        return bookMapper.updateBook(book);
    }

    @Override
    @Transactional
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

    /**
     * 字符流流实现将数据导出为txt
     * @param books
     * @param dir 文件路径
     * @param outFile 输出文件
     */
    public void exportTxt(List<Book> books, File dir, File outFile){
        BufferedWriter writer = null;
        try {
            //判断路径与文件是否存在
            if(!dir.exists()) {
                dir.mkdirs();
            }
            if(!outFile.isFile()) {
                outFile.createNewFile();
            }
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile,true), "utf-8"));
            for (Book book : books) {
                StringBuffer buffer = new StringBuffer();
                buffer.append(book.getId());
                buffer.append(",");
                buffer.append(book.getBookName());
                buffer.append(",");
                buffer.append(book.getAuthor());
                buffer.append(",");
                buffer.append(book.getPubTime());
                buffer.append("\r\n");

                writer.write(buffer.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                writer.flush();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * poi实现excel的导出
     * @param response
     * @param bookList
     * @throws IOException
     */
    public void userExcelDownloads(HttpServletResponse response, List<Book> bookList) throws IOException {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("书本表");

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

    /**
     * poi实现excel的导入
     * @return List<Book>，从excel中读取的数据集
     * @throws IOException
     */
    public List<Book> excelUploads() throws IOException {
        System.out.println("开始导入-------------------");
        int flag = 1;
        List<Book> books = new ArrayList<>();
        //获取要导入的文件
        HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(new File("D://Google下载//bookinf.xls")));
        //获取工作簿
        HSSFSheet sheet = workbook.getSheet("书本表");

        System.out.println("读取数据-------------------");
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            //获取行数据
            HSSFRow row = sheet.getRow(i);

            //将表格中的行数据封装到对象中
            Book book = new Book();
            book.setBookName(row.getCell(0).getStringCellValue());
            book.setAuthor(row.getCell(1).getStringCellValue());
            book.setPubTime(new Date());
            book.setReaderId(0);


            //调用插入方法
            books.add(book);
        }
        System.out.println("导入结束-------------------");
        return books;
    }
}
