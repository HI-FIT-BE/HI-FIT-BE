package hifit.be.config;

import hifit.be.user.dto.response.LoggedInUser;
import hifit.be.user.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
@Component
@RequiredArgsConstructor
public class LoginInterceptor implements HandlerInterceptor {

    private final JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (request.getHeader("Authorization") == null) {
            log.info("Authorization header is null");
            throw new IllegalArgumentException("Authorization header is null");
        }

        if (request.getHeader("Authorization").split(" ").length != 2) {
            log.info("Authorization header is not Bearer type");
            throw new IllegalArgumentException("Authorization header is not Bearer type");
        }

        String token = request.getHeader("Authorization").split(" ")[1];
        if (!request.getHeader("Authorization").split(" ")[0].equals("Bearer")) {
            throw new IllegalArgumentException("Authorization header is not Bearer type");
        }

        if (!jwtUtil.validateTokenIsExpired(token)) {
            throw new IllegalArgumentException("Token is expired");
        }

        if (!jwtUtil.validateTokenIsManipulated(token)) {
            throw new IllegalArgumentException("Token is manipulated");
        }

        LoggedInUser loggedInUser = jwtUtil.extractedUserFromToken(token);
        request.setAttribute("userId", loggedInUser.getId());

        return true;
    }
}
