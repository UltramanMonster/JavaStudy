package com.servlet;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/5/27.
 */
@WebServlet(name = "UploadServlet", value = "/servlet/UploadServlet")
public class UploadServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("utf-8");
        response.getWriter().println("请以 POST 方式上传文件");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html");

        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">");
        out.println("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
        out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />");
        out.println("<head><title>A Servlet</title></head>");
        out.println("<body>");

        File file = null;
        DiskFileUpload diskFileUpload = new DiskFileUpload();
        try {
            List<FileItem> list = diskFileUpload.parseRequest(request);
            for (FileItem fileItem : list){
                File remoteFile = new File(new String(fileItem.getName().getBytes(),"UTF-8"));
                out.println("遍历到 file ... <br/>");
                out.println("客户端文件位置：" + remoteFile.getAbsolutePath() + "<br/>");
                String newFileName = String.valueOf(new Date().getTime()) + ".jpg";
                file = new File(this.getServletContext().getRealPath("upload"),newFileName);
                file.getParentFile().mkdirs();
                file.createNewFile();

                InputStream ins = fileItem.getInputStream();
                OutputStream ous = new FileOutputStream(file);

                byte[] buffer = new byte[1024];
                int len = 0;
                while ((len = ins.read(buffer)) > -1){
                    ous.write(buffer,0,len);
                }

                out.println("文件已保存。<br/>");

                ous.close();
                ins.close();

            }
        }catch (Exception e){
            e.printStackTrace();
        }

        if (file != null){
            out.println("<div class='line'>");
            out.println("<div align='left' class='leftDiv'>file:</div>");
            out.println("<div align='left' class='rightDiv'>");
            out.println("<a href='" + request.getContextPath() + "/upload/" + file.getName() + "' target=_blank>" +
                     file.getName() + "</a>");
            out.println("</div>");
            out.println("</div>");
        }

        out.println("</body>");
        out.println("</html>");
        out.flush();
        out.close();

    }

}
