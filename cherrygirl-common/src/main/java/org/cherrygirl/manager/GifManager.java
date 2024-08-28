package org.cherrygirl.manager;

import cn.hutool.http.HttpUtil;
import org.cherrygirl.config.ResourcesConfig;
import org.cherrygirl.gif.AnimatedGifEncoder;
import org.cherrygirl.utils.FileUtil;
import org.cherrygirl.utils.ImageMergeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author nannanness
 */
@Service
public class GifManager {

    @Autowired
    private ResourcesConfig resourcesConfig;

    private static final String BASE = "base/";
    private static final String MID = "mid/";
    private static final String NEW = "new/";
    private static final String RESIZE = "resize/";
    private static final String SOURCE = "source/";

    private String getRoot(){
        return resourcesConfig.getRoot() + "/image/gif/mm/";
    }

    public String genSource(String url, String filePath){
        String source = getRoot() + SOURCE + filePath + ".png";
        File file = new File(source);
        System.out.println("url : " + url);
        System.out.println("source : " + source);
        HttpUtil.downloadFile(url, source);
        return source;
    }

    /**
     * 生成摸摸gif
     *
     * @param source
     * @param qq
     * @return
     * @throws IOException
     */
    public String getMmGif(String source, String qq) throws IOException {
        List<BufferedImage> bufferedImages = new ArrayList<>();
        BufferedImage mm1 = ImageMergeUtil.getBufferedImage(getRoot() + BASE + "mm1.png");
        BufferedImage mm2 = ImageMergeUtil.getBufferedImage(getRoot() + BASE + "mm2.png");
        BufferedImage mm3 = ImageMergeUtil.getBufferedImage(getRoot() + BASE + "mm3.png");
        BufferedImage mm4 = ImageMergeUtil.getBufferedImage(getRoot() + BASE + "mm4.png");
        BufferedImage mm5 = ImageMergeUtil.getBufferedImage(getRoot() + BASE + "mm5.png");
        bufferedImages.add(mm1);
        bufferedImages.add(mm2);
        bufferedImages.add(mm3);
        bufferedImages.add(mm4);
        bufferedImages.add(mm5);
        List<Rectangle> rectangles = new ArrayList<>();
        Rectangle rectangle1 = new Rectangle(10, 10, 102, 102);
        Rectangle rectangle2 = new Rectangle(6, 13, 110, 112-13);
        Rectangle rectangle3 = new Rectangle(2, 16, 116, 112-16);
        Rectangle rectangle4 = new Rectangle(6, 15, 110, 112-13);
        Rectangle rectangle5 = new Rectangle(10, 15, 112, 102);
        rectangles.add(rectangle1);
        rectangles.add(rectangle2);
        rectangles.add(rectangle3);
        rectangles.add(rectangle4);
        rectangles.add(rectangle5);
        String resizePath = getRoot() + RESIZE + qq;
        String midPath = getRoot() + MID + qq;
        String roundPath = getRoot() + MID + qq + "round.png";
        ImageMergeUtil.catRound(roundPath, source, "png", true);
        List<BufferedImage> overlays = new ArrayList<>();
        for(int i = 0; i < rectangles.size(); i++){
            String resize = resizePath + i + "resize.png";
            ImageMergeUtil.resizeImage(112, 112, new FileInputStream(roundPath), new FileOutputStream(resize), rectangles.get(i), "png", false);
            BufferedImage resizeBuff = ImageMergeUtil.getBufferedImage(resize);
            BufferedImage overly = ImageMergeUtil.overlyingImage(resizeBuff, bufferedImages.get(i), 0, 0, 1f);
            ImageMergeUtil.generateSaveFile(overly, midPath + i + "overly.png");
            overlays.add(overly);
        }
        String gifPath = getRoot() + NEW + qq + "momo.gif";
        genGif(overlays, gifPath);
        clear();
        return gifPath;
    }

    /**
     * 生成gif
     *
     * @param bufferedImages
     * @param gifPath
     * @throws IOException
     */
    public void genGif(List<BufferedImage> bufferedImages, String gifPath) throws IOException {

        FileOutputStream outputStream = new FileOutputStream(new File(gifPath));
        AnimatedGifEncoder encoder = new AnimatedGifEncoder();
        encoder.setRepeat(0);
        encoder.start(outputStream);
        for(int i = 0; i < bufferedImages.size(); i++){
            encoder.addFrame(bufferedImages.get(i));
            encoder.setDelay(15);
        }
        encoder.finish();
        outputStream.flush();
        outputStream.close();
    }

    public void clear(){
        FileUtil.deleteDir(getRoot() + MID);
        FileUtil.deleteDir(getRoot() + RESIZE);
    }
}
