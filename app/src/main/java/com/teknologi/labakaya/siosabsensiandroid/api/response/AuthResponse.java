package com.teknologi.labakaya.siosabsensiandroid.api.response;

import com.teknologi.labakaya.siosabsensiandroid.entity.User;

import java.util.List;

public class AuthResponse extends ApiResponse {
    public String token;
    public String role;
    public List<User> data;
}

