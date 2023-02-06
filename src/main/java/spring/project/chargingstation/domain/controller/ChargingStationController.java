package spring.project.chargingstation.domain.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.project.chargingstation.domain.entity.ChargingStation;
import spring.project.chargingstation.domain.service.ChargingStationRepositoryService;
import spring.project.chargingstation.util.CsvUtils;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ChargingStationController {

    private final ChargingStationRepositoryService chargingStationRepositoryService;

    // 데이터 초기 셋팅을 위한 임시 메소드
    @GetMapping("/csv/save")
    public String saveCsv() {
        saveCsvToDatabase();
        //saveCsvToRedis();

        return "success save";
    }

    public void saveCsvToDatabase() {

        List<ChargingStation> chargingStationList = loadChargingStationList();
        chargingStationRepositoryService.saveAll(chargingStationList);
    }

    private List<ChargingStation> loadChargingStationList() {
        return CsvUtils.convertToChargingStationDtoList()
                .stream().map(chargingStationDto ->
                        ChargingStation.builder()
                                .id(chargingStationDto.getId())
                                .chargingStationName(chargingStationDto.getChargingStationName())
                                .latitude(chargingStationDto.getLatitude())
                                .longitude(chargingStationDto.getLongitude())
                                .build())
                .collect(Collectors.toList());
    }

}
