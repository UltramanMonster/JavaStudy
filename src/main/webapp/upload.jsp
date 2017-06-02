<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>文件上传</title>
</head>
<style>
    #progressBar{
        width: 400px;
        height: 12px;
        background: #FFFFFF;
        border: 1px solid #000000;
        padding: 1px;
    }
    #progressBarItem{
        width: 30%;
        height: 100%;
        background: #FF0000;
    }
</style>
<body>

<iframe name="upload_iframe" width="0" height="0"></iframe>
<form action="servlet/ProgressUploadServlet" method="post" enctype="multipart/form-data" target="upload_iframe" onsubmit="showStatus()">
    <input type="file" name="file1" style="width: 350px"><br/><br/>
    <input type="file" name="file2" style="width: 350px"><br/><br/>
    <input type="file" name="file3" style="width: 350px"><br/><br/>
    <input type="file" name="file4" style="width: 350px"><br/><br/>
    <input type="submit" value="开始上传" id="btnSubmit" name="btnSubmit">
</form>

<div id="status" style="display: none">
    上传进度条:
    <div id="progressBar"><div id="progressBarItem"></div></div>
    <div id="statusInfo"></div>
</div>

</body>
<script type="text/javascript">
    var _finished = true;//是否上传结束标志位
    function $(obj) {
        return document.getElementById(obj);//返回指定id的HTML元素
    }
    function showStatus() {//显示进度条
        _finished = false;
        $('status').style.display = 'block';//将隐藏的进度条显示
        $('progressBarItem').style.width = '1%';//设置进度条初始值为1%
        $('btnSubmit').disabled = true;//将提交按钮置灰，防止重复提交
        setTimeout("requestStatus()",1000);//一秒钟后执行requestStatus()方法，更新上传进度
    }
    function requestStatus() {//向服务器请求上传进度信息
        if (_finished) return;//如果已经结束，则返回
        var req = createRequest();//获取ajax请求
        req.open("GET","servlet/ProgressUploadServlet");//设置请求路径
        req.onreadystatechange = function () {
            callback(req);//请求完毕就执行callback(req)
        }
        req.send(null);//发送请求
        setTimeout("requestStatus()",1000)//一秒钟重新请求
    }
    function createRequest() {//返回ajax请求对象
        if (window.XMLHttpRequest){//Netscape浏览器
            return new XMLHttpRequest();
        }else {//IE浏览器
            try {
                return new ActiveXObject("Msxm12.XMLHTTP");
            }catch (e){
                return new ActiveXObject("Microsoft.XMLHTTP");
            }
        }
        return null;
    }
    function callback(req) {//刷新进度条
        if (req.readyState == 4){//请求结束后
            if (req.status != 200){
                debug("发生错误。req.status:" + req.status + "");
                return;
            }
            debug("status.jsp 返回值：" + req.responseText);//显示debug信息
            var ss = req.responseText.split("||");
            $('progressBarItem').style.width = '' + ss[0] + '%';
            $('statusInfo').innerHTML = '<br/>已完成百分比：' + ss[0] + '% <br/> 已完成数（M）' + ss[1]
            + '<br/>文件总长度（M）：' + ss[2] + '<br/>传输速率（K）：' + ss[3] + '<br/>已用时间（s）：'
            + ss[4] + '<br/>估计总时间（s）：' + ss[5] + '<br/>估计剩余时间（s）：' + ss[6] + '<br/>正在上传第几个文件：' + ss[7];

            if (ss[1] == ss[2]){
                _finished = true;
                $('statusInfo').innerHTML += "<br/><br/><br/>上传已完成。";
                $('btnSubmit').disabled = false;
            }

        }
    }
    function debug(obj) {//显示调试信息
        var div = document.createElement("DIV");
        div.innerHTML = "[debug]:" + obj;
        document.body.appendChild(div);
    }
</script>
</html>
