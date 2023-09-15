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
    private int age;
    private double height;
    private double weight;
    private double systolicBloodPressure;
    private double diastolicBloodPressure;
    private int heartRate;
    private double waistSize;
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
