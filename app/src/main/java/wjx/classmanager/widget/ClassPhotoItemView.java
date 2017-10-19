package wjx.classmanager.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import wjx.classmanager.R;
import wjx.classmanager.model.ClassPhoto;

/**
 * Created by wjx on 2017/10/18.
 */

public class ClassPhotoItemView extends RelativeLayout {

    private ImageView mImageView;
    private TextView mTextView;

    public ClassPhotoItemView(Context context) {
        this(context,null);
    }

    public ClassPhotoItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_class_pic,this);
        mImageView = (ImageView) view.findViewById(R.id.item_pic);
        mTextView = (TextView) view.findViewById(R.id.item_url);
    }

    public void bindView(Context context,ClassPhoto classPhoto){
        Glide.with(context).load(classPhoto.getUrl()).into(mImageView);
        mTextView.setText(classPhoto.getUrl());
    }
}
