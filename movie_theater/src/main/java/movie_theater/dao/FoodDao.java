package movie_theater.dao;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import movie_theater.entity.Food;

public interface FoodDao extends JpaRepository<Food, Long> {

	Set<Food> findAllByFoodIn(Set<String> foods);

}
