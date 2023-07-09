package movie_theater.controller.model;

import java.util.HashSet;
import java.util.Set;

import lombok.Data;
import lombok.NoArgsConstructor;
import movie_theater.entity.Employee;
import movie_theater.entity.Food;
import movie_theater.entity.Movie;
import movie_theater.entity.MovieTheater;



@Data
@NoArgsConstructor
public class MovieTheaterData {
	private Long movieTheaterId;
	private String movieTheaterName;
	private String movieTheaterAddress;
	private String movieTheaterCity;
	private String movieTheaterState;
	private String movieTheaterZip;
	private String movieTheaterPhone;
	private Set<MovieTheaterEmployee> employees = new HashSet<>();
	private Set<MovieTheaterMovie> movies = new HashSet<>();
	private Set<String> foods = new HashSet<>();
	
	 public MovieTheaterData(MovieTheater movieTheater) {
		  movieTheaterId = movieTheater.getMovieTheaterId();
		  movieTheaterName = movieTheater.getMovieTheaterName();
		  movieTheaterAddress = movieTheater.getMovieTheaterAddress();
	      movieTheaterCity = movieTheater.getMovieTheaterCity();
		  movieTheaterState = movieTheater.getMovieTheaterState();
		  movieTheaterZip = movieTheater.getMovieTheaterZip();
		  movieTheaterPhone = movieTheater.getMovieTheaterPhone();
		  
		  for(Movie movie : movieTheater.getMovies()) {
			  movies.add(new MovieTheaterMovie(movie));
		  }
		  
		  for(Employee employee : movieTheater.getEmployees()) {
			  employees.add(new MovieTheaterEmployee(employee));
		  }
		  
		  for(Food food : movieTheater.getFoods()) {
			  foods.add(food.getFood());
		  }
	  }
	 
	 @Data
	 @NoArgsConstructor
	 public static class MovieTheaterEmployee {
		  private Long employeeId;
		  private String employeeFirstName;
		  private String employeeLastName;
		  private String employeePhone;
		  private String employeeJobTitle;
		
		  
	public MovieTheaterEmployee(Employee employee){
			 employeeId = employee.getEmployeeId();
			 employeeFirstName = employee.getEmployeeFirstName();
			 employeeLastName = employee.getEmployeeLastName();
			 employeeJobTitle = employee.getEmployeeJobTitle();
			 employeePhone = employee.getEmployeePhone();
			 
			
		  }
	 }
	 
	@Data
    @NoArgsConstructor
	public static class MovieTheaterMovie {
		 private Long movieId;
		 private String movieTitle;
		 private String movieGenre;
		 private String movieReleaseDate;
		 private String movieRunTime;
			
	public MovieTheaterMovie(Movie movie) {
			movieId = movie.getMovieId();
			movieTitle = movie.getMovieTitle();
			movieGenre = movie.getMovieGenre();
			movieReleaseDate = movie.getMovieReleaseDate();
			movieRunTime = movie.getMovieRunTime();
		}
	 }
	 
}