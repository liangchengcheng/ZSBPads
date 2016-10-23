package com.lcc.mvp.presenter;

import com.lcc.entity.TestEntity;

import java.util.List;

public interface TestPresenter {

    void getData(int page,String options,String startTime,String endTime,String orders);

    void loadMore(int page,String options,String startTime,String endTime,String orders);

    void refresh(int page,String options,String startTime,String endTime,String orders);

}
