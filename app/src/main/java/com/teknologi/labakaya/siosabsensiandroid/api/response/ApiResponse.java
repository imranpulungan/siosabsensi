package com.teknologi.labakaya.siosabsensiandroid.api.response;

import java.util.List;

public class ApiResponse {
    public boolean success;
    public boolean status;
    public String message;
    public String jenis_absen;
    public List<Error> error;

    public class Error{
        public String code;
        public String field;
        public String message;
    }
}
