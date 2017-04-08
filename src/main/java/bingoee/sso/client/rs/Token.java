package bingoee.sso.client.rs;

/**
 * yuanq
 */
public interface Token {
    /**
     * 返回用户ID，没有用户信息则返回null
     */
    String getAccessToken();

    /**
     * 返回用户登录名（loginName）
     */
    String getTokenType();

    /**
     * 返回用户的scope列表
     */
    String getRefreshToken();

    /**
     * 返回收到这个token时距离过期还有多长时间
     */
    int getExpiresIn();

    /**
     * 返回指定属性名的属性，没有则返回null
     * @param fieldName
     */
    Object get(String fieldName);
}
