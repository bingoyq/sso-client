package bingoee.sso.client.rs.impl;

import bingoee.sso.client.rs.Token;
import bingoee.sso.client.rs.util.CacheToken;
import bingoee.sso.client.rs.util.RefreshTokenTask;
import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuanq on 2017/4/8.
 */
public class HttpClientUtil {

    /**
     *
     * @param params [url ,username,password,client_id,client_secret]
     * @return
     * @throws Throwable
     */
    public static Token grantTypePassword (String ...params) {

        Token t = CacheToken.getToken(params[1].concat(":").concat(params[3]));
        if(t != null) {
            System.out.println("无请求");
            return t;
        }

        List<NameValuePair> list = new ArrayList<>();
        list.add(new BasicNameValuePair("grant_type","password"));
        list.add(new BasicNameValuePair("username",params[1]));
        list.add(new BasicNameValuePair("password",params[2]));
        list.add(new BasicNameValuePair("client_id",params[3]));
        list.add(new BasicNameValuePair("client_secret",params[4]));

        return  token(list,params);
    }

    /**
     *
     * @param params [url ,refresh_token]
     * @return
     */
    public static Token grantTypeRefreshToken (String ...params) {
        List<NameValuePair> list = new ArrayList<>();
        list.add(new BasicNameValuePair("grant_type","refresh_token"));
        list.add(new BasicNameValuePair("refresh_token",params[1]));

        return  token(list,params);
    }

    private static Token token(List<NameValuePair> list , String ...params){
        try {
            HttpResponse response = post(params[0] , list);
            if(response.getStatusLine().getStatusCode() == 200 ) {

                HttpEntity entity = response.getEntity();
                String strResult = EntityUtils.toString(entity);
                JSONObject json = JSONObject.fromObject(strResult);

                TokenImpl token = new TokenImpl();
                token.setAccessToken(json.getString("access_token"));
                token.setExpiresIn(json.getInt("expires_in"));
                token.setRefreshToken(json.getString("refresh_token"));
                token.setTokenType(json.getString("token_type"));

                CacheToken.setToken(token,params[1].concat(":").concat(params[3])); //username:clientid
                return token;
            }
        }catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * post 请求
     * @param url
     * @param list
     * @return
     * @throws Throwable
     */
    private static HttpResponse post (String url , List<NameValuePair> list) throws Throwable{
        RefreshTokenTask.url = url;
        HttpPost httpRequest = new HttpPost(url);
        httpRequest.setEntity(new UrlEncodedFormEntity(list, HTTP.UTF_8));
        CloseableHttpClient client = HttpClients.createDefault();
        RequestConfig config = RequestConfig.custom().setSocketTimeout(10000).setConnectTimeout(10000).build(); // 握手超时、连接超时配置
        httpRequest.setConfig(config);
        HttpResponse httpResponse = client.execute(httpRequest);
        return httpResponse;
    }
}
