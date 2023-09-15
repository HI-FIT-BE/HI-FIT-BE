package hifit.be.user.dto.response;

import hifit.be.user.entity.Gender;
import hifit.be.user.entity.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserSurveyInfo {

    private Gender gender;
    private int age;
    private Double height;
    private Double weight;

    public static UserSurveyInfo of(User user) {

        return UserSurveyInfo.builder()
                .age(user.getAge())
                .gender(user.getGender())
                .build();
    }
}
