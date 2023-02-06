package spring.project.chargingstation.direction.service;

import io.seruco.encoding.base62.Base62;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import spring.project.chargingstation.api.dto.DocumentDto;
import spring.project.chargingstation.direction.entity.Direction;
import spring.project.chargingstation.direction.repository.DirectionRepository;
import spring.project.chargingstation.domain.dto.ChargingStationDto;
import spring.project.chargingstation.domain.service.ChargingStationSearchService;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class DirectionService {

    private static final int MAX_SEARCH_COUNT = 3;
    private static final double RADIS_KM = 10.0;

    private final ChargingStationSearchService chargingStationSearchService;
    private final DirectionRepository directionRepository;
    private final Base62Service base62Service;

    @Transactional
    public List<Direction> saveAll(List<Direction> directionList){
        if(CollectionUtils.isEmpty(directionList)) return Collections.emptyList();
        return directionRepository.saveAll(directionList);
    }

    public Direction findById(String encodedId){
        Long decodedId = base62Service.decodeDirectionId(encodedId);
        return directionRepository.findById(decodedId).orElse(null);
    }

    public List<Direction> buildDirectionList(DocumentDto documentDto){

        if(Objects.isNull(documentDto)) return Collections.emptyList();

        return chargingStationSearchService.searchChargingStationDtoList()
                .stream().map(chargingStationDto ->
                        Direction.builder()
                                .inputAddress(documentDto.getAddressName())
                                .inputLatitude(documentDto.getLatitude())
                                .inputLongitude(documentDto.getLongitude())
                                .targetChargingStationName(chargingStationDto.getChargingStationName())
                                .targetLatitude(chargingStationDto.getLatitude())
                                .targetLongitude(chargingStationDto.getLongitude())
                                .distance(
                                        calculateDistance(documentDto.getLatitude(), documentDto.getLongitude(),
                                                chargingStationDto.getLatitude(), chargingStationDto.getLongitude())
                                )
                                .build())
                .filter(direction -> direction.getDistance() <= RADIS_KM)
                .sorted(Comparator.comparing(Direction::getDistance))
                .limit(MAX_SEARCH_COUNT)
                .collect(Collectors.toList());
    }

    private double calculateDistance(double lat1, double lon1, double lat2, double lon2){
        lat1 = Math.toRadians(lat1);
        lon1 = Math.toRadians(lon1);
        lat2 = Math.toRadians(lat2);
        lon2 = Math.toRadians(lon2);

        double earthRadius = 6371; //Kilometers
        return earthRadius * Math.acos(Math.sin(lat1) * Math.sin(lat2) + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon1 - lon2));
    }
}
