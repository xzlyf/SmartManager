package com.xz.sm.custom;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.xz.sm.R;
import com.xz.sm.constan.KdType;
import com.xz.sm.contract.ItemOnclickListener;
import com.xz.sm.entity.KdList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class OddChoostDialog extends Dialog {

    private Context mContext;
    private String title;
    private View mView;
    private ListView oddListView;
    private TextView tvTitle;
    private List<KdList> mList = new ArrayList<>();
    private String[] nameData;//快递列表数据-名字
    private String[] codeData;//快递列表 代号
    private String tips;//提示信息
    private LinearLayout tipLayout;
    private TextView tipsText;
    private SearchView searchKd;
    private ArrayAdapter<String> adapter;
    private ItemOnclickListener mListener;
    private String expNo;//快递单号


    public OddChoostDialog(Context context) {
        super(context);
        this.mContext = context;
    }

    public OddChoostDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.mContext = context;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_odd_choose);
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = mContext.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        lp.width = (int) (d.widthPixels * 0.8);
        lp.height = (int) (d.heightPixels * 0.6);
        lp.dimAmount = 0.2f;//背景不变暗
        dialogWindow.setAttributes(lp);
        initView();

    }

    private void initView() {
        tvTitle = findViewById(R.id.tvtitle);
        oddListView = findViewById(R.id.oddListView);
        tipLayout = findViewById(R.id.tip_layout);
        tipsText = findViewById(R.id.tips_text);
        //搜索框
        searchKd = findViewById(R.id.search_kd);
//        searchKd.setSubmitButtonEnabled(true);//是否显示提交按钮
        searchKd.setQueryHint("快递公名称");
        searchKd.setVisibility(View.GONE);//手动模式才显示
        searchKd.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                //submit按钮监听
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                //文本改变监听
                adapter.getFilter().filter(s);
                return false;
            }
        });
        //列表
        oddListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //获取点击的项
                String tn = adapter.getItem(position);
                //查询该项在数组第几位
                int i;
                for (i = 0; i < nameData.length; i++) {
                    if (nameData[i].equals(tn)) {
                        break;
                    }
                }
                try {
                    //通过查询到的索引找到对应快递公司代号的数组的索引对应的元素
                    mListener.onClick(codeData[i],expNo);
                    dismiss();

                } catch (Exception e) {
                    //防止数组越位
                    Toast.makeText(mContext, "暂无此物流信息", Toast.LENGTH_SHORT).show();
                    dismiss();
                }

            }
        });

    }

    @Override
    public void show() {
        super.show();
        show(this);
    }

    private void show(OddChoostDialog dialog) {
        if (!TextUtils.isEmpty(dialog.title)) {
            dialog.tvTitle.setText(dialog.title);
        }
        if (!TextUtils.isEmpty(dialog.tips)) {
            dialog.tipLayout.setVisibility(View.VISIBLE);
            dialog.tipsText.setText(dialog.tips);
            //跑马灯效果
            dialog.tipsText.setSelected(true);
        }


        //如果列表集合里没有数据就加载本地的数据(手动模式）
        if (mList.size() != 0) {

            nameData = new String[mList.size()];
            codeData = new String[mList.size()];
            for (int i = 0; i < mList.size(); i++) {
                nameData[i] = mList.get(i).getShipperName();
                codeData[i] = mList.get(i).getShipperCode();
            }

        } else {
            searchKd.setVisibility(View.VISIBLE);//手动模式才显示
            nameData = KdType.getNameArray();
            codeData = KdType.getCodeArray();
        }
        adapter = new ArrayAdapter<>(mContext, android.R.layout.simple_list_item_1, nameData);
        oddListView.setAdapter(adapter);

    }

    public void setData(List<KdList> kdLists) {
        mList.addAll(kdLists);
    }

    public static class Builder {
        private OddChoostDialog dialog;

        public Builder(Context context) {
            dialog = new OddChoostDialog(context);
        }

        public Builder(Context context, int themeResId) {
            dialog = new OddChoostDialog(context, themeResId);
        }


        public Builder setTitle(String title) {
            dialog.title = title;
            return this;
        }

        public Builder setTips(String st) {
            dialog.tips = st;
            return this;
        }

        public Builder setView(View view) {
            dialog.mView = view;
            return this;
        }

        public Builder setCancelable(boolean cancelable) {
            dialog.setCancelable(cancelable);
            return this;
        }

        public Builder setItemOnClickListener(ItemOnclickListener listener) {
            dialog.mListener = listener;
            return this;
        }

        /**
         * 设置快递单号
         * @param no
         * @return
         */
        public Builder setExpNo(String no){
            dialog.expNo = no;
            return this;
        }


        /**
         * 通过Builder类设置完属性后构造对话框的方法
         */
        public OddChoostDialog create() {
            return dialog;
        }


    }

}
