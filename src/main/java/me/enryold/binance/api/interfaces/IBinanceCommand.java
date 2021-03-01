package me.enryold.binance.api.interfaces;


import me.enryold.binance.api.enums.RequestMethod;
import me.enryold.binance.api.utils.UrlParamsBuilder;

public interface IBinanceCommand {

    boolean needAuth();
    RequestMethod getRequestMethod();
    String getPath();
    UrlParamsBuilder getParameters();
}
