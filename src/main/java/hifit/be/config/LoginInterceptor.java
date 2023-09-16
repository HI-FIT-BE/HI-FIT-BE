package hifit.be.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {


        HttpSession session = request.getSession(false);

        if (request.getMethod().equals("OPTIONS")) {
            return true;
        }
        if (session == null) {
            throw new IllegalArgumentException("[ERROR] 로그인이 필요합니다.");
        }
        if (session.getAttribute("userId") == null) {
            throw new IllegalArgumentException("[ERROR] 로그인이 필요합니다.");
        }

        Long userId = (Long) session.getAttribute("userId");
        request.setAttribute("userId", userId);
        return true;
    }
}
