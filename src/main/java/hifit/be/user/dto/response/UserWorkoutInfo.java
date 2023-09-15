package hifit.be.user.dto.response;

import hifit.be.user.entity.User;
import lombok.*;

import javax.persistence.Entity;

@Getter
@Builder
public class UserWorkoutInfo {

    private String name;
    private int stamp;
    private int targetStamp;

    public static UserWorkoutInfo of(User user) {

        return UserWorkoutInfo.builder()
                .name(user.getName())
                .stamp(user.getStamp())
                .targetStamp(20)
                .build();
    }
}
