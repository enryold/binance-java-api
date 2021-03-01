package me.enryold.binance.api.impl.futures.commands;


import me.enryold.binance.api.abstracts.BinanceCommand;
import me.enryold.binance.api.enums.RequestMethod;

public class ChangeMarginType extends BinanceCommand {

    public enum MarginType{
        ISOLATED,CROSSED
    }

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
        return "/fapi/v1/marginType";
    }

    public ChangeMarginType withSymbol(String val){
        addParam("symbol", val);
        return this;
    }

    public ChangeMarginType withMarginType(MarginType val){
        addParam("marginType", val.toString());
        return this;
    }
    
    public ChangeMarginType withRecvWindow(String val){
        addParam("recvWindow", val);
        return this;
    }

    public ChangeMarginType withTimestamp(Long val){
        addParam("timestamp", val);
        return this;
    }

}
