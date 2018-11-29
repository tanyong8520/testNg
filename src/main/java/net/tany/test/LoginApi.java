package net.tany.test;

import net.tany.serializers.JacksonSerializer;
import net.tany.utils.AjaxResult;
import net.tany.utils.RestClient;

public class LoginApi {
    private static final String LOGIN_URL="/login";

    public static AjaxResult login(RestClient restClient, String username, String password){
        String body = "";
        String url = String.format("%s?username=%s&password=%s", LOGIN_URL, username, password);
        String result = restClient.post(url,body);
        AjaxResult ajaxResult = new JacksonSerializer().deserialize(result, AjaxResult.class);
        return ajaxResult;
    }

    public static AjaxResult logout(RestClient restClient){
        String result = restClient.get(LOGIN_URL);
        AjaxResult ajaxResult = new JacksonSerializer().deserialize(result, AjaxResult.class);
        return ajaxResult;
    }

    public static AjaxResult resetPassword(RestClient restClient, String username, String password) {
        String body = "";
        String url = String.format("%s?password=%s&username=%s", LOGIN_URL, password, username);

        String result = restClient.put(url,body);
        AjaxResult ajaxResult = new JacksonSerializer().deserialize(result, AjaxResult.class);
        return ajaxResult;
    }
}
