package com.zyproject.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: zyproject
 * @description: jwt公共类
 * @author: zhouyu(zhouyu629 # qq.com)
 * @create: 2020-03-04
 **/
public class JwtUtil {
    static final long EXPTIME = 3600_000_000L; //超时时间
    static final String SECRET = "Abc@1234"; //示例代码，默认密钥

    /**
     * 生成token
     * @param user_name
     * @return
     */
    public static String generatorToken(String user_name){
        HashMap<String,Object> map = new HashMap<>();
        map.put("user_name",user_name);
        String jwt = Jwts.builder()
                .setClaims(map)
                .setExpiration(new Date(System.currentTimeMillis()+EXPTIME))
                .signWith(SignatureAlgorithm.HS512,SECRET)
                .compact();
        return "Bearer  "+ jwt;
    }

    /**
     * token校验
     * @param token
     */
    public static void validateToken(String token){
        try{
            Map<String,Object> body = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token.replace("Bearer ",""))
                    .getBody();
        }catch (Exception e){
            throw new IllegalStateException("Invalid Token."+e.getMessage());
        }

    }
}
