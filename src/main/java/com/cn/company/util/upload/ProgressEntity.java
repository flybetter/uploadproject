package com.cn.company.util.upload;

import org.springframework.stereotype.Component;

/**
 * ProgressEntity class
 *
 * @author bingyu wu
 *         Date: 2018/1/16
 *         Time: 上午9:21
 */
@Component
public class ProgressEntity {
    /**
     * how much bytes already read
     */
    private long pBytesRead=0L;
    /**
     *  the length of the upload file
     */
    private long pContentLength=0L;
    /**
     *  the number of  files
     */
    private int pItems;




    public long getpBytesRead() {
        return pBytesRead;
    }

    public void setpBytesRead(long pBytesRead) {
        this.pBytesRead = pBytesRead;
    }

    public long getpContentLength() {
        return pContentLength;
    }

    public void setpContentLength(long pContentLength) {
        this.pContentLength = pContentLength;
    }

    public int getpItems() {
        return pItems;
    }

    public void setpItems(int pItems) {
        this.pItems = pItems;
    }



    @Override
    public String toString() {
        float tmp=(float)pBytesRead;
        float result=tmp/pContentLength*100;

        return "ProgressEntity{" +
                "pBytesRead=" + pBytesRead +
                ", pContentLength=" + pContentLength +
                ", pItems=" + pItems +
                ", percentage=" + result+"%" +
                '}';
    }
}

