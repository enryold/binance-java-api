package me.enryold.binance.api.impl.futures.commands;


import me.enryold.binance.api.abstracts.BinanceCommand;
import me.enryold.binance.api.enums.RequestMethod;

import java.math.BigDecimal;

public class NewOrderCommand extends BinanceCommand {
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
        return "/fapi/v1/order";
    }

    @Override
    public String[] mandatoryParameters() {
        return new String[] {"symbol", "side", "type", "timestamp"};
    }

    public NewOrderCommand withSymbol(String val){
        addParam("symbol", val);
        return this;
    }

    public NewOrderCommand withSide(String val){
        addParam("side", val);
        return this;
    }

    public NewOrderCommand withPositionSide(String val){
        addParam("positionSide", val);
        return this;
    }

    public NewOrderCommand withType(String val){
        addParam("type", val);
        return this;
    }

    public NewOrderCommand withTimeInForce(String val){
        addParam("timeInForce", val);
        return this;
    }

    public NewOrderCommand withQuantity(BigDecimal val){
        addParam("quantity", val);
        return this;
    }

    public NewOrderCommand withReduceOnly(String val){
        addParam("reduceOnly", val);
        return this;
    }

    public NewOrderCommand withPrice(BigDecimal val){
        addParam("price", val);
        return this;
    }

    public NewOrderCommand withNewClientOrderId(String val){
        addParam("newClientOrderId", val);
        return this;
    }

    public NewOrderCommand withStopPrice(BigDecimal val){
        addParam("stopPrice", val);
        return this;
    }

    public NewOrderCommand withClosePosition(String val){
        addParam("closePosition", val);
        return this;
    }

    public NewOrderCommand withActivationPrice(BigDecimal val){
        addParam("activationPrice", val);
        return this;
    }

    public NewOrderCommand withCallbackRate(BigDecimal val){
        addParam("callbackRate", val);
        return this;
    }

    public NewOrderCommand withWorkingType(String val){
        addParam("workingType", val);
        return this;
    }

    public NewOrderCommand withPriceProtect(String val){
        addParam("priceProtect", val);
        return this;
    }

    public NewOrderCommand withNewOrderRespType(String val){
        addParam("newOrderRespType", val);
        return this;
    }

    public NewOrderCommand withRecvWindow(String val){
        addParam("recvWindow", val);
        return this;
    }

    public NewOrderCommand withTimestamp(Long val){
        addParam("timestamp", val);
        return this;
    }

}
