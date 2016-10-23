package com.lcc.mvp.presenter;

import com.lcc.entity.Article;
import com.lcc.entity.LookArticle;

public interface LookMenuContentPresenter {

    void Fav(LookArticle article, String type);

    void UnFav(LookArticle article);

    //为了查看收藏的重新获取
    void getArticleContentAndFav(String id);
}
