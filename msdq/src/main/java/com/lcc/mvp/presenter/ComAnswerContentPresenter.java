package com.lcc.mvp.presenter;

import com.lcc.entity.Answer;
import com.lcc.entity.CompanyAnswer;

public interface ComAnswerContentPresenter {

    void isFav(String nid);

    void Fav(CompanyAnswer article, String type, String title);

    void UnFav(CompanyAnswer article);

    void getContent(String mid);

    void Good(CompanyAnswer article, String type,String title);

    void UnGood(CompanyAnswer article,String type);
}
