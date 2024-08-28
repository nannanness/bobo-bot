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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author nannanness
 */
@Service
public class TiGifManager {

    @Autowired
    private ResourcesConfig resourcesConfig;

    private static final String BASE = "base/";
    private static final String MID = "mid/";
    private static final String NEW = "new/";
    private static final String RESIZE = "resize/";
    private static final String SOURCE = "source/";

    private String getRoot(){
        return resourcesConfig.getRoot() + "/image/gif/t/";
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
        ImageMergeUtil.catRound(qqRoundPath, qqSource, "png", true);
        ImageMergeUtil.catRound(tiRoundPath, tiSource, "png", true);

        BufferedImage t1 = ImageMergeUtil.getBufferedImage(getRoot() + BASE + "11.png");
        BufferedImage t2 = ImageMergeUtil.getBufferedImage(getRoot() + BASE + "22.png");
        BufferedImage t3 = ImageMergeUtil.getBufferedImage(getRoot() + BASE + "33.png");
        Rectangle rectangle1 = new Rectangle(125, 125, 80, 80);
        Rectangle rectangle245 = new Rectangle(240, 125, 80, 80);
        Rectangle rectangle3 = new Rectangle(105, 125, 100, 100);

        // 头像变形处理目录
        String resizePath = getRoot() + RESIZE + qq + "X" + ti;
        // 头像和base合并目录, 以及切圆目录
        String midPath = getRoot() + MID + qq + "X" + ti;

        List<BufferedImage> overlays = new ArrayList<>();
        // 绘制第一张
        String resize1 = resizePath + "resize1.png";
        ImageMergeUtil.resize2Image(400, 400, new FileInputStream(qqRoundPath), new FileInputStream(tiRoundPath), new FileOutputStream(resize1), rectangle1, rectangle245, "png", true);
        BufferedImage resizeBuff1 = ImageMergeUtil.getBufferedImage(resize1);
        BufferedImage overly1 = ImageMergeUtil.overlyingImage(t1 , resizeBuff1, 0, 0, 1f);
        ImageMergeUtil.generateSaveFile(overly1, midPath + "overly1.png");
        overlays.add(overly1);

        // 绘制第二张
        String resize2 = resizePath + "resize2.png";
        // 旋转
        BufferedImage qqRoundBufferedImage = ImageMergeUtil.getBufferedImage(qqRoundPath);
        String qqObliquePath = getRoot() + MID + qq + "oblique.png";
        ImageMergeUtil.rotateImage(qqRoundBufferedImage, 15, new File(qqObliquePath));
        ImageMergeUtil.resize2Image(400, 400, new FileInputStream(qqObliquePath), new FileInputStream(tiRoundPath), new FileOutputStream(resize2), rectangle3, rectangle245, "png", true);
        BufferedImage resizeBuff2 = ImageMergeUtil.getBufferedImage(resize2);
        BufferedImage overly2 = ImageMergeUtil.overlyingImage(t2, resizeBuff1, 0, 0, 1f);
        ImageMergeUtil.generateSaveFile(overly2, midPath + "overly2.png");
        overlays.add(overly2);

        // 绘制第三张
        String resize3 = resizePath + "resize3.png";
        ImageMergeUtil.resizeImage(400, 400, new FileInputStream(tiRoundPath), new FileOutputStream(resize3), rectangle245, "png",true);
        BufferedImage resizeBuff3 = ImageMergeUtil.getBufferedImage(resize3);
        BufferedImage overly3 = ImageMergeUtil.overlyingImage(t3, resizeBuff3, 0, 0, 1f);
        ImageMergeUtil.generateSaveFile(overly3, midPath + "overly3.png");
        overlays.add(overly3);

        String gifPath = getRoot() + NEW + qq + "X" + ti + "t.gif";
        genGif(overlays, gifPath);
//        clear();
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
        System.out.println("gif size: " + bufferedImages.size());
        FileOutputStream outputStream = new FileOutputStream(new File(gifPath));
        AnimatedGifEncoder encoder = new AnimatedGifEncoder();
        encoder.setRepeat(0);
        encoder.start(outputStream);
        for(int i = 0; i < bufferedImages.size(); i++){
            encoder.addFrame(bufferedImages.get(i));
            encoder.setDelay(200);
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
