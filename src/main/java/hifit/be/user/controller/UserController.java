package hifit.be.user.controller;

import hifit.be.user.dto.request.*;
import hifit.be.user.dto.response.*;
import hifit.be.user.dto.token.KakaoOauthInfo;
import hifit.be.user.dto.token.OauthToken;
import hifit.be.user.service.OauthLoginService;
import hifit.be.user.service.UserService;
import hifit.be.util.CustomResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Date;
import java.util.List;

@Tag(name = "User", description = "사용자 API")
@RestController
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final OauthLoginService oauthLoginService;
    private final UserService userService;
    private final KakaoOauthInfo kakaoOauthInfo;

    @PostMapping("/users/oauth/login")
    public ResponseEntity<CustomResponse<JwtResponse>> kakaoLogin(@RequestBody LoginCodeRequest code) {
        KakaoLoginResponse kakaoLoggedInUser = oauthLoginService.processKakaoLogin(code.getCode(), kakaoOauthInfo.getLoginUri());

        long userId = userService.processLogin(kakaoLoggedInUser);

        Date expiredDate = new Date(new Date().getTime() + 3600000);

        return ResponseEntity
                .ok()
                .body(new CustomResponse<>(
                        "success",
                        200,
                        "카카오 로그인 성공",
                        userService.userToJwtToken(userId, expiredDate)));
    }

    @GetMapping("/users/workoutInfo")
    public ResponseEntity<CustomResponse<UserWorkoutInfo>> getWorkoutInfo(@RequestAttribute Long userId) {

        UserWorkoutInfo userWorkoutInfo = userService.findUserWorkoutInfo(userId);

        return ResponseEntity
                .ok()
                .body(new CustomResponse<>(
                        "success",
                        200,
                        "유저 운동 현황 정보 조회 성공",
                        userWorkoutInfo));
    }

    @GetMapping("/users/bodyInfo")
    public ResponseEntity<CustomResponse<UserBodyInfo>> getBodyInfo(@RequestAttribute Long userId) {

        UserBodyInfo userBodyInfo = userService.findBodyInfo(userId);

        return ResponseEntity
                .ok()
                .body(new CustomResponse<>(
                        "success",
                        200,
                        "유저 체중, BMI 정보 조회 (현재, 목표) 성공",
                        userBodyInfo));
    }

    @GetMapping("/users/surveyInfo")
    public ResponseEntity<CustomResponse<UserSurveyInfo>> getSurveyInfo(@RequestAttribute Long userId) {

        UserSurveyInfo userSurveyInfo = userService.findSurveyInfo(userId);

        return ResponseEntity
                .ok()
                .body(new CustomResponse<>(
                        "success",
                        200,
                        "설문지에 필요한 유저 정보 조회 성공",
                        userSurveyInfo));
    }

    @GetMapping("/users/healthStatusInfo")
    public ResponseEntity<CustomResponse<UserHealthStatusInfo>> getHealthStatusInfo(@RequestAttribute Long userId) {

        UserHealthStatusInfo userHealthStatusInfo = userService.findHealthStatusInfo(userId);

        return ResponseEntity
                .ok()
                .body(new CustomResponse<>(
                        "success",
                        200,
                        "유저 설문 결과 조회 성공",
                        userHealthStatusInfo));
    }

    @PatchMapping("/users/stamps")
    public ResponseEntity<CustomResponse> addStamp(@RequestAttribute Long userId) {

        userService.addStamp(userId);

        return ResponseEntity
                .ok()
                .body(new CustomResponse<>(
                        "success",
                        200,
                        "유저 스탬프 적립 성공",
                        null));
    }

    @PatchMapping("/users/healthInfo")
    public ResponseEntity<CustomResponse> updateHealthInfo(@RequestAttribute Long userId, @RequestBody HealthInfoRequest healthInfo) {

        userService.updateHealthInfo(userId, healthInfo);

        return ResponseEntity
                .ok()
                .body(new CustomResponse<>(
                        "success",
                        200,
                        "유저 건강 정보 업데이트 성공",
                        null));
    }

    @PatchMapping("/users/sarcopenia")
    public ResponseEntity<CustomResponse> updateSarcopenia(@RequestAttribute Long userId, @RequestBody SarcopeniaRequest sarcopeniaRequest) {

        userService.updateSarcopenia(userId, sarcopeniaRequest.getSarcopenia());

        return ResponseEntity
                .ok()
                .body(new CustomResponse<>(
                        "success",
                        200,
                        "유저 근감소증 상태 업데이트 성공",
                        null));
    }

    @GetMapping("/users/exercises")
    public ResponseEntity<CustomResponse<List<ExerciseResponse>>> getExercises(@RequestAttribute Long userId) {
        log.info("userId : {}", userId);

        List<ExerciseResponse> exercises = userService.findExercises(userId);

        return ResponseEntity
                .ok()
                .body(new CustomResponse<>(
                        "success",
                        200,
                        "유저 운동 기록 조회 성공",
                        exercises));
    }

    @GetMapping("/users/diet")
    public ResponseEntity<CustomResponse<DietResponse>> getDiet(@RequestAttribute Long userId) {

        DietResponse diet = userService.findDiet(userId);

        return ResponseEntity
                .ok()
                .body(new CustomResponse<>(
                        "success",
                        200,
                        "유저 식단 조회 성공",
                        diet));
    }
}
