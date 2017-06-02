package com.servlet;

import org.apache.commons.fileupload.ProgressListener;

/**
 * 文件上传监听
 * Created by Administrator on 2017/6/2.
 */
public class UploadListener implements ProgressListener{

    private UploadStatus status;//记录上传信息的Java Bean

    public UploadListener(UploadStatus status){
        this.status = status;
    }

    public void update(long bytesRead, long contentLength, int items) {

        status.setBytesRead(bytesRead);
        status.setContentLength(contentLength);
        status.setItems(items);

    }
}
