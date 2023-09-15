package hifit.be.user.dto.response;

import hifit.be.user.entity.Gender;
import hifit.be.user.entity.HealthInformation;
import hifit.be.user.entity.Sarcopenia;
import hifit.be.user.entity.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserHealthStatusInfo {

    private Gender gender;
    private String name;
    private Integer age;
    private Double height;
    private Double weight;
    private Double systolicBloodPressure;
    private Double diastolicBloodPressure;
    private Integer heartRate;
    private Double waistSize;
    private Sarcopenia sarcopenia;

    public static UserHealthStatusInfo of(User user, HealthInformation healthInformation) {

        return UserHealthStatusInfo.builder()
                .name(user.getName())
                .gender(user.getGender())
                .age(user.getAge())
                .height(healthInformation.getHeight())
                .weight(healthInformation.getWeight())
                .systolicBloodPressure(healthInformation.getSystolicBloodPressure())
                .diastolicBloodPressure(healthInformation.getDiastolicBloodPressure())
                .heartRate(healthInformation.getHeartRate())
                .waistSize(healthInformation.getWaistSize())
                .build();
    }
}
