package hifit.be.user.entity;

import hifit.be.user.dto.request.HealthInfoRequest;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class HealthInformation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    private Double height;
    private Double weight;
    private Double systolicBloodPressure;
    private Double diastolicBloodPressure;
    private Integer heartRate;
    private Double waistSize;
    @Enumerated(EnumType.STRING)
    private Sarcopenia sarcopenia;


    public void updateHealthInfo(HealthInfoRequest healthInfoRequest, Sarcopenia sarcopenia) {

        this.height = healthInfoRequest.getHeight();
        this.weight = healthInfoRequest.getWeight();
        this.systolicBloodPressure = healthInfoRequest.getSystolicBloodPressure();
        this.diastolicBloodPressure = healthInfoRequest.getDiastolicBloodPressure();
        this.heartRate = healthInfoRequest.getHeartRate();
        this.waistSize = healthInfoRequest.getWaistSize();
        this.sarcopenia = sarcopenia;
    }

    public void updateSarcopenia(Sarcopenia sarcopenia) {

        this.sarcopenia = sarcopenia;
    }
}
