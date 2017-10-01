package wjx.classmanager.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.InputFilter;
import android.text.Spanned;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import wjx.classmanager.R;
import wjx.classmanager.adapter.ManageAdapter;
import wjx.classmanager.model.Manage;

/**
 * Created by wjx on 2017/9/16.
 */

public class ManageFragment extends BaseFragment {

    private String[] mFunctionTitle={"发布通知","公布成绩","资料收集","活动投票",
            "推优评优","班级管理","班级考勤","更多管理"};

    private int[] mFunctionIcon={R.drawable.title_bar_add,R.drawable.title_bar_icon,
            R.drawable.title_bar_search,R.drawable.title_bar_add,R.drawable.title_bar_icon,
            R.drawable.title_bar_search,R.drawable.title_bar_add,R.drawable.title_bar_icon
    };

    private List<Manage> mManages= new ArrayList<>();

    private RecyclerView mRecyclerView;
    private ManageAdapter mManageAdapter;

    @Override
    protected void initListener() {

    }

    @Override
    protected void initView() {
        mRecyclerView = (RecyclerView) mView.findViewById(R.id.recycler_manage);
        mManageAdapter =new ManageAdapter(mManages);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mManageAdapter);
    }

    @Override
    protected void initData() {
        for(int i=0;i<mFunctionTitle.length;i++){
            mManages.add(new Manage(mFunctionIcon[i],mFunctionTitle[i]));
        }
    }

    @Override
    public int getLayoutResourceId() {
        return R.layout.fragment_manage;
    }

    /**
     * 禁止输入空格
     * @param editText
     */
    public void setEditTextInhibitInputSpace(EditText editText){
        InputFilter filter=new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                if(source.equals(" "))return "";
                else return null;
            }
        };
        editText.setFilters(new InputFilter[]{filter});
    }

    /**
     * 禁止输入特殊符号
     * @param editText
     */
    public void setEditTextInhibitInputSpeChat(EditText editText) {
        InputFilter filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                String speChat = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
                Pattern pattern = Pattern.compile(speChat);
                Matcher matcher = pattern.matcher(source.toString());
                if (matcher.find()) return "";
                else return null;
            }
        };
    }
}
