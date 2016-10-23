package com.lcc.mvp.presenter;

import com.lcc.entity.ComTestAdd;
import com.lcc.entity.Replay;

import java.io.File;
import java.util.List;

public interface ComQuesAddPresenter {

    void ComQuesAdd(ComTestAdd replay, List<File> files);
}
