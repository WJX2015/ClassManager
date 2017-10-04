package wjx.classmanager.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;
import wjx.classmanager.R;
import wjx.classmanager.model.Message;

/**
 * Created by wjx on 2017/10/3.
 */

public class MessageItemView extends RelativeLayout implements Badge.OnDragStateChangedListener,View.OnClickListener{

    private ImageView mIcon;    //图标
    private TextView mTitle;    //消息标题
    private TextView mTime;     //发布时间
    private TextView mCount;    //消息数量

    private Badge mQBadgeView;
    private LinearLayout mLinearLayout;

    public MessageItemView(Context context) {
        this(context,null);
    }

    public MessageItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
        initListener();
    }

    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_msg,this);
        mIcon = (ImageView) view.findViewById(R.id.item_icon);
        mTitle = (TextView) view.findViewById(R.id.item_title);
        mTime = (TextView) view.findViewById(R.id.item_time);
        mCount = (TextView) view.findViewById(R.id.item_count);
        mQBadgeView =new QBadgeView(context).bindTarget(mIcon).setBadgeNumber(5);
        mLinearLayout = (LinearLayout) view.findViewById(R.id.item_msg_ll);
    }

    private void initListener() {
        mQBadgeView.setOnDragStateChangedListener(this);
        //mLinearLayout.setOnClickListener(this);
    }

    public void bindView(Message message){
        mIcon.setImageResource(message.getIcon());
        mTitle.setText(message.getTitle());
        mTime.setText(message.getTime());
        //mCount
    }

    @Override
    public void onDragStateChanged(int dragState, Badge badge, View targetView) {
        switch (dragState){
        }
    }

    @Override
    public void onClick(View v) {
        int number=mQBadgeView.getBadgeNumber();
        number++;
        mQBadgeView.setBadgeNumber(number);
    }
}
