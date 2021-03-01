package me.enryold.binance.api.abstracts;



import me.enryold.binance.api.interfaces.IBinanceCommand;
import me.enryold.binance.api.utils.UrlParamsBuilder;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public abstract class BinanceCommand implements IBinanceCommand {

    private Map<String, String> stringMap = new HashMap<>();
    private Map<String, BigDecimal> numMap = new HashMap<>();

    public BinanceCommand(){
        addParam("timestamp", Instant.now().toEpochMilli());
    }

    @Override
    public UrlParamsBuilder getParameters() {

        UrlParamsBuilder builder = UrlParamsBuilder.build()
                .setMethod(getRequestMethod().toString());

        switch (getRequestMethod()){
            case POST:
            case PUT:
            case DELETE:
            case GET: {
                stringMap.forEach(builder::putToUrl);
                numMap.forEach(builder::putToUrl);
                break;
            }
            default: break;
        }

        return builder;
    }


    protected void addParam(String name, String value) {
        this.stringMap.put(name, value);
    }

    protected void addParam(String name, Date value, String format) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat(format);
        this.addParam(name, value != null ? dateFormatter.format(value) : null);
    }

    protected void addParam(String name, Integer val) {
        this.numMap.put(name, new BigDecimal(val));
    }

    protected void addParam(String name, Long val) {
        this.numMap.put(name, new BigDecimal(val));
    }

    protected void addParam(String name, BigDecimal val) {
        this.numMap.put(name, val);
    }
}
