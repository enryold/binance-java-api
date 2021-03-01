package me.enryold.binance.api.interfaces;

import okhttp3.OkHttpClient;

import java.io.IOException;

public interface ICommandTest {

    String responsePayload();
    OkHttpClient mockClient(final String serializedBody) throws IOException;
    OkHttpClient mockError() throws IOException;
}
