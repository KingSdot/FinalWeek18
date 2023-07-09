package movie_theater.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import movie_theater.controller.model.MovieTheaterData;
import movie_theater.controller.model.MovieTheaterData.MovieTheaterEmployee;
import movie_theater.controller.model.MovieTheaterData.MovieTheaterMovie;
import movie_theater.entity.MovieTheater;
import movie_theater.service.MovieTheaterService;

@RestController
@RequestMapping("/movie_theater")
@Slf4j
public class MovieTheaterController {
	
	@Autowired
	private MovieTheaterService movieTheaterService;
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public MovieTheaterData insertMovieTheater(@RequestBody MovieTheaterData movieTheaterData ){
		log.info("Creating movie theater {}", movieTheaterData);
		return movieTheaterService.saveMovieTheater(movieTheaterData);
	}
	
	@PutMapping("/{movieTheaterId}")
	 public MovieTheaterData updateMovieTheater(@PathVariable Long movieTheaterId, @RequestBody MovieTheaterData movieTheaterData) {
		 	
		 	movieTheaterData.setMovieTheaterId(movieTheaterId);
	    	log.info("Updating movie theater {}", movieTheaterData);
	    	return movieTheaterService.saveMovieTheater(movieTheaterData);
	    }
	
	@GetMapping("/movie_theater")
	public List<MovieTheaterData> retrieveAllMovieTheaters(){
			log.info("Retrieve all movies called.");
			return movieTheaterService.retrieveAllMovieTheaters();
		}
	
	@GetMapping("/{movieTheaterId}")
	 public MovieTheaterData retrieveMovieTheaterById(@PathVariable Long movieTheaterId) {
	    	log.info("Retrieving movie theater with ID={}", movieTheaterId);
	    	return movieTheaterService.retrieveMovieTheaterById(movieTheaterId);
	    }
	
	@DeleteMapping("/{movieTheaterId}")
	 public Map<String, String> deleteMovieTheaterById(@PathVariable Long movieTheaterId){
	    	log.info("Deleting movie theater with ID={}", movieTheaterId);
	    	
	    	movieTheaterService.deleteMovieTheaterById(movieTheaterId);
	    	
	    	return Map.of("message", "Deletion of movie theater with ID=" + movieTheaterId +" was successful.");
	    }
	
	@PostMapping("/{movieTheaterId}/movie")
	@ResponseStatus(code = HttpStatus.CREATED)
	public MovieTheaterMovie insertMovieTheaterMovie(@PathVariable Long movieTheaterId, @RequestBody MovieTheaterMovie movieTheaterMovie) {
		log.info("Creating movie theater movie {} for movie theater with ID={}", movieTheaterId, movieTheaterMovie);
		 
		 return movieTheaterService.saveMovie(movieTheaterId, movieTheaterMovie);
	}
	
	@PutMapping("/{movieTheaterId}/movie/{movieId}")
	 public MovieTheaterMovie updateMovieTheaterMovie(@PathVariable Long movieTheaterId, @PathVariable Long movieId, @RequestBody MovieTheaterMovie movieTheaterMovie) {
		 	
		 	movieTheaterMovie.setMovieId(movieId);
	    	log.info("Updating movie theater movie {}", movieTheaterMovie);
	    	return movieTheaterService.saveMovie(movieTheaterId, movieTheaterMovie);
	    }
	
	@GetMapping("/movie")
	public List<MovieTheaterMovie> retrieveAllMovies(){
			log.info("Retrieve all movies called.");
			return movieTheaterService.retrieveAllMovies();
		}
	
	 @GetMapping("/{movieTheaterId}/movie/{movieId}")
	 public MovieTheaterMovie retrieveMovieById(@PathVariable Long movieTheaterId, @PathVariable Long movieId) {
	    	log.info("Retrieving movie with ID={}", movieId);
	    	return movieTheaterService.retrieveMovieById(movieTheaterId, movieId);
	    }
	 
	 
	
	@PostMapping("/{movieTheaterId}/employee")
	@ResponseStatus(code = HttpStatus.CREATED)
	public MovieTheaterEmployee insertMovieTheaterEmployee(@PathVariable Long movieTheaterId, @RequestBody MovieTheaterEmployee movieTheaterEmployee) {
    	log.info("Creating movie theater employee {} for movie theater with ID={}", movieTheaterId, movieTheaterEmployee);
    	
    	return movieTheaterService.saveEmployee(movieTheaterId, movieTheaterEmployee);
    }
	
	
	
}
