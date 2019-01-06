package com.teknologi.sios.absensi.api.response;

import com.teknologi.sios.absensi.entity.User;

import java.util.List;

public class AuthResponse extends ApiResponse {
    public String token;
    public String role;
    public List<User> data;
}

