package com.tca.core.config.filter;

import com.tca.core.config.holder.RequestHolder;
import com.tca.core.service.CommonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class AuthSignatureFilter implements Filter, Ordered {

    /**
     * 放开的uri
     */
    private List<String> excludUris = new ArrayList<String>() {{

    }};

    @Autowired
    private CommonService commonService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        try {
            RequestHolder.init(request);
            HttpServletResponse response = (HttpServletResponse) servletResponse;
            String uri = request.getRequestURI();
            //登陆和退出放开
            log.info("Request url: {}", uri);
            filterChain.doFilter(servletRequest, servletResponse);
//            log.info("Verify Token result: {}", commonService.verifyToken("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjZW8xIiwiaWF0IjoxNzAwNzI1NDk2LCJleHAiOjE3MDA3MzI2OTZ9.K_YAHAKjPlXkB_1IiC_l9hdC8vwvYMhNbU8RSGZ1BqI").isPresent());

            // 如果是服务间的调用，那么直接放开
//            String loginSign = request.getHeader(ProjectConfigConstant.INNER_REQ_HEADER);
//            if (StringUtils.isNotEmpty(loginSign)) {
//                long reqTime = InnerFeignSigner.getReqTime(loginSign);
//                long now = System.currentTimeMillis();
//                long loginTime = now - reqTime;
//                if (loginTime > 1000 * 60 * 10) {
//                    logger.error("连接超时！loginSign: {}, reqTime: {}, now: {}, 当前连接所用毫秒数: {}",
//                            loginSign, reqTime, now, loginTime);
//                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
//                    return;
//                }
//                filterChain.doFilter(servletRequest, servletResponse);
//                return;
//            }
//            String token = request.getHeader(HttpHeaders.AUTHORIZATION);
//            if (StringUtils.isEmpty(token)) {
//                logger.info("【Token为空,请登陆】");
//                response.setStatus(HttpStatus.UNAUTHORIZED.value());
//                return;
//            }
//            token = token.split("[ ]")[1];
//            Boolean isToken;
//            try {
//                Map<String, ?> map = authCheckService.checkToken(token);
//                if (map != null) {
//                    //获取token的resourceId
//                    Collection<String> resourceIds = (Collection<String>) map.get("aud");
//                    if (uri.startsWith(URL_PREFIX)) {
//                        if (resourceIds.contains(client2ResourceId)) {
//                            Boolean isPermission;
//                            try {
//                                logger.info("MyAccount -> checkPermission");
//                                isPermission = authCheckService.checkMyaccountPermission();
//                            } catch (Exception ex) {
//                                logger.error("调用checkPermission时出现异常!", ex);
//                                response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
//                                return;
//                            }
//                            logger.info("checkPermission验证权限结果:{}", isPermission);
//                            if (isPermission) {
//                                filterChain.doFilter(servletRequest, servletResponse);
//                                return;
//                            }
//                            response.setStatus(HttpStatus.FORBIDDEN.value());
//                            return;
//                        } else {
//                            isToken = false;
//                        }
//                    } else if (uri.startsWith(URL_PREFIX_MOBILE)){
//                        if (resourceIds.contains(client6ResourceId)) {
//                            Boolean isPermission;
//                            try {
//                                logger.info("mobile -> checkPermission");
//                                isPermission = authCheckService.checkMobilePermission();
//                            } catch (Exception ex) {
//                                logger.error("调用checkPermission时出现异常!", ex);
//                                response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
//                                return;
//                            }
//                            logger.info("checkPermission验证权限结果:{}", isPermission);
//                            if (isPermission) {
//                                filterChain.doFilter(servletRequest, servletResponse);
//                                return;
//                            }
//                            response.setStatus(HttpStatus.FORBIDDEN.value());
//                            return;
//                        } else {
//                            isToken = false;
//                        }
//                    } else if (uri.startsWith(URL_PREFIX_MERCHANT)) {
//                        if (resourceIds.contains(client3ResourceId)) {
//                            Boolean isPermission;
//                            try {
//                                logger.info("Merchant -> checkPermission");
//                                isPermission = authCheckService.checkMerchantPermission();
//                            } catch (Exception ex) {
//                                logger.error("调用checkPermission时出现异常!", ex);
//                                response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
//                                return;
//                            }
//                            logger.info("checkPermission验证权限结果:{}", isPermission);
//                            if (isPermission) {
//                                filterChain.doFilter(servletRequest, servletResponse);
//                                return;
//                            }
//                            response.setStatus(HttpStatus.FORBIDDEN.value());
//                            return;
//                        } else {
//                            isToken = false;
//                        }
//                    } else if (uri.startsWith(URL_PREFIX_MARKET)) {
//                        if (resourceIds.contains(client5ResourceId)) {
//                            Boolean isPermission;
//                            try {
//                                logger.info("Market -> checkPermission");
//                                isPermission = authCheckService.checkMarketPermission();
//                            } catch (Exception ex) {
//                                logger.error("Market -> checkPermission 调用checkPermission时出现异常!", ex);
//                                response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
//                                return;
//                            }
//                            logger.info("checkPermission验证权限结果:{}", isPermission);
//                            if (isPermission) {
//                                filterChain.doFilter(servletRequest, servletResponse);
//                                return;
//                            }
//                            response.setStatus(HttpStatus.FORBIDDEN.value());
//                            return;
//                        } else {
//                            isToken = false;
//                        }
//                    } else {
//                        if (resourceIds.contains(client1ResourceId)) {
//                            Boolean isPermission;
//                            try {
//                                logger.info("CRM -> checkPermission");
//                                isPermission = authCheckService.checkPermission();
//                            } catch (Exception ex) {
//                                logger.error("调用checkPermission时出现异常!", ex);
//                                response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
//                                return;
//                            }
//                            logger.info("checkPermission验证权限结果:{}", isPermission);
//                            if (isPermission) {
//                                filterChain.doFilter(servletRequest, servletResponse);
//                                return;
//                            }
//                            response.setStatus(HttpStatus.FORBIDDEN.value());
//                            return;
//                        } else {
//                            isToken = false;
//                        }
//                    }
//                } else {
//                    logger.info("验证token出现未知异常！checkToken检查结果为null");
//                    isToken = false;
//                }
//            } catch (FeignException ex) {
//                logger.error("验证token出现FeignException！", ex);
//                if (ex.status() == HttpStatus.UNAUTHORIZED.value()) {
//                    isToken = false;
//                } else {
//                    throw ex;
//                }
//            } catch (Exception ex) {
//                logger.error("验证token出现未知异常！", ex);
//                throw ex;
//            }
//            logger.info("checkToken验证token结果:{}", isToken);
//            if (isToken != null && !isToken) {
//                response.setStatus(HttpStatus.UNAUTHORIZED.value());
//                return;
//            }
//            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            RequestHolder.clean();
        }
    }

    @Override
    public void destroy() {

    }

    @Override
    public int getOrder() {
        return 0;
    }
}
