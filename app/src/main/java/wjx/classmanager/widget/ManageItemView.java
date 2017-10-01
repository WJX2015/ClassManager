package wjx.classmanager.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;
import wjx.classmanager.R;
import wjx.classmanager.model.Manage;

/**
 * Created by wjx on 2017/9/25.
 */

public class ManageItemView extends RelativeLayout {

    private View mView;

    private CircleImageView mIcon;
    private TextView mTitle;

    public ManageItemView(@NonNull Context context) {
        this(context, null);
    }

    public ManageItemView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        mView = LayoutInflater.from(context).inflate(R.layout.item_manage, this);
        mIcon = (CircleImageView) mView.findViewById(R.id.item_icon);
        mTitle = (TextView) mView.findViewById(R.id.item_title);
    }

    public void bindView(Manage manage) {
        mIcon.setImageResource(manage.getIcon());
        mTitle.setText(manage.getTitle());
    }
}
