package spring.project.chargingstation.domain.cache;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import spring.project.chargingstation.domain.dto.ChargingStationDto;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChargingStationRedisTemplateService {

    private static final String CACHE_KEY = "CHARGINGSTATION";

    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;

    private HashOperations<String, String, String> hashOperations;

    @PostConstruct
    public void init(){
        this.hashOperations = redisTemplate.opsForHash();
    }

    public void save(ChargingStationDto chargingStationDto) {
        if(Objects.isNull(chargingStationDto) || Objects.isNull(chargingStationDto.getId())) {
            log.error("Required Values must not be null");
            return;
        }

        try {
            hashOperations.put(CACHE_KEY,
                    chargingStationDto.getId().toString(),
                    serializeChargingStationDto(chargingStationDto));
            log.info("[ChargingStationRedisTemplateService save success] id: {}", chargingStationDto.getId());
        } catch (Exception e) {
            log.error("[ChargingStationRedisTemplateService save error] {}", e.getMessage());
        }
    }

    public List<ChargingStationDto> findAll() {

        try {
            List<ChargingStationDto> list = new ArrayList<>();
            for (String value : hashOperations.entries(CACHE_KEY).values()) {
                ChargingStationDto chargingStationDto = deserializeChargingStationDto(value);
                list.add(chargingStationDto);
            }
            return list;

        } catch (Exception e) {
            log.error("[ChargingStationRedisTemplateService findAll error]: {}", e.getMessage());
            return Collections.emptyList();
        }
    }

    private String serializeChargingStationDto(ChargingStationDto chargingStationDto) throws JsonProcessingException {
        return objectMapper.writeValueAsString(chargingStationDto);
    }

    private ChargingStationDto deserializeChargingStationDto(String value) throws JsonProcessingException{
        return objectMapper.readValue(value, ChargingStationDto.class);
    }

}
