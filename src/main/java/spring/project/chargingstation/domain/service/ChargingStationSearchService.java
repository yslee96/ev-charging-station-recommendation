package spring.project.chargingstation.domain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import spring.project.chargingstation.domain.dto.ChargingStationDto;
import spring.project.chargingstation.domain.entity.ChargingStation;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChargingStationSearchService {

    private final ChargingStationRepositoryService chargingStationRepositoryService;

    public List<ChargingStationDto> searchChargingStationDtoList(){

        //redis

        //db
        return chargingStationRepositoryService.findAll()
                .stream()
                .map(entity -> convertToChargingStationDto(entity))
                .collect(Collectors.toList());
    }
    
    private ChargingStationDto convertToChargingStationDto(ChargingStation chargingStation){

        return ChargingStationDto.builder()
                .id(chargingStation.getId())
                .chargingStationName(chargingStation.getChargingStationName())
                .latitude(chargingStation.getLatitude())
                .longitude(chargingStation.getLongitude())
                .build();
    }
}
