package com.lcc.mvp.presenter;

import com.lcc.entity.Article;

public interface MenuContentPresenter {

    void getArticleContent(String id);

    void Fav(Article article, String type);

    void UnFav(Article article);

}
