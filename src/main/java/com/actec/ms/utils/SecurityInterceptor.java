package com.actec.ms.utils;

import com.actec.ms.dao.sys.ResourceDao;
import com.actec.ms.vo.Authority;
import com.actec.ms.vo.AuthorityType;
import com.actec.ms.vo.RestResult;
import com.actec.ms.vo.sys.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

@Component
public class SecurityInterceptor extends HandlerInterceptorAdapter {

    private static Logger logger = LoggerFactory.getLogger(SecurityInterceptor.class);
    private static ObjectMapper mapper = new ObjectMapper();

    private static ResourceDao resourceDao = ApplicationContextHelper.getBean(ResourceDao.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        logger.info("preHandler url :" + request.getRequestURI() + ", method:" + request.getMethod());
        boolean validate = false;
        Authority authority = validateAuthType(handler);
        if(authority != null && authority.value() == AuthorityType.NO_VALIDATE) {
            validate = true;
            return validate;
        } else {
            String auth = request.getHeader("auth");
            validate = validateClaims(request, auth, authority);
            if(validate)
                return true;
        }

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");

        RestResult resultMsg = RestResult.buildResult(RestResult.Status.UNAUTHORIZED, null);
        response.getWriter().write(mapper.writeValueAsString(resultMsg));
        return false;
    }

    private Authority validateAuthType(Object handler) {
        Authority authority = null;
        HandlerMethod hm = (HandlerMethod) handler;
        Class<?> clazz = hm.getBeanType();
        Method m = hm.getMethod();
        try {
            if (clazz != null && m != null) {
                boolean isClzAnnotation = clazz.isAnnotationPresent(Authority.class);
                boolean isMethodAnnotation = m.isAnnotationPresent(Authority.class);
                if (isMethodAnnotation) {
                    authority = m.getAnnotation(Authority.class);
                } else if (isClzAnnotation) {
                    authority = clazz.getAnnotation(Authority.class);
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return authority;
    }

    private boolean validateClaims(HttpServletRequest request, String auth, Authority authority) {
        if (!StringUtils.isEmpty(auth)) {
            Claims claims = null;
            try {
                claims = JWTUtil.parseJWT(auth);
                if (claims != null) {
                    int userId = Integer.parseInt(claims.get("userId").toString());
                    if(User.isAdmin(userId))
                        return true;
                    if(AuthorityType.requireAdmin(authority) && !User.isAdmin(userId))
                        return false;
                    else {
                        String url = trimUrl(request.getRequestURI());
                        Integer resourceId = resourceDao.findIdByUrl(url, request.getMethod());
                        if(resourceId == null || resourceId < 1)
                            return false;
                        int count =  resourceDao.userResourceCount(userId, resourceId);
                        if(count > 1) {
                            resourceDao.deleteAuth(userId, resourceId);
                            return false;
                        } else if(count == 1) {
                            return true;
                        }
                    }
                }
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        return false;
    }

    private String trimUrl(String originUrl) {
        int digitalIndex = StringUtil.digitalIndex(originUrl);
        if(digitalIndex > 0) {
            return originUrl.substring(0, digitalIndex - 1);
        }
        return originUrl;
    }

    private String getRemortIpByRequest(HttpServletRequest request) {
        String ip = null;
        if (request.getHeader("x-forwarded-for") == null) {
            ip = request.getRemoteAddr();
        }else {
            ip = request.getHeader("x-forwarded-for");
        }
        return ip.equals("0:0:0:0:0:0:0:1") ? "127.0.0.1" : ip;
    }

}