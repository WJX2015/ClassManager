package wjx.classmanager.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import wjx.classmanager.R;
import wjx.classmanager.model.Member;

/**
 * Created by wjx on 2017/10/12.
 */

public class MemberItemView extends RelativeLayout {

    private TextView mSectionText;
    private TextView mNameText;

    public MemberItemView(Context context) {
        this(context,null);
    }

    public MemberItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_member,this);
        mSectionText = (TextView) view.findViewById(R.id.item_section);
        mNameText = (TextView) view.findViewById(R.id.item_name);
    }

    public void bindView(Member member){
        mNameText.setText(member.getUsername());
        if(member.isSection()){
            mSectionText.setVisibility(VISIBLE);
            mSectionText.setText(member.getFirstLetterString());
        }else{
            mSectionText.setVisibility(GONE);
        }
    }
}
