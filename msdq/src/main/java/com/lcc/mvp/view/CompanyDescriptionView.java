package com.lcc.mvp.view;

import com.lcc.entity.CompanyDescription;
import com.lcc.entity.TestEntity;

import java.util.List;

public interface CompanyDescriptionView extends BaseView{

    void getLoading();

    void getDataEmpty();

    void getDataFail(String msg);

    void refreshOrLoadFail(String msg);

    void refreshView(List<CompanyDescription> entities);

    void loadMoreView(List<CompanyDescription> entities);
}
