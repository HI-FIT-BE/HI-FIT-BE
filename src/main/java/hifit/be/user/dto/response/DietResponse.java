package hifit.be.user.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DietResponse {

    private Integer calorie;
    private Integer protein;
    private Integer eggCount;
    private Integer tofuCount;
    private Integer chickenBreastCount;
}
