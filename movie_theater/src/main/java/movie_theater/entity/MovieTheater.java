package movie_theater.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class MovieTheater {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long movieTheaterId;
	private String movieTheaterName;
	private String movieTheaterAddress;
	private String movieTheaterCity;
	private String movieTheaterState;
	private String movieTheaterZip;
	private String movieTheaterPhone;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany(mappedBy = "movieTheater", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Employee> employees = new HashSet<>();
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(name = "movie_theater_movie",
		joinColumns = @JoinColumn(name = "movie_theater_id"),
		inverseJoinColumns = @JoinColumn(name = "movie_id"))
	private Set<Movie> movies = new HashSet<>();
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(name = "movie_theater_food",
		joinColumns = @JoinColumn(name = "movie_theater_id"),
		inverseJoinColumns = @JoinColumn(name = "food_id"))
	private Set<Food> foods = new HashSet<>();
}