package com.teknologi.sios.absensi.api.response;

public class QrCodeResponse extends ApiResponse{
    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
