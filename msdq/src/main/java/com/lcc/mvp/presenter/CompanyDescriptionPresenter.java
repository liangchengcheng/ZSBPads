package com.lcc.mvp.presenter;

public interface CompanyDescriptionPresenter {

    void getData(int page, String company_name, String area);

    void loadMore(int page, String company_name, String area);

    void refresh(String company_name, String area);

}
