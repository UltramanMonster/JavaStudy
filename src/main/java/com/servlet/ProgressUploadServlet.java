package com.servlet;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Administrator on 2017/6/2.
 */
@WebServlet(name = "ProgressUploadServlet",value = "/servlet/ProgressUploadServlet")
public class ProgressUploadServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        UploadStatus status = new UploadStatus();
        UploadListener listener = new UploadListener(status);//监听器
        request.getSession(true).setAttribute("uploadStatus",status);//把状态放到session里
        ServletFileUpload upload = new ServletFileUpload(new DiskFileItemFactory());//解析
        upload.setProgressListener(listener);

        try {

            List itemList = upload.parseRequest(request);//提交所有的参数
            for (Iterator it = itemList.iterator(); it.hasNext(); ){//遍历所有参数
                FileItem item = (FileItem) it.next();

                if (item.isFormField()){//如果是表单数据
                    System.out.println("FormField:" + item.getFieldName() + " = " + item.getString());
                }else {//否则就是上传的文件
                    String name = item.getName();
                    if (!name.equals("") && name != null){
                        String fileName = System.currentTimeMillis() + name.substring(name.lastIndexOf("."),name.length());
                        File saved = new File("D:\\upload_test",fileName);//创建文件对象
                        saved.getParentFile().mkdirs();//保证路径存在

                        InputStream ins = item.getInputStream();
                        OutputStream ous = new FileOutputStream(saved);

                        byte[] tmp = new byte[1024];
                        int len = -1;
                        while ((len = ins.read(tmp)) != -1){
                            ous.write(tmp, 0, len);
                        }

                        ous.close();
                        ins.close();
                        response.getWriter().println("已保存文件：" +saved);
                    }
                }

            }

        }catch (Exception e){
            response.getWriter().println("上传发生错误：" +e.getMessage());
            e.printStackTrace();
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setHeader("Cache-Control","no-store");//禁止浏览器缓存
        response.setHeader("Pragrma","no-cache");//禁止浏览器缓存
        response.setDateHeader("Expires",0);//禁止浏览器缓存

        //从session中读取上传信息
        UploadStatus status = (UploadStatus) request.getSession(true).getAttribute("uploadStatus");
        if (status == null){
            response.getWriter().println("没有上传信息");
            return;
        }

        long startTime = status.getStartTime();//上传开始时间
        long currentTime = System.currentTimeMillis();//现在时间
        long time = (currentTime - startTime) / 1000 + 1;//已传输的时间，单位：秒
        double velocity = ((double) status.getBytesRead()) / (double)time;//传输速度 单位：byte/s
        double totalTime = status.getContentLength() / velocity ;//估计总时间
        double timeLeft = totalTime - time ;//估计剩余时间
        int percent = (int) (100 * (double)status.getBytesRead() / (double)status.getContentLength()) ;//已完成百分比
        double length = ((double) status.getBytesRead()) / 1024 / 1024 ;//已完成数，单位：M
        double totalLength = ((double) status.getContentLength()) / 1024 / 1024 ;//总长度，单位：M

        //格式：百分比||已完成数（M）||文件总长度（M）||传输速率（K）||已用时间（S）||估计总时间（S）||
        //估计剩余时间（S）||正在上传第几个文件
        String value = percent + "||" + length + "||" + totalLength + "||" + velocity + "||"
                + time + "||" + totalTime + "||" + timeLeft + "||" + status.getItems();
        response.getWriter().println(value);//输出给浏览器进度条

    }
}
