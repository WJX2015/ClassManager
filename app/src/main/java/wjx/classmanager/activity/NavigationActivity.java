package wjx.classmanager.activity;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import wjx.classmanager.R;
import wjx.classmanager.widget.PageTransformer;

public class NavigationActivity extends BaseActivity {

    private ViewPager mViewPager;
    private TextView mTextView;

    private int mImgs[] ={R.drawable.nav1,R.drawable.nav2,
            R.drawable.nav3,R.drawable.nav4};

    private ImageView[] mImageViews = new ImageView[mImgs.length];

    @Override
    public void initView() {
        mTextView = (TextView) findViewById(R.id.nav_used);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);

        mViewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return mImageViews.length;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view==object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                ImageView imageView = new ImageView(mContext);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setImageResource(mImgs[position]);
                container.addView(imageView);
                mImageViews[position]=imageView;
                return imageView;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(mImageViews[position]);
            }
        });

        mViewPager.setPageTransformer(true,new PageTransformer());
    }

    @Override
    public void initListener() {
        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(MainActivity.class,true);
            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position==mImgs.length-1){
                    mTextView.setVisibility(View.VISIBLE);
                    mTextView.setAlpha(0f);
                    mTextView.animate().alpha(1f).setDuration(1000).start();
                }else{
                    mTextView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void initData() {

    }

    @Override
    public int getLayout() {
        return R.layout.activity_navigation;
    }
}
