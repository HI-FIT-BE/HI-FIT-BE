package hifit.be.user.dto.response;

import hifit.be.user.entity.Sarcopenia;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserHealthStatusInfo {

    private String gender;
    private String name;
    private int age;
    private Sarcopenia sarcopenia;

}
