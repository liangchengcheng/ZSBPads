package com.lcc.entity;

import java.io.Serializable;

/**
 * Author:       梁铖城
 * Email:        1038127753@qq.com
 * Date:         2015年11月21日15:28:25
 * Description:  开始或者结束轮训器
 */
public class NewsInfo implements Serializable {


    /**
     * z_data : 1
     * replay_data : 12
     * xt_data : 1
     * latter_data : 0
     */

    private int z_data;
    private int replay_data;
    private int xt_data;
    private int latter_data;

    public int getZ_data() {
        return z_data;
    }

    public void setZ_data(int z_data) {
        this.z_data = z_data;
    }

    public int getReplay_data() {
        return replay_data;
    }

    public void setReplay_data(int replay_data) {
        this.replay_data = replay_data;
    }

    public int getXt_data() {
        return xt_data;
    }

    public void setXt_data(int xt_data) {
        this.xt_data = xt_data;
    }

    public int getLatter_data() {
        return latter_data;
    }

    public void setLatter_data(int latter_data) {
        this.latter_data = latter_data;
    }
}
