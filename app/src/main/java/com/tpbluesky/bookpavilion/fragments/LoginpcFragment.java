package com.tpbluesky.bookpavilion.fragments;


import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.tpbluesky.bookpavilion.R;
import com.tpbluesky.bookpavilion.tools.StringUtils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * 采用Phone+Code登录的Fragment
 */
@ContentView(R.layout.fragment_loginpc)
public class LoginpcFragment extends BaseFragment {


    private static final String TAG = "LoginapFragment";

    @ViewInject(R.id.et_phone_num)
    private EditText et_phone_num;
    @ViewInject(R.id.iv_clear_phone)
    private ImageView iv_clear_phone;
    @ViewInject(R.id.et_code)
    private EditText et_code;
    @ViewInject(R.id.iv_clear_code)
    private ImageView iv_clear_code;
    @ViewInject(R.id.btn_login)
    private Button btn_login;
    @ViewInject(R.id.btn_get_code)
    private Button btn_get_code;

    public LoginpcFragment() {

    }

    //手机号是否合法
    private boolean phone_not_valid = false;
    //验证码是否合法
    private boolean code_not_valid = false;

    //是否发送过验证码
    private boolean hasGetCode = false;

    private static final int CODE_SENDED = 0;
    private static final int CODE_COUNT = 1;
    private static final int CODE_COUNT_OVER = 2;

    //过去多少时间
    private int secondPast = 0;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CODE_SENDED:
                    secondPast = 0;
                    btn_get_code.setText("短信已发送");
                    handler.sendEmptyMessageDelayed(CODE_COUNT, 1000);
                    break;
                case CODE_COUNT:
                    if (secondPast < 60) {
                        if (secondPast == 0) {
                            btn_get_code.setEnabled(false);
                        }
                        handler.sendEmptyMessageDelayed(CODE_COUNT, 1000);
                        btn_get_code.setText((60 - secondPast) + "秒后重试");
                        secondPast++;
                    } else {
                        handler.sendEmptyMessage(CODE_COUNT_OVER);
                    }
                    break;
                case CODE_COUNT_OVER:
                    btn_get_code.setEnabled(true);
                    btn_get_code.setText("获取验证码");
                    break;
            }
        }
    };

    @Override
    protected void initViews() {
        phone_not_valid = false;
        code_not_valid = false;
        hasGetCode = false;
        et_phone_num.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!(et_phone_num.getText().length() == 0) && hasFocus) {
                    iv_clear_phone.setVisibility(View.VISIBLE);
                } else {
                    iv_clear_phone.setVisibility(View.INVISIBLE);
                }
            }
        });
        et_phone_num.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                iv_clear_phone.setVisibility(s.length() > 0 ? View.VISIBLE : View.INVISIBLE);
                if (StringUtils.isMobileNO(s.toString()) && !hasGetCode) {
                    btn_get_code.setEnabled(true);
                    phone_not_valid = true;
                    if (code_not_valid) {
                        btn_login.setEnabled(true);
                    }
                } else {
                    btn_get_code.setEnabled(false);
                    phone_not_valid = false;
                    btn_login.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_code.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!(et_code.getText().length() == 0) && hasFocus) {
                    iv_clear_code.setVisibility(View.VISIBLE);
                } else {
                    iv_clear_code.setVisibility(View.INVISIBLE);
                }
            }
        });
        et_code.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                iv_clear_code.setVisibility(s.length() > 0 ? View.VISIBLE : View.INVISIBLE);
                if ((et_code.getText().length() == 6)) {
                    code_not_valid = true;
                    if (phone_not_valid) {
                        btn_login.setEnabled(true);
                    }
                } else {
                    code_not_valid = false;
                    btn_login.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Event({R.id.iv_clear_phone, R.id.iv_clear_code, R.id.btn_login, R.id.btn_get_code})
    private void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_clear_phone:
                et_phone_num.setText("");
                break;
            case R.id.iv_clear_code:
                et_code.setText("");
                break;
            case R.id.btn_login:
                login();
                break;
            case R.id.btn_get_code:
                handler.sendEmptyMessage(CODE_SENDED);
                break;
        }
    }

    private void login() {

    }

    private void getCode() {

    }

}
