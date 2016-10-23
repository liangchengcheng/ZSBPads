package com.lcc.utils;

import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileUtil {

    /**
     * Copy a file from src to dst path.
     */
    public static boolean fileCopy(String srcFilepath, String dstFilepath) {
        return fileCopy(new File(srcFilepath), new File(dstFilepath));
    }

    public static boolean fileCopy(File srcFile, File dstFile) {
        int length = 1048891;
        FileChannel inC = null;
        FileChannel outC = null;
        try {
            FileInputStream in = new FileInputStream(srcFile);
            FileOutputStream out = new FileOutputStream(dstFile);
            inC = in.getChannel();
            outC = out.getChannel();
            ByteBuffer b = null;
            while (inC.position() < inC.size()) {
                if ((inC.size() - inC.position()) < length) {
                    length = (int) (inC.size() - inC.position());
                } else {
                    length = 1048891;
                }
                b = ByteBuffer.allocateDirect(length);
                inC.read(b);
                b.flip();
                outC.write(b);
                outC.force(false);
            }

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (inC != null && inC.isOpen()) {
                    inC.close();
                }
                if (outC != null && outC.isOpen()) {
                    outC.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean createFolder(String path) {
        if (!Environment.getExternalStorageState().equalsIgnoreCase(Environment.MEDIA_MOUNTED))
            return false;

        File dir = new File(path);
        if (!dir.exists() && !dir.mkdirs()) {
            return false;
        }

        return true;
    }

    public static boolean deleteFileDir(File dir) {
        if (dir == null) {
            return true;
        }

        File[] fList = dir.listFiles();
        if (fList == null || fList.length <= 0) {
            return true;
        }

        for (File file : fList) {
            if (file == null) {
                continue;
            }

            if (file.isDirectory()) {
                if (!deleteFileDir(file)) {
                    return false;
                }
            } else {
                if (!file.delete()) {
                    return false;
                }
            }
        }

        return true;
    }

    public static void createTempFile(String tempUrl) {
        File tempFile = new File(tempUrl);
        if (!tempFile.exists()) {
            tempFile.mkdir();
        }
    }

    /** */
    /**
     * 文件重命名
     *
     * @param path    文件目录
     * @param oldname 原来的文件名
     * @param newname 新文件名
     */
    public static void renameFile(String path, String oldname, String newname) {
        //新的文件名和以前文件名不同时,才有必要进行重命名
        if (!oldname.equals(newname)) {
            File old_file = new File(path + "/" + oldname);
            File new_file = new File(path + "/" + newname);
            if (!old_file.exists()) {
                return;
            }

            //若在该目录下已经有一个文件和新文件名相同，则不允许重命名
            if (new_file.exists())
                System.out.println(newname + "已经存在！");
            else {
                old_file.renameTo(new_file);
            }
        } else {
            System.out.println("新文件名和旧文件名相同...");
        }
    }

    public static void copyFile(String oldPath, String newPath) {
        try {
            int byte_sum = 0;
            int byte_read = 0;
            File old_file = new File(oldPath);
            if (old_file.exists()) {
                //读入原文件
                InputStream inStream = new FileInputStream(oldPath);
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1444];
                while ((byte_read = inStream.read(buffer)) != -1) {
                    //字节数 文件大小
                    byte_sum += byte_read;
                    System.out.println(byte_sum);
                    fs.write(buffer, 0, byte_read);
                }
                inStream.close();
            }
        } catch (Exception e) {
            System.out.println("复制单个文件操作出错");
            e.printStackTrace();
        }
    }
}
