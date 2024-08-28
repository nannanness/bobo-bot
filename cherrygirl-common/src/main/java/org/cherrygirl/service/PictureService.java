package org.cherrygirl.service;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

/**
 * @author nannanness
 */
public interface PictureService {

    /**
     * 获取触站 图片
     *
     * @param keyword
     * @return
     * @throws IOException
     */
    String getChuzhanPicture(String keyword) throws IOException;
}
