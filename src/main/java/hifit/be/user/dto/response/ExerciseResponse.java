package hifit.be.user.dto.response;

import hifit.be.user.entity.WorkoutSession;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExerciseResponse {

    private Long id;
    private LocalDate workoutDate;

    public static ExerciseResponse of(WorkoutSession workoutSession) {

        return ExerciseResponse.builder()
                .id(workoutSession.getId())
                .workoutDate(workoutSession.getWorkoutDate())
                .build();
    }
}
