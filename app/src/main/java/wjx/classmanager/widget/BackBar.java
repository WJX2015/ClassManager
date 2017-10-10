package wjx.classmanager.widget;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import wjx.classmanager.R;
import wjx.classmanager.utils.ThreadUtil;

/**
 * Created by wjx on 2017/10/10.
 */

public class BackBar extends RelativeLayout implements View.OnClickListener{

    private ImageView mBackIcon;
    private TextView mBackTitle;
    private ImageView mBackEdit;

    public BackBar(Context context) {
        this(context,null);
    }

    public BackBar(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BackBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.back_bar_layout,this);

        initView();
        initListener();
    }

    private void initView() {
        mBackIcon = (ImageView) findViewById(R.id.back_image);
        mBackTitle = (TextView) findViewById(R.id.back_title);
        mBackEdit= (ImageView) findViewById(R.id.back_edit);
    }

    private void initListener() {
        mBackIcon.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        ((Activity)getContext()).finish();
    }

    public void setText(final String text){
        ThreadUtil.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mBackTitle.setText(text);
            }
        });
    }

    public void setEditButtonCLickListener(OnClickListener listener){
        mBackEdit.setOnClickListener(listener);
    }
}
