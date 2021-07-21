package com.example.api.network;

import com.example.api.BuildConfig;
import com.example.api.network.intercepter.HeaderInterceptor;
import com.example.api.network.intercepter.LogInterceptor;
import com.example.api.network.intercepter.TokenInterceptor;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * author : Cong
 * date   : 2021-07-01
 * time   : 17:16
 * whats the fst
 */
public final class Network {

    /**
     * 超时
     */
    public static final int TIME_OUT = 15;

    public static final TypeAdapter<String> STRING_TYPE_ADAPTER = new TypeAdapter<String>() {
        @Override
        public void write(JsonWriter out, String value) throws IOException {
            try {
                if (value == null) {
                    out.nullValue();
                    return;
                }
                out.value(value);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public String read(JsonReader in) throws IOException {
            try {
                if (in.peek() == JsonToken.NULL) {
                    in.nextNull();
                    return "";//强制null值返回空串""
                }
                return in.nextString();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "";
        }
    };


    /**
     * Retrofit
     */
    private Retrofit mRetrofit;

    private Network() {
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder()
                .readTimeout(TIME_OUT, TimeUnit.SECONDS)
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .addNetworkInterceptor(new HeaderInterceptor())
                .addNetworkInterceptor(new TokenInterceptor());

        if (BuildConfig.DEBUG) {
            builder.addInterceptor(new LogInterceptor())
                    .addNetworkInterceptor(new StethoInterceptor());
        }

        GsonBuilder gsonBuilder = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .registerTypeHierarchyAdapter(String.class, STRING_TYPE_ADAPTER);

        mRetrofit = new Retrofit.Builder()
//                .baseUrl(ApplicationConfig.getInstance().getApiHost())
                .addConverterFactory(GsonConverterFactory.create(gsonBuilder.create()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(builder.build())
                .build();
    }

    public static Network getInstance() {
        return SingleHolder.INSTANCE;
    }

    /**
     * 获取服务
     *
     * @param cls ServiceClass
     * @param <T> ServiceClass
     */
    public <T> T getService(Class<T> cls) {
        return mRetrofit.create(cls);
    }

    /**
     * 单例
     */
    private static class SingleHolder {
        /**
         * 单例
         */
        private static final Network INSTANCE = new Network();
    }

}
