package com.cn.company.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

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
    public String uploadVideo(@RequestParam("file")MultipartFile file)throws Exception{
        if (!file.isEmpty()){
            File localFile=new File("/Users/wubingyu/IdeaProjects/365company_demo/UploadProject/upload/"+file.getOriginalFilename());
            try {
                file.transferTo(localFile);
            } catch (IOException e) {
                logger.error("error",e);
            }
            return "sucess";
        }else {
            return "file is empty, so upload file fail";
        }
    }

    @RequestMapping("/uploadPatchVideo")
    @ResponseBody
    public String uploadPatchVideo(){

        return "uploadPatchVideo";
    }


}
