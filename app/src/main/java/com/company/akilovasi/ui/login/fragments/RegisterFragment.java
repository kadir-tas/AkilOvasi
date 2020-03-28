package com.company.akilovasi.ui.login.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingComponent;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import com.company.akilovasi.R;
import com.company.akilovasi.data.remote.models.other.Message;
import com.company.akilovasi.data.remote.models.requests.RegisterUserRequest;
import com.company.akilovasi.data.remote.models.responses.Response;
import com.company.akilovasi.databinding.FragmentRegisterBinding;
import com.company.akilovasi.ui.BaseFragment;

import retrofit2.Call;
import retrofit2.Callback;

public class RegisterFragment extends BaseFragment<RegisterViewModel, FragmentRegisterBinding> implements View.OnClickListener {
    private static final String TAG = "RegisterFragment";
    private OnRegisteredListener onRegistered;

    @Override
    public Class<RegisterViewModel> getViewModel() {
        return RegisterViewModel.class;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        Log.d(TAG, "onCreate: Register Fragment Created");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        dataBinding.register.setOnClickListener(this);
        return v;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_register;
    }

    @Override
    public DataBindingComponent getBindigComponent() {
        return null;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnRegisteredListener) {
            onRegistered = (OnRegisteredListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnRegisteredListener");
        }
    }

    private boolean validateForm(){
        boolean isValid = true;
        isValid = isValid & !dataBinding.firstname.getEditText().getText().toString().isEmpty();
        isValid = isValid & !dataBinding.lastname.getEditText().getText().toString().isEmpty();
        isValid = isValid & (!dataBinding.password.getEditText().getText().toString().isEmpty() && dataBinding.password.getEditText().getText().toString().length() >= 6);
        isValid = isValid & !dataBinding.email.getEditText().getText().toString().isEmpty();
        isValid = isValid & !dataBinding.homeaddress.getEditText().getText().toString().isEmpty();
        isValid = isValid & !dataBinding.username.getEditText().getText().toString().isEmpty();
        isValid = isValid & !dataBinding.phone.getEditText().getText().toString().isEmpty();
        return isValid;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.register){
            if(validateForm()){
                dataBinding.setLoading(true);
                RegisterUserRequest request = new RegisterUserRequest();
                request.setUserFirstname( dataBinding.firstname.getEditText().getText().toString() );
                request.setUserLastname( dataBinding.lastname.getEditText().getText().toString() );
                request.setUsername( dataBinding.username.getEditText().getText().toString() );
                request.setUserPassword( dataBinding.password.getEditText().getText().toString() );
                request.setUserEmail( dataBinding.email.getEditText().getText().toString() );
                request.setUserHomeAddress( dataBinding.homeaddress.getEditText().getText().toString() );
                request.setUserPhone( dataBinding.phone.getEditText().getText().toString() );

                viewModel.registerUser(request).enqueue(new Callback<Response<Message>>() {
                    @Override
                    public void onResponse(Call<Response<Message>> call, retrofit2.Response<Response<Message>> response) {
                        dataBinding.setLoading(false);
                        if(response.isSuccessful() && response.body() != null && response.body().getSuccess()){
                            Toast.makeText(getContext(), R.string.register_succsessfull, Toast.LENGTH_SHORT).show();
                            onRegistered.registeredSuccessfully(request);
                        }else {
                            Toast.makeText(getContext(), R.string.register_form_failed, Toast.LENGTH_SHORT).show();
                            onRegistered.registerFailed();
                        }
                    }

                    @Override
                    public void onFailure(Call<Response<Message>> call, Throwable t) {
                        dataBinding.setLoading(false);
                        onRegistered.registerFailed();
                    }
                });

            }else{
                Toast.makeText(getContext(), R.string.register_form_alert, Toast.LENGTH_SHORT).show();
            }
        }
    }


    public interface OnRegisteredListener{
        void registeredSuccessfully(RegisterUserRequest registerUserRequest);
        void registerFailed();
    }
}
