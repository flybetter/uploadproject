package com.cn.company;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * ffmpegTest class
 *
 * @author bingyu wu
 *         Date: 2018/1/22
 *         Time: 下午2:21
 */
public class ffmpegTest {
    /** logger */
    private static final Logger logger = LoggerFactory.getLogger(ffmpegTest.class);

    @Test
    public void name() throws Exception {
        File inputFile=new File("filein/demo.mp4");
        File outputFile=new File("fileout/demo.flv");

        String command="ffmpeg -i "+inputFile.getAbsolutePath()+" "+outputFile.getAbsolutePath();
        Process process=Runtime.getRuntime().exec(command);
        process.waitFor();

    }
}
