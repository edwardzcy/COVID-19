package com.johne.covid.util;

import com.alibaba.fastjson.JSONObject;
import okhttp3.*;
import okhttp3.internal.http2.Header;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


/**
 * @Author: johne
 * @Date: 2022/11/4 15:22
 * Copyright (c) 2020 tencent
 */
public class RiskAreaUtil {
    public static String url = "http://bmfw.www.gov.cn/bjww/interface/interfaceJson";
    public static String a = "23y0ufFl5YxIyGrI8hWRUZmKkvtSjLQA";
    public static String i = "123456789abcdefg";
    public static String signature = "fTN2pfuisxTavbTuYVSsNJHetwq5bJvCQkjjtiLM2dCratiA";

    public static String x_wif_nonce = "QkjjtiLM2dCratiA";
    public static String x_wif_paasid = "smt-application";

    public static String appId = "NcApplication";
    public static String paasHeader = "zdww";
    public static String nonceHeader = "123456789abcdefg";
    public static String key = "3C502C97ABDA40D0A60FBEE50FAAD1DA";

    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    public static String get_risk() {
        long timestamp = get_timestamp();
        Map<String, String> headers = new HashMap<>();
        headers.put("x-wif-nonce", x_wif_nonce);
        headers.put("x-wif-signature", get_x_wif_signature(timestamp));
        headers.put("x-wif-timestamp", timestamp + "");
        headers.put("x-wif-paasid", x_wif_paasid);
        headers.put("Accept", "*/*");

        Map<String, Object> data = new HashMap<String, Object>();
        data.put("appId", appId);
        data.put("paasHeader", paasHeader);
        data.put("timestampHeader", timestamp);
        data.put("nonceHeader", nonceHeader);
        data.put("signatureHeader", crypo_sha256(timestamp));
        data.put("key", key);
        System.out.println(headers);
        System.out.println(data);

        String body = JSONObject.toJSONString(data);
        RequestBody requestBody = RequestBody.create(body,JSON);
        Headers.Builder builder = new Headers.Builder();
        headers.forEach(builder::add);
        Headers headers1 = builder.build();

        OkHttpClient client = new OkHttpClient.Builder().
                readTimeout(3000, TimeUnit.MILLISECONDS).
                writeTimeout(3000, TimeUnit.MILLISECONDS).build();
        Request request = new Request.Builder().
                url(url).
                post(requestBody).headers(headers1).build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static long get_timestamp(){
        return System.currentTimeMillis()/1000;
    }
    public static String crypo_sha256(long timestamp){
        String str = timestamp + a + i + timestamp;
        String r = SHA256Util.getSHA256String(str).toUpperCase();
        return r;
    }
    public static String get_x_wif_signature(long timestamp) {
        String str = (timestamp + signature +timestamp);
        String r = SHA256Util.getSHA256String(str).toUpperCase();
        return r;
    }

}