package com.lcc.mvp.presenter;

import com.lcc.entity.AnswerAdd;
import com.lcc.entity.TestEntity;

import java.io.File;
import java.util.List;

public interface TestAnswerAddPresenter {

    void TestAnswerAdd(AnswerAdd replay, List<File> files);
}
