package pl.mjanik.github.config;

import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import pl.mjanik.github.base.Constants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

@Component
class ValidateRequestInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) {
        String username = request.getParameter(Constants.USERNAME_PARAM);

        if (Objects.isNull(username) || Strings.isEmpty(username)) {
            throw new ValidateException();
        }
        return true;
    }
}
