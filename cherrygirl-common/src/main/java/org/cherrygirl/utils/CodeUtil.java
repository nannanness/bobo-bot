package org.cherrygirl.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.springframework.util.ObjectUtils;

import javax.imageio.ImageIO;
import javax.swing.filechooser.FileSystemView;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * 二维码工具类
 */
public class CodeUtil {

    //二维码类型，枚举类
    private static final BarcodeFormat CODE_TYPE = BarcodeFormat.QR_CODE;
    //二维码宽度，单位像素
    private static final int CODE_WIDTH = 400;
    //二维码高度，单位像素
    private static final int CODE_HEIGHT = 400;
    //二维码前景色，0x000000表示黑色
    private static final int FRONT_COLOR = 0x000000;
    //二维码背景色，0xFFFFFF表示白色
    private static final int BACKGROUND_COLOR = 0xFFFFFF;

    /**
     * 生成二维码并保存为文件
     * @param content 二维码内容
     * @param codeImgFileSaveDir 生成的二维码图片存储位置
     * @param fileName 二维码图片文件名
     */
    public static void createCodeToFile(String content, File codeImgFileSaveDir, String fileName) {
        if (ObjectUtils.isEmpty(content) || ObjectUtils.isEmpty(fileName)) {
            return;
        }
        content = content.trim();
        if (codeImgFileSaveDir == null || codeImgFileSaveDir.isFile()) {
            //二维码图片存储目录为空，默认放在桌面
            FileSystemView.getFileSystemView().getHomeDirectory();
        }
        if (!codeImgFileSaveDir.exists()) {
            //二维码图片存储目录不存在，则创建
            codeImgFileSaveDir.mkdirs();
        }
        //生成二维码
        try {
            BufferedImage bufferedImage = getBufferedImage(content);
            File codeImgFile = new File(codeImgFileSaveDir, fileName);
            ImageIO.write(bufferedImage, "png", codeImgFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成二维码并输出到输出流，通常用于输出到网页上进行显示
     * @param content 二维码内容
     * @param outputStream 输出流
     */
    public static void createCodeToOutputStream(String content, OutputStream outputStream) {
        if (ObjectUtils.isEmpty(content)) {
            return;
        }
        content = content.trim();
        //生成二维码
        try {
            BufferedImage bufferedImage = getBufferedImage(content);
            ImageIO.write(bufferedImage, "png", outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 核心代码，生成二维码
     * @param content
     * @return BufferedImage
     * @throws WriterException
     */
    private static BufferedImage getBufferedImage(String content) throws WriterException {
        //com.google.zxing.EncodeHintType：编码提示类，枚举类型
        Map<EncodeHintType, Object> hints = new HashMap<>();
        //EncodeHintType.CHARACTER_SET：设置字符编码类型
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        //EncodeHintType.ERROR_CORRECTION：设置纠错级别
        //ErrorCorrectionLevel：纠错级别，【L：%7】【M：15%】【Q：25%】[H：30%]
        //默认为L级别，纠错级别不同，生成的图案不同，但扫描结果一致
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
        //EncodeHintType.MARGIN：设置二维码边距，单位像素
        hints.put(EncodeHintType.MARGIN, 1);

        //创建工厂类
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        //指定二维码类型和参数，返回对应的Writter进行编码后的二维码对象
        BitMatrix bitMatrix = multiFormatWriter.encode(content, CODE_TYPE, CODE_WIDTH, CODE_HEIGHT, hints);
        //创建图像缓冲区
        BufferedImage bufferedImage = new BufferedImage(CODE_WIDTH, CODE_HEIGHT, BufferedImage.TYPE_INT_BGR);
        //填充
        for (int x = 0; x < CODE_WIDTH; x++) {
            for (int y = 0; y < CODE_HEIGHT; y++) {
                //bitMatrix.get(x, y)返回true，表示黑色即前景色
                bufferedImage.setRGB(x, y, bitMatrix.get(x, y) ? FRONT_COLOR : BACKGROUND_COLOR);
            }
        }
        return bufferedImage;
    }

}
