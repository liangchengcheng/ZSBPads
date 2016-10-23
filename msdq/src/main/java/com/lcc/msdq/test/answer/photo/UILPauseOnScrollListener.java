package com.lcc.msdq.test.answer.photo;

import com.nostra13.universalimageloader.core.ImageLoader;

import cn.finalteam.galleryfinal.PauseOnScrollListener;

public class UILPauseOnScrollListener extends PauseOnScrollListener {

    public UILPauseOnScrollListener(boolean pauseOnScroll, boolean pauseOnFling) {
        super(pauseOnScroll, pauseOnFling);
    }

    @Override
    public void resume() {
        ImageLoader.getInstance().resume();
    }

    @Override
    public void pause() {
        ImageLoader.getInstance().pause();
    }
}
