package tested;

import bingoee.sso.client.rs.Authenticator;
import bingoee.sso.client.rs.Principal;
import bingoee.sso.client.rs.impl.AuthenticatorFactory;
import org.junit.Test;

/**
 * Created by skadi on 2017/4/7.
 */
public class TestAuthenticatorFactory {
    static String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDASOjIWexLpnXiJNJF2pL6NzP\n" +
            "fBoF0tKEr2ttAkJ/7f3uUHhj2NIhQ01Wu9OjHfXjCvQSXMWqqc1+O9G1UwB2Xslb\n" +
            "WNwEZFMwmQdP5VleGbJLR3wOl3IzdggkxBJ1Q9rXUlVtslK/CsMtkwkQEg0eZDH1\n" +
            "VeJXqKBlEhsNckYIGQIDAQAB";
    static  String token ="eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJ1c2VyX2lkIjoiNDNGRTY0NzYtQ0Q3Qi00O" +
            "TNCLTgwNDQtQzdFMzE0OUQwODc2IiwidXNlcm5hbWUiOiJhZG1pbiIsInNjb3BlIjoiIiwiZXhwaXJlc19pbiI6MzU4" +
            "NzIsImV4cGlyZXMiOjE0OTA3MzU1MzcwMDAsImVuYWJsZWQiOjEsImV4cCI6MTQ5MDczNTY2NDc0OX0.S3PhkZ3bz48oLr" +
            "0he3mdbuUcOqEn0nw7R2JTtWTt2XPqCEgGtTgT0R4Qqto3to7_EP8ahey4NTVmtZKduoYJVtKoE47GaGQunq7avehwU1xCXo" +
            "b444jIKEhdr9EXOSXAa4gZQRJt6jPRBNM2NyA8LutsgaFQPC6GnsWdsV_8SGU";
    @Test
    public void test() throws Throwable {
        Authenticator f = AuthenticatorFactory.generateByPublicKey(publicKey);
          Principal p =f.verifyToken(token);
          System.out.println(p.getExpires());
          System.out.println(p.getUsername());
          System.out.println(p.get("exp"));
    }
}
