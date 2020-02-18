package com.company.akilovasi.ui.login;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.company.akilovasi.R;
import com.company.akilovasi.data.remote.models.requests.LoginRequest;
import com.company.akilovasi.databinding.ActivityLoginBinding;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class LoginActivity extends AppCompatActivity implements LifecycleOwner {

    LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    LoginViewModel loginViewModel;

    ActivityLoginBinding binding;

    public static Intent newIntent(Context context, int movieId) {
        Intent intent = new Intent(context, LoginActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
//        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        loginViewModel = ViewModelProviders.of(this, viewModelFactory).get(LoginViewModel.class);
        binding.setLogin(new LoginRequest("asdasd","dcsdfsdf"));
//        loginViewModel.login()

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                ActivityCompat.finishAfterTransition(this);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public LifecycleRegistry getLifecycle() {
        return lifecycleRegistry;
    }

}