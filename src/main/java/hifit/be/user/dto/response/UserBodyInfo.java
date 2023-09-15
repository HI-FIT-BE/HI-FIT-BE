package hifit.be.user.dto.response;

import hifit.be.user.entity.User;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Builder
public class UserBodyInfo {

    private Double currentBmi;
    private Double currentWeight;
    private Double targetBmi;
    private Double targetWeight;

    public static UserBodyInfo of(Double currentBmi, Double currentWeight, Double targetBmi, Double targetWeight) {

        return UserBodyInfo.builder()
                .currentBmi(currentBmi)
                .currentWeight(currentWeight)
                .targetBmi(targetBmi)
                .targetWeight(targetWeight)
                .build();
    }
}
