package com.aomei.struts2.common;

import com.opensymphony.xwork2.ActionSupport;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@ParentPackage("common")
@Namespace("/fileupload")
@Component
@SuppressWarnings("serial")
public class FileAction extends ActionSupport {

    private File file;
    @Getter
    private File file1;
    private String fileFileName;
    private String fileFileContentType;

    private String message = "你已成功上传文件";

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setFile1(File file1){
        this.file1=file1;
        this.file=file1;
    }
    public File getFile() {

        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getFileFileName() {
        return fileFileName;
    }

    public void setFileFileName(String fileFileName) {
        this.fileFileName = fileFileName;
    }

    public String getFileFileContentType() {
        return fileFileContentType;
    }

    public void setFileFileContentType(String fileFileContentType) {
        this.fileFileContentType = fileFileContentType;
    }

    @Getter
    @Setter
    private Map<String,Object> map=new HashMap<String, Object>();
    @Action(value = "uploadFile", results = { @Result(name = "success", type = "json", params = {
            "root", "map" }) })
    public String fileUploadAction() throws Exception {

        String path = ServletActionContext.getRequest().getRealPath("/upload");

        try {
            File f = this.getFile();
            if(this.getFileFileName().endsWith(".exe")){
                message="对不起,你上传的文件格式不允许!!!";
                return ERROR;
            }
            FileInputStream inputStream = new FileInputStream(f);
            String suff=this.getFileFileName().substring(this.getFileFileName().lastIndexOf("."));
            String storeName= System.nanoTime()+suff;
            FileOutputStream outputStream = new FileOutputStream(path + "/"+storeName);
            byte[] buf = new byte[1024];
            int length = 0;
            while ((length = inputStream.read(buf)) != -1) {
                outputStream.write(buf, 0, length);
            }
            inputStream.close();
            outputStream.flush();
            map.put("sotrePath",("upload/"+storeName));
        } catch (Exception e) {
            e.printStackTrace();
            message = "对不起,文件上传失败了!!!!";
        }
        map.put("msg",message);

        return SUCCESS;
    }

}