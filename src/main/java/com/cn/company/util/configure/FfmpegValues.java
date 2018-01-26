package com.cn.company.util.configure;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * ffmpegValues class
 *
 * @author bingyu wu
 *         Date: 2018/1/23
 *         Time: 上午9:28
 */
@Component
@ConfigurationProperties(prefix = "ffmpegYml")
public class FfmpegValues {

    private String hehe;

    private List<String> formats=new ArrayList<>();

    private List<String> codecs=new ArrayList<>();


    public String getHehe() {
        return hehe;
    }

    public List<String> getCodecs() {
        return codecs;
    }

    public void setCodecs(List<String> codecs) {
        this.codecs = codecs;
    }

    public void setHehe(String hehe) {
        this.hehe = hehe;
    }


    public List<String> getFormats() {
        return formats;
    }

    public void setFormats(List<String> formats) {
        this.formats = formats;
    }

    @Override
    public String toString() {
        return "FfmpegValues{" +
                "hehe='" + hehe + '\'' +
                ", formats=" + formats +
                ", codecs=" + codecs +
                '}';
    }
}
