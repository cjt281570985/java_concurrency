package com.cjt;

import java.io.File;

public class TestEditFileNameReplaceMac {

    public static void main(String[] args) {
        //查找win文件
        File file = new File("/Volumes/4T/study/Python/路飞-爬虫开发+APP逆向超级大神班/06--爬虫6期：爬虫&逆向6期【完结】");
        //String newPath = "D:\\study\\李兴华Oracle";
        String delStr = "第18章-day17识货2" + "-";
        editName(file, delStr, file.getAbsolutePath());

        String addName = "555";
        // addFileName(file, ".avi", addName, newPath);
    }

    private static void editName(File file, String delStr, String newPath) {
        String name = file.getName();
        System.out.println(name);
        if (name.indexOf(delStr) != -1) {
            //String newName = name.substring(regex.length());//截取_后面的String
            String newName = name.replace(delStr, "");
            file.renameTo(new File(newPath + "/" + newName));
        }
        //检查子目录
        if (file.isDirectory()) {
            for (File child : file.listFiles()) {
                editName(child, delStr, child.isFile() ? child.getParent() : child.getAbsolutePath());
            }
        }
    }

    private static void addFileName(File file, String regex, String addName, String path) {
        String name = file.getName();
        if (name.endsWith(regex)) {
            file.renameTo(new File(path + "\\" + addName + name));
        }
        //检查子目录
        if (file.isDirectory()) {
            for (File child : file.listFiles()) {
                addFileName(child, regex, addName, path);
            }
        }
    }
}
