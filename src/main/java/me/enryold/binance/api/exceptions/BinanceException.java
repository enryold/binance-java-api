package me.enryold.binance.api.exceptions;

public class BinanceException extends RuntimeException {

    private String errCode;

    public BinanceException(String errType, String errMsg) {
        super(errMsg);
        this.errCode = errType;
    }

    public BinanceException(Throwable e) {
        super(e);
    }

    public BinanceException(String errType, String errMsg, Throwable e) {
        super(errMsg, e);
        this.errCode = errType;
    }
}
