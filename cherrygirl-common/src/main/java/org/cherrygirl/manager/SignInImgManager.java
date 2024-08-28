package org.cherrygirl.manager;

import cn.hutool.http.HttpUtil;
import org.cherrygirl.config.ResourcesConfig;
import org.cherrygirl.gif.AnimatedGifEncoder;
import org.cherrygirl.utils.FileUtil;
import org.cherrygirl.utils.ImageMergeUtil;
import org.cherrygirl.utils.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.List;

@Component
public class SignInImgManager {

    @Autowired
    private ResourcesConfig resourcesConfig;

    private static final String BASE = "base/";
    private static final String OUT = "out/";
    private static final String RESIZE = "resize/";
    private static final String SOURCE = "source/";

    private String getRoot(){
        return resourcesConfig.getRoot() + "/image/signin/";
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
    public String getSignImg(String qqSource, String qq) throws IOException {
        clear();
        String qqRoundPath = getRoot() + RESIZE + qq + "round.png";
        // 切圆
        ImageMergeUtil.catRound(qqRoundPath, qqSource, "png",true);

        // find img
        String filePath = getRoot() + BASE;
        File file = new File(filePath);
        List<File> list = Arrays.asList(Objects.requireNonNull(file.listFiles()));
        File target = list.get(new Random().nextInt(list.size()));

        BufferedImage t = ImageMergeUtil.getBufferedImage(target.getPath());
        Rectangle rectangleQQ1 = new Rectangle(500, 20, 80, 80);
        Rectangle rectangleQQ2 = new Rectangle(20, 20, 80, 80);
        Integer nextNumber = RandomUtil.nextNumber(5, 1, 50000);
        Rectangle rectangle = nextNumber % 2 == 0 ? rectangleQQ1 : rectangleQQ2;
        // 头像变形处理目录
        String resizePath = getRoot() + RESIZE + qq + "X" + "resize1.png";
        // 头像和base合并目录, 以及切圆目录
        String midPath = getRoot() + OUT + qq + "X" + "overly1.png";

        java.util.List<BufferedImage> overlays = new ArrayList<>();

        // 绘制
        ImageMergeUtil.resizeImage(600, 518, new FileInputStream(qqRoundPath),  new FileOutputStream(resizePath), rectangle, "png", true);
        BufferedImage resizeBuff1 = ImageMergeUtil.getBufferedImage(resizePath);
        BufferedImage overly1 = ImageMergeUtil.overlyingImage(t , resizeBuff1, 0, 0, 1f);
        ImageMergeUtil.generateSaveFile(overly1, midPath);
        overlays.add(overly1);

        return midPath;
    }




    public void clear(){
        FileUtil.deleteDir(getRoot() + OUT);
        FileUtil.deleteDir(getRoot() + RESIZE);
    }
}
