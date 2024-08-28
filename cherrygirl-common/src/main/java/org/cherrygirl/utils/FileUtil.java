package org.cherrygirl.utils;

import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author nannanness
 */
public class FileUtil {

    public static boolean deleteDir(String path) {
        File file = new File(path);
        //判断是否待删除目录是否存在
        if (!file.exists()) {
            System.err.println("The dir are not exists!");
            return false;
        }
        //取得当前目录下所有文件和文件夹
        String[] content = file.list();
        for (String name : content) {
            File temp = new File(path, name);
            //判断是否是目录
            if (temp.isDirectory()) {
                //递归调用，删除目录里的内容
                deleteDir(temp.getAbsolutePath());
                //删除空目录
                temp.delete();
            } else {
                //直接删除文件
                if (!temp.delete()) {
                    System.err.println("Failed to delete " + name);
                }
            }
        }
        return true;
    }

    public static File findVideoFile(String path) {
        File file = new File(path);
        //判断是否待删除目录是否存在
        if (!file.exists()) {
            System.err.println("The dir are not exists!");
            return null;
        }
        //取得当前目录下所有文件和文件夹
        String[] content = file.list();
        if(content == null){
            return null;
        }
        List<File> fileList = new ArrayList<>();
        for (String name : content) {
            File temp = new File(path, name);
            if(temp.exists() && temp.isFile()){
                fileList.add(temp);
            }else if (temp.exists() && !temp.isFile()){
                findPathFile(fileList, temp.getAbsolutePath());
            }
        }
        System.out.println("fileList size:" + fileList.size());
        File res = fileList.get(new Random().nextInt(fileList.size()));
        System.out.println("choose file:" + res.getName());
        return res;
    }

    public static void findPathFile(List<File> fileList, String path) {
        File file = new File(path);
        //判断是否待删除目录是否存在
        if (!file.exists()) {
            System.err.println("The dir are not exists!");
            return;
        }
        //取得当前目录下所有文件和文件夹
        String[] content = file.list();
        if(content == null){
            return;
        }
        for (String name : content) {
            File temp = new File(path, name);
            //判断是否是目录
            if (temp.isDirectory()) {
                findPathFile(fileList, temp.getAbsolutePath());
            }else if (!temp.isDirectory()) {
                fileList.add(temp);
            }
        }
    }

    public static boolean makeFile(String folder) {
        File file = new File(folder);
        //判断是否待删除目录是否存在
        if (!file.exists()) {
            System.err.println("The dir are not exists!");
            // 调用mkdir()方法创建文件夹
            boolean mkdir = file.mkdir();
            if(mkdir){
                System.err.println("The dir is create:" + folder);
            }
            return mkdir;
        }
        return true;
    }

    public static String checkLegalityFilename(String filename){
        filename = StringUtils.replace(filename, "/", "");
        filename = StringUtils.replace(filename, "\\", "");
        filename = StringUtils.replace(filename, ":", "");
        filename = StringUtils.replace(filename, "*", "");
        filename = StringUtils.replace(filename, "?", "");
        filename = StringUtils.replace(filename, "\"", "");
        filename = StringUtils.replace(filename, "<", "");
        filename = StringUtils.replace(filename, ">", "");
        filename = StringUtils.replace(filename, "|", "");
        return filename;
    }

    public static boolean saveToFile(InputStream inputStream, String filePath) {
        FileOutputStream outputStream = null;
        try {
            int firstByte = inputStream.read();
            int length = inputStream.available();
            System.out.println("inputStream available : " + length);
            if(length > 1024){
                outputStream = new FileOutputStream(filePath);
                outputStream.write(firstByte);
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
                outputStream.flush();
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(inputStream != null){
                    inputStream.close();
                }
                if(outputStream != null){
                    outputStream.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return false;
    }
}
