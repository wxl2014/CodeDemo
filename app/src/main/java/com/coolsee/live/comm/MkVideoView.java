package com.coolsee.live.comm;

//import com.uniview.parser.PlayUrl;

import java.util.Map;

//import com.mywa.parser.PlayUrl;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Parcel;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.widget.AbsoluteLayout;

public class MkVideoView extends NVideoView {

	static final String TAG = "MkVideoView";

	public final static int SCREEN_SMALL_STRETCH = 6;
	public final static int SCREEN_FUL_STRETCH = 5;//全屏
	public final static int SCREEN_FUL_KEEPRADIO = 1; //原比例
	public final static int SCREEN_FUL_CUT = 2;
	public final static int SCREEN_NONE = 3;
	public final static int SCREEN_NORMAL = 4;
	public final static int SCREEN_FUL_AUTO = 0;//自适应
	

	int ScreenWidth, ScreenHeight, mScreenMode;
	
	private int mVideoWidth;
	private int mVideoHeight;
	double screenRatio;
	
	private int mSmallWidth = 0;
	private int mSmallHeight = 0;
	
	final static boolean DEBUG = true;

//	private PlayUrl mPlayUrl = null;
	
	private Uri mCurVideoUri = null;
	private Map<String, String> mHeaders = null;
	
	
	private void init() {
		WindowManager wMg = (WindowManager) this.getContext().getSystemService(
				Context.WINDOW_SERVICE);
		Display display = wMg.getDefaultDisplay();
		ScreenWidth = display.getWidth();
		ScreenHeight = display.getHeight();
		
		if ( 1280 == ScreenWidth )
		{
			ScreenHeight = 720;
		}
		// for android 4.0, the APIs to get screen width and height are changed, but hidden from application.
//		ScreenWidth = 1280;
//		ScreenHeight = 720;
		screenRatio = (double)ScreenWidth / (double)ScreenHeight;
	}
	
	public MkVideoView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public MkVideoView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public MkVideoView(Context context) {
		super(context);
		init();
	}

	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    	if(DEBUG) Log.i(TAG, "onMeasure");
        int width = getDefaultSize(mVideoWidth, widthMeasureSpec);
        int height = getDefaultSize(mVideoHeight, heightMeasureSpec);

        if ( mScreenMode == SCREEN_SMALL_STRETCH ) {
        	width = mSmallWidth;
        	height = mSmallHeight;
        }
        else if (mScreenMode == 5) {
        	width = ScreenWidth;
        	height = ScreenHeight;
        }
        else 
        {
	        if (mVideoWidth > 0 && mVideoHeight > 0) {
	            if ( mVideoWidth * height  > width * mVideoHeight ) {
	                if(DEBUG) Log.i(TAG, "image too tall, correcting");
	                height = width * mVideoHeight / mVideoWidth;
	            } else if ( mVideoWidth * height  < width * mVideoHeight ) {
	            	if(DEBUG) Log.i(TAG, "image too wide, correcting");
	                width = height * mVideoWidth / mVideoHeight;
	            } else {
	            	if(DEBUG) Log.i(TAG, "aspect ratio is correct: " +
	                        width+"/"+height+"="+
	                        mVideoWidth+"/"+mVideoHeight);
	            }
	        }
        }
        
        if(DEBUG) Log.i(TAG, "setting size: " + width + 'x' + height);
        setMeasuredDimension(width, height);
    }
	
	// purpose:full screen display according to full screen mode
	public void setLocation(int x , int y , int w , int h) {

		int left = x;
		int top = y;

		Log.d(TAG ,"ScreenWidth=" + ScreenWidth + "ScreenHeight=" + ScreenHeight +  "X = " + x  + " Y=" + y + " W=" +w + " H" +h);
			
		w = (ScreenWidth - x) < w ? (ScreenWidth - x ) : w ;
		h = (ScreenHeight - y) < h ? (ScreenHeight - y ) : h ;
		
		Log.d(TAG , "left = " + x  + " top=" + y + " W=" +w + " H" +h);
		
		mSmallWidth = w;
		mSmallHeight = h;
		mScreenMode = SCREEN_SMALL_STRETCH;
		resetLayoutParam(x, y, w, h);
	}
	
	// purpose:full screen display according to full screen mode
	public void fullScreen(int iMode) {

		int videoWidth = 0;
		int videoHeight = 0;
		int mWidth = ScreenWidth;
		int mHeight = ScreenHeight;

		int left = 0;
		int top = 0;
		
		mScreenMode = iMode;
		
		switch (iMode) {
		case SCREEN_FUL_STRETCH: // stretch to full screen
			Log.e(TAG, "SCREEN_FUL_STRETCH " + ScreenWidth + ", "
					+ ScreenHeight);
			resetLayoutParam(0, 0, ScreenWidth, ScreenHeight);
			break;
		case SCREEN_FUL_KEEPRADIO: // stretch to full screen keeping raw ratio
			videoWidth = getVideoWidth();
			videoHeight = getVideoHeight();

			left = 0;
			top = 0;
			if (videoWidth > 0 && videoHeight > 0) {
				if (videoWidth * mHeight > mWidth * videoHeight) {
					// Log.i("@@@", "image too tall, correcting");
					mHeight = mWidth * videoHeight / videoWidth;
					top = (ScreenHeight - mHeight) / 2;
				} else if (videoWidth * mHeight < mWidth * videoHeight) {
					// Log.i("@@@", "image too wide, correcting");
					mWidth = mHeight * videoWidth / videoHeight;
					left = (ScreenWidth - mWidth) / 2;
				} else {

				}
			}

			Log.e(TAG, "SCREEN_FUL_KEEPRADIO " + left + ", " + top + ", "
					+ mWidth + ", " + mHeight);
			resetLayoutParam(left, top, mWidth, mHeight);
			break;
		case SCREEN_NORMAL:
			videoWidth = getVideoWidth();
			videoHeight = getVideoHeight();
			
			left = (ScreenWidth - videoWidth) / 2;
			top = (ScreenHeight - videoHeight) / 2;
			
			Log.e(TAG, "SCREEN_FUL_KEEPRADIO " + left + ", " + top + ", "
					+ videoWidth + ", " + videoHeight);
			resetLayoutParam(left, top, videoWidth, videoHeight);
			break;
		}
	}

	private final static String IMEDIA_PLAYER = "android.media.IMediaPlayer";

	public Parcel newRequest() {
		Parcel parcel = Parcel.obtain();
		parcel.writeInterfaceToken(IMEDIA_PLAYER);
		return parcel;
	}

	public void setBrowserMode(MediaPlayer mediaPlayer) {
//		Parcel reply = Parcel.obtain();// replay from invoke
//		Parcel request = newRequest();
//		int flag = 208;// cmd_type for invoke
//
//		// construct request
//		request.writeInt(flag);
//		request.writeInt(1);
//
//		if (0 == mediaPlayer.invoke(request, reply)) {
//			//Log.d(TAG, "setBrowserMode() successful!");
//			return;
//		} else {
//			//Log.d(TAG, "setBrowserMode() failed!");
//			return;
//		}
	}

	public int getVideoWidth() {
		return mVideoWidth;
	}

	// add by xiaogang.xie
	// purpose:get heigth of the video
	public int getVideoHeight() {
		return mVideoHeight;
	}

	public void setVideoSize(int w, int h) {
		mVideoWidth = w;
		mVideoHeight = h;
	}

	public void VideoSelfFit(boolean b, int screenFulStretch) {
		// TODO Auto-generated method stub
		if ( screenFulStretch == SCREEN_FUL_AUTO) {
			
	       	double ratio = (double) mVideoWidth / (double) mVideoHeight;
	       	
	       	if (ratio > screenRatio) {
	       		fullScreen( MkVideoView.SCREEN_FUL_KEEPRADIO);  // 强制性的转成保持比例，防止一些电影变形
	       	}
	       	else {
	       		fullScreen( MkVideoView.SCREEN_FUL_STRETCH); // 其他为铺满屏幕
	       	}
		}
		else {
			fullScreen(screenFulStretch);
		}
	}

	private void resetLayoutParam(int x, int y, int w, int h) {
		AbsoluteLayout.LayoutParams alp = (AbsoluteLayout.LayoutParams)this.getLayoutParams();
		
		Log.d(TAG, "("+alp.x + ", " + alp.y + ", " + alp.width + ", " + alp.height + ") -> " + "("+x + ", " + y + ", " + w + ", " + h + ")");
		
		alp.x = x;
		alp.y = y;
		alp.width = w;
		alp.height = h;
		
		this.setLayoutParams(alp);
	}
	
//	public void setPlayUrl(PlayUrl playUrl) {
//		mPlayUrl = playUrl;
//		mNeedSeek = 0;
//	}

	@Override
	public int getDuration() {
		// TODO Auto-generated method stub
//		if ( null != mPlayUrl && mPlayUrl.hasMuilUrls() ) {
//			return mPlayUrl.getTotalDuration();
//		}
		return super.getDuration();
	}

	@Override
	public int getCurrentPosition() {
		// TODO Auto-generated method stub
//		if ( null != mPlayUrl && mPlayUrl.hasMuilUrls() ) {
//			return (super.getCurrentPosition() + mPlayUrl.getBasePosition());
//		}
		
		return super.getCurrentPosition();
	}
	
	@Override
	public void seekTo(int msec) {
		// TODO Auto-generated method stub
//		if ( null != mPlayUrl && mPlayUrl.hasMuilUrls() ) {
//			// 通过msec计算,需要切换到哪个分片
//			int leaveMsec = mPlayUrl.seekToUrl(msec);
//			String sliceUrl = mPlayUrl.getCurrUrl();
//			Uri sliceUri = Uri.parse(sliceUrl);
////			if ( sliceUri.getPath().equals(mCurVideoUri.getPath()) ) {
//			if ( 0 == sliceUri.compareTo(mCurVideoUri) ) {
//				mNeedSeek = 0;
//				super.seekTo(leaveMsec);
//			} else {
//				this.stopPlayback();
//				this.setVideoPath(sliceUrl);
//				mNeedSeek = leaveMsec;
//			}
//			return;
//		}
		super.seekTo(msec);
	}

	public void seekToDirect(int msec) {
		super.seekTo(msec);
	}
	private int mNeedSeek = 0;
	
	public int getNeedSeek() {
		return mNeedSeek;
	}

	@Override
	public void setVideoPath(String path) {
		// TODO Auto-generated method stub
//		setVideoURI(Uri.parse(path), null);
		mCurVideoUri = Uri.parse(path);
		mHeaders = null;
		super.setVideoPath(path);
	}
	
	public void setVideoURI(Uri uri, Map<String, String> headers) {
		mCurVideoUri = uri;
		mHeaders = headers;
		super.setVideoURI(uri, headers);
	}
	
}
