package com.teknologi.sios.absensi.presenter;

import com.teknologi.sios.absensi.api.response.ApiResponse;

public interface iPresenterResponse {
    void doSuccess(ApiResponse response, String tag);
    void doFail(String message);
    void doValidationError(boolean error);
    void doConnectionError();
}
