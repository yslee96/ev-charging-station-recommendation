package spring.project.chargingstation.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.project.chargingstation.domain.entity.ChargingStation;

public interface ChargingStationRepository extends JpaRepository<ChargingStation, Long> {

}
