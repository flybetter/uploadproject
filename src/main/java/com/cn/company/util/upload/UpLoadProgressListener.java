package com.cn.company.util.upload;


import org.apache.commons.fileupload.ProgressListener;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

/**
 * UpLoadProgressListener class
 *
 * @author bingyu wu
 *         Date: 2018/1/16
 *         Time: 上午9:19
 */
@Component
public class UpLoadProgressListener implements ProgressListener {

    private HttpSession session;

    public void setSession(HttpSession session) {
        this.session = session;
        ProgressEntity status = new ProgressEntity();
        session.setAttribute("status", status);
    }

    @Override
    public void update(long pBytesRead, long pContextLength, int pItems) {
        ProgressEntity status = (ProgressEntity) session.getAttribute("status");
        status.setpBytesRead(pBytesRead);
        status.setpContentLength(pContextLength);
        status.setpItems(pItems);
    }
}
