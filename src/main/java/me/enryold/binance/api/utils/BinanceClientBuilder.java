package me.enryold.binance.api.utils;

import me.enryold.binance.api.BinanceClient;
import me.enryold.binance.api.impl.futures.endpoints.FuturesEndpoint;
import okhttp3.OkHttpClient;

public class BinanceClientBuilder {

    public static BinanceClient futures(OkHttpClient okHttpClient, String apiKey, String apiSecret){
        return new BinanceClient(okHttpClient, new FuturesEndpoint(), apiKey, apiSecret);
    }
}
