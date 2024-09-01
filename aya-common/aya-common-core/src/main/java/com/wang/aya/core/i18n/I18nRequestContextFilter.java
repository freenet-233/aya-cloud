package com.wang.aya.core.i18n;


import com.wang.aya.core.util.I18nUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Setter;
import org.springframework.boot.web.servlet.filter.OrderedRequestContextFilter;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.IOException;

/**
 * 上下文请求拦截器（国际化）
 */
@Setter
public final class I18nRequestContextFilter extends OrderedRequestContextFilter {

    public static final String LANG = "lang";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        ServletRequestAttributes attributes = new ServletRequestAttributes(request, response);
        initContextHolders(request, attributes);
        try {
            filterChain.doFilter(request, response);
        }
        finally {
            resetContextHolders();
            if (logger.isTraceEnabled()) {
                logger.trace("Cleared thread-bound request context: " + request);
            }
            attributes.requestCompleted();
        }
    }

    /**
     * 往国际化本地线程变量写入初始化上下文请求的值.
     * @param request 请求对象
     * @param requestAttributes 请求属性
     */
    private void initContextHolders(HttpServletRequest request, ServletRequestAttributes requestAttributes) {
        I18nUtil.set(request);
        RequestContextHolder.setRequestAttributes(requestAttributes, true);
        if (logger.isTraceEnabled()) {
            logger.trace("Bound request context to thread: " + request);
        }
    }

    /**
     * 注销国际化本地线程变量.
     */
    private void resetContextHolders() {
        I18nUtil.reset();
        RequestContextHolder.resetRequestAttributes();
    }
}
