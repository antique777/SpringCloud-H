package org.javaboy.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class PermissFilter extends ZuulFilter {

    /**
     * 过滤器 类型，权限判断一般是pre
     *
     * @return java.lang.String
     * @author xab
     * @date 2023/8/11 18:59
     */
    @Override
    public String filterType() {
        return "pre";
    }

    /**
     * 过滤器的优先级
     *
     * @return int
     * @author xab
     * @date 2023/8/11 18:59
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * 是否过滤，  就是开关的意思
     * 正常判断请求地址去决定走不走这个过滤
     *
     * @return boolean
     * @author xab
     * @date 2023/8/11 18:59
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 核心的过滤逻辑写在这里
     * 虽然有返回值，但是这个返回值目前是无所谓的（没有用）
     *
     * @return java.lang.Object
     * @author xab
     * @date 2023/8/11 19:00
     */
    @Override
    public Object run() throws ZuulException {
        RequestContext currentContext = RequestContext.getCurrentContext();
        //获取当前请求
        HttpServletRequest request = currentContext.getRequest();
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        if (!"javaboy".equals(username) || !"123".equals(password)){
            //如果请求调节不满足，在这里响应
            currentContext.setSendZuulResponse(false);
            currentContext.setResponseStatusCode(401);
            currentContext.addZuulRequestHeader("content-type","text/html;charset=utf-8");
            currentContext.setResponseBody("非法访问");
        }

        return null;
    }
}
