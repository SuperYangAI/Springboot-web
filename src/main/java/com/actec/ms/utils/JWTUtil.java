package com.actec.ms.utils;

import io.jsonwebtoken.*;
import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JWTUtil {
    private static final String JWT_SECRET = "hello_all";
    private static Logger logger = LoggerFactory.getLogger(JWTUtil.class);

    /**
     * 创建jwt
     * @param id
     * @param ttlMillis 过期的时间长度
     * @return
     * @throws Exception
     */
    public static String createJWT(String id, Map<String, Object> claims, long ttlMillis) throws Exception {
        /**
         * 指定签名的时候使用的签名算法，也就是header那部分，jwt已经将这部分内容封装好了。
         */
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        long nowMillis = System.currentTimeMillis();//生成JWT的时间
        logger.info("create token timestamp:" + nowMillis);
        Date now = new Date(nowMillis);

        /**
         * 生成签名的时候使用的秘钥secret,这个方法本地封装
         *  一般可以从本地配置文件中读取，切记这个秘钥不能外露
         *  它就是你服务端的私钥，在任何场景都不应该流露出去
         *  一旦客户端得知这个secret, 那就意味着客户端是可以自我签发jwt
         * */
        SecretKey key = generalKey();
        //为payload添加各种标准声明和私有声明
        JwtBuilder builder = Jwts.builder() //new一个JwtBuilder，设置jwt的body
                .setClaims(claims)
                .setId(id)              //设置jti(JWT ID)：是JWT的唯一标识，根据业务需要，这个可以设置为一个不重复的值，主要用来作为一次性token,从而回避重放攻击。
                .setIssuedAt(now)       //iat: jwt的签发时间
                //.setSubject(subject)  //sub(Subject)：代表这个JWT的主体，即它的所有人，这个是一个json格式的字符串，可以存放什么userid，roldid之类的，作为什么用户的唯一标志。
                .signWith(signatureAlgorithm, key);//设置签名使用的签名算法和签名使用的秘钥
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }
        return builder.compact();
    }

    /**
     * 解密jwt
     *
     * @param jwt
     * @return
     * @throws Exception
     */
    public static Claims parseJWT(String jwt) throws Exception {
        SecretKey key = generalKey();
        Claims claims = Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(jwt).getBody();
        return claims;
    }

    /**
     * 由字符串生成加密key
     *
     * @return
     */
    private static SecretKey generalKey() {
        byte[] encodedKey = Base64.decodeBase64(JWT_SECRET);//本地的密码解码
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return key;
    }

    public static void main(String[] args) throws Exception {
        Map<String, Object> claims = new HashMap<>();
        claims.put("user", "admin");
        claims.put("role", "manager");
        String jwtStr = createJWT("jwt", claims, 60000);
        Claims c = parseJWT(jwtStr);//注意：如果jwt已经过期了，这里会抛出jwt过期异常。
        System.out.println(c);
    }
}
