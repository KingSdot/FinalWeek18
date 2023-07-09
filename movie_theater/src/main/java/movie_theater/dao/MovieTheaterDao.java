package movie_theater.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import movie_theater.entity.MovieTheater;

public interface MovieTheaterDao extends JpaRepository<MovieTheater, Long> {

}
