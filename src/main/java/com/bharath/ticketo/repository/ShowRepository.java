package com.bharath.ticketo.repository;

import com.bharath.ticketo.model.Show;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ShowRepository extends JpaRepository<Show, Long> {
    List<Show> findByMovieId(Long movieId);

    List<Show> findByScreenTheatreId(Long theatreId);

    boolean existsByMovieId(Long movieId);

    boolean existsByScreenId(Long id);

    List<Show> findByStartTimeBetween(LocalDateTime start, LocalDateTime end);

    @Query("""
        SELECT COUNT(s) > 0
        FROM Show s
        WHERE s.screen.id = :screenId
        AND s.startTime < :endTime
        AND s.endTime > :startTime
    """)
    boolean existsOverlappingShow(
            @Param("screenId") Long screenId,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime
    );

    @Query("""
        SELECT COUNT(s) > 0
        FROM Show s
        WHERE s.screen.id = :screenId
        AND s.id <> :showId
        AND s.startTime < :endTime
        AND s.endTime > :startTime
    """)
    boolean existsOverlappingShowForUpdate(
            @Param("screenId") Long screenId,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime,
            @Param("showId") Long showId
    );
}
