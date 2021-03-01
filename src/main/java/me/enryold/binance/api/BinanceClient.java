package me.enryold.binance.api;


import me.enryold.binance.api.enums.RequestMethod;
import me.enryold.binance.api.exceptions.BinanceException;
import me.enryold.binance.api.impl.futures.endpoints.FuturesEndpoint;
import me.enryold.binance.api.interfaces.IBinanceClient;
import me.enryold.binance.api.interfaces.IBinanceCommand;
import me.enryold.binance.api.interfaces.IBinanceEndpoint;
import me.enryold.binance.api.utils.BinanceRequestBuilder;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class BinanceClient implements IBinanceClient {


    private OkHttpClient okHttpClient;
    private IBinanceEndpoint endpoint;
    private String apiKey;
    private String apiSecret;


    public BinanceClient(OkHttpClient okHttpClient, IBinanceEndpoint endpoint, String apiKey, String apiSecret){
        this.okHttpClient = okHttpClient;
        this.endpoint = endpoint;
        this.apiKey = apiKey;
        this.apiSecret = apiSecret;
    }

    @Override
    public String submitCommand(IBinanceCommand command) {

        Request request = command.needAuth() ?
                this.requestWithSignature(
                        new BinanceRequestBuilder(endpoint.getUrl(), apiKey, apiSecret),
                        command) :
                this.requestWithoutSignature(
                        new BinanceRequestBuilder(endpoint.getUrl()),
                        command);

        Response response = null;
        String result = null;
        try {
            response = okHttpClient.newCall(request).execute();
            if(response.code() != 200){
                // TODO: NOT REALLY GOOD, FIX THIS ONE
                throw new BinanceException(response.code()+"", new String(response.body().bytes(), StandardCharsets.UTF_8));
            }
            else{
                result = new String(response.body().bytes(), StandardCharsets.UTF_8);
            }
        } catch (IOException e) {
            throw new BinanceException(e);
        }finally {
            if(response != null){
                response.close();
            }
        }

        return result;
    }

    private Request requestWithSignature(BinanceRequestBuilder binanceRequestBuilder, IBinanceCommand command){
        Request request;

        switch (command.getRequestMethod()){
            case GET: {
                request = binanceRequestBuilder.createRequestByGetWithSignature(command.getPath(), command.getParameters());
                break;
            }
            case PUT: {
                request = binanceRequestBuilder.createRequestByPutWithSignature(command.getPath(), command.getParameters());
                break;
            }
            case POST: {
                request = binanceRequestBuilder.createRequestByPostWithSignature(command.getPath(), command.getParameters());
                break;
            }
            case DELETE: {
                request = binanceRequestBuilder.createRequestByDeleteWithSignature(command.getPath(), command.getParameters());
                break;
            }
            default: throw new BinanceException("UserError","Method: "+command.getRequestMethod()+" not supported");
        }

        return request;
    }


    private Request requestWithoutSignature(BinanceRequestBuilder binanceRequestBuilder, IBinanceCommand command){
        Request request;

        if (command.getRequestMethod() == RequestMethod.GET) {
            request = binanceRequestBuilder.createRequestByGet(command.getPath(), command.getParameters());
        } else {
            throw new BinanceException("UserError", "Method: " + command.getRequestMethod() + " not supported");
        }

        return request;
    }
}
