package spring.project.chargingstation.direction.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.project.chargingstation.direction.entity.Direction;

public interface DirectionRepository extends JpaRepository<Direction, Long> {
}
