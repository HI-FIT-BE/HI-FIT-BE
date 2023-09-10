package hifit.be.user.dto.response;

import hifit.be.user.entity.Sarcopenia;
import hifit.be.user.entity.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserHealthStatusInfo {

    private String gender;
    private String name;
    private int age;
    private Sarcopenia sarcopenia;

    public static UserHealthStatusInfo of(User user) {

        return UserHealthStatusInfo.builder()
                .name(user.getName())
                .gender(user.getGender())
                .age(user.getAge())
                .sarcopenia(user.getSarcopenia())
                .build();
    }
}
