package hifit.be.user.dto.response;

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
}
