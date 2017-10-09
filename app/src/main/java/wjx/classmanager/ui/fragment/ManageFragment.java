package wjx.classmanager.ui.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import wjx.classmanager.presenter.impl.ManagePresenterImpl;
import wjx.classmanager.view.ManageView;

/**
 * Created by wjx on 2017/9/16.
 */

public class ManageFragment extends BaseFragment implements ManageView{

    private RecyclerView mRecyclerView;
    private ManageAdapter mManageAdapter;
    private ManagePresenterImpl mManagePresenter;

    @Override
    protected void initListener() {

    }

    @Override
    protected void initView() {
        mManagePresenter = new ManagePresenterImpl(this);
        mRecyclerView = (RecyclerView) mView.findViewById(R.id.recycler_manage);
        mManageAdapter =new ManageAdapter(mManagePresenter.getManageList());
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mManageAdapter);
    }

    @Override
    public int getLayoutResourceId() {
        return R.layout.fragment_manage;
    }

}
