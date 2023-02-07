package spring.project.chargingstation.domain.repository

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spring.project.chargingstation.AbstractIntegrationContainerBaseTest
import spring.project.chargingstation.domain.entity.ChargingStation

import java.time.LocalDateTime

@SpringBootTest
class ChargingStationRepositoryTest extends AbstractIntegrationContainerBaseTest {

    @Autowired
    private ChargingStationRepository chargingStationRepository

    def setup() {
        chargingStationRepository.deleteAll()
    }

    def "chargingStationRepository save"(){
        String name = "고려대학교"
        double latitude = 37.59
        double longitude = 127.03

        def chargingStation = ChargingStation.builder()
                .chargingStationName(name)
                .latitude(latitude)
                .longitude(longitude)
                .build()

        when:
        def result = chargingStationRepository.save(chargingStation)

        then:
        result.getChargingStationName() == name
        result.getLatitude() == latitude
        result.getLongitude() == longitude
    }

    def "chargingStationRepository saveAll"() {
        String name = "고려대학교"
        double latitude = 37.59
        double longitude = 127.03

        def chargingStation = ChargingStation.builder()
                .chargingStationName(name)
                .latitude(latitude)
                .longitude(longitude)
                .build()

        when:
        chargingStationRepository.saveAll(Arrays.asList(chargingStation))
        def result = chargingStationRepository.findAll()

        then:
        result.size()==1
    }

    def "BaseTimeEntity 등록"(){
        given:
        LocalDateTime now = LocalDateTime.now()
        String name = "고려대학교"

        def chargingStation = ChargingStation.builder()
                .chargingStationName(name)
                .build()

        when:
        chargingStationRepository.save(chargingStation)
        def result = chargingStationRepository.findAll()

        then:
        result.get(0).getCreatedDate().isAfter(now)
        result.get(0).getModifiedDate().isAfter(now)
    }
}
