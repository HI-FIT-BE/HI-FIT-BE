package hifit.be.user.service;

import hifit.be.user.dto.request.HealthInfoRequest;
import hifit.be.user.entity.Sarcopenia;
import hifit.be.util.RandomForestUtil;
import org.springframework.stereotype.Service;

@Service
public class SarcopeniaService {

    public Sarcopenia getSarcopenia(HealthInfoRequest request) {

        Double bmi = getBmi(request.getHeight(), request.getWeight());
        double obesity = getObesity(bmi);

        int healthStatus = RandomForestUtil.getSarcopenia(request, bmi, obesity);
        int workOutStatus = getWorkOutStatus(request);

        return getSarcopenia(healthStatus, workOutStatus);
    }

    private Double getBmi(double height, double weight) {

        return height / (weight * weight);
    }

    private Double getObesity(Double bmi) {

        if (bmi < 18.5) {
            return 1.0;
        }
        if (bmi < 25) {
            return 2.0;
        }
        return 3.0;
    }

    private int getWorkOutStatus(HealthInfoRequest request) {

        int result = request.getStairs() +
                request.getWalkChairToChair() +
                request.getWalkSideToSide() +
                request.getLiftPear();

        if (result >= 4) {
            return 1;
        }
        return 0;
    }

    private Sarcopenia getSarcopenia(int healthStatus, int workoutStatus) {

        int result = healthStatus + workoutStatus;

        if (result == 0) {
            return Sarcopenia.LOW;
        }
        if (result == 1) {
            return Sarcopenia.MEDIUM;
        }
        return Sarcopenia.HIGH;
    }
}
