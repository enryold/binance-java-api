# binance-java-api
Binance Java API library, easily customizable with new commands

### Why I'm doing it
I used joaopsilva/binance-java-api for a while, but I found it not very friendly to extend, so I created a simple interface to call any Binance API you want.
By the way, I want to thanks Joaopsilva cause I brutally copy&paste its authentication classes :)


### Usage
1) Extend the abstract class BinanceCommand to create your API endpoint call

```java
public class ChangeInitialLeverage extends BinanceCommand {
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
        return "/fapi/v1/leverage";
    }

    @Override
    public String[] mandatoryParameters() {
        return new String[]{"symbol", "leverage"};
    }

```

2) Add the additional API call parameters in a pure old java style.

```java
       public ChangeInitialLeverage withSymbol(String val){
           addParam("symbol", val);
           return this;
       }
   
       public ChangeInitialLeverage withLeverage(Integer val){
           addParam("leverage", val);
           return this;
       }
```

3) Instantiate the client and fire your command

```java
        BinanceClient client =  new BinanceClient(okHttpClient, new FuturesEndpoint(), apiKey, apiSecret);
        client.submitCommand(new ChangeInitialLeverage()
                .withSymbol("BTCUSDT")
                .withLeverage(4)
        );
```

### Contribution
Mapping all APIs is torture for all of us boring nerds.
So, feel free to contribute by adding your commands.
The only rule is properly testing them!
