package com.coolsee.live.comm;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Build;
import android.util.Base64;
import android.util.DisplayMetrics;

import com.coolsee.live.bean.ChannelGroup;
import com.coolsee.live.ui.MyApplication;

import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;
import java.util.List;
import java.util.Locale;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

public class CommApi
{
    private static Context mContext = null;
    private static Typeface mFontawesomeTypeFace = null;
    private static String mAppVer = null;// 当前软件版本号
    private static int mAppVerCode = 0;// 当前软件版本号
    private static int mSdkVer = 0;// sdk版本号
    private static int mDisplayWidth = 0;
    private static int mDisplayHeight = 0;

    public static int getStatusBarHeight()
    {
        return Resources.getSystem().getDimensionPixelSize(Resources.getSystem()
                .getIdentifier("status_bar_height", "dimen", "android"));
    }

    /* 初始化部分变量到内存当中 */
    public static void initEnvParams(Context ctx)
    {
        mContext = ctx;
        if( null == mFontawesomeTypeFace )
        {
            mFontawesomeTypeFace = Typeface.createFromAsset(mContext
                    .getAssets(), "fontawesome-webfont.ttf");
        }
        if( null == mAppVer )
        {
            String verName = null;
            try
            {
                verName = ctx.getPackageManager()
                        .getPackageInfo(ctx.getPackageName(), 0).versionName;
                mAppVer = verName;
                mAppVerCode = ctx.getPackageManager()
                        .getPackageInfo(ctx.getPackageName(), 0).versionCode;
            } catch( Exception e )
            {
            }
        }
        if( 0 == mSdkVer )
        {
            mSdkVer = Build.VERSION.SDK_INT;
        }
        if( 0 == mDisplayWidth )
        {
            DisplayMetrics dm = new DisplayMetrics();
            ((Activity) ctx).getWindowManager().getDefaultDisplay()
                    .getMetrics(dm);
            mDisplayWidth = dm.widthPixels;

            mDisplayHeight = dm.heightPixels;
        }
        return;
    }

    /**
     * 获取屏幕宽度，with px
     */
    public static int getDisPlayWidth()
    {
        return mDisplayWidth;
    }

    /**
     * 获取屏幕高度，with px
     */
    public static int getDisPlayHeight()
    {
        return mDisplayHeight;
    }

    /**
     * 系统当前语言环境 zh 中文 en 英文
     *
     * @return
     */
    public static String getLanguage()
    {
        String language = "zh";
        if( null != mContext )
        {
            Locale locale = mContext.getResources().getConfiguration().locale;
            language = locale.getLanguage();
        }
        return language;
    }

    /**
     * 获取sdk版本号
     *
     * @return
     */
    public static String getSdkVer()
    {
        String ver = "";
        try
        {
            ver = String.valueOf(mSdkVer);
        } catch( Exception e )
        {
        }
        return ver;
    }

    /**
     * 整型版本号
     *
     * @return
     */
    public static int getSdkVerInteger()
    {
        return mSdkVer;
    }

    /**
     * version code
     *
     * @return
     */
    public static int getVersionCode()
    {
        return mAppVerCode;
    }

    /**
     * 获取app版本号
     */
    public static String getAppVer()
    {
        return mAppVer;
    }

    /**
     * 判断url是否有效
     *
     * @param url
     * @return
     */
    public static boolean checkUrlValid(String url)
    {
        if( url == null )
        {
            return false;
        }

        if( url.length() < 10 )
        {
            return false;
        }

        if( url.indexOf("http://") < 0 )
        {
            return false;
        }

        if( url.indexOf("{") >= 0 || url.indexOf("}") >= 0 )
        {
            return false;
        }

        return true;
    }

    /**
     * 将需要的关键字数据加入到url当中去
     *
     * @param url
     * @param key
     * @param value
     * @return
     */
    public static String checkUrlKeyValue(String url, String key, String value)
    {
        if( null == url )
        {
            return null;
        }

        if( url.length() == 0 )
        {
            return null;
        }

        if( url.indexOf(key + "=") >= 0 )
        {
            return url;
        }
        else if( url.indexOf("?") >= 0 )
        {
            return (url + "&" + key + "=" + value);
        }
        else
        {
            return (url + "?" + key + "=" + value);
        }
    }

    /**
     * 根据分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue)
    {
        if( null == context )
        {
            return (int) dpValue;
        }
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue)
    {
        if( null == context )
        {
            return (int) pxValue;
        }
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static boolean isTestVersion()
    {
        try
        {
            MyApplication app = ((MyApplication) ((Activity) mContext)
                    .getApplication());
            return app.isTestVersion();
        } catch( Exception e )
        {
        }
        return true;
    }

    public static byte[] decode(String data) throws Exception
    {
        try
        {
            String key = "helloLTP";
            String strIv = "xiaoweig";
            DESKeySpec dks = new DESKeySpec(key.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            // key的长度不能够小于8位字节
            Key secretKey = keyFactory.generateSecret(dks);
            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec(strIv.getBytes());
            AlgorithmParameterSpec paramSpec = iv;
            cipher.init(Cipher.DECRYPT_MODE, secretKey, paramSpec);
            byte[] bytes = cipher.doFinal(Base64.decode(data,Base64.DEFAULT));
            return bytes;
        } catch( Exception e )
        {
            throw new Exception(e);
        }
    }

    public static List<ChannelGroup> getChannelGroups()
    {
        MyApplication app = ((MyApplication) ((Activity) mContext)
                .getApplication());
        return app.getChannelGroups();
    }
}
