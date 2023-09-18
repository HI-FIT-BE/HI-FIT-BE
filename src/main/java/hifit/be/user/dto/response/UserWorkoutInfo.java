package hifit.be.user.dto.response;

import hifit.be.user.entity.User;
import lombok.*;

import javax.persistence.Entity;
import java.time.LocalDate;
import java.time.Month;

@Getter
@Builder
public class UserWorkoutInfo {

    private String name;
    private int month;
    private int stamp;
    private int targetStamp;
    private int point;

    public static UserWorkoutInfo of(User user, int stamp) {

        return UserWorkoutInfo.builder()
                .name(user.getName())
                .stamp(stamp)
                .targetStamp(20)
                .point(user.getPoint())
                .month(LocalDate.now().getMonthValue())
                .build();
    }
}
