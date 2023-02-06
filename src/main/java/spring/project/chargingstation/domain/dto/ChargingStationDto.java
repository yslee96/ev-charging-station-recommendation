package spring.project.chargingstation.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChargingStationDto {
    private Long id;
    private String chargingStationName;

    private double latitude;
    private double longitude;
}
