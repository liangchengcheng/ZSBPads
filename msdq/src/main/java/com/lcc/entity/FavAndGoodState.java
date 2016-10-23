package com.lcc.entity;

import java.io.Serializable;

/**
 * Author:       梁铖城
 * Email:        1038127753@qq.com
 * Date:         2015年11月21日15:28:25
 * Description:  FavAndGoodState
 */
public class FavAndGoodState implements Serializable {

    /**
     * fav : 1
     * good : 1
     */

    private String fav;
    private String good;

    public String getFav() {
        return fav;
    }

    public void setFav(String fav) {
        this.fav = fav;
    }

    public String getGood() {
        return good;
    }

    public void setGood(String good) {
        this.good = good;
    }
}
