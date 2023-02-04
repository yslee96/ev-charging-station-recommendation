package spring.project.chargingstation.domain.repository

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spring.project.chargingstation.AbstractIntegrationContainerBaseTest
import spring.project.chargingstation.domain.entity.ChargingStation

@SpringBootTest
class ChargingStationRepositoryTest extends AbstractIntegrationContainerBaseTest {

    @Autowired
    private ChargingStationRepository chargingStationRepository

    def setup() {
        chargingStationRepository.deleteAll()
    }

    def "chargingStationRepository save"(){
        String address = "서울 특별시 성북구 안암동"
        String name = "고려대학교"
        double latitude = 37.59
        double longitude = 127.03

        def chargingStation = ChargingStation.builder()
                .chargingStationAddress(address)
                .chargingStationName(name)
                .latitude(latitude)
                .longitude(longitude)
                .build()

        when:
        def result = chargingStationRepository.save(chargingStation)

        then:
        result.getChargingStationAddress() == address
        result.getChargingStationName() == name
        result.getLatitude() == latitude
        result.getLongitude() == longitude
    }

    def "chargingStationRepository saveAll"() {
        String address = "서울 특별시 성북구 안암동"
        String name = "고려대학교"
        double latitude = 37.59
        double longitude = 127.03

        def chargingStation = ChargingStation.builder()
                .chargingStationAddress(address)
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
}
