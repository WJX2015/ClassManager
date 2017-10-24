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
import wjx.classmanager.model.BmobPhoto;

/**
 * Created by wjx on 2017/10/22.
 */

public class BmobPhotoItemView extends RelativeLayout {

    private TextView mTextView;
    private ImageView mImageView;

    public BmobPhotoItemView(Context context) {
        this(context,null);
    }

    public BmobPhotoItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initData(context);
    }

    private void initData(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_class_pic,this);
        mTextView = (TextView) view.findViewById(R.id.item_url);
        mImageView = (ImageView) view.findViewById(R.id.item_pic);
    }

    public void bindView(Context context,BmobPhoto bmobPhoto){
        mTextView.setText(bmobPhoto.getUrl());
        Glide.with(context).load(bmobPhoto.getUrl()).into(mImageView);
    }
}
