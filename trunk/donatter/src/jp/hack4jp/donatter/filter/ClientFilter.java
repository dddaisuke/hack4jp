package jp.hack4jp.donatter.filter;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import jp.hack4jp.donatter.meta.ClientAuthMeta;
import jp.hack4jp.donatter.model.ClientAuth;

import org.slim3.datastore.Datastore;

public class ClientFilter implements Filter {

    private static final Logger LOG =
        Logger.getLogger(ClientFilter.class.getName());

    public void init(FilterConfig arg0) throws ServletException {

    }

    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain filterChain) throws IOException, ServletException {

        String clientKey = request.getParameter("clientKey");
        LOG.info("clientKey : " + clientKey);

        ClientAuth ca =
            Datastore.query(ClientAuth.class).filter(
                ClientAuthMeta.get().authToken.equal(clientKey)).asSingle();

        LOG.info(((HttpServletRequest) request).getRequestURL()
            + "："
            + new java.util.Date());

        if (ca != null) {
            filterChain.doFilter(request, response);
        } else {
            response.getWriter().write("invalid!");
            response.getWriter().flush();
        }

    }

    public void destroy() {
    }

}
