package hifit.be.user.service;

import hifit.be.user.dto.request.HealthInfoRequest;
import hifit.be.user.dto.response.*;
import hifit.be.user.entity.HealthInformation;
import hifit.be.user.entity.Sarcopenia;
import hifit.be.user.entity.User;
import hifit.be.user.repository.HealthInformationRepository;
import hifit.be.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final HealthInformationRepository healthInformationRepository;

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

        return UserWorkoutInfo.of(findById(id));
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

        //TODO : 에러 핸들링
        User user = findById(id);
        user.addStamp();
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
}
