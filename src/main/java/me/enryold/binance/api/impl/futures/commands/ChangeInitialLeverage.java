package me.enryold.binance.api.impl.futures.commands;


import me.enryold.binance.api.abstracts.BinanceCommand;
import me.enryold.binance.api.enums.RequestMethod;

public class ChangeInitialLeverage extends BinanceCommand {
    @Override
    public boolean needAuth() {
        return true;
    }

    @Override
    public RequestMethod getRequestMethod() {
        return RequestMethod.POST;
    }

    @Override
    public String getPath() {
        return "/fapi/v1/leverage";
    }

    public ChangeInitialLeverage withSymbol(String val){
        addParam("symbol", val);
        return this;
    }

    public ChangeInitialLeverage withLeverage(Integer val){
        addParam("leverage", val);
        return this;
    }

    public ChangeInitialLeverage withRecvWindow(String val){
        addParam("recvWindow", val);
        return this;
    }

    public ChangeInitialLeverage withTimestamp(Long val){
        addParam("timestamp", val);
        return this;
    }

}
