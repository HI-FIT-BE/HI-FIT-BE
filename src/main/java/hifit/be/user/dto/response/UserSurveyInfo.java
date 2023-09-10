package hifit.be.user.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserSurveyInfo {

    private String gender;
    private int age;
    private Double height;
    private Double weight;

}
