package com.xz.sm.activity.presenter;

import android.util.Log;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.xz.sm.activity.MainActivity;
import com.xz.sm.constan.Local;
import com.xz.sm.contract.Contract;
import com.xz.sm.activity.model.Model;
import com.xz.sm.entity.KdList;
import com.xz.sm.entity.KdTraces;
import com.xz.sm.utils.KdnSignUtil;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.xz.sm.utils.KdnSignUtil.urlEncoder;

public class MainPresenter implements Contract.Presenter {

    private String TAG = "xz";

    private Model mModel;
    private MainActivity activity;

    public MainPresenter(MainActivity activity) {
        mModel = new Model();
        this.activity = activity;
    }

    /**
     * 快递查询
     * 即时查询API 3000次/每小时
     *
     * @param expCode 快递公司代号
     * @param expNo 快递单号
     * @throws Exception
     */
    public void expCheck(String expCode, String expNo) throws Exception {

        String requestData = "{'OrderCode':'','ShipperCode':'" + expCode + "','LogisticCode':'" + expNo + "'}";
        Map<String, String> params = new HashMap<String, String>();
        params.put("RequestData", urlEncoder(requestData, "UTF-8"));
        params.put("EBusinessID", Local.EBusinessID);
        params.put("RequestType", "1002");
        String dataSign = KdnSignUtil.encrypt(requestData, Local.AppKey, "UTF-8");
        params.put("DataSign", urlEncoder(dataSign, "UTF-8"));
        params.put("DataType", "2");
        mModel.post_Asyn(Local.expCheckURL, params, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Logger.w("onFailure: ");
                activity.sToast("快递查询失败");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String jsonData = response.body().string();
                Logger.w("onResponse: " + jsonData);

                Gson gson = new Gson();
                KdTraces kdt = gson.fromJson(jsonData,KdTraces.class);
                activity.backToUI(kdt);

            }
        });
    }

    /**
     * 快递单号识别
     * 提供单号识别快递公司
     *
     * @param expNo 快递单号
     */
    public void oddCheck(String expNo) throws Exception {

        String requestData = "{'LogisticCode':'" + expNo + "'}";
        Map<String, String> params = new HashMap<String, String>();
        params.put("RequestData", urlEncoder(requestData, "UTF-8"));
        params.put("EBusinessID", Local.EBusinessID);
        params.put("RequestType", "2002");
        String dataSign = KdnSignUtil.encrypt(requestData, Local.AppKey, "UTF-8");
        params.put("DataSign", urlEncoder(dataSign, "UTF-8"));
        params.put("DataType", "2");
        mModel.post_Asyn(Local.expCheckURL, params, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.w(TAG, "请求异常，将启动手动选择: ");
                activity.backToUI(false);

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String jsonData = response.body().string();
                Logger.w("单号识别： " + jsonData);

                JSONObject obj = null;

                try {
                    obj = new JSONObject(jsonData);

                    if (obj.getBoolean("Success")) {
                        //成功
                        String no = obj.getString("LogisticCode");
                        JSONArray obj2 = obj.getJSONArray("Shippers");
                        List<KdList> kdLists = new ArrayList<>();
                        JSONObject obj3;
                        for (int i = 0; i < obj2.length(); i++) {
                            obj3 = obj2.getJSONObject(i);
                            kdLists.add(new KdList(obj3.getString("ShipperName")
                                    , obj3.getString("ShipperCode")
                                    ,no));
                        }

                        activity.backToUI(kdLists);

                    } else {
                        //失败
                        Logger.e("单号识别失败：" + obj.getInt("code"));
                        Logger.w("单号识别可能已经达到零界点，将启用手动选择");
                        activity.backToUI(false);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    Logger.w("单号识别可能已经达到零界点，将启用手动选择");
                    activity.backToUI(false);
                }


            }
        });
    }


}
