package com.company.akilovasi.ui.main.callbacks;

import com.company.akilovasi.data.local.entities.User;
import com.company.akilovasi.data.remote.models.requests.ResetPasswordRequest;

public interface ApplyChangesClick {

    void onApplyChangesClicked(User user);

    void onResetPasswordClicked();

    void onConfirmResetPasswordClicked(ResetPasswordRequest resetPasswordRequest);
}
