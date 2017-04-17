package com.tpbluesky.bookpavilion.http;

import android.content.Context;
import android.util.Log;

import com.tpbluesky.bookpavilion.application.Config;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.ex.HttpException;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 这个Http请求工具类是基于Xutils3
 * Created by tpbluesky on 2017/2/16.
 */
public class HttpUtils {

    private static final String TAG = "HttpUtils";
    private static final boolean DEBUG = Config._DEBUG_;

    private HttpUtils() {
        throw new UnsupportedOperationException("HttpUtils工具类不允许被实例化");
    }

    public static Callback.Cancelable httpGet(HashMap<String, String> urlParams, final HttpCallback callback) {

        RequestParams requestParams = new RequestParams(Config.SERVER_URL);
        Set<Map.Entry<String, String>> set = urlParams.entrySet();
        for (Map.Entry<String, String> entry : set) {
            requestParams.addQueryStringParameter(entry.getKey(), entry.getValue());
        }
        Callback.Cancelable cancelable = x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if (DEBUG) {
                    Log.e(TAG, "onSuccess: " + result);
                }
                try {
                    JSONObject obj = new JSONObject(result);
                    int ret = obj.getInt("ret");
                    switch (ret / 100) {
                        case 2:
                            if (callback != null) {
                                callback.onSuccess(obj.getJSONObject("data").toString());
                            }
                            break;
                        case 4:
                            if (DEBUG) {
                                Log.e(TAG, "客户端请求错误：" + obj.getString("msg"));
                            }
                            break;
                        case 5:
                            if (DEBUG) {
                                Log.e(TAG, "服务端运行错误：" + obj.getString("msg"));
                            }
                            break;

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                if (ex instanceof HttpException) {
                    if (DEBUG) {
                        Log.e(TAG, "onError: " + "网络错误");
                    }
                    if (callback != null) {
                        callback.onFail("无法连接到服务器，情检查您的网络连接是否正常");
                    }
                } else {
                    if (DEBUG) {
                        Log.e(TAG, "onError: " + "数据解析错误");
                    }
                }
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
        return cancelable;
    }


    public static Callback.Cancelable httpPost(Context context, Map<String, String> urlParams, final HttpCallback callback) {

        RequestParams requestParams = new RequestParams(Config.SERVER_URL);
        Set<Map.Entry<String, String>> set = urlParams.entrySet();
        for (Map.Entry<String, String> entry : set) {
            requestParams.addBodyParameter(entry.getKey(), entry.getValue());
        }
        requestParams.setConnectTimeout(5000);
        requestParams.setCacheMaxAge(60 * 1000);
        Callback.Cancelable cancelable = x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if (DEBUG) {
                    Log.e(TAG, "onSuccess: " + result);
                }
                try {
                    JSONObject obj = new JSONObject(result);
                    int ret = obj.getInt("ret");
                    switch (ret / 100) {
                        case 2:
                            if (callback != null) {
                                callback.onSuccess(obj.getJSONObject("data").toString());
                            }
                            break;
                        case 4:
                            if (DEBUG) {
                                Log.e(TAG, "客户端请求错误：" + obj.getString("msg"));
                            }
                            break;
                        case 5:
                            if (DEBUG) {
                                Log.e(TAG, "服务端运行错误：" + obj.getString("msg"));
                            }
                            break;

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                if (ex instanceof ConnectException) {
                    if (DEBUG) {
                        Log.e(TAG, "onError: " + "网络错误");
                    }
                    if (callback != null) {
                        callback.onFail("无法连接到服务器，情检查您的网络连接是否正常");
                    }
                } else if (ex instanceof SocketTimeoutException) {
                    if (DEBUG) {
                        Log.e(TAG, "onError: " + "连接超时");
                    }
                    if (callback != null) {
                        callback.onFail("连接到服务器超时，请稍后重试");
                    }
                } else {
                    if (DEBUG) {
                        Log.e(TAG, "onError: " + "数据解析错误");
                    }
                }
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

        return cancelable;
    }


    public interface HttpCallback {
        void onSuccess(String result);

        void onFail(String error);
    }
}
