package hifit.be.user.service;

import hifit.be.user.entity.User;
import hifit.be.user.entity.Workout;
import hifit.be.user.repository.UserRepository;
import hifit.be.user.repository.WorkoutRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class WorkoutService {

    private final WorkoutRepository workOutRepository;

    public boolean checkWorkoutStatus(Long userId, LocalDate workoutDate) {

        return workOutRepository.existsByUserIdAndWorkoutDate(userId, workoutDate);
    }

    @Transactional
    public void saveWorkout(Long userId, LocalDate workoutDate) {

        Workout workout = Workout.builder()
                .user(User.builder().id(userId).build())
                .workoutDate(workoutDate)
                .build();

        workOutRepository.save(workout);
    }
}
