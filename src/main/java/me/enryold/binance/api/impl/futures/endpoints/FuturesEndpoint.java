package me.enryold.binance.api.impl.futures.endpoints;


import me.enryold.binance.api.interfaces.IBinanceEndpoint;

public class FuturesEndpoint implements IBinanceEndpoint {
    @Override
    public String getUrl() {
        return "https://fapi.binance.com";
    }
}
