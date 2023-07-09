package movie_theater.service;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import movie_theater.controller.model.MovieTheaterData;
import movie_theater.controller.model.MovieTheaterData.MovieTheaterEmployee;
import movie_theater.controller.model.MovieTheaterData.MovieTheaterMovie;
import movie_theater.dao.EmployeeDao;
import movie_theater.dao.FoodDao;
import movie_theater.dao.MovieDao;
import movie_theater.dao.MovieTheaterDao;
import movie_theater.entity.Employee;
import movie_theater.entity.Food;
import movie_theater.entity.Movie;
import movie_theater.entity.MovieTheater;



@Service
public class MovieTheaterService {
	
	@Autowired
	private FoodDao foodDao;
	
	@Autowired
	private EmployeeDao employeeDao;
	
	@Autowired
	private MovieDao movieDao;
	
	@Autowired 
	private MovieTheaterDao movieTheaterDao;
	
	@Transactional(readOnly = false)
	public MovieTheaterData saveMovieTheater(MovieTheaterData movieTheaterData) {
		Long movieTheaterId = movieTheaterData.getMovieTheaterId();
		
		MovieTheater movieTheater = findOrCreateMovieTheater(movieTheaterId);
		
		Set<Food> foods = foodDao.findAllByFoodIn(movieTheaterData.getFoods());
		
		copyMovieTheaterFields(movieTheater, movieTheaterData);
		return new MovieTheaterData(movieTheaterDao.save(movieTheater));
	
	}

	private MovieTheater findOrCreateMovieTheater(Long movieTheaterId) {
		MovieTheater movieTheater;
		
		if(Objects.isNull(movieTheaterId)) {
			movieTheater = new MovieTheater();
		}
		else {
			movieTheater = findMovieTheaterById(movieTheaterId);
		}
		return movieTheater;
	}

	private MovieTheater findMovieTheaterById(Long movieTheaterId) {
		return movieTheaterDao.findById(movieTheaterId).orElseThrow(() -> 
		new NoSuchElementException("Movie theater store with ID=" + movieTheaterId + " was not found."));
	}
	
	private void copyMovieTheaterFields(MovieTheater movieTheater, MovieTheaterData movieTheaterData) {
		  movieTheater.setMovieTheaterName(movieTheaterData.getMovieTheaterName());
		  movieTheater.setMovieTheaterAddress(movieTheaterData.getMovieTheaterAddress());
		  movieTheater.setMovieTheaterCity(movieTheaterData.getMovieTheaterCity());
	      movieTheater.setMovieTheaterState(movieTheaterData.getMovieTheaterState());
		  movieTheater.setMovieTheaterZip(movieTheaterData.getMovieTheaterZip());
		  movieTheater.setMovieTheaterPhone(movieTheaterData.getMovieTheaterPhone());
	}
	
	public List<MovieTheaterData> retrieveAllMovieTheaters() {
		List<MovieTheater> movieTheater = movieTheaterDao.findAll();
		List<MovieTheaterData> mt = new LinkedList<>();
		
		for(MovieTheater movieTheaters : movieTheater) {
			mt.add(new MovieTheaterData(movieTheaters));
			
		}
		return mt;
	}
	
	@Transactional(readOnly = true)
	public MovieTheaterData retrieveMovieTheaterById(Long movieTheaterId) {
		MovieTheater movieTheater = findMovieTheaterById(movieTheaterId);
		return new MovieTheaterData(movieTheater);
	}
	
	@Transactional(readOnly = false)
	public void deleteMovieTheaterById(Long movieTheaterId) {
		MovieTheater movieTheater = findMovieTheaterById(movieTheaterId);
		movieTheaterDao.delete(movieTheater);	
	}
	
	
	@Transactional(readOnly = false)
	public MovieTheaterMovie saveMovie(Long movieTheaterId, MovieTheaterMovie movieTheaterMovie) {
		MovieTheater movieTheater = findMovieTheaterById(movieTheaterId);
		Long movieId = movieTheaterMovie.getMovieId();
		Movie movie = findOrCreateMovie(movieTheaterId, movieId);
		
		
		copyMovieFields(movie, movieTheaterMovie);
		
		//movie.getMovieTheaters.add(movieTheater);
		movieTheater.getMovies().add(movie);
		
		Movie dbMovie = movieDao.save(movie);
		return new MovieTheaterMovie(dbMovie);
	}

	private void copyMovieFields(Movie movie, MovieTheaterMovie movieTheaterMovie) {
		
		movie.setMovieTitle(movieTheaterMovie.getMovieTitle());
		movie.setMovieGenre(movieTheaterMovie.getMovieGenre());
		movie.setMovieReleaseDate(movieTheaterMovie.getMovieReleaseDate());
		movie.setMovieRunTime(movieTheaterMovie.getMovieRunTime());
		
	}

	private Movie findOrCreateMovie(Long movieTheaterId, Long movieId) {
		
		if(Objects.isNull(movieId)) {
			return new Movie();
		}
		
		return  findMovieById(movieTheaterId, movieId);
	}

	private Movie findMovieById(Long movieTheaterId, Long movieId) {
		Movie movie = movieDao.findById(movieId)
				.orElseThrow(() -> new NoSuchElementException("Movie with ID=" + movieId + "does not exist."));
		boolean theaterFound = false;
		
		for(MovieTheater theater : movie.getMovieTheaters()) {
			if(theater.getMovieTheaterId() == movieTheaterId) {
				theaterFound = true;
				break;
			}
		}
		if(!theaterFound) {
			throw new IllegalStateException("Movie with ID=" + movieId + " does not match movie theater with ID="
					+ movieTheaterId);
		}
		return movie;
	}
	
	public MovieTheaterEmployee saveEmployee(Long movieTheaterId, MovieTheaterEmployee movieTheaterEmployee) {
		MovieTheater movieTheater = findMovieTheaterById(movieTheaterId);
		Long employeeId = movieTheaterEmployee.getEmployeeId();
		Employee employee = findOrCreateEmployee(movieTheaterId, employeeId);
		
		
		copyEmployeeFields(employee, movieTheaterEmployee);
		
		employee.setMovieTheater(movieTheater);
		movieTheater.getEmployees().add(employee);
		
		Employee dbEmployee = employeeDao.save(employee);
		return new MovieTheaterEmployee(dbEmployee);
	}

	private void copyEmployeeFields(Employee employee, MovieTheaterEmployee movieTheaterEmployee) {
		employee.setEmployeeFirstName(movieTheaterEmployee.getEmployeeFirstName());
		employee.setEmployeeLastName(movieTheaterEmployee.getEmployeeLastName());
		employee.setEmployeeId(movieTheaterEmployee.getEmployeeId());
		employee.setEmployeeJobTitle(movieTheaterEmployee.getEmployeeJobTitle());
		employee.setEmployeePhone(movieTheaterEmployee.getEmployeePhone());
		
	}

	private Employee findOrCreateEmployee(Long movieTheaterId, Long employeeId) {
		if(Objects.isNull(employeeId)) {
			return new Employee();
		}
		
		return  findEmployeeById(movieTheaterId, employeeId);
	}

	private Employee findEmployeeById(Long movieTheaterId, Long employeeId) {
		Employee employee = employeeDao.findById(employeeId)
				.orElseThrow(() -> new NoSuchElementException("Employee with ID=" + employeeId + "does not exist."));
		
		if(employee.getMovieTheater().getMovieTheaterId() != movieTheaterId) {
			throw new IllegalStateException("Employee with ID=" + employeeId +
				" does not much movie theater with ID=" + movieTheaterId);
		}
		
		return employee;

	}

	public List<MovieTheaterMovie> retrieveAllMovies() {
		List<Movie> movies = movieDao.findAll();
		List<MovieTheaterMovie> movieTheaterMovie = new LinkedList<>();
		
		for(Movie movie : movies) {
			MovieTheaterMovie mtm = new MovieTheaterMovie(movie);
			
			movieTheaterMovie.add(mtm);
		}
		return movieTheaterMovie;
	}
	
	private Movie findMovieById(Long movieId) {
		return movieDao.findById(movieId)
				.orElseThrow(() -> new NoSuchElementException("Movie with ID="
						+ movieId + " was not found."));
	}
	
	@Transactional(readOnly = true)
	public MovieTheaterMovie retrieveMovieById(Long movieTheaterId, Long movieId) {
		Movie movie = findMovieById(movieTheaterId, movieId);
		return new MovieTheaterMovie(movie);
	}

	
	
	

}