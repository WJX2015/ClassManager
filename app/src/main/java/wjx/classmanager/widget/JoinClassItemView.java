package wjx.classmanager.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import wjx.classmanager.R;
import wjx.classmanager.model.JoinClass;

/**
 * Created by wjx on 2017/10/14.
 */

public class JoinClassItemView extends RelativeLayout implements View.OnClickListener{

    private TextView mClassName;
    private Button mClassJoin;

    public JoinClassItemView(Context context) {
        this(context,null);
    }

    public JoinClassItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
        initListener();
    }

    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_join_class,this);
        mClassName = (TextView) view.findViewById(R.id.item_name);
        mClassJoin = (Button) view.findViewById(R.id.item_join_class);
    }

    private void initListener(){
        mClassJoin.setOnClickListener(this);
    }

    public void bindView(JoinClass joinclass){
        mClassName.setText(joinclass.getClassName());
    }

    @Override
    public void onClick(View v) {
       String str =mClassName.getText().toString();
        //发送加入班级申请
    }
}
