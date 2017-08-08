package com.coolsee.live.ui;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.view.WindowManager;

import com.coolsee.live.R;
import com.coolsee.live.comm.CommApi;
import com.coolsee.live.comm.NVideoView;
import com.coolsee.live.comm.XDnldThreadPool;
import com.coolsee.live.mvp.MainActivityContract;
import com.coolsee.live.mvp.base.MainActivityPresenter;
import com.socks.library.KLog;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity
		implements MainActivityContract.View
{

	@BindView(R.id.video)
	NVideoView mVideo;
	private MainActivityContract.Presenter mPresenter;
    protected XDnldThreadPool mDnldThread = null;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_main);
		ButterKnife.bind(this);
		CommApi.initEnvParams(this);
		new MainActivityPresenter(this, this, mDownloadQueue);
		mPresenter.getChannels();
		initView();
	}

	private void initView()
	{
//		mVideo.setVideoPath("http://live.cgtn.com/500/prog_index.m3u8");
        mVideo.setOnPreparedListener(new MyVideoListener());
		return;
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

	private class MyVideoListener implements MediaPlayer.OnPreparedListener,
			MediaPlayer.OnCompletionListener, MediaPlayer.OnInfoListener,
			MediaPlayer.OnErrorListener
	{

		@Override
		public void onCompletion(MediaPlayer mp)
		{

		}

		@Override
		public boolean onError(MediaPlayer mp, int what, int extra)
		{
			return false;
		}

		@Override
		public boolean onInfo(MediaPlayer mp, int what, int extra)
		{
			return false;
		}

		@Override
		public void onPrepared(MediaPlayer mp)
		{
			mVideo.start();
		}
	}
}
