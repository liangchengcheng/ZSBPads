package com.lcc.mvp.view;

import com.lcc.entity.FavEntity;
import com.lcc.entity.Letter;

import java.util.List;

/**
 * Author:       梁铖城
 * Email:        1038127753@qq.com
 * Date:         2015年11月21日15:28:25
 * Description:  LetterView
 */
public interface LetterView extends BaseView{

    void getLoading();

    void getDataEmpty();

    void getDataFail(String msg);

    void getDataSuccess(List<Letter> list);
}
