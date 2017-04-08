package bingoee.sso.client.rs.util;

import bingoee.sso.client.rs.Token;
import bingoee.sso.client.rs.impl.HttpClientUtil;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.*;

/**
 * Created by yuanq on 2016/8/11.
 */
public class RefreshTokenTask extends TimerTask {

    public static String url = ""; // 刷新token 地址
    private static Map<String ,Long> tokenOutTime = new HashMap<>(); // token 超时记录<username,time[当前登录时间，此次token时效（秒）]>

    @Override
    public void run() {
        for(Map.Entry<String ,Long> entry : tokenOutTime.entrySet()) {
            long currentTime = System.currentTimeMillis();
            if(entry.getValue() - currentTime <= 0) {
                System.out.println(entry.getKey() + " 更换token");
                Token token = CacheToken.getToken(entry.getKey());
                if(token != null) {
                    CacheToken.setToken(HttpClientUtil.grantTypeRefreshToken(new String[]{url ,token.getRefreshToken()}) , entry.getKey());
                }
            }
        }
    }

    public static void setTokenOutTime(String username ,Long time) {
        tokenOutTime.put(username , time);
    }
}
