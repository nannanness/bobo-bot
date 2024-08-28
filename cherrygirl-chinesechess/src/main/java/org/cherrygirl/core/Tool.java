package org.cherrygirl.core;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Tool {
    /*--------------------------------------
     * private静态变量
    ----------------------------------------*/
    /* 路径分隔符 */
    public static String separator = File.separator;
    /* 图片根目录 */
    public static String img_dir;
    /* 棋子根目录 */
    public static String chess_dir;
    /* 棋盘根目录 */
    public static String map_dir;
    /* 走子提示根目录 */
    public static String remind_dir;
    /* 输出根目录 */
    public static String out_dir;
    /*日志打印等级*/
    private static int log_rank = 3;

    public static void initPath(String root) {
        img_dir = root;
        chess_dir = img_dir + separator + "chess";
        map_dir = img_dir + separator + "map";
        remind_dir = img_dir + separator + "moveRemind";
        out_dir = img_dir + separator + "out";
    }


    public static void log(Log rank, String func, String msg) {
        if (log_rank >= rank.getRank()) {
            System.out.println(rank.getMsg() + "[" + func + "] " + msg);
        }
    }

    public static void setLogRank(int rank) {
        if (rank > 3)
            log_rank = 4;
        else
            log_rank = Math.max(rank, 0);
    }

    public static BufferedImage loadImg(String path) {
        try {
            return ImageIO.read(new File(path));
        } catch (IOException e) {
            log(Log.error, "loadImg", path + "图片加载失败");
        }
        return null;
    }

}
