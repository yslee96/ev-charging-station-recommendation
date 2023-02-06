package spring.project.chargingstation.direction.service

import spock.lang.Specification
import spring.project.chargingstation.domain.dto.ChargingStationDto
import spring.project.chargingstation.api.dto.DocumentDto
import spring.project.chargingstation.domain.service.ChargingStationSearchService

class DirectionServiceTest extends Specification {

    private ChargingStationSearchService chargingStationSearchService = Mock()

    private DirectionService directionService = new DirectionService(chargingStationSearchService);

    private List<ChargingStationDto> chargingStationList;

    def setup(){
        chargingStationList = new ArrayList<>();
        chargingStationList.addAll(
            ChargingStationDto.builder()
                .id(1L)
                .chargingStationName("고려대학교")
                .chargingStationAddress("주소1")
                .latitude(37.588985)
                .longitude(127.033897)
                .build(),
            ChargingStationDto.builder()
                .id(2L)
                .chargingStationName("고려대학교(자연과학)")
                .chargingStationAddress("주소2")
                .latitude(37.58358844893844)
                .longitude(127.02635101125968)
                .build()
        )
    }

    def "buildDirectionList - 결과값이 거리순으로 정렬되는지 확인"(){

        given:
        def addressName = "서울 성북구 종암로10길"
        double inputLatitude = 37.5960650456809
        double inputLongitude = 127.037033003036

        def documentDto = DocumentDto.builder()
                .addressName(addressName)
                .latitude(inputLatitude)
                .longitude(inputLongitude)
                .build()

        when:
        chargingStationSearchService.searchChargingStationDtoList() >> chargingStationList
        def result = directionService.buildDirectionList(documentDto)

        then:
        result.size() == 2
        result.get(0).getTargetChargingStationName() == "고려대학교"
        result.get(1).getTargetChargingStationName() == "고려대학교(자연과학)"

    }
}
