package com.xz.sm.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.xz.sm.R;
import com.xz.sm.activity.presenter.MainPresenter;
import com.xz.sm.adapter.OddAdapter;
import com.xz.sm.base.BaseActivity;
import com.xz.sm.constan.KdType;
import com.xz.sm.contract.ItemOnclickListener;
import com.xz.sm.custom.OddChoostDialog;
import com.xz.sm.entity.KdList;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.search_input)
    EditText searchInput;
    @BindView(R.id.search_delete)
    ImageView searchDelete;
    @BindView(R.id.submit)
    Button submit;
    @BindView(R.id.recycler_odd)
    RecyclerView recyclerOdd;

    private MainPresenter presenter;
    private OddAdapter adapter;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {
        presenter = new MainPresenter(this);
        KdType.initType(this);
        initRecycler();

        submit.setOnClickListener(this);



    }

    private void initRecycler() {
        adapter = new OddAdapter(this);
        recyclerOdd.setLayoutManager(new LinearLayoutManager(this));
        recyclerOdd.setAdapter(adapter);
    }

    @Override
    public void showData(Object object) {
        if (object instanceof List<?>) {
            //object转为 list<kdlList> 转型
            List<KdList> kdLists = new ArrayList<>();
            for (Object o : (List<?>) object) {
                kdLists.add((KdList) o);
            }

            //打开选择框
            OddChoostDialog builder = new OddChoostDialog.Builder(this, R.style.base_dialog)
                    .setTitle("选择快递公司 ")
                    .setExpNo(kdLists.get(0).getShipperNo())//设置快递单号
                    .setItemOnClickListener(new ItemOnclickListener() {
                        @Override
                        public void onClick(String s1, String s2) {
                            try {
                                presenter.expCheck(s1, s2);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    })
                    .create();
            builder.setData(kdLists);
            builder.show();

        } else if (object instanceof Boolean) {
            //打开选择框
            OddChoostDialog builder = new OddChoostDialog.Builder(this, R.style.base_dialog)
                    .setTitle("手动选择快递公司 ")
                    .setItemOnClickListener(new ItemOnclickListener() {
                        @Override
                        public void onClick(String s1, String s2) {
                            try {
                                presenter.expCheck(s1, s2);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    })
                    .setTips("单号查询失败，请手动选择快递公司")
                    .create();
            builder.show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit:
                try {
//                    presenter.expCheck("YD", "4301040730105");
                    presenter.oddCheck("4301040730105");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
