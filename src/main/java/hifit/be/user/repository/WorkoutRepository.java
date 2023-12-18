package hifit.be.user.repository;

import hifit.be.user.entity.Workout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface WorkoutRepository extends JpaRepository<Workout, Long> {

    @Query("SELECT CASE WHEN COUNT(w) > 0 THEN true ELSE false END FROM Workout w WHERE w.user.id = :userId AND w.workoutDate = :workoutDate")
    boolean existsByUserIdAndWorkoutDate(@Param("userId") Long userId, @Param("workoutDate") LocalDate workoutDate);
}
