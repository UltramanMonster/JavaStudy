package com.servlet;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

/**
 * Created by Administrator on 2017/5/27.
 */
@WebServlet(name = "IdentityServlet", value = "/servlet/IdentityServlet")
public class IdentityServlet extends HttpServlet {

    public static final char[] CHARS = {'0','1','2','3','4','5','6','7','8','9',
        'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U',
        'V','W','X','Y','Z'};//随机字符字典

    public static Random random = new Random();

    /**
     * 获取6位随机数
     * @return
     */
    public static String getRandomString(){

        StringBuffer sb = new StringBuffer();
        for (int i = 0 ; i < 6 ; i++){
            sb.append(CHARS[random.nextInt(CHARS.length)]);
        }

        return sb.toString();
    }

    /**
     * 获取随机颜色
     * @return
     */
    public static Color getRandomColor(){
        return new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255));
    }

    /**
     * 返回某颜色的反色
     * @param color
     * @return
     */
    public static Color getReverseColor(Color color){
        return new Color(255-color.getRed(),255-color.getGreen(),255-color.getBlue());
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("image/jpeg");//设置输出类型
        String randomString = getRandomString();
        request.getSession(true).setAttribute("randomString",randomString);

        int width = 100;
        int height = 30;

        Color color = getRandomColor();//随机颜色，用于背景色
        Color reverse = getReverseColor(color);//反色，用于前景色

        BufferedImage bi = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);//创建一个彩色图片
        //获取绘图对象
        Graphics2D g = bi.createGraphics();
        g.setFont(new Font(Font.SANS_SERIF,Font.BOLD,16));//设置字体
        g.setColor(color);//设置颜色
        g.fillRect(0,0,width,height);//绘制背景
        g.setColor(reverse);
        g.drawString(randomString,18,20);
        //随机画噪音点
        for (int i = 0 , n = random.nextInt(100) ; i < n ; i++){
            g.drawRect(random.nextInt(width),random.nextInt(height),1,1);
        }

        ServletOutputStream out = response.getOutputStream();
        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
        encoder.encode(bi);//对图片进行编码
        out.flush();

    }

}
