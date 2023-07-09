package movie_theater.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import movie_theater.entity.Movie;

public interface MovieDao extends JpaRepository<Movie, Long> {

}
