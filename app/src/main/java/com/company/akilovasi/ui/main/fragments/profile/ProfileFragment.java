package com.company.akilovasi.ui.main.fragments.profile;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.company.akilovasi.R;
import com.company.akilovasi.data.Resource;
import com.company.akilovasi.data.Status;
import com.company.akilovasi.data.local.entities.User;
import com.company.akilovasi.data.remote.models.requests.ResetPasswordRequest;
import com.company.akilovasi.data.remote.models.requests.UpdateUserRequest;
import com.company.akilovasi.databinding.FragmentProfileBinding;
import com.company.akilovasi.databinding.ResetPasswordDialogBinding;
import com.company.akilovasi.ui.BaseFragment;
import com.company.akilovasi.ui.login.LoginActivity;
import com.company.akilovasi.ui.main.MainActivity;
import com.company.akilovasi.ui.main.callbacks.ApplyChangesClick;

import static com.company.akilovasi.data.remote.ApiConstants.ACCESS_TOKEN;
import static com.company.akilovasi.data.remote.ApiConstants.REFRESH_TOKEN;
import static com.company.akilovasi.data.remote.ApiConstants.USER_ID;


public class ProfileFragment  extends BaseFragment<ProfileFragmentViewModel, FragmentProfileBinding> implements ApplyChangesClick {

    public static final String TAG = "ProfileFragment";

    private FragmentActivity mActivity;

    private Dialog resetPasswordDialog;

    @Override
    public Class<ProfileFragmentViewModel> getViewModel() {
        return ProfileFragmentViewModel.class;
    }

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_profile;
    }

    @Override
    public DataBindingComponent getBindigComponent() {
        return null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mActivity = getActivity();
        return dataBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dataBinding.setApplyChangesClick(this);
        dataBinding.setLoading(true);
        initObservers();
    }

    private void initObservers() {
        viewModel.getUserProfile().observe(getViewLifecycleOwner(), listResource -> {
            switch (listResource.status) {
                case SUCCESS:
                    if (listResource.data != null) {
                        dataBinding.setUser(listResource.data);
                        dataBinding.setLoading(false);
                    }
                    break;
                case ERROR:
                    String message = listResource.message;
                    if (message != null) {
//                        dataBinding.progressBar.hide();
                        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                    }
//                    viewModel.getUserProfile().removeObservers(getViewLifecycleOwner());
//                    getActivity().getSupportFragmentManager().beginTransaction().remove(ProfileFragment.this).commit();
                    break;
                case LOADING:
                    Log.d(TAG, "onChanged: Loading...");
                    dataBinding.setLoading(true);
                    break;
            }
        });
    }

    @Override
    public void onApplyChangesClicked(User user) {
        viewModel.updateUserProfile(user).observe(getViewLifecycleOwner(), new Observer<Resource<User>>() {
            @Override
            public void onChanged(Resource<User> userResource) {
                switch (userResource.status) {
                    case SUCCESS:
                        if (userResource.data != null) {
                            dataBinding.setUser(userResource.data);
                            dataBinding.setLoading(false);
                            Toast.makeText(getContext(),"Kullanıcı bilgileri güncellendi!", Toast.LENGTH_LONG).show();
                        }
                        break;
                    case ERROR:
                        String message = userResource.message;
                        if (message != null) {
                        dataBinding.progressBar.hide();
                            Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                        }
//                    viewModel.getUserProfile().removeObservers(getViewLifecycleOwner());
//                    getActivity().getSupportFragmentManager().beginTransaction().remove(ProfileFragment.this).commit();
                        break;
                    case LOADING:
                        Log.d(TAG, "onChanged: Loading...");
                        dataBinding.setLoading(true);
                        break;
                }
            }
        });
    }

    @Override
    public void onResetPasswordClicked() {
        openDialog();
    }

    private void openDialog() {

        resetPasswordDialog = new Dialog(getActivity());
        ResetPasswordDialogBinding binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.reset_password_dialog, null, false);
        resetPasswordDialog.setContentView(binding.getRoot());
        resetPasswordDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        binding.setApplyChangesClickDialog(this);
        resetPasswordDialog.show();

    }

    @Override
    public void onConfirmResetPasswordClicked(ResetPasswordRequest resetPasswordRequest) {

    }


}
