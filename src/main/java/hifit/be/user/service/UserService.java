package hifit.be.user.service;

import hifit.be.user.dto.response.UserBodyInfo;
import hifit.be.user.dto.response.UserHealthStatusInfo;
import hifit.be.user.dto.response.UserSurveyInfo;
import hifit.be.user.dto.response.UserWorkoutInfo;
import hifit.be.user.entity.Sarcopenia;
import hifit.be.user.entity.User;
import hifit.be.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User findBySocialId(Long socialId) {

        return userRepository.findBySocialId(socialId)
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 존재하지 않는 회원입니다. 회원 가입을 해주세요."));
    }

    public User findById(Long id) {

        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 존재하지 않는 회원입니다. 회원 가입을 해주세요."));
    }

    public boolean existsBySocialId(Long socialId) {

        return userRepository.existsBySocialId(socialId);
    }

    public UserWorkoutInfo findUserWorkoutInfo(Long id) {

        return UserWorkoutInfo.of(findById(id));
    }

    public UserBodyInfo findBodyInfo(Long id) {

        return UserBodyInfo.of(findById(id));
    }

    public UserSurveyInfo findSurveyInfo(Long id) {

        return UserSurveyInfo.of(findById(id));
    }

    public UserHealthStatusInfo findHealthStatusInfo(Long id) {

        return UserHealthStatusInfo.of(findById(id));
    }

    @Transactional
    public void addStamp(Long id) {

        //TODO : 에러 핸들링
        User user = findById(id);
        user.addStamp();
    }

    @Transactional
    public void updateHeight(Long id, double height) {

        User user = findById(id);
        user.updateHeight(height);
    }

    @Transactional
    public void updateWeight(Long id, double weight) {

        User user = findById(id);
        user.updateWeight(weight);
    }

    @Transactional
    public void updateSarcopenia(Long id, Sarcopenia sarcopenia) {

        User user = findById(id);
        user.updateSarcopenia(sarcopenia);
    }
}
