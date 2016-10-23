package com.lcc.mvp.presenter;

import com.lcc.entity.CompanyDescription;
import com.lcc.entity.TestEntity;

import java.io.File;
import java.util.List;

public interface TestQuestionAddPresenter {

    void TestQuestionAdd(TestEntity replay, List<File> files);
}
