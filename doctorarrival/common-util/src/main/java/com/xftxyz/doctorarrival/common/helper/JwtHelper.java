package com.xftxyz.doctorarrival.common.helper;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

public class JwtHelper {
    // 用户id
    public static final String X_USER_ID = "x-user-id";

    // 过期时间
    private static final String SECRET_KEY = "doctor-arrival-secret-key";
    // 密钥
    private static final long EXPIRE_TIME = 7 * 24 * 60 * 60 * 1000; // 7天

    // 生成token
    public static String generateToken(Long userId) {
        return JWT.create().withClaim(X_USER_ID, userId)
                .withExpiresAt(new java.util.Date(System.currentTimeMillis() + EXPIRE_TIME))
                .sign(Algorithm.HMAC256(SECRET_KEY));
    }

    // 解析token
    public static Long parseToken(String token) {
        return JWT.require(Algorithm.HMAC256(SECRET_KEY)).build().verify(token).getClaim(X_USER_ID).asLong();
    }

}
