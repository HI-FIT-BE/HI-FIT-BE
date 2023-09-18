package hifit.be.user.service;

import hifit.be.user.dto.request.HealthInfoRequest;
import hifit.be.user.dto.response.*;
import hifit.be.user.entity.HealthInformation;
import hifit.be.user.entity.Sarcopenia;
import hifit.be.user.entity.User;
import hifit.be.user.entity.WorkoutSession;
import hifit.be.user.jwt.JwtUtil;
import hifit.be.user.repository.HealthInformationRepository;
import hifit.be.user.repository.UserRepository;
import hifit.be.user.repository.WorkoutSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final HealthInformationRepository healthInformationRepository;
    private final WorkoutSessionRepository workoutSessionRepository;
    private final JwtUtil jwtUtil;

    public User findBySocialId(Long socialId) {

        return userRepository.findBySocialId(socialId)
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 존재하지 않는 회원입니다. 회원 가입을 해주세요."));
    }

    public User findById(Long id) {

        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 존재하지 않는 회원입니다. 회원 가입을 해주세요."));
    }

    public HealthInformation findByUserIdFromHealthInfo(Long id) {

        return healthInformationRepository.findByUserId(id)
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 존재하지 않는 회원입니다. 회원 가입을 해주세요."));
    }

    public boolean existsBySocialId(Long socialId) {

        return userRepository.existsBySocialId(socialId);
    }

    public UserWorkoutInfo findUserWorkoutInfo(Long id) {

        int workoutCount = workoutSessionRepository.countWorkoutSessionsThisMonthByUserId(id);
        return UserWorkoutInfo.of(findById(id), workoutCount);
    }

    public UserBodyInfo findBodyInfo(Long id) {

        double targetBmi = 23.0;

        HealthInformation healthInfo = findByUserIdFromHealthInfo(id);
        // TODO: null 처리
        Double currentWeight = healthInfo.getWeight();

        if (currentWeight == null) {
            return UserBodyInfo.of(null, null, null, null);
        }

        Double currentHeight = healthInfo.getHeight();

        Double currentBmi = calculateBMI(currentHeight, currentWeight);
        Double targetWeight = calculateTargetWeight(currentHeight, 23.0);

        return UserBodyInfo.of(currentBmi, currentWeight, targetBmi, targetWeight);
    }

    @Transactional
    public UserSurveyInfo findSurveyInfo(Long id) {

        HealthInformation healthInfo = findByUserIdFromHealthInfo(id);

        return UserSurveyInfo.of(healthInfo);
    }

    @Transactional
    public UserHealthStatusInfo findHealthStatusInfo(Long id) {

        HealthInformation healthInfo = findByUserIdFromHealthInfo(id);
        return UserHealthStatusInfo.of(healthInfo.getUser(), healthInfo);
    }

    @Transactional
    public void addStamp(Long id) {

        WorkoutSession workout = WorkoutSession.builder()
                .user(findById(id))
                .workoutDate(LocalDate.now())
                .build();

        workoutSessionRepository.findWorkoutSessionByUserIdAndDate(id, LocalDate.now())
                .ifPresentOrElse(
                        workoutSession -> {
                            throw new IllegalArgumentException("[ERROR] 이미 운동을 하셨습니다.");
                        },
                        () -> workoutSessionRepository.save(workout)
                );
    }

    @Transactional
    public void updateHealthInfo(Long id, HealthInfoRequest healthInfoRequest) {

        HealthInformation healthInfo = findByUserIdFromHealthInfo(id);
        healthInfo.updateHealthInfo(healthInfoRequest);
    }

    @Transactional
    public void updateSarcopenia(Long id, Sarcopenia sarcopenia) {

        HealthInformation healthInfo = findByUserIdFromHealthInfo(id);
        healthInfo.updateSarcopenia(sarcopenia);
    }

    public double calculateBMI(double height, double weight) {

        return weight / (height * height) * 10000;
    }

    public double calculateTargetWeight(double heightInMeters, double targetBMI) {

        return targetBMI * (heightInMeters * heightInMeters) / 10000;
    }

    public long processLogin(KakaoLoginResponse kakaoLoginResponse) {

        User user;
        try{
            // DB에 이미 존재할 경우 로그인 진행
            user = findBySocialId(kakaoLoginResponse.getId());
        } catch (IllegalArgumentException e) {
            // DB에 없을 경우 회원가입
            user = userRepository.save(User.builder()
                    .socialId(kakaoLoginResponse.getId())
                    .name(kakaoLoginResponse.getKakaoAccount().getName())
                    .phoneNumber(kakaoLoginResponse.getKakaoAccount().getPhoneNumber())
                    .build());

            healthInformationRepository.save(HealthInformation.builder()
                    .user(user)
                    .build());
        }

        return user.getId();
    }

    public JwtResponse userToJwtToken(Long userId, Date expiredDate) {

        LoggedInUser user = LoggedInUser.builder()
                .id(userId)
                .build();

        String token = jwtUtil.createToken(user, expiredDate);

        return JwtResponse.builder()
                .code(token)
                .build();
    }

    public List<ExerciseResponse> findExercises(Long userId) {

        List<WorkoutSession> workouts = workoutSessionRepository.findWorkoutSessionsByUserIdThisMonth(userId);
        List<ExerciseResponse> exerciseResponses = new ArrayList<>();

        for (WorkoutSession workout : workouts) {
            exerciseResponses.add(ExerciseResponse.of(workout));
        }

        return exerciseResponses;
    }
}
