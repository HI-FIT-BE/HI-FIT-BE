package hifit.be.user.dto.response;

import lombok.*;

import javax.persistence.Entity;

@Getter
@Builder
public class UserWorkoutInfo {

    private String name;
    private int stamp;
}
