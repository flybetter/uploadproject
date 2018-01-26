package com.cn.company;

import com.cn.company.util.configure.FfmpegValues;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * testYml class
 *
 * @author bingyu wu
 *         Date: 2018/1/23
 *         Time: 上午9:21
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UploadprojectApplication.class)
public class testYml {

    /** logger */
    private static final Logger logger = LoggerFactory.getLogger(testYml.class);
    
    @Autowired
    private FfmpegValues ffmpegValues;

    @Test
    public void name() throws Exception {
        logger.info(ffmpegValues.getFormats().toString());
        logger.info(ffmpegValues.getHehe());
    }

}
