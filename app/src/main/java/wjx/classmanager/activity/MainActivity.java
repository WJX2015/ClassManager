package wjx.classmanager.activity;

import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.hyphenate.chat.EMClient;

import wjx.classmanager.R;
import wjx.classmanager.fragment.ManageFragment;
import wjx.classmanager.fragment.MessageFragment;
import wjx.classmanager.fragment.NotifyFragment;
import wjx.classmanager.widget.SlideMenu;
import wjx.classmanager.widget.TitleBar;

public class MainActivity extends BaseActivity implements View.OnClickListener{

    private static final int FRAGMENT_MSG=0;
    private static final int FRAGMENT_NOTIFY=1;
    private static final int FRAGMENT_MANAGE=2;
    private static final int FRAGMENT_COUNT=3;

    private SlideMenu mSlideMenu;
    private TitleBar mTitleBar;
    private RadioGroup mRadioGroup;

    private Fragment[] mFragments;
    private FragmentManager mFragmentManager;
    private FragmentTransaction mTransaction;

    private FrameLayout mMessageFrame;
    private FrameLayout mNotifyFrame;
    private FrameLayout mManageFrame;
    private FrameLayout[] mFrameLayouts;

    //左边菜单选项
    private RelativeLayout mLeftClass;
    private RelativeLayout mLeftEvaluate;
    private RelativeLayout mLeftVote;
    private RelativeLayout mLeftData;
    private RelativeLayout mLeftInfo;
    private RelativeLayout mLeftUnsign;

    @Override
    public void initView() {
        //控件绑定
        mSlideMenu = (SlideMenu) findViewById(R.id.slide_menu);
        mTitleBar = (TitleBar) findViewById(R.id.title_bar);
        mRadioGroup = (RadioGroup) findViewById(R.id.radio_group);

        //左边菜单
        mLeftClass = (RelativeLayout) findViewById(R.id.left_class);
        mLeftEvaluate = (RelativeLayout) findViewById(R.id.left_evaluate);
        mLeftVote = (RelativeLayout) findViewById(R.id.left_vote);
        mLeftData = (RelativeLayout) findViewById(R.id.left_data);
        mLeftInfo = (RelativeLayout) findViewById(R.id.left_info);
        mLeftUnsign = (RelativeLayout) findViewById(R.id.left_unsign);

        //布局绑定
        mMessageFrame = (FrameLayout) findViewById(R.id.frame_message);
        mNotifyFrame = (FrameLayout) findViewById(R.id.frame_notify);
        mManageFrame = (FrameLayout) findViewById(R.id.frame_manage);
        mFrameLayouts = new FrameLayout[FRAGMENT_COUNT];

        //管理布局
        mFrameLayouts[0] =mMessageFrame;
        mFrameLayouts[1] =mNotifyFrame;
        mFrameLayouts[2] =mManageFrame;

        //获取Fragment事务
        mFragmentManager=getSupportFragmentManager();
        mTransaction =mFragmentManager.beginTransaction();

        //创建Fragment
        MessageFragment messageFragment = new MessageFragment();
        NotifyFragment notifyFragment = new NotifyFragment();
        ManageFragment manageFragment = new ManageFragment();

        //管理Fragment
        mFragments = new Fragment[FRAGMENT_COUNT];
        mFragments[0]=messageFragment;
        mFragments[1]=notifyFragment;
        mFragments[2]=manageFragment;

        //默认显示消息界面
        showFragment(FRAGMENT_MSG);
    }

    @Override
    public void initListener() {
        //左边菜单
        mLeftClass.setOnClickListener(this);
        mLeftEvaluate.setOnClickListener(this);
        mLeftVote.setOnClickListener(this);
        mLeftData.setOnClickListener(this);
        mLeftInfo.setOnClickListener(this);
        mLeftUnsign.setOnClickListener(this);

        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId){
                    case R.id.radio_msg:
                        showFragment(FRAGMENT_MSG);
                        break;
                    case R.id.radio_notify:
                        showFragment(FRAGMENT_NOTIFY);
                        break;
                    case R.id.radio_manage:
                        showFragment(FRAGMENT_MANAGE);
                        break;
                }
            }
        });
    }

    @Override
    public void initData() {
        mTitleBar.setSlideMenu(mSlideMenu);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_main;
    }

    /**
     * 显示某个Fragment
     * @param index
     */
    private void showFragment(int index) {
        //每次更改Fragment都需要重新获取事务,犯错重大Bug
        mTransaction =mFragmentManager.beginTransaction();
        for(int i=0;i<FRAGMENT_COUNT;i++){
            mTransaction.hide(mFragments[i]);
            mFrameLayouts[i].setVisibility(View.GONE);
        }
        mFrameLayouts[index].setVisibility(View.VISIBLE);
        mTransaction.show(mFragments[index]);
        mTransaction.replace(mFrameLayouts[index].getId(),mFragments[index]);
        mTransaction.commit();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.left_class:
                break;
            case R.id.left_evaluate:
                break;
            case R.id.left_vote:
                break;
            case R.id.left_data:
                break;
            case R.id.left_info:
                break;
            case R.id.left_unsign:
                break;
        }
    }
}
