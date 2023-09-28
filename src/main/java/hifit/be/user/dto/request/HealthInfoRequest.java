package hifit.be.user.dto.request;

import hifit.be.user.entity.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HealthInfoRequest {

    private Integer age;
    private Gender gender;
    private Double height;
    private Double weight;
    private Double systolicBloodPressure;
    private Double diastolicBloodPressure;
    private Integer heartRate;
    private Double waistSize;
}
