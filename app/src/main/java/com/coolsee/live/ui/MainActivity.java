package com.coolsee.live.ui;

import android.os.Bundle;

import com.coolsee.live.R;
import com.coolsee.live.comm.CommApi;
import com.coolsee.live.mvp.MainActivityContract;
import com.coolsee.live.mvp.base.MainActivityPresenter;

public class MainActivity extends BaseActivity implements MainActivityContract.View
{

    private MainActivityContract.Presenter mPresenter;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
        CommApi.initEnvParams(this);
        new MainActivityPresenter(this,this,mDownloadQueue);
        mPresenter.getChannels();
	}

    @Override
    public void setPresenter(MainActivityContract.Presenter presenter)
    {
        mPresenter = presenter;
    }

    @Override
    public void refreshChannels()
    {

    }
}
