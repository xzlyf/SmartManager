package com.xz.sm.custom;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.xz.sm.R;
import com.xz.sm.entity.KdList;

import java.util.ArrayList;
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
        oddListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(mContext, nameData[position], Toast.LENGTH_SHORT).show();
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
        //如果列表集合里没有数据就加载本地的数据
        if (mList.size() != 0) {

            nameData = new String[mList.size()];
            codeData = new String[mList.size()];

            for (int i = 0; i < mList.size(); i++) {
                nameData[i] = mList.get(i).getShipperName();
                codeData[i] = mList.get(i).getShipperCode();
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(mContext, android.R.layout.simple_list_item_1, nameData);
            oddListView.setAdapter(adapter);
        } else {
            nameData = new String[12];
            for (int i = 0; i < 12; i++) {
                nameData[i] = i + "";

            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(mContext, android.R.layout.simple_list_item_1, nameData);
            oddListView.setAdapter(adapter);
        }

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


        /**
         * 通过Builder类设置完属性后构造对话框的方法
         */
        public OddChoostDialog create() {
            return dialog;
        }


    }

}
