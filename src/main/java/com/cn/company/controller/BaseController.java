package com.cn.company.controller;

import com.cn.company.util.upload.ProgressEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

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
    public ResponseEntity<?> uploadFile(
            @RequestParam("uploadfile") MultipartFile file) {

        try {
            // Get the filename and build the local file path (be sure that the
            // application have write permissions on such directory)
//            String filename = uploadfile.getOriginalFilename();
//            String directory = "/Users/wubingyu/IdeaProjects/365company_demo/UploadProject/upload/";
//            String filepath = Paths.get(directory, filename).toString();
//
//            // Save the file locally
//            BufferedOutputStream stream =
//                    new BufferedOutputStream(new FileOutputStream(new File(filepath)));
//            stream.write(uploadfile.getBytes());
//            stream.close();
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

}
