package hifit.be.user.controller;

import hifit.be.user.dto.request.*;
import hifit.be.user.dto.response.*;
import hifit.be.user.dto.token.KakaoOauthInfo;
import hifit.be.user.dto.token.OauthToken;
import hifit.be.user.entity.Gender;
import hifit.be.user.entity.Sarcopenia;
import hifit.be.user.entity.User;
import hifit.be.user.exception.InvalidLoginCodeException;
import hifit.be.user.service.OauthLoginService;
import hifit.be.user.service.UserService;
import hifit.be.util.CustomResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.http.HttpSession;

@Tag(name = "User", description = "사용자 API")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final OauthLoginService oauthLoginService;
    private final UserService userService;
    private final KakaoOauthInfo kakaoOauthInfo;
    private User mockUser = User.builder()
            .id(1L)
            .socialId(123456789L)
            .name("mockUser")
            .age(43)
            .gender(Gender.MALE)
            .phoneNumber("010-1234-5678")
            .stamp(0)
            .build();


    @PostMapping("/users/oauth/login")
    public ResponseEntity<CustomResponse> kakaoLogin(HttpSession session, @RequestBody LoginCodeRequest code) {

        OauthToken kakaoAccessToken;

        try {
            kakaoAccessToken = oauthLoginService.getOauthToken(code.getCode(), kakaoOauthInfo);
        } catch (HttpClientErrorException e) {
            throw new InvalidLoginCodeException("[ERROR] 로그인 코드가 유효하지 않습니다.");
        }

        KakaoLoginResponse kakaoLoggedInUser = oauthLoginService.processKakaoLogin(kakaoAccessToken.getAccessToken(), kakaoOauthInfo.getLoginUri());

        // TODO : DB에서 없으면 회원가입, 있으면 로그인
        // TODO : 세션 설정

        return ResponseEntity
                .ok()
                .body(new CustomResponse<>(
                        "success",
                        200,
                        "카카오 로그인 성공",
                        null));
    }

    @PostMapping("/users/logout")
    public ResponseEntity<CustomResponse> logout(HttpSession session) {

        session.invalidate();

        return ResponseEntity
                .ok()
                .body(new CustomResponse<>(
                        "success",
                        200,
                        "로그아웃 성공",
                        null));
    }

    @GetMapping("/users/workoutInfo")
    public ResponseEntity<CustomResponse<UserWorkoutInfo>> getWorkoutInfo(HttpSession session) {

        //TODO : 세션에서 조회하도록 수정
        UserWorkoutInfo userWorkoutInfo = userService.findUserWorkoutInfo(mockUser.getId());

        return ResponseEntity
                .ok()
                .body(new CustomResponse<>(
                        "success",
                        200,
                        "유저 운동 현황 정보 조회 성공",
                        userWorkoutInfo));
    }

    @GetMapping("/users/bodyInfo")
    public ResponseEntity<CustomResponse<UserBodyInfo>> getBodyInfo(HttpSession session) {

        //TODO : 세션에서 조회하도록 수정
        UserBodyInfo userBodyInfo = userService.findBodyInfo(mockUser.getId());

        return ResponseEntity
                .ok()
                .body(new CustomResponse<>(
                        "success",
                        200,
                        "유저 체중, BMI 정보 조회 (현재, 목표) 성공",
                        userBodyInfo));
    }

    @GetMapping("/users/surveyInfo")
    public ResponseEntity<CustomResponse<UserSurveyInfo>> getSurveyInfo(HttpSession session) {

        //TODO : 세션에서 조회하도록 수정
        UserSurveyInfo userSurveyInfo = userService.findSurveyInfo(mockUser.getId());

        return ResponseEntity
                .ok()
                .body(new CustomResponse<>(
                        "success",
                        200,
                        "설문지에 필요한 유저 정보 조회 성공",
                        userSurveyInfo));
    }

    @GetMapping("/users/healthStatusInfo")
    public ResponseEntity<CustomResponse<UserHealthStatusInfo>> getHealthStatusInfo(HttpSession session) {

        //TODO : 세션에서 조회하도록 수정
        UserHealthStatusInfo userHealthStatusInfo = userService.findHealthStatusInfo(mockUser.getId());

        return ResponseEntity
                .ok()
                .body(new CustomResponse<>(
                        "success",
                        200,
                        "유저 설문 결과 조회 성공",
                        userHealthStatusInfo));
    }

    @PatchMapping("/users/stamps")
    public ResponseEntity<CustomResponse> addStamp(HttpSession session) {

        //TODO : 세션에서 조회하도록 수정
        userService.addStamp(mockUser.getId());

        return ResponseEntity
                .ok()
                .body(new CustomResponse<>(
                        "success",
                        200,
                        "유저 스탬프 적립 성공",
                        null));
    }

    @PatchMapping("/users/healthInfo")
    public ResponseEntity<CustomResponse> updateHealthInfo(HttpSession session, @RequestBody HealthInfoRequest healthInfo) {

        //TODO: 세션에서 조회하도록 수정
        long id = 1L;
        userService.updateHealthInfo(id, healthInfo);

        return ResponseEntity
                .ok()
                .body(new CustomResponse<>(
                        "success",
                        200,
                        "유저 건강 정보 업데이트 성공",
                        null));
    }

    @PatchMapping("/users/sarcopenia")
    public ResponseEntity<CustomResponse> updateSarcopenia(HttpSession session, @RequestBody SarcopeniaRequest sarcopeniaRequest) {

        //TODO : 세션에서 조회하도록 수정
        userService.updateSarcopenia(mockUser.getId(), sarcopeniaRequest.getSarcopenia());

        return ResponseEntity
                .ok()
                .body(new CustomResponse<>(
                        "success",
                        200,
                        "유저 근감소증 상태 업데이트 성공",
                        null));
    }
}
