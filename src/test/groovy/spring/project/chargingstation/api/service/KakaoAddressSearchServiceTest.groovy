package spring.project.chargingstation.api.service

import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification
import spring.project.chargingstation.AbstractIntegrationContainerBaseTest
import spring.project.chargingstation.api.dto.KakaoApiResponseDto

class KakaoAddressSearchServiceTest extends AbstractIntegrationContainerBaseTest {

    @Autowired
    KakaoAddressSearchService kakaoAddressSearchService

    def "address 값 null이면 requestAddressSearch 메소드가 null을 리턴"(){
        given:
        String address = null

        when:
        def result = kakaoAddressSearchService.requestAddressSearch(address)

        then:
        result == null
    }

    def "주소 값이 vaild 하면 requestAddressSearch 메소드가 document를 반환한다"(){
        given:
        def address = "서울 성북구 종암로 10길"

        when:
        def result = kakaoAddressSearchService.requestAddressSearch(address)

        then:
        result.documentList.size() > 0
        result.metaDto.totalCount > 0
        result.documentList.get(0).addressName != null
    }
}
