package com.cn.company.util.code;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

/**
 * CodeUtil class
 *
 * @author bingyu wu
 *         Date: 2018/1/23
 *         Time: 上午11:29
 */
public class CodeUtil {

    /** logger */
    private static final Logger logger = LoggerFactory.getLogger(CodeUtil.class);

    public static void Coding(String format,String inputName,String outputName){
        File inputFile=new File("filein");
        File outputFile=new File("fileout");

        String command="ffmpeg -i "+inputFile.getAbsolutePath()+"/"+inputName+" "+outputFile.getAbsolutePath()+"/"+outputName+"."+format;
        logger.info("command:"+command);
        try {
            Process process=Runtime.getRuntime().exec(command);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void clearOutputFile(){
        File outputFile=new File("fileout");
        File []files=outputFile.listFiles();
        for (int i = 0; i < files.length; i++) {
                files[i].delete();
        }
    }
}
