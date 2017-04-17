package com.tpbluesky.bookpavilion.fragments;


import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.tpbluesky.bookpavilion.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * 采用Account+Password登录的Fragment
 */
@ContentView(R.layout.fragment_loginap)
public class LoginapFragment extends BaseFragment {

    private static final String TAG = "LoginapFragment";

    @ViewInject(R.id.et_account)
    private EditText et_account;
    @ViewInject(R.id.iv_clear_account)
    private ImageView iv_clear_account;
    @ViewInject(R.id.et_password)
    private EditText et_password;
    @ViewInject(R.id.iv_clear_password)
    private ImageView iv_clear_password;
    @ViewInject(R.id.iv_password_state)
    private ImageView iv_password_state;
    @ViewInject(R.id.btn_login)
    private Button btn_login;

    public LoginapFragment() {

    }

    private boolean account_not_empty = false;
    private boolean password_not_empty = false;

    @Override
    protected void initViews() {
        et_account.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!(et_account.getText().length() == 0) && hasFocus) {
                    iv_clear_account.setVisibility(View.VISIBLE);
                } else {
                    iv_clear_account.setVisibility(View.INVISIBLE);
                }
            }
        });
        et_account.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                iv_clear_account.setVisibility(s.length() > 0 ? View.VISIBLE : View.INVISIBLE);
                if (count > 0) {
                    account_not_empty = true;
                    if (password_not_empty) {
                        btn_login.setEnabled(true);
                    }
                } else {
                    btn_login.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!(et_password.getText().length() == 0) && hasFocus) {
                    iv_clear_password.setVisibility(View.VISIBLE);
                } else {
                    iv_clear_password.setVisibility(View.INVISIBLE);
                }
            }
        });
        et_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                iv_clear_password.setVisibility(s.length() > 0 ? View.VISIBLE : View.INVISIBLE);
                if (count > 0) {
                    password_not_empty = true;
                    if (account_not_empty) {
                        btn_login.setEnabled(true);
                    }
                } else {
                    btn_login.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Event({R.id.iv_password_state, R.id.iv_clear_account, R.id.iv_clear_password, R.id.btn_login})
    private void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_password_state:
                if (iv_password_state.isSelected()) {
                    iv_password_state.setSelected(false);
                    et_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    et_password.setSelection(et_password.getText().length());
                } else {
                    iv_password_state.setSelected(true);
                    et_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    et_password.setSelection(et_password.getText().length());
                }
                break;
            case R.id.iv_clear_account:
                et_account.setText("");
                break;
            case R.id.iv_clear_password:
                et_password.setText("");
                break;
            case R.id.btn_login:
                login();
                break;
        }
    }

    private void login() {

    }

}
