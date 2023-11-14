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

    /** 아래의 필드는
     전혀 어렵지 않다 0
     좀 어렵다 1
     너무 힘들다 2
     라는 값으로 받아서 계산식에 들어갑니다.
     */
    private int stairs;
    private int walkChairToChair;
    private int walkSideToSide;
    private int liftPear;
}
