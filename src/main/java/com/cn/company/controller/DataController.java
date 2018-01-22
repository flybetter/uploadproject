package com.cn.company.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * DataController class
 *
 * @author bingyu wu
 *         Date: 2018/1/22
 *         Time: 下午2:50
 */
@Controller
public class DataController {

    @GetMapping(value ="listDataPager")
    public String listDataPager(Model model){

        List <String>inputFileNameList=new ArrayList();

        File inputFile=new File("filein");

        if (inputFile.exists()){
          File[] files =inputFile.listFiles();
            for (int i = 0; i <files.length ; i++) {
                inputFileNameList.add(files[i].getName());
            }
        }

        List<String> outputFileList =new ArrayList<>();
        
        File outputFile=new File("fileout");
        if (outputFile.exists()){
            File []outputFileNames=outputFile.listFiles();
            for (int i = 0; i < outputFileNames.length; i++) {
                outputFileList.add(outputFileNames[i].getName());
            }
                    
        }

        model.addAttribute("inputFileNames",inputFileNameList);
        model.addAttribute("outputFileNames",outputFileList);
        return "listData";
    }


}
