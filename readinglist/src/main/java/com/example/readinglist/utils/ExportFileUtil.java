package com.example.readinglist.utils;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ExportFileUtil<T> {
    /**
     * 通过泛型与字符流实现将不同服务单数据导出为txt
     * @param type 服务单类型
     * @param path 文件夹创建路径
     * @param list 服务单数据
     */
    public void exportTxt(String type,String path, List<T> list){
        BufferedWriter writer = null;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String today = dateFormat.format(new Date());
        //文件夹路径
        String pathName = path + today;
        //文件名
        String fileName = type + "-" + today + ".txt";
        File dir = new File(pathName);
        File outFile = new File(dir,fileName);
        //判断路径与文件是否存在
        if(!dir.exists()) {
            dir.mkdirs();
        }
        try {
            if(!outFile.isFile()) {
                outFile.createNewFile();
            }
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile,true), "utf-8"));
            for (T t : list) {
                writer.write(t.toString() + "\r\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if (writer != null){
                    writer.flush();
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
