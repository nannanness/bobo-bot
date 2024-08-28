package org.cherrygirl.manager;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.RandomUtil;
import org.cherrygirl.config.ResourcesConfig;
import org.cherrygirl.gif.AnimatedGifEncoder;
import org.cherrygirl.utils.ImageMergeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.List;

/**
 * @author nannanness
 */
@Service
public class GenshinManager {

    @Autowired
    private ResourcesConfig resourcesConfig;

    private static final String BASE = "base/";
    private static final String ROLE = "role/";
    private static final String ROLE5 = "5role/";
    private static final String ARMS = "arms/";
    private static final String GIF = "gif/";
    private static final String RESULT = "result/";

    private String getRoot(){
        return resourcesConfig.getRoot() + "/image/genshin/";
    }


    /**
     * 生成十连gif
     *
     * @param group
     * @param qq
     * @return
     * @throws IOException
     */
    public List<String> getTenCompaniesGif(String group, String qq) throws IOException {
        String basePath = getRoot() + BASE;
        String rolePath = getRoot() + ROLE;
        String role5Path = getRoot() + ROLE5;
        String armsPath = getRoot() + ARMS;
        String gifPath = getRoot() + GIF;
        String resultPath = getRoot() + RESULT + qq + ".png";
        String resultPathGif = getRoot() + RESULT + qq + ".gif";
        List<String> roleFileList = getAllFileStr(new File(rolePath));
        List<String> role5FileList = getAllFileStr(new File(role5Path));
        List<String> armsFileList = getAllFileStr(new File(armsPath));
        List<String> resultList = new ArrayList<>();
        resultList.add(basePath + "十连头.png");
        resultList.add(basePath + "十连金脖.png");

        int i = RandomUtil.randomInt(1, 6);
        int j = RandomUtil.randomInt(1, 7);
        int k = i + j > 10 ? 0 : 10 - (i + j);
        j = i + j > 10 ? 10 - i : j;
        for(int o = 0 ; o < i ; o++) {
            String fileName = role5FileList.get(RandomUtil.randomInt(role5FileList.size()));
            resultList.add(role5Path + fileName);
        }
        for(int l = 0 ; l < j ; l++) {
            String fileName = roleFileList.get(RandomUtil.randomInt(roleFileList.size()));
            resultList.add(rolePath + fileName);
        }
        if(k > 0){
            for(int m = 0 ; m < k ; m++) {
                String fileName = armsFileList.get(RandomUtil.randomInt(armsFileList.size()));
                resultList.add(armsPath + fileName);
            }
        }
        resultList.add(basePath + "十连尾.png");

        ImageMergeUtil.joinImageListHorizontal(resultList.toArray(new String[resultList.size()]) , "png", resultPath);
        String miniPath = getRoot() + RESULT + qq + "mini.png";
        Rectangle rectangle = new Rectangle(0, 0, 833, 394);
        ImageMergeUtil.resizeImage(833, 394, new FileInputStream(resultPath), new FileOutputStream(miniPath), rectangle, "png",true);

        return Arrays.asList(miniPath, resultPathGif);
    }

    /**
     * 获取指定文件夹下所有文件，不含文件夹里的文件
     *
     * @param dirFile 文件夹
     * @return
     */
    public List<String> getAllFileStr(File dirFile) {
        // 如果文件夹不存在或着不是文件夹，则返回 null
        if (Objects.isNull(dirFile) || !dirFile.exists() || dirFile.isFile()){
            return null;
        }

        File[] childrenFiles = dirFile.listFiles();
        if (Objects.isNull(childrenFiles) || childrenFiles.length == 0) {
            return null;
        }
        List<String> files = new ArrayList<>();
        for (File childFile : childrenFiles) {
            // 如果是文件，直接添加到结果集合
            if (childFile.isFile()) {
                files.add(childFile.getName());
            }
            //以下几行代码取消注释后可以将所有子文件夹里的文件也获取到列表里
//            else {
//                // 如果是文件夹，则将其内部文件添加进结果集合
//                List<File> cFiles = getAllFile(childFile);
//                if (Objects.isNull(cFiles) || cFiles.isEmpty()) continue;
//                files.addAll(cFiles);
//            }
        }
        return files;
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
            if(i == bufferedImages.size() - 1){
                encoder.setDelay(60);
            }else {
                encoder.setDelay(5);
            }
        }
        encoder.finish();
        outputStream.flush();
        outputStream.close();
    }
}
