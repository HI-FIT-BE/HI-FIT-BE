package hifit.be.user.controller;

import hifit.be.user.dto.request.LoginCodeRequest;
import hifit.be.user.dto.response.KakaoLoginResponse;
import hifit.be.user.dto.token.KakaoOauthInfo;
import hifit.be.user.dto.token.OauthToken;
import hifit.be.user.exception.InvalidLoginCodeException;
import hifit.be.user.service.OauthLoginService;
import hifit.be.user.service.UserService;
import hifit.be.util.CustomResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.http.HttpSession;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final OauthLoginService oauthLoginService;
    private final UserService userService;
    private final KakaoOauthInfo kakaoOauthInfo;


    @PostMapping("/users/oauth/login")
    public ResponseEntity<CustomResponse> kakaoLogin(HttpSession session, @RequestBody LoginCodeRequest code) {

        OauthToken kakaoAccessToken;

        try {
            kakaoAccessToken = oauthLoginService.getOauthToken(code.getCode(), kakaoOauthInfo);
        } catch (HttpClientErrorException e) {
            throw new InvalidLoginCodeException("[ERROR] 로그인 코드가 유효하지 않습니다.");
        }

        KakaoLoginResponse kakaoLoggedInUser = oauthLoginService.processKakaoLogin(kakaoAccessToken.getAccessToken(), kakaoOauthInfo.getLoginUri());

        System.out.println(kakaoLoggedInUser.getId() );

        return ResponseEntity
                .ok()
                .body(new CustomResponse<>(
                        "success",
                        200,
                        "카카오 로그인 성공",
                        null));
    }

    @PostMapping("/users/logout")
    public ResponseEntity<CustomResponse> upbrellaLogout(HttpSession session) {

        session.invalidate();

        return ResponseEntity
                .ok()
                .body(new CustomResponse<>(
                        "success",
                        200,
                        "로그아웃 성공",
                        null));
    }

}
