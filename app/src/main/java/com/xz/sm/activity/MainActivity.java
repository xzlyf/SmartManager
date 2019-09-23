package com.xz.sm.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xz.sm.R;
import com.xz.sm.activity.presenter.MainPresenter;
import com.xz.sm.adapter.OddAdapter;
import com.xz.sm.base.BaseActivity;
import com.xz.sm.constan.KdType;
import com.xz.sm.contract.ItemOnclickListener;
import com.xz.sm.custom.OddChoostDialog;
import com.xz.sm.entity.KdList;
import com.xz.sm.entity.KdTraces;

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
    @BindView(R.id.states)
    TextView states;
    @BindView(R.id.close_btn)
    ImageView closeBtn;
    @BindView(R.id.info_1)
    FrameLayout info1;

    private MainPresenter presenter;
    private OddAdapter adapter;
    private String bianhao;//快递编号
    private String danhao;//快递单号
    private boolean canSave ;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {
        presenter = new MainPresenter(this);
        info1.setVisibility(View.GONE);
        KdType.initType(this);
        initRecycler();

        submit.setOnClickListener(this);
        closeBtn.setOnClickListener(this);


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
        } else if (object instanceof KdTraces) {
            KdTraces kd = (KdTraces) object;
            canSave = true;
            danhao = kd.getLogisticCode();
            bianhao = kd.getShipperCode();
            String state = kd.getState();
            if (state.equals("2")) {
                states.setText("物流状态：在途中");
            } else if (state.equals("3")) {
                states.setText("物流状态：已签收");
            } else if (state.equals("4")) {
                states.setText("物流状态：问题件");
            }else if (state.equals("0")){
                states.setText("物流状态：暂无物流信息");
            }

            adapter.refresh(kd.getTraces());

            info1.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit:
                try {
//                    presenter.expCheck("YD", "4301040730105");
//                    presenter.oddCheck("YT4088139384088");
                    if (!searchInput.getText().toString().trim().equals("")) {
                        presenter.oddCheck(searchInput.getText().toString().trim());
                    } else {
                        sToast("快递单号不可为空");
                        return;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.close_btn:
                adapter.cleanAll();
                info1.setVisibility(View.GONE);
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
