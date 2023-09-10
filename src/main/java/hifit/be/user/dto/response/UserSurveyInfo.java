package hifit.be.user.dto.response;

import hifit.be.user.entity.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserSurveyInfo {

    private String gender;
    private int age;
    private Double height;
    private Double weight;

    public static UserSurveyInfo of(User user) {

        return UserSurveyInfo.builder()
                .age(user.getAge())
                .gender(user.getGender())
                .height(user.getHeight())
                .weight(user.getWeight())
                .build();
    }
}
