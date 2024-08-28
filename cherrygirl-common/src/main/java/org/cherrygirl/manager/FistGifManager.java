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
public class FistGifManager {

    @Autowired
    private ResourcesConfig resourcesConfig;

    private static final String BASE = "base/";
    private static final String MID = "mid/";
    private static final String NEW = "new/";
    private static final String RESIZE = "resize/";
    private static final String SOURCE = "source/";

    private String getRoot(){
        return resourcesConfig.getRoot() + "/image/gif/fist/";
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
     * 生成踢gif
     *
     * @param qqSource
     * @param qq
     * @return
     * @throws IOException
     */
    public String getTiGif(String qqSource, String qq, String tiSource, String ti) throws IOException {
        clear();
        // 切圆1
        String qqRoundPath = getRoot() + MID + qq + "round.png";
        String tiRoundPath = getRoot() + MID + ti + "round.png";
        // 切圆2
        ImageMergeUtil.catRound(qqRoundPath, qqSource, "png",true);
        ImageMergeUtil.catRound(tiRoundPath, tiSource, "png", true);

        BufferedImage t1 = ImageMergeUtil.getBufferedImage(getRoot() + BASE + "1.png");
        BufferedImage t2 = ImageMergeUtil.getBufferedImage(getRoot() + BASE + "2.png");
        BufferedImage t3 = ImageMergeUtil.getBufferedImage(getRoot() + BASE + "3.png");
        BufferedImage t4 = ImageMergeUtil.getBufferedImage(getRoot() + BASE + "4.png");
        BufferedImage t5 = ImageMergeUtil.getBufferedImage(getRoot() + BASE + "5.png");
        BufferedImage t6 = ImageMergeUtil.getBufferedImage(getRoot() + BASE + "6.png");

        Rectangle rectangleQQ1 = new Rectangle(84, 103, 81, 81);
        Rectangle rectangleT1 = new Rectangle(201, 105, 81, 81);
        Rectangle rectangleQQ2 = new Rectangle(82, 98, 81, 81);
        Rectangle rectangleT2 = new Rectangle(204, 105, 81, 81);
        Rectangle rectangleQQ3 = new Rectangle(131, 124, 81, 81);
        Rectangle rectangleT3 = new Rectangle(227, 113, 81, 81);
        Rectangle rectangleQQ4 = new Rectangle(154, 139, 81, 81);
        Rectangle rectangleT4 = new Rectangle(265, 118, 81, 81);
        Rectangle rectangleQQ5 = new Rectangle(163, 138, 81, 81);
        Rectangle rectangleT5 = new Rectangle(278, 122, 81, 81);
        Rectangle rectangleQQ6 = new Rectangle(186, 137, 81, 81);
        Rectangle rectangleT6 = new Rectangle(304, 123, 81, 81);
        // 头像变形处理目录
        String resizePath = getRoot() + RESIZE + qq + "X" + ti;
        // 头像和base合并目录, 以及切圆目录
        String midPath = getRoot() + MID + qq + "X" + ti;

        List<BufferedImage> overlays = new ArrayList<>();

        String resize1 = resizePath + "resize1.png";
        String resize2 = resizePath + "resize2.png";
        String resize3 = resizePath + "resize3.png";
        String resize4 = resizePath + "resize4.png";
        String resize5 = resizePath + "resize5.png";
        String resize6 = resizePath + "resize6.png";
        // 绘制
        BufferedImage overly1 = splicing2Img(midPath + "overly1.png", t1, qqRoundPath, tiRoundPath, resize1, rectangleQQ1, rectangleT1);
        BufferedImage overly2 = splicing2Img(midPath + "overly2.png", t2, qqRoundPath, tiRoundPath, resize2, rectangleQQ2, rectangleT2);
        BufferedImage overly3 = splicing2Img(midPath + "overly3.png", t3, qqRoundPath, tiRoundPath, resize3, rectangleQQ3, rectangleT3);
        BufferedImage overly4 = splicing2Img(midPath + "overly4.png", t4, qqRoundPath, tiRoundPath, resize4, rectangleQQ4, rectangleT4);
        BufferedImage overly5 = splicing2Img(midPath + "overly5.png", t5, qqRoundPath, tiRoundPath, resize5, rectangleQQ5, rectangleT5);
        BufferedImage overly6 = splicing2Img(midPath + "overly6.png", t6, qqRoundPath, tiRoundPath, resize6, rectangleQQ6, rectangleT6);
        overlays.add(overly1);
        overlays.add(overly2);
        overlays.add(overly3);
        overlays.add(overly4);
        overlays.add(overly5);
        overlays.add(overly6);

        String gifPath = getRoot() + NEW + qq + "X" + ti + "t.gif";
        genGif(overlays, gifPath);
//        clear();
        return gifPath;
    }

    private BufferedImage splicing2Img(String midPath, BufferedImage t1, String qqRoundPath, String tiRoundPath, String resize, Rectangle rectangleQQ1, Rectangle rectangleT1) throws IOException {
        ImageMergeUtil.resize2Image(500, 387, new FileInputStream(qqRoundPath), new FileInputStream(tiRoundPath), new FileOutputStream(resize), rectangleQQ1, rectangleT1, "png", true);
        BufferedImage resizeBuff = ImageMergeUtil.getBufferedImage(resize);
        BufferedImage overly = ImageMergeUtil.overlyingImage(t1 , resizeBuff, 0, 0, 1f);
        ImageMergeUtil.generateSaveFile(overly, midPath);
        return overly;
    }


    /**
     * 生成gif
     *
     * @param bufferedImages
     * @param gifPath
     * @throws IOException
     */
    public void genGif(List<BufferedImage> bufferedImages, String gifPath) throws IOException {
        System.out.println("gif size: " + bufferedImages.size());
        FileOutputStream outputStream = new FileOutputStream(new File(gifPath));
        AnimatedGifEncoder encoder = new AnimatedGifEncoder();
        encoder.setRepeat(0);
        encoder.start(outputStream);
        for(int i = 0; i < bufferedImages.size(); i++){
            encoder.addFrame(bufferedImages.get(i));
            encoder.setDelay(140);
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
