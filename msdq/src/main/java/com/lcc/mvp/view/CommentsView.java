package com.lcc.mvp.view;

import com.lcc.entity.Article;
import com.lcc.entity.Comments;

import java.util.List;

/**
 * Author:       梁铖城
 * Email:        1038127753@qq.com
 * Date:         2015年11月21日15:28:25
 * Description:
 */
public interface CommentsView extends BaseView{

    void getLoading();

    void getDataEmpty();

    void getDataFail(String msg);

    void refreshOrLoadFail(String msg);

    void refreshDataSuccess(List<Comments> list);

    void loadMoreWeekDataSuccess(List<Comments> entities);

    void rePlaying();

    void replaySuccess();

    void replayFail();

}
