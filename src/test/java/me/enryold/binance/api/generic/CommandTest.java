package me.enryold.binance.api.generic;

import me.enryold.binance.api.interfaces.ICommandTest;
import okhttp3.*;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public abstract class CommandTest implements ICommandTest {


    @Override
    public OkHttpClient mockClient(String serializedBody) throws IOException {

        OkHttpClient okHttpClient = mock(OkHttpClient.class);
        Call remoteCall = mock(Call.class);

        Response response = new Response.Builder()
                .request(new Request.Builder().url("http://url.com").build())
                .protocol(Protocol.HTTP_1_1)
                .code(200).message("").body(
                        ResponseBody.create(
                                MediaType.parse("application/json"),
                                serializedBody
                        ))
                .build();

        when(remoteCall.execute()).thenReturn(response);
        when(okHttpClient.newCall(any())).thenReturn(remoteCall);

        return okHttpClient;
    }

    @Override
    public OkHttpClient mockError() throws IOException {
        OkHttpClient okHttpClient = mock(OkHttpClient.class);
        Call remoteCall = mock(Call.class);

        Response response = new Response.Builder()
                .request(new Request.Builder().url("http://url.com").build())
                .protocol(Protocol.HTTP_1_1)
                .code(400).message("").body(
                    ResponseBody.create(
                            MediaType.parse("application/json"),
                            ""
                    ))
                .build();

        when(remoteCall.execute()).thenReturn(response);
        when(okHttpClient.newCall(any())).thenReturn(remoteCall);

        return okHttpClient;
    }
}
