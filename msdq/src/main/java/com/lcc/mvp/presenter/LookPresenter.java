package com.lcc.mvp.presenter;

import com.lcc.entity.UserGood;

public interface LookPresenter {

    void isFav(String nid);

    void Fav(String nid, String type, String title);

    void UnFav(String nid);

    void getContent(String mid);

    void Good(UserGood good, String type, String title);

    void UnGood(String nid);
}
