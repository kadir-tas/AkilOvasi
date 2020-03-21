package com.company.akilovasi.ui.login;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.company.akilovasi.R;
import com.company.akilovasi.data.Resource;
import com.company.akilovasi.data.remote.models.requests.LoginRequest;
import com.company.akilovasi.data.remote.models.responses.LoginResponse;
import com.company.akilovasi.databinding.ActivityLoginBinding;
import com.company.akilovasi.di.SecretPrefs;
import com.company.akilovasi.ui.BaseActivity;
import com.company.akilovasi.ui.login.callbacks.LoginButtonClick;
import com.company.akilovasi.ui.main.MainActivity;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

import static com.company.akilovasi.data.remote.ApiConstants.ACCESS_TOKEN;
import static com.company.akilovasi.data.remote.ApiConstants.REFRESH_TOKEN;

public class LoginActivity extends BaseActivity<LoginViewModel, ActivityLoginBinding> implements LoginButtonClick {

    private static final String TAG = "LoginActivity";

    private EditText usernameOrEmailEditText;
    private EditText passwordEditText;

    @Inject
    @SecretPrefs
    SharedPreferences secretPreferences;

    @Override
    public Class<LoginViewModel> getViewModel() {
        return LoginViewModel.class;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_login;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);

        dataBinding.setLoginButtonEnable(false);
        dataBinding.txtEmailAddress.setEndIconDrawable(R.drawable.ic_error_outline_black_24dp);
        dataBinding.txtEmailAddress.setEndIconVisible(false);
        dataBinding.txtPassword.setEndIconMode(TextInputLayout.END_ICON_PASSWORD_TOGGLE);
        dataBinding.txtPassword.setEndIconVisible(true);


        String token = secretPreferences.getString(ACCESS_TOKEN,null);
        String refreshToken = secretPreferences.getString(REFRESH_TOKEN,null);
        if(!(token == null || token.isEmpty() || refreshToken == null || refreshToken.isEmpty())){
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        usernameOrEmailEditText = dataBinding.txtEmailAddress.getEditText();
        passwordEditText = dataBinding.txtPassword.getEditText();

        if(usernameOrEmailEditText != null && passwordEditText != null){
            usernameOrEmailEditText.addTextChangedListener(loginTextWatcher);
            passwordEditText.addTextChangedListener(loginTextWatcher);
            usernameOrEmailEditText.setOnFocusChangeListener((v, hasFocus) -> {
                if(hasFocus) {
                    dataBinding.txtEmailAddress.setError(null);
                    dataBinding.txtEmailAddress.setEndIconVisible(false);
                }
            });

            passwordEditText.setOnFocusChangeListener((v, hasFocus) -> {
                if(hasFocus){
                    dataBinding.txtPassword.setError(null);
                    dataBinding.txtPassword.setEndIconMode(TextInputLayout.END_ICON_PASSWORD_TOGGLE);
                    dataBinding.txtPassword.setEndIconVisible(true);
                }
            });
        }

        dataBinding.setLoginClick(this);
        dataBinding.progressBar.hide();
    }

    @Override
    public void onLoginButtonClicked(String usernameOrEmail, String password) {

        dataBinding.setLoginButtonEnable(false);
        dataBinding.progressBar.show();
        dataBinding.txtPassword.clearFocus();
        dataBinding.txtEmailAddress.clearFocus();
        if(isUsernameOrEmailFieldValid() && isPasswordFieldValid()) {
            LoginRequest loginRequest = new LoginRequest(usernameOrEmail, password);
            viewModel.login(loginRequest).observe(this, loginResponseResource -> {
                if (loginResponseResource != null) {
                    switch (loginResponseResource.status) {
                        case SUCCESS:
                            if (loginResponseResource.data != null && !loginResponseResource.data.getAccessToken().isEmpty() && !loginResponseResource.data.getRefreshToken().isEmpty()) {
                                secretPreferences.edit().putString(ACCESS_TOKEN, loginResponseResource.data.getAccessToken()).apply();
                                secretPreferences.edit().putString(REFRESH_TOKEN, loginResponseResource.data.getRefreshToken()).apply();
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                            dataBinding.progressBar.hide();
                            break;
                        case ERROR:
                            Log.e(TAG, "onChanged: Error" + loginResponseResource.message);
                            dataBinding.progressBar.hide();
                            dataBinding.setLoginButtonEnable(true);
                            break;
                        case LOADING:
                            Log.d(TAG, "onChanged: Loading...");
                            dataBinding.progressBar.show();
                            break;
                    }
                }
            });
        }
    }

    private boolean isPasswordFieldValid() {
        if(passwordEditText.getText().toString().trim().length() < 6){
            dataBinding.txtPassword.setEndIconMode(TextInputLayout.END_ICON_NONE);
            dataBinding.txtPassword.setEndIconDrawable(R.drawable.ic_error_outline_black_24dp);
            dataBinding.txtPassword.setEndIconVisible(true);
            dataBinding.txtPassword.setError("En az 6 karakter olmalı.");
            dataBinding.progressBar.hide();
            dataBinding.setLoginButtonEnable(true);
            return false;
        }
        return true;
    }

    private boolean isUsernameOrEmailFieldValid() {
        if(usernameOrEmailEditText.getText().toString().trim().length() <= 2){
            isPasswordFieldValid();
            dataBinding.txtEmailAddress.setError("En az 3 karakter olmalı.");
            dataBinding.txtEmailAddress.setEndIconVisible(true);
            dataBinding.progressBar.hide();
            dataBinding.setLoginButtonEnable(true);
            return false;
        }
        return true;
    }

    private TextWatcher loginTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            dataBinding.setLoginButtonEnable(
                    !(usernameOrEmailEditText.getText().toString().trim().isEmpty())
                    && !(passwordEditText.getText().toString().trim().isEmpty()));
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

}