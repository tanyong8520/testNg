package net.tany.utils;

import net.tany.serializers.JacksonSerializer;
import org.glassfish.jersey.client.JerseyClientBuilder;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RestClient {

    private static final String PREFIX_API="/api";
    private static final List<String> IGNORE_COOKIE = Arrays.asList("rememberMe");

    private static final int REST_ERROR=500;
    private final Client client;
    private final WebTarget hostWebTarget;
    private Map<String, String> cookies= new HashMap<String, String>();

    public RestClient(String host){
        SSLContext ctx = null;
        try {
            ctx = SSLContext.getInstance("SSL");
            TrustManager[] trustAllCerts = { new InsecureTrustManager() };
            ctx.init(null, trustAllCerts, new java.security.SecureRandom());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        client = JerseyClientBuilder.newBuilder().sslContext(ctx).build();
        hostWebTarget = client.target(host);
    }

    private void updateCookies(Map<String, NewCookie> newCookies){
        for(NewCookie cookie:newCookies.values()){
            if(IGNORE_COOKIE.indexOf(cookie.getName())>=0){
                continue;
            }
            cookies.put(cookie.getName(), cookie.getValue());
        }
    }

    private Invocation.Builder getBuilder(String path){
        if(!path.startsWith(PREFIX_API)){
            path =PREFIX_API + path;
        }
        String[] items = path.split("[?]");
        WebTarget webTarget = hostWebTarget.path(items[0]);
        if(items.length>1){
            String queryStr = items[1];
            String[] queryParams = queryStr.split("&");
            for(int i=0; i<queryParams.length; i++){
                String[] paraItems = queryParams[i].split("=");
                webTarget = webTarget.queryParam(paraItems[0], paraItems[1]);
            }
        }
        Invocation.Builder builder = webTarget.request(MediaType.APPLICATION_JSON);

        for(Map.Entry<String, String> entry : cookies.entrySet()){
            builder = builder.cookie(entry.getKey(), entry.getValue());
        }
        return builder;
    }

    private void checkStatus(Response response, String result){
        if(REST_ERROR == response.getStatus() ){
            ErrorResponse errorResponse = new JacksonSerializer().deserialize(result, ErrorResponse.class);
            throw new RestException(errorResponse.getError());
        }
    }

    public String get(String path){
        Invocation.Builder invocationBuilder = getBuilder(path);

        Response response = invocationBuilder.get();
        updateCookies(response.getCookies());
        String result = response.readEntity(String.class);
        response.close();

        checkStatus(response, result);
        return result;
    }

    public String delete(String path){
        Invocation.Builder invocationBuilder = getBuilder(path);

        Response response = invocationBuilder.delete();
        updateCookies(response.getCookies());
        String result = response.readEntity(String.class);
        response.close();

        checkStatus(response, result);
        return result;
    }

    public String post(String path, String jsonBody){
        Invocation.Builder invocationBuilder = getBuilder(path);

        Response response = invocationBuilder.post(Entity.entity(jsonBody, MediaType.APPLICATION_JSON));
        updateCookies(response.getCookies());
        String result = response.readEntity(String.class);
        response.close();

        checkStatus(response, result);
        return result;
    }

    public String put(String path, String jsonBody){
        Invocation.Builder invocationBuilder = getBuilder(path);

        Response response = invocationBuilder.put(Entity.entity(jsonBody, MediaType.APPLICATION_JSON));
        updateCookies(response.getCookies());
        String result = response.readEntity(String.class);
        response.close();

        checkStatus(response, result);
        return result;
    }
}
