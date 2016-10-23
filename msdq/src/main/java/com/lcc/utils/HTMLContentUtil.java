package com.lcc.utils;

import android.text.TextUtils;
import android.util.Log;

import com.lcc.AppConstants;
import com.lcc.view.edit.editor.SEditorData;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Author:       梁铖城
 * Email:        1038127753@qq.com
 * Date:         2016年07月31日22:20:32
 * Description:  HTMLContentUtil
 */
public class HTMLContentUtil {

    public static String getContent(List<SEditorData> editList) {
        StringBuilder sb = new StringBuilder();
        sb.append("<p>");
        for (SEditorData itemData : editList) {
            if (itemData.getInputStr() != null) {
                sb.append(itemData.getInputStr());
            } else if (itemData.getImagePath() != null) {
                sb.append("<br/>");
                sb.append("<img src=\"");
                sb.append(AppConstants.RequestPath.BASE_URL+"/images");
                sb.append(getImageName(itemData.getImagePath()));
                sb.append("\" width='100%' />");
                sb.append("<br/>");
            }
        }
        sb.append("</p>");
        return sb.toString();
    }

    public static List<File> getFiles(List<SEditorData> editList) {
        List<File> files = new ArrayList<>();
        for (SEditorData itemData : editList) {
            if (itemData.getImagePath() != null) {
                File file = new File(itemData.getImagePath());
                files.add(file);
            }
        }

        return files;
    }

    private static String getImageName(String imagePath) {
        if (TextUtils.isEmpty(imagePath)) {
            return "服务器上的一个错误的图片地址";
        }

        return imagePath.substring(imagePath.lastIndexOf("/"));
    }
}
