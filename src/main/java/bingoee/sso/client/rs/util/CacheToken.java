package bingoee.sso.client.rs.util;

import bingoee.sso.client.rs.Token;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;

/**
 * Created by yuanq on 2017/4/8.
 */
public class CacheToken {
    private static Map<String ,Token> tokenMap = new HashMap<>();

    static {
        // token 缓存对象开始被调用，开启定时刷新token任务
        Timer timer = new Timer();
        timer.schedule(new RefreshTokenTask() , 5000 , 10000); // 任务5秒开始执行，后每 10 秒执行一次
    }

    /**
     * 缓存鉴权信息
     * @param token
     * @param key
     */
    public static void setToken(Token token ,String key) {
        tokenMap.put(key ,token);
        RefreshTokenTask.setTokenOutTime(key , System.currentTimeMillis() + token.getExpiresIn());
    }

    /**
     * username:clientid
     * @param key
     * @return
     */
    public static Token getToken(String key) {
        return tokenMap.get(key);
    }
}
