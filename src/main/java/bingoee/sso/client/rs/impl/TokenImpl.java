package bingoee.sso.client.rs.impl;


import bingoee.sso.client.rs.Principal;
import bingoee.sso.client.rs.Token;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kael on 2017/4/7.
 */
class TokenImpl implements Token {

    private String accessToken;
    private String tokenType;
    private String refreshToken;
    private int expiresIn;
    private Map<String, Object> properties = new HashMap<String, Object>();

    @Override
    public String getAccessToken() {
        return accessToken;
    }

    @Override
    public String getTokenType() {
        return tokenType;
    }

    @Override
    public String getRefreshToken() {
        return refreshToken;
    }

    @Override
    public int getExpiresIn() {
        return expiresIn;
    }

    @Override
    public Object get(String fieldName) {
        return properties;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }

    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }
}
