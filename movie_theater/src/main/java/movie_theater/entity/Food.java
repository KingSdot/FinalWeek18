package movie_theater.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class Food {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long foodId;
	private String food;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToMany(mappedBy = "foods")
	private Set<MovieTheater> movieTheaters = new HashSet<>();
	
}
