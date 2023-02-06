package spring.project.chargingstation.util;

import com.opencsv.exceptions.CsvValidationException;
import lombok.extern.slf4j.Slf4j;
import com.opencsv.CSVReader;
import spring.project.chargingstation.domain.dto.ChargingStationDto;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
public class CsvUtils {

    public static List<ChargingStationDto> convertToChargingStationDtoList() {

        String file = "charging_station.csv";
        List<List<String>> csvList = new ArrayList<>();
        try (CSVReader csvReader = new CSVReader(new FileReader(file))) {
            String[] values = null;
            while ((values = csvReader.readNext()) != null) {
                csvList.add(Arrays.asList(values));
            }
        } catch (IOException | CsvValidationException e) {
            log.error("CsvUtils convertToChargingStationDtoList Fail: {}", e.getMessage());
        }

        return IntStream.range(1, csvList.size()).mapToObj(index -> {
            List<String> rowList = csvList.get(index);


            return ChargingStationDto.builder()
                    .chargingStationName(rowList.get(1))
                    .latitude(Double.parseDouble(rowList.get(2)))
                    .longitude(Double.parseDouble(rowList.get(3)))
                    .build();
        }).collect(Collectors.toList());
    }
}