package com.qrCode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.io.File;
import java.nio.file.Path;
import java.util.HashMap;

/**
 * 生成二维码
 */
public class QrCodeCreate {

    public static void createArCode(){

        int width = 300;
        int height = 300;
        String format = "png";
        String content = "www.baidu.com";

        //定义二维码的参数
        HashMap hashMap = new HashMap();
        hashMap.put(EncodeHintType.CHARACTER_SET,"utf-8");
        hashMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
        hashMap.put(EncodeHintType.MARGIN,2);

        try {
            BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE,width,height,hashMap);
            Path file = new File("D:/img.png").toPath();
            MatrixToImageWriter.writeToPath(bitMatrix,format,file);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args){
        QrCodeCreate.createArCode();
    }

}
