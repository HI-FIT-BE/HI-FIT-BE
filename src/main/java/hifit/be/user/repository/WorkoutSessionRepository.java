package hifit.be.user.repository;

import hifit.be.user.entity.WorkoutSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface WorkoutSessionRepository extends JpaRepository<WorkoutSession, Long> {

    @Query("SELECT COUNT(w) FROM WorkoutSession w WHERE w.user.id = :userId AND FUNCTION('MONTH', w.workoutDate) = FUNCTION('MONTH', CURRENT_DATE())")
    int countWorkoutSessionsThisMonthByUserId(@Param("userId") Long userId);

    @Query("SELECT w FROM WorkoutSession w WHERE w.user.id = :userId AND w.workoutDate = :today")
    Optional<WorkoutSession> findWorkoutSessionByUserIdAndDate(@Param("userId") Long userId, @Param("today") LocalDate today);

    @Query("SELECT w FROM WorkoutSession w WHERE w.user.id = :userId AND FUNCTION('YEAR', w.workoutDate) = FUNCTION('YEAR', CURRENT_DATE()) AND FUNCTION('MONTH', w.workoutDate) = FUNCTION('MONTH', CURRENT_DATE())")
    List<WorkoutSession> findWorkoutSessionsByUserIdThisMonth(@Param("userId") Long userId);
}
