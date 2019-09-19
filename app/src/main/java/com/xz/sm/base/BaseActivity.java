package com.xz.sm.base;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.orhanobut.logger.Logger;
import com.xz.sm.MyApplication;
import com.xz.sm.contract.Contract;
import com.xz.sm.utils.ToastUtil;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity implements Contract.View {
    private final int backMain = 1452;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case backMain:
                    showData(msg.obj);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResource());
        MyApplication.getInstance().addActivity(this);
        ButterKnife.bind(this);
        initData();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //结束Activity&从集合中移除
        MyApplication.getInstance().finishActivity(this);
    }

    /**
     * 自动隐藏软键盘
     *
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {

            View view = getCurrentFocus();
            if (isShouldHideInput(view, ev)) {
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                if (inputMethodManager != null) {
                    inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }

            }
            return super.dispatchTouchEvent(ev);

        }

        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);

    }

    /**
     * 判断是否应该隐藏软键盘
     *
     * @param view
     * @param event
     * @return
     */
    private boolean isShouldHideInput(View view, MotionEvent event) {
        if (view != null && (view instanceof EditText)) {
            int[] leftTop = {0, 0};
            view.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + view.getHeight();
            int right = left + view.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域
                return false;
            } else {
                //清除输入框焦点
                view.clearFocus();
                return true;
            }
        }
        return false;
    }


    /**
     * 异步结果回到主线程
     *
     * @param object
     */
    public void backToUI(Object object) {
        Message msg = handler.obtainMessage();
        msg.what = backMain;
        msg.obj = object;
        handler.sendMessage(msg);

    }


    @Override
    public void sToast(String text) {
        ToastUtil.Shows(this, text);
    }

    @Override
    public void lToast(String text) {
        ToastUtil.Shows_LONG(this, text);
    }

    @Override
    public void showData(Object object) {

    }

    @Override
    public void showLoading(String msg) {

    }

    @Override
    public void dissmissLoading(String msg) {

    }

    abstract protected int getLayoutResource();

    protected abstract void initData();
}
