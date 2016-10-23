package com.lcc.mvp.view;

import com.lcc.entity.TestEntity;

import java.util.List;

public interface TestView extends BaseView {

    void getLoading();

    void getDataEmpty();

    void getDataFail(String msg);

    void refreshOrLoadFail(String msg);

    void refreshView(List<TestEntity> entities);

    void loadMoreView(List<TestEntity> entities);
}
