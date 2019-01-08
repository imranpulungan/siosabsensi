package com.teknologi.labakaya.siosabsensiandroid.presenter;

import com.teknologi.labakaya.siosabsensiandroid.api.response.ApiResponse;

public interface iPresenterResponse {
    void doSuccess(ApiResponse response, String tag);
    void doFail(String message);
    void doValidationError(boolean error);
    void doConnectionError();
}
