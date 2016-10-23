package com.lcc.frame;

import android.os.Environment;

/**
 * Author:       梁铖城
 * Email:        1038127753@qq.com
 * Date:         2015年11月21日15:28:25
 * Description:  Propertity
 */
public class Propertity {
    public static final String newFile = Environment.getExternalStorageDirectory().getPath()
            + "/com.lcc.mstdq/";

    public final class Article {
        public final static String NAME = "文章";
    }

    public final class Test {
        public final static String QUESTION = "资料问题";
        public final static String ANSWER = "资料答案";
    }

    public final class COM {
        public final static String DESCRIPTION = "公司介绍";
        public final static String QUESTION = "公司问题";
        public final static String ANSWER = "公司答案";
    }

    public final class OPTIONS {
        public final static String ZYZS = "专业知识";
        public final static String RSZS = "人事知识";
        public final static String QT = "其他";
    }

    public final class ArticleType{
        public final static String MSZB = "面试准备";
        public final static String MSJL = "面试简历";
        public final static String MSJQ = "面试技巧";
        public final static String MSZZ = "常问问题";
        public final static String MSGX = "面试感想";
        public final static String MSJZ = "面试举止";
        public final static String MSJT = "面试流程";
        public final static String QT = "其他";
    }

}
