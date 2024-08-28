package org.cherrygirl.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.io.*;
import java.util.Base64;

/**
 * @author nannanness
 */
public class ImageMergeUtil {

    /**
     * 对图片进行旋转
     *
     * @param image  被旋转图片
     * @param degree 旋转角度
     * @param newFile 旋转后图片
     * @return 旋转后的图片
     */
    public static void rotateImage(BufferedImage image, double degree, File newFile) throws IOException {

        degree = Math.toRadians(360 - degree);

        int w = image.getWidth();
        int h = image.getHeight();
        int newW = (int) Math.ceil(Math.abs(w * Math.cos(degree)) + Math.abs(h * Math.sin(degree)));
        int newH = (int) Math.ceil(Math.abs(h * Math.cos(degree)) + Math.abs(w * Math.sin(degree)));

        BufferedImage rotImg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_RGB);

        for (int x = 0; x < newW; x++) {
            for (int y = 0; y < newH; y++) {
                int x1 = (int) Math.round(x * Math.cos(degree) + y * Math.sin(degree) - 0.5 * newW * Math.cos(degree) - 0.5 * newH * Math.sin(degree) + 0.5 * w);
                int y1 = (int) Math.round(-x * Math.sin(degree) + y * Math.cos(degree) + 0.5 * newW * Math.sin(degree) - 0.5 * newH * Math.cos(degree) + 0.5 * h);
                if (x1 >= 0 && x1 < w && y1 >= 0 && y1 < h) {
                    rotImg.setRGB(x, y, image.getRGB(x1, y1));
                } else {
                    rotImg.setRGB(x, y, new Color(255, 255, 255).getRGB());
                }

            }
        }
        // 把修改过的 rotImg 保存到本地
        ImageIO.write(rotImg, "png", newFile);
    }


    /**
     * @param fileUrl 文件绝对路径或相对路径
     * @return 读取到的缓存图像
     * @throws IOException 路径错误或者不存在该文件时抛出IO异常
     */
    public static BufferedImage getBufferedImage(String fileUrl) throws IOException {
        File f = new File(fileUrl);
        return ImageIO.read(f);
    }

    /**
     * 输出图片
     *
     * @param buffImg  图像拼接叠加之后的BufferedImage对象
     * @param savePath 图像拼接叠加之后的保存路径
     */
    public static void generateSaveFile(BufferedImage buffImg, String savePath) {
        int temp = savePath.lastIndexOf(".") + 1;
        try {
            File outFile = new File(savePath);
            if (!outFile.exists()) {
                outFile.createNewFile();
            }
            ImageIO.write(buffImg, savePath.substring(temp), outFile);
            System.out.println("ImageIO write...");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * @param buffImg  源文件(BufferedImage)
     * @param waterImg 水印文件(BufferedImage)
     * @param x        距离右下角的X偏移量
     * @param y        距离右下角的Y偏移量
     * @param alpha    透明度, 选择值从0.0~1.0: 完全透明~完全不透明
     * @return BufferedImage
     * @throws IOException
     * @Title: 构造图片
     * @Description: 生成水印并返回java.awt.image.BufferedImage
     */
    public static BufferedImage overlyingImage(BufferedImage buffImg, BufferedImage waterImg, int x, int y, float alpha) throws IOException {

        // 创建Graphics2D对象，用在底图对象上绘图
        Graphics2D g2d = buffImg.createGraphics();
        int waterImgWidth = waterImg.getWidth();// 获取层图的宽度
        int waterImgHeight = waterImg.getHeight();// 获取层图的高度
        // 在图形和图像中实现混合和透明效果
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
        g2d.drawImage(waterImg, x, y, waterImgWidth, waterImgHeight, null);
        // 绘制
        g2d.dispose();// 释放图形上下文使用的系统资源
        return buffImg;
    }

    /**
     * 改变两张图片的大小到宽为size，然后高随着宽等比例变化
     *
     * @param is        上传的图片的输入流
     * @param os        改变了图片的大小后，把图片的流输出到目标OutputStream
     * @param rectangle 新图片的宽高
     * @param format    新图片的格式
     * @param trans     是否透明背景
     * @throws IOException
     */
    public static OutputStream resize2Image(int width, int height, InputStream is, InputStream is2, OutputStream os, Rectangle rectangle, Rectangle rectangle2, String format, boolean trans) throws IOException {
        BufferedImage prevImage = ImageIO.read(is);
        BufferedImage prevImage2 = ImageIO.read(is2);
//        double width = prevImage.getWidth();
//        double height = prevImage.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = image.createGraphics();

        if(trans){
            //设置图片透明
            image = graphics.getDeviceConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT);
            graphics = image.createGraphics();
            graphics.setColor(Color.LIGHT_GRAY);
            graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
        } else {
            graphics.setColor(Color.WHITE);//设置笔刷白色
            graphics.fillRect(0, 0, width, height);//填充整个屏幕
        }

        graphics.drawImage(prevImage, (int) rectangle.getX(), (int) rectangle.getY(), (int) rectangle.getWidth(), (int) rectangle.getHeight(), null);
        graphics.drawImage(prevImage2, (int) rectangle2.getX(), (int) rectangle2.getY(), (int) rectangle2.getWidth(), (int) rectangle2.getHeight(), null);
        ImageIO.write(image, format, os);
        os.flush();
        is.close();
        is2.close();
        os.close();
        return os;
    }

    /**
     * 改变图片的大小到宽为size，然后高随着宽等比例变化
     *
     * @param is        上传的图片的输入流
     * @param os        改变了图片的大小后，把图片的流输出到目标OutputStream
     * @param rectangle 新图片的宽高
     * @param format    新图片的格式
     * @throws IOException
     */
    public static OutputStream resizeImage(int width, int height, InputStream is, OutputStream os, Rectangle rectangle, String format, boolean trans) throws IOException {
        BufferedImage prevImage = ImageIO.read(is);
//        double width = prevImage.getWidth();
//        double height = prevImage.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = image.createGraphics();
        if(trans){
            //设置图片透明
            image = graphics.getDeviceConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT);
            graphics = image.createGraphics();
            graphics.setColor(Color.LIGHT_GRAY);
            graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
        } else {
            graphics.setColor(Color.WHITE);//设置笔刷白色
            graphics.fillRect(0, 0, width, height);//填充整个屏幕
        }
        graphics.drawImage(prevImage, (int) rectangle.getX(), (int) rectangle.getY(), (int) rectangle.getWidth(), (int) rectangle.getHeight(), null);
        ImageIO.write(image, format, os);
        os.flush();
        is.close();
        os.close();
        return os;
    }

    /**
     * 切割圆形
     *
     * @param filePath
     * @param fmt
     * @throws IOException
     */
    public static void catRound(String filePath, String fileUrl, String fmt, boolean trans) throws IOException {
        BufferedImage img = getBufferedImage(fileUrl);
        BufferedImage bi2 = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Ellipse2D.Double shape = new Ellipse2D.Double(0, 0, img.getWidth(), img.getHeight());
        Graphics2D g2 = bi2.createGraphics();
        if(trans){
            //设置图片透明
            bi2 = g2.getDeviceConfiguration().createCompatibleImage(img.getWidth(), img.getHeight(), Transparency.TRANSLUCENT);
            g2 = bi2.createGraphics();
            g2.setColor(Color.LIGHT_GRAY);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
        } else {
            g2.setBackground(Color.WHITE);
        }
//        g2.fill(new Rectangle(bi2.getWidth(), bi2.getHeight()));
        g2.setClip(shape);
        //设置抗锯齿
        g2.drawImage(img, 0, 0, null);
        g2.dispose();
        ImageIO.write(bi2, fmt, new File(filePath));
    }

    /**
     * 横向拼接图片（两张）
     * @param firstSrcImagePath 第一张图片的路径
     * @param secondSrcImagePath	第二张图片的路径
     * @param imageFormat	拼接生成图片的格式
     * @param toPath	拼接生成图片的路径
     */
    public static void joinImagesHorizontal(String firstSrcImagePath, String secondSrcImagePath,String imageFormat, String toPath){
        try {
            //读取第一张图片
            File  fileOne  =  new  File(firstSrcImagePath);
            BufferedImage  imageOne = ImageIO.read(fileOne);
            int  width  =  imageOne.getWidth();//图片宽度
            int  height  =  imageOne.getHeight();//图片高度
            //从图片中读取RGB
            int[]  imageArrayOne  =  new  int[width*height];
            imageArrayOne  =  imageOne.getRGB(0,0,width,height,imageArrayOne,0,width);

            //对第二张图片做相同的处理
            File  fileTwo  =  new  File(secondSrcImagePath);
            BufferedImage  imageTwo  =  ImageIO.read(fileTwo);
            int width2 = imageTwo.getWidth();
            int height2 = imageTwo.getHeight();
            int[]   ImageArrayTwo  =  new  int[width2*height2];
            ImageArrayTwo  =  imageTwo.getRGB(0,0,width,height,ImageArrayTwo,0,width);
            //ImageArrayTwo  =  imageTwo.getRGB(0,0,width2,height2,ImageArrayTwo,0,width2);

            //生成新图片
            //int height3 = (height>height2 || height==height2)?height:height2;
            BufferedImage  imageNew  =  new  BufferedImage(width*2,height,BufferedImage.TYPE_INT_RGB);
            //BufferedImage  imageNew  =  new  BufferedImage(width+width2,height3,BufferedImage.TYPE_INT_RGB);
            imageNew.setRGB(0,0,width,height,imageArrayOne,0,width);//设置左半部分的RGB
            imageNew.setRGB(width,0,width,height,ImageArrayTwo,0,width);//设置右半部分的RGB
            //imageNew.setRGB(width,0,width2,height2,ImageArrayTwo,0,width2);//设置右半部分的RGB

            File  outFile  =  new  File(toPath);
            ImageIO.write(imageNew,  imageFormat,  outFile);//写图片
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 横向拼接一组（多张）图像
     * @param pics  将要拼接的图像
     * @param type 图像写入格式
     * @param dstPic 图像写入路径
     * @return
     */
    public static boolean joinImageListHorizontal(String[] pics, String type, String dstPic) {
        try {
            int len = pics.length;
            if (len < 1) {
                System.out.println("pics len < 1");
                return false;
            }
            File[] src = new File[len];
            BufferedImage[] images = new BufferedImage[len];
            int[][] imageArrays = new int[len][];
            for (int i = 0; i < len; i++) {
                src[i] = new File(pics[i]);
                images[i] = ImageIO.read(src[i]);
                int width = images[i].getWidth();
                int height = images[i].getHeight();
                imageArrays[i] = new int[width * height];// 从图片中读取RGB
                imageArrays[i] = images[i].getRGB(0, 0, width, height,  imageArrays[i], 0, width);
            }

            int dst_width = 0;
            int dst_height = images[0].getHeight();
            for (int i = 0; i < images.length; i++) {
                dst_height = dst_height > images[i].getHeight() ? dst_height : images[i].getHeight();
                dst_width += images[i].getWidth();
            }
            //System.out.println(dst_width);
            //System.out.println(dst_height);
            if (dst_height < 1) {
                System.out.println("dst_height < 1");
                return false;
            }
            /*
             * 生成新图片
             */
            BufferedImage ImageNew = new BufferedImage(dst_width, dst_height,  BufferedImage.TYPE_INT_RGB);
            int width_i = 0;
            for (int i = 0; i < images.length; i++) {
                ImageNew.setRGB(width_i, 0, images[i].getWidth(), dst_height,  imageArrays[i], 0, images[i].getWidth());
                width_i += images[i].getWidth();
            }
            File outFile = new File(dstPic);
            ImageIO.write(ImageNew, type, outFile);// 写图片
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Base64字符串转图片
     * @param base64String
     * @param imageFileName
     */
    public static void convertBase64StrToImage(String base64String, String imageFileName) {
        ByteArrayInputStream bais = null;
        try {
            //获取图片类型
            String suffix = imageFileName.substring(imageFileName.lastIndexOf(".") + 1);
            //获取JDK8里的解码器Base64.Decoder,将base64字符串转为字节数组
            byte[] bytes = Base64.getDecoder().decode(base64String);
            //构建字节数组输入流
            bais = new ByteArrayInputStream(bytes);
            //通过ImageIO把字节数组输入流转为BufferedImage
            BufferedImage bufferedImage = ImageIO.read(bais);
            //构建文件
            File imageFile = new File(imageFileName);
            //写入生成文件
            ImageIO.write(bufferedImage, suffix, imageFile);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (bais != null) {
                    bais.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
