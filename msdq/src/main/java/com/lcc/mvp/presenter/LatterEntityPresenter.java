package com.lcc.mvp.presenter;

import com.lcc.entity.Replay;
import com.lcc.entity.SendLatter;

public interface LatterEntityPresenter {

    void getData(String page,String from);

    void sendLatter(SendLatter replay);

}
