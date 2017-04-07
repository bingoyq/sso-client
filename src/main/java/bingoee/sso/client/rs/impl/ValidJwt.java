package bingoee.sso.client.rs.impl;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * Created by kael on 2016/7/7.
 */
public class ValidJwt {
    /**
     * 验证token
     * @param token
     * @return
     */

    public static String getvalidInfo(String token, String publicKey){
        String info ="";
        if(token.length() > 7){
            token = token.replaceAll("^Bearer ","");
//            token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJ1c2VybmFtZSI6InBlbmdoaiIsInNjb3BlIjpudWxsLCJleHBpcmVzX2luIjozNjAwMCwiZXhwaXJlcyI6MTQ2NzkwMjYzOTAwMCwiZXhwIjoxNDY3OTAyNjg3MjgwfQ.P4LSoLj4cqnnNdW61HjPxWPWCvV8BdimHXp_5K0sMEAF3KRb9AFqCDAnbwWOj6OdWlIJrWt3ftBptPW7beyrKpbckRDg0YpuYLdTNKS6uJ1htpgZ5y3iRtA1r1YKl-h7GdZSqzxkXjsVH8hy03Hpg1h_TamBVQAIzonu7aclI30";
            String[] parts = token.split("\\.");
            if(parts != null && parts.length >= 3) {
                String content;
                String payload;
                String signature;
                content = parts[0] + "." + parts[1];
                payload = parts[1];
                signature = parts[2];
                //验证token
                if (!verifySignature(content, signature,publicKey)) {
                    // 验证失败
                } else {
                    // 验证成功
                    try {
                        byte[] decodes = Base64.getUrlDecoder().decode(payload.getBytes("UTF-8"));
                        info = new String(decodes);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }

            }
        }
        return info;
    }

    /**
     * 获取用户信息
     * @param token
     * @param publicKey
     * @return
     */
    public static boolean validToken(String token, String publicKey){
        if(token.length() > 7){
            token = token.replaceAll("^Bearer ","");
//            token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJ1c2VybmFtZSI6InBlbmdoaiIsInNjb3BlIjpudWxsLCJleHBpcmVzX2luIjozNjAwMCwiZXhwaXJlcyI6MTQ2NzkwMjYzOTAwMCwiZXhwIjoxNDY3OTAyNjg3MjgwfQ.P4LSoLj4cqnnNdW61HjPxWPWCvV8BdimHXp_5K0sMEAF3KRb9AFqCDAnbwWOj6OdWlIJrWt3ftBptPW7beyrKpbckRDg0YpuYLdTNKS6uJ1htpgZ5y3iRtA1r1YKl-h7GdZSqzxkXjsVH8hy03Hpg1h_TamBVQAIzonu7aclI30";
            String[] parts = token.split("\\.");
            if(parts != null && parts.length >= 3){
                String content;
                String signature;
                content = parts[0] + "." + parts[1];
                signature = parts[2];
                return verifySignature(content, signature,publicKey);
            }
        }
        return false;
    }

    // 用公钥校验token的有效性
    private static boolean verifySignature(String content, String signed, String publicKey) {
        try {
            byte[] signedData = Base64.getUrlDecoder().decode(signed.getBytes("UTF-8"));
            byte[] contentData = content.getBytes();

            Signature signature = Signature.getInstance("SHA256withRSA");
            signature.initVerify(decodePublicKey(publicKey));
            signature.update(contentData);

            return signature.verify(signedData);
        } catch (GeneralSecurityException e) {
            return false;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return false;
        }
    }
    // 生成校验用的公钥
    public static RSAPublicKey decodePublicKey(String base64) {
        X509EncodedKeySpec spec = new X509EncodedKeySpec(Base64.getMimeDecoder().decode(base64));
        try {
            KeyFactory f = KeyFactory.getInstance("RSA");
            return (RSAPublicKey) f.generatePublic(spec);
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException(e.getMessage(), e);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}