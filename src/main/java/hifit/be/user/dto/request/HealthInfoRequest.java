package hifit.be.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HealthInfoRequest {

    private Double height;
    private Double weight;
    private Double systolicBloodPressure;
    private Double diastolicBloodPressure;
    private Integer heartRate;
    private Double waistSize;
}
