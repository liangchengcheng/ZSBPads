package com.lcc.mvp.presenter;

import com.lcc.entity.CompanyDescription;
import com.lcc.entity.QuestionAdd;

import java.io.File;
import java.util.List;

public interface QuestionAddPresenter {

    void QuestionAdd(QuestionAdd replay, List<File> files);
}
