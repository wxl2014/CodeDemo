package com.coolsee.live.mvp;

import com.coolsee.live.mvp.base.BasePresenter;
import com.coolsee.live.mvp.base.BaseView;

public interface MainActivityContract
{
    interface View extends BaseView<Presenter>
    {
        void refreshChannels();
    }

    interface Presenter extends BasePresenter
    {
        void getChannels();
    }
}
