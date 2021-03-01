package me.enryold.binance.api.impl.futures.commands;


import me.enryold.binance.api.abstracts.BinanceCommand;
import me.enryold.binance.api.enums.RequestMethod;

public class PositionRiskCommand extends BinanceCommand {
    @Override
    public boolean needAuth() {
        return true;
    }

    @Override
    public RequestMethod getRequestMethod() {
        return RequestMethod.GET;
    }

    @Override
    public String getPath() {
        return "/fapi/v2/positionRisk";
    }

    public PositionRiskCommand withSymbol(String val){
        addParam("SYMBOL", val);
        return this;
    }

    public PositionRiskCommand withRecvWindow(Long val){
        addParam("recvWindow", val);
        return this;
    }

    public PositionRiskCommand withTimestamp(Long val){
        addParam("timestamp", val);
        return this;
    }
}
