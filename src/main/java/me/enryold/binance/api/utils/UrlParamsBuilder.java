package me.enryold.binance.api.utils;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.enryold.binance.api.exceptions.BinanceException;
import okhttp3.MediaType;
import okhttp3.RequestBody;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

public class UrlParamsBuilder {
    private static final MediaType JSON_TYPE = MediaType.parse("application/json");
    private final ParamsMap paramsMap = new ParamsMap();
    private final ParamsMap postBodyMap = new ParamsMap();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private String method = "GET";


    public static UrlParamsBuilder build() {
        return new UrlParamsBuilder();
    }

    private UrlParamsBuilder() {
    }

    public UrlParamsBuilder setMethod(String mode) {
        this.method = mode;
        return this;
    }

    public Boolean checkMethod(String mode) {
        return mode.equals(this.method);
    }

    public <T extends Enum> UrlParamsBuilder putToUrl(String name, T value) {
        if (value != null) {
            this.paramsMap.put(name, value.toString());
        }

        return this;
    }

    public UrlParamsBuilder putToUrl(String name, String value) {
        this.paramsMap.put(name, value);
        return this;
    }

    public UrlParamsBuilder putToUrl(String name, Date value, String format) {
        this.paramsMap.put(name, value, format);
        return this;
    }

    public UrlParamsBuilder putToUrl(String name, Integer value) {
        this.paramsMap.put(name, value);
        return this;
    }

    public UrlParamsBuilder putToUrl(String name, Long value) {
        this.paramsMap.put(name, value);
        return this;
    }

    public UrlParamsBuilder putToUrl(String name, BigDecimal value) {
        this.paramsMap.put(name, value);
        return this;
    }

    public UrlParamsBuilder putToPost(String name, String value) {
        this.postBodyMap.put(name, value);
        return this;
    }

    public <T extends Enum> UrlParamsBuilder putToPost(String name, T value) {
        if (value != null) {
            this.postBodyMap.put(name, value.toString());
        }

        return this;
    }

    public UrlParamsBuilder putToPost(String name, Date value, String format) {
        this.postBodyMap.put(name, value, format);
        return this;
    }

    public UrlParamsBuilder putToPost(String name, Integer value) {
        this.postBodyMap.put(name, value);
        return this;
    }

    public <T> UrlParamsBuilder putToPost(String name, List<String> list) {
        this.postBodyMap.stringListMap.put(name, list);
        return this;
    }

    public UrlParamsBuilder putToPost(String name, Long value) {
        this.postBodyMap.put(name, value);
        return this;
    }

    public UrlParamsBuilder putToPost(String name, BigDecimal value) {
        this.postBodyMap.put(name, value);
        return this;
    }

    public String buildUrl() {
        Map<String, String> map = new LinkedHashMap(this.paramsMap.map);
        StringBuilder head = new StringBuilder("");
        return "?" + this.AppendUrl(map, head);
    }

    public String buildSignature() {
        Map<String, String> map = new LinkedHashMap(this.paramsMap.map);
        StringBuilder head = new StringBuilder();
        return this.AppendUrl(map, head);
    }

    private String AppendUrl(Map<String, String> map, StringBuilder stringBuilder) {
        Iterator var3 = map.entrySet().iterator();

        while(var3.hasNext()) {
            Map.Entry<String, String> entry = (Map.Entry)var3.next();
            if (!"".equals(stringBuilder.toString())) {
                stringBuilder.append("&");
            }

            stringBuilder.append((String)entry.getKey());
            stringBuilder.append("=");
            stringBuilder.append(urlEncode((String)entry.getValue()));
        }

        return stringBuilder.toString();
    }

    public RequestBody buildPostBody() throws JsonProcessingException {
        if (this.postBodyMap.stringListMap.isEmpty()) {
            return this.postBodyMap.map.isEmpty() ? RequestBody.create(JSON_TYPE, "") : RequestBody.create(JSON_TYPE, objectMapper.writeValueAsString(this.postBodyMap.map));
        } else {
            return RequestBody.create(JSON_TYPE, objectMapper.writeValueAsString(this.postBodyMap.stringListMap));
        }
    }

    public boolean hasPostParam() {
        return !this.postBodyMap.map.isEmpty() || "POST".equals(this.method);
    }

    public String buildUrlToJsonString() throws JsonProcessingException {
        return objectMapper.writeValueAsString(this.paramsMap.map);
    }

    private static String urlEncode(String s) {
        try {
            return URLEncoder.encode(s, "UTF-8").replaceAll("\\+", "%20");
        } catch (UnsupportedEncodingException var2) {
            throw new BinanceException("RuntimeError", "[URL] UTF-8 encoding not supported!");
        }
    }

    class ParamsMap {
        final Map<String, String> map = new LinkedHashMap();
        final Map<String, List> stringListMap = new HashMap();

        ParamsMap() {
        }

        void put(String name, String value) {
            if (name != null && !"".equals(name)) {
                if (value != null && !"".equals(value)) {
                    this.map.put(name, value);
                }
            } else {
                throw new BinanceException("RuntimeError", "[URL] Key can not be null");
            }
        }

        void put(String name, Integer value) {
            this.put(name, value != null ? Integer.toString(value) : null);
        }

        void put(String name, Date value, String format) {
            SimpleDateFormat dateFormatter = new SimpleDateFormat(format);
            this.put(name, value != null ? dateFormatter.format(value) : null);
        }

        void put(String name, Long value) {
            this.put(name, value != null ? Long.toString(value) : null);
        }

        void put(String name, BigDecimal value) {
            this.put(name, value != null ? value.toPlainString() : null);
        }
    }
}

