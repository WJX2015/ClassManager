package wjx.classmanager.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.DisplayMetrics;
import android.view.Display;
import android.widget.ImageView;

import wjx.classmanager.R;
import wjx.classmanager.presenter.impl.SplashPresentImpl;
import wjx.classmanager.view.SplashView;
import wjx.classmanager.widget.ManageItemView;

public class SplashActivity extends BaseActivity implements SplashView{

    private SplashPresentImpl mSplashPresent;

    @Override
    public void initView() {
        mSplashPresent =new SplashPresentImpl(this);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(3000);
                    mSplashPresent.checkLogInStatus();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();

    }

    @Override
    public void initListener() {
    }

    @Override
    public void initData() {

    }

    @Override
    public int getLayout() {
        return R.layout.activity_splash;
    }

    @Override
    public void onLogIn() {
        startActivity(new Intent(SplashActivity.this,RegisterActivity.class));
        finish();
    }

    @Override
    public void onNotLogIn() {
        startActivity(new Intent(SplashActivity.this,RegisterActivity.class));
        finish();
    }
}
