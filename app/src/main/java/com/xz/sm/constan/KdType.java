package com.xz.sm.constan;

import android.content.Context;
import android.content.res.Resources;

import com.xz.sm.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class KdType {

    private static String[] name;
    private static String[] code;
    private static Resources res;
    private static KdType kdType = null;

    private KdType(Context context) {
        res = context.getResources();
        name = res.getStringArray(R.array.kd_name);
        code = res.getStringArray(R.array.kd_code);
    }

    /**
     * 初始化一次即可
     * @param context
     * @return
     */
    public static KdType initType(Context context) {
        if (kdType == null) {

            synchronized (KdType.class) {
                if (kdType == null) {
                    kdType = new KdType(context);
                }

            }
        }
        return kdType;
    }

    /**
     * 返回名称数组
     * @return
     */
    public static String[] getNameArray() {
        return name;
    }

    /**
     * 返回代号数组
     * @return
     */
    public static String[] getCodeArray() {
        return code;
    }

    /**
     * 返回名称集合
     * @return
     */
    public static List<String> getNameList() {
        List<String> mlist = new ArrayList<>();
        Collections.addAll(mlist, name);
        return mlist;
    }

    /**
     * 返回代号集合
     * @return
     */
    public static List<String> getCodeList(){
        List<String> mlist = new ArrayList<>();
        Collections.addAll(mlist,code);
        return mlist;
    }
}
