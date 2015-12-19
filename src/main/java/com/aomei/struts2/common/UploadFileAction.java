package com.aomei.struts2.common;

import com.opensymphony.xwork2.ActionSupport;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Bill Tu(tujiyue/iwtxokhtd)
 * Jun 8, 2011[8:31:01 PM]
 *
 */
@Slf4j
@ParentPackage("common")
@Namespace("/upload")
@Component
public class UploadFileAction extends ActionSupport implements
        ServletRequestAware {
    /**
     *
     */
    private static final long serialVersionUID = -1896915260152387341L;
    private HttpServletRequest request;
    public void setServletRequest(HttpServletRequest req) {
        this.request=req;
    }


    private List<File> fileName;//这里的"fileName"一定要与表单中的文件域名相同
    private List<String> fileNameContentType;//格式同上"fileName"+ContentType
    private List<String> fileNameFileName;//格式同上"fileName"+FileName
    private String savePath="/upload";//文件上传后保存的路径

    public List<File> getFileName() {
        return fileName;
    }

    public void setFileName(List<File> fileName) {
        this.fileName = fileName;
    }

    public List<String> getFileNameContentType() {
        return fileNameContentType;
    }

    public void setFileNameContentType(List<String> fileNameContentType) {
        this.fileNameContentType = fileNameContentType;
    }

    public List<String> getFileNameFileName() {
        return fileNameFileName;
    }

    public void setFileNameFileName(List<String> fileNameFileName) {
        this.fileNameFileName = fileNameFileName;
    }

    @SuppressWarnings("deprecation")
    public String getSavePath() {
        return request.getRealPath(savePath);
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }
    @Getter
    @Setter
    private Map<String,Object> map=new HashMap<String, Object>();
    @Action(value = "uploadFile", results = { @Result(name = "success", type = "json", params = {
            "root", "map" }) })
    public String uploadFile() throws Exception {
        try{
            File dir=new File(getSavePath());
            if(!dir.exists()){
                dir.mkdirs();
            }
            List<File> files=getFileName();
            for(int i=0;i<files.size();i++){
                FileOutputStream fos=new FileOutputStream(getSavePath()+"//"+getFileNameFileName().get(i));
                FileInputStream fis=new FileInputStream(getFileName().get(i));
                byte []buffers=new byte[1024];
                int len=0;
                while((len=fis.read(buffers))!=-1){
                    fos.write(buffers,0,len);
                }
            }
            map.put("success","OK");
        }catch (Exception e){
            map.put("error","error");
            e.printStackTrace();
        }

        return SUCCESS;
    }

}
