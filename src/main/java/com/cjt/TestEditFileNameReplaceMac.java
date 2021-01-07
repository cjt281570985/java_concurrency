package com.cjt;

import java.io.File;

public class TestEditFileNameReplaceMac {

    public static void main(String[] args) {
        //查找win文件
        File file = new File("/Volumes/500G/202009/Final Cut Pro X 10.4.6教程/");
        //String newPath = "D:\\study\\李兴华Oracle";
        String delStr = "Final Cut Pro X 10.4.6中文基础教程 - ";
        editName(file, delStr, file.getAbsolutePath());

        String addName = "chenjt";
        // addFileName(file, ".avi", addName, newPath);
    }

    private static void editName(File file, String delStr, String newPath) {
        String name = file.getName();
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
