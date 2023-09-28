package hifit.be.config;

import hifit.be.user.dto.response.LoggedInUser;
import hifit.be.user.exception.AuthorizationNullException;
import hifit.be.user.exception.NotBearerException;
import hifit.be.user.exception.TokenExpiredException;
import hifit.be.user.exception.TokenManipulatedException;
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
            throw new AuthorizationNullException("Authorization header is null");
        }

        if (request.getHeader("Authorization").split(" ").length != 2) {
            log.info("Authorization header is not Bearer type");
            throw new NotBearerException("Authorization header is not Bearer type");
        }

        String token = request.getHeader("Authorization").split(" ")[1];
        if (!request.getHeader("Authorization").split(" ")[0].equals("Bearer")) {
            throw new NotBearerException("Authorization header is not Bearer type");
        }

        if (!jwtUtil.validateTokenIsExpired(token)) {
            throw new TokenExpiredException("Token is expired");
        }

        if (!jwtUtil.validateTokenIsManipulated(token)) {
            throw new TokenManipulatedException("Token is manipulated");
        }

        LoggedInUser loggedInUser = jwtUtil.extractedUserFromToken(token);
        request.setAttribute("userId", loggedInUser.getId());

        return true;
    }
}
