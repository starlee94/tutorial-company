package com.tca.core.config;
import lombok.Getter;
import lombok.Setter;

import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Getter
@Setter
public class ConfigProperties {

    /**
     * @see GlobalLogFilter#excludeUris
     */
    private List<String> logFilterExcludeUris;

    /**
     * @see CrossDomainFilter#doFilter(ServletRequest, ServletResponse, FilterChain)
     * @see GlobalErrorController#handleError(HttpServletRequest, HttpServletResponse)
     */
    private String allowOrigin;


    /**
     * @see CrossDomainFilter#doFilter(ServletRequest, ServletResponse, FilterChain)
     * @see GlobalErrorController#handleError(HttpServletRequest, HttpServletResponse)
     */
    private String allowMethods;


    /**
     * @see CrossDomainFilter#doFilter(ServletRequest, ServletResponse, FilterChain)
     * @see GlobalErrorController#handleError(HttpServletRequest, HttpServletResponse)
     */
    private String allowHeaders;

}
