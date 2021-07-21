package com.example.api.network.services;

import com.example.api.response.NetworkResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * author : Cong
 * date   : 2021-07-01
 * time   : 17:49
 * whats the fst
 */
public interface UserServise {
        /**
         * 获取用户信息（其他用户
         */
        @GET("/user/profile")
        Observable<NetworkResponse<String>> getCreator(@Query("id") String id);
}
