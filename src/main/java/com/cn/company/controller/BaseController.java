package com.cn.company.controller;

import com.cn.company.util.upload.ProgressEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.jni.FileInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * BaseController class
 *
 * @author bingyu wu
 *         Date: 2018/1/15
 *         Time: 下午1:32
 */
@Controller
public class BaseController {
    
    /** logger */
    private static final Logger logger = LoggerFactory.getLogger(BaseController.class);

    public static String localPath="/Users/wubingyu/IdeaProjects/365company_demo/UploadProject/upload/";


    @Autowired
    ServletContext context;

    @RequestMapping("/")
    public String index(Model model) {
        model.addAttribute("name", "先生或者女生");
        return "index";
    }


    @RequestMapping("/uploadPage")
    public String uploadPage() {
        return "uploadPage";
    }

    @RequestMapping("/uploadPatchPage")
    public String uploadPatchPage() {
        return "uploadPatchPage";
    }

    @RequestMapping("/uploadVideo")
    @ResponseBody
    public String uploadVideo(MultipartFile file)throws Exception{
        if (!file.isEmpty()){
            File localFile=new File("/Users/wubingyu/IdeaProjects/365company_demo/UploadProject/upload/"+file.getOriginalFilename());
            try {
                file.transferTo(localFile);
            } catch (IOException e) {
                logger.error("error",e);
            }
            return "success";
        }else{
            return "error";
        }


    }

    @RequestMapping("/uploadPatchVideo")
    @ResponseBody
    public String uploadPatchVideo(){

        return "uploadPatchVideo";
    }

    @RequestMapping("/getProgress")
    @ResponseBody
    public String getProgress(HttpServletRequest request, HttpServletResponse response){

        String key="status";

        if (request.getSession().getAttribute(key)==null){
            logger.info("get into the controller");
            return "0";
        }

        ProgressEntity progressEntity=(ProgressEntity)request.getSession().getAttribute("status");

        logger.info(progressEntity.toString());
        float tmp=(float)progressEntity.getpBytesRead();
        float result=tmp/progressEntity.getpContentLength()*100;
        return String.valueOf(result);
    }


    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> uploadFile(MultipartFile file) {

        try {
            logger.info("the controller is ready for the upload file");
            File localFile=new File("/Users/wubingyu/IdeaProjects/365company_demo/UploadProject/upload/"+file.getOriginalFilename());
            try {
                file.transferTo(localFile);
            } catch (IOException e) {
                logger.error("error",e);
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    } // method uploadFile

    @GetMapping(value = "/jqueryFile")
    public String jqueryFile(){
        return "jqueryFile";
    }



    @RequestMapping(value = "/fileupload", headers = ("content-type=multipart/*"), method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<FileInfo> upload(@RequestParam("file") MultipartFile inputFile) {
        FileInfo fileInfo = new FileInfo();
        HttpHeaders headers = new HttpHeaders();
        if (!inputFile.isEmpty()) {
            try {
                String originalFilename = inputFile.getOriginalFilename();
                File destinationFile = new File(
                        context.getRealPath("C:/Users/kamel/workspace/credit_app/uploaded") + File.separator + originalFilename);
                inputFile.transferTo(destinationFile);
                headers.add("File Uploaded Successfully - ", originalFilename);
                return new ResponseEntity<FileInfo>(fileInfo, headers, HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<FileInfo>(HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<FileInfo>(HttpStatus.BAD_REQUEST);
        }
    }



    @RequestMapping(value = "/beakpointUpload")
    @ResponseBody
    public void beakpointUpload(HttpServletRequest request, HttpServletResponse response, Model model)throws Exception{
        logger.info("the new controller");
        ServletFileUpload upload = new ServletFileUpload(new DiskFileItemFactory());
        List<FileItem> list = upload.parseRequest(request);
        for (FileItem item : list) {
            if (!item.isFormField()) {
                String fileName = item.getName();
                logger.info("fileName"+fileName);
                String realSavePath = localPath+"real/";
                String tempSavePath = localPath+"temp/";
                File realFile = new File(realSavePath + fileName);
                File tempFile = new File(tempSavePath + fileName);
                if (realFile.exists()) {
                    //文件已经传输成功
                    System.out.println("文件已经存在，请不要重复上传");
                } else {
                    InputStream in = item.getInputStream();
                    long needSkipBytes = 0;
                    if (tempFile.exists()) {
                        //续传
                        needSkipBytes = tempFile.length();
                    } else {//第一次传
                        tempFile.createNewFile();
                    }
                    System.out.println("跳过的字节数为：" + needSkipBytes);
                    in.skip(needSkipBytes);
                    RandomAccessFile tempRandAccessFile = new RandomAccessFile(tempFile, "rw");
                    tempRandAccessFile.seek(needSkipBytes);
                    byte[] buffer = new byte[1024];
                    int len = 0;
                    int count = 0;
                    while ((len = in.read(buffer)) > 0) {
                        tempRandAccessFile.write(buffer);
                        count++;
                    }
                    in.close();
                    tempRandAccessFile.close();
                    realFile.createNewFile();
                    if (fileCopy(tempFile, realFile)) {
                        tempFile.delete();
                    }
                }
            }
        }

    }
    private boolean fileCopy(File sourceFile, File targetFile) {
        boolean success = true;
        try {
            FileInputStream in = new FileInputStream(sourceFile);
            FileOutputStream out = new FileOutputStream(targetFile);
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = in.read(buffer)) > 0) {
                out.write(buffer);
            }
            in.close();
            out.close();
        } catch (FileNotFoundException e) {
            success = false;
        } catch (IOException e) {
            success = false;
        }
        return success;
    }


    @RequestMapping(value = "/uploadFile2", method = RequestMethod.POST)
    @ResponseBody
    public String uploadFile2(MultipartFile file,@RequestParam(value = "fileName")String fileName)throws Exception {
        ObjectMapper mapper=new ObjectMapper();
        Map<String,String> resultMap=new HashMap<>();
        logger.info("fileName:"+file.getOriginalFilename());
        logger.info("true fileName"+fileName);
        try {
            logger.info("the controller is ready for the upload file");
            File localFile=new File("/Users/wubingyu/IdeaProjects/365company_demo/UploadProject/upload/"+fileName);

//            try {
//                file.transferTo(localFile);
//            } catch (IOException e) {
//                logger.error("error",e);
//            }
            this.file_input_content(file,localFile);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            resultMap.put("success","false");
            String json=mapper.writeValueAsString(resultMap);
            return json;
        }
        resultMap.put("success","true");
        String json=mapper.writeValueAsString(resultMap);
        return json;
    } // method uploadFile



    public void file_input_content(MultipartFile file,File localFile)throws Exception{

        if (localFile.exists()){
        // if the file exist
            RandomAccessFile randomAccessFile=new RandomAccessFile(localFile,"rw");
            randomAccessFile.seek(localFile.length());
            randomAccessFile.write(file.getBytes());
            randomAccessFile.close();
        }else{
           file.transferTo(localFile);

        }

    }

}
