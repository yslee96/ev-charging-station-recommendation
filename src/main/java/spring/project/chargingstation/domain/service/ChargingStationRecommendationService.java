package spring.project.chargingstation.domain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.util.UriComponentsBuilder;
import spring.project.chargingstation.api.dto.DocumentDto;
import spring.project.chargingstation.api.dto.KakaoApiResponseDto;
import spring.project.chargingstation.api.service.KakaoAddressSearchService;
import spring.project.chargingstation.direction.dto.OutputDto;
import spring.project.chargingstation.direction.entity.Direction;
import spring.project.chargingstation.direction.service.Base62Service;
import spring.project.chargingstation.direction.service.DirectionService;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChargingStationRecommendationService {

    private final KakaoAddressSearchService kakaoAddressSearchService;
    private final DirectionService directionService;
    private final Base62Service base62Service;

    private static final String ROAD_VIEW_BASE_URL = "https://map.kakao.com/link/roadview/";
    @Value("${charging-station.recommendation.base.url}")
    private String baseUrl;

    public List<OutputDto> RecommendChargingStationList(String address){
        KakaoApiResponseDto kakaoApiResponseDto = kakaoAddressSearchService.requestAddressSearch(address);

        if(Objects.isNull(kakaoApiResponseDto) || CollectionUtils.isEmpty(kakaoApiResponseDto.getDocumentList())){
            log.error("[ChargingStationRecommendationService]: RecommendChargingStationList has been failed.");
            return Collections.emptyList();
        }

        DocumentDto documentDto = kakaoApiResponseDto.getDocumentList().get(0);
        List<Direction> directionList = directionService.buildDirectionList(documentDto);

        return directionService.saveAll(directionList)
                .stream()
                .map(this::convertToOutputDto)
                .collect(Collectors.toList());
    }

    public OutputDto convertToOutputDto(Direction direction){

        return OutputDto.builder()
                .chargingStationName(direction.getTargetChargingStationName())
                .directionUrl(baseUrl + base62Service.encodeDirectionId(direction.getId()))
                .roadViewUrl(ROAD_VIEW_BASE_URL + direction.getTargetLatitude() + "," + direction.getTargetLongitude())
                .distance(String.format("%.2f km", direction.getDistance()))
                .build();
    }

}
