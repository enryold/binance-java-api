package me.enryold.binance.api.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import me.enryold.binance.api.exceptions.BinanceException;
import okhttp3.Request;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class BinanceRequestBuilder {

    class ApiSignature {
        static final String op = "op";
        static final String opValue = "auth";
        private static final String signatureMethodValue = "HmacSHA256";
        public static final String signatureVersionValue = "2";

        ApiSignature() {
        }

        void createSignature(String accessKey, String secretKey, UrlParamsBuilder builder) {
            if (accessKey != null && !"".equals(accessKey) && secretKey != null && !"".equals(secretKey)) {
                builder.putToUrl("recvWindow", Long.toString(60000L)).putToUrl("timestamp", Long.toString(System.currentTimeMillis()));

                Mac hmacSha256;
                try {
                    hmacSha256 = Mac.getInstance("HmacSHA256");
                    SecretKeySpec secKey = new SecretKeySpec(secretKey.getBytes(), "HmacSHA256");
                    hmacSha256.init(secKey);
                } catch (NoSuchAlgorithmException var7) {
                    throw new BinanceException("RuntimeError", "[Signature] No such algorithm: " + var7.getMessage());
                } catch (InvalidKeyException var8) {
                    throw new BinanceException("RuntimeError", "[Signature] Invalid key: " + var8.getMessage());
                }

                String payload = builder.buildSignature();
                String actualSign = new String(Hex.encodeHex(hmacSha256.doFinal(payload.getBytes())));
                builder.putToUrl("signature", actualSign);
            } else {
                throw new BinanceException("KeyMissing", "API key and secret key are required");
            }
        }
    }

    private String serverUrl, apiKey, secretKey;


    public BinanceRequestBuilder(String serverUrl, String apiKey, String secretKey){
        this.serverUrl = serverUrl;
        this.apiKey = apiKey;
        this.secretKey = secretKey;
    }

    public BinanceRequestBuilder(String serverUrl){
        this.serverUrl = serverUrl;
    }




    public Request createRequestByGet(String address, UrlParamsBuilder builder) {
        System.out.println(this.serverUrl);
        return this.createRequestByGet(this.serverUrl, address, builder);
    }

    public Request createRequestByGet(String url, String address, UrlParamsBuilder builder) {
        return this.createRequest(url, address, builder);
    }

    public Request createRequest(String url, String address, UrlParamsBuilder builder) {
        String requestUrl = url + address;
        System.out.print(requestUrl);
        if (builder != null) {
            try{
                return builder.hasPostParam() ? (new Request.Builder()).url(requestUrl).post(builder.buildPostBody()).addHeader("Content-Type", "application/json").addHeader("client_SDK_Version", "binance_futures-1.0.1-java").build() : (new Request.Builder()).url(requestUrl + builder.buildUrl()).addHeader("Content-Type", "application/x-www-form-urlencoded").addHeader("client_SDK_Version", "binance_futures-1.0.1-java").build();
            }catch (JsonProcessingException e){
                e.printStackTrace();
                throw new BinanceException("JsonError", "Cannot build json request: "+e.getMessage());
            }
        } else {
            return (new Request.Builder()).url(requestUrl).addHeader("Content-Type", "application/x-www-form-urlencoded").addHeader("client_SDK_Version", "binance_futures-1.0.1-java").build();
        }
    }

    public Request createRequestWithSignature(String url, String address, UrlParamsBuilder builder) {
        if (builder == null) {
            throw new BinanceException("RuntimeError", "[Invoking] Builder is null when create request with Signature");
        } else {
            String requestUrl = url + address;
            (new ApiSignature()).createSignature(this.apiKey, this.secretKey, builder);

            try{
                if (builder.hasPostParam()) {
                    requestUrl = requestUrl + builder.buildUrl();
                    return (new Request.Builder()).url(requestUrl).post(builder.buildPostBody()).addHeader("Content-Type", "application/json").addHeader("X-MBX-APIKEY", this.apiKey).addHeader("client_SDK_Version", "binance_futures-1.0.1-java").build();
                } else if (builder.checkMethod("PUT")) {
                    requestUrl = requestUrl + builder.buildUrl();
                    return (new Request.Builder()).url(requestUrl).put(builder.buildPostBody()).addHeader("Content-Type", "application/x-www-form-urlencoded").addHeader("X-MBX-APIKEY", this.apiKey).addHeader("client_SDK_Version", "binance_futures-1.0.1-java").build();
                } else if (builder.checkMethod("DELETE")) {
                    requestUrl = requestUrl + builder.buildUrl();
                    return (new Request.Builder()).url(requestUrl).delete().addHeader("Content-Type", "application/x-www-form-urlencoded").addHeader("client_SDK_Version", "binance_futures-1.0.1-java").addHeader("X-MBX-APIKEY", this.apiKey).build();
                } else {
                    requestUrl = requestUrl + builder.buildUrl();
                    return (new Request.Builder()).url(requestUrl).addHeader("Content-Type", "application/x-www-form-urlencoded").addHeader("client_SDK_Version", "binance_futures-1.0.1-java").addHeader("X-MBX-APIKEY", this.apiKey).build();
                }
            }catch (JsonProcessingException e){
                e.printStackTrace();
                throw new BinanceException("JsonError", "Cannot build json request: "+e.getMessage());
            }

        }
    }

    public Request createRequestByPostWithSignature(String address, UrlParamsBuilder builder) {
        return this.createRequestWithSignature(this.serverUrl, address, builder.setMethod("POST"));
    }

    public Request createRequestByGetWithSignature(String address, UrlParamsBuilder builder) {
        return this.createRequestWithSignature(this.serverUrl, address, builder);
    }

    public Request createRequestByPutWithSignature(String address, UrlParamsBuilder builder) {
        return this.createRequestWithSignature(this.serverUrl, address, builder.setMethod("PUT"));
    }

    public Request createRequestByDeleteWithSignature(String address, UrlParamsBuilder builder) {
        return this.createRequestWithSignature(this.serverUrl, address, builder.setMethod("DELETE"));
    }

    private Request createRequestWithApikey(String url, String address, UrlParamsBuilder builder) {
        if (builder == null) {
            throw new BinanceException("RuntimeError", "[Invoking] Builder is null when create request with Signature");
        } else {
            String requestUrl = url + address;
            requestUrl = requestUrl + builder.buildUrl();

            try{
                if (builder.hasPostParam()) {
                    return (new Request.Builder()).url(requestUrl).post(builder.buildPostBody()).addHeader("Content-Type", "application/json").addHeader("X-MBX-APIKEY", this.apiKey).addHeader("client_SDK_Version", "binance_futures-1.0.1-java").build();
                } else if (builder.checkMethod("DELETE")) {
                    return (new Request.Builder()).url(requestUrl).delete().addHeader("Content-Type", "application/x-www-form-urlencoded").addHeader("X-MBX-APIKEY", this.apiKey).addHeader("client_SDK_Version", "binance_futures-1.0.1-java").build();
                } else {
                    return builder.checkMethod("PUT") ? (new Request.Builder()).url(requestUrl).put(builder.buildPostBody()).addHeader("Content-Type", "application/x-www-form-urlencoded").addHeader("X-MBX-APIKEY", this.apiKey).addHeader("client_SDK_Version", "binance_futures-1.0.1-java").build() : (new Request.Builder()).url(requestUrl).addHeader("Content-Type", "application/x-www-form-urlencoded").addHeader("X-MBX-APIKEY", this.apiKey).addHeader("client_SDK_Version", "binance_futures-1.0.1-java").build();
                }            }
            catch (JsonProcessingException e){
                e.printStackTrace();
                throw new BinanceException("JsonError", "Cannot build json request: "+e.getMessage());
            }
        }
    }

    public Request createRequestByGetWithApikey(String address, UrlParamsBuilder builder) {
        return this.createRequestWithApikey(this.serverUrl, address, builder);
    }
}
