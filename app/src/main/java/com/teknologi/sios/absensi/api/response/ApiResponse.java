package com.teknologi.sios.absensi.api.response;

import java.util.List;

public class ApiResponse {
    public boolean success;
    public boolean status;
    public String message;
    public List<Error> error;

    public class Error{
        public String code;
        public String field;
        public String message;
    }
}
