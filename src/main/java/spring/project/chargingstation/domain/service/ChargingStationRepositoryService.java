package spring.project.chargingstation.domain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import spring.project.chargingstation.domain.entity.ChargingStation;
import spring.project.chargingstation.domain.repository.ChargingStationRepository;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChargingStationRepositoryService {
    private final ChargingStationRepository chargingStationRepository;

    @Transactional(readOnly = true)
    public List<ChargingStation> findAll() {
        return chargingStationRepository.findAll();
    }

    @Transactional
    public List<ChargingStation> saveAll(List<ChargingStation> chargingStationList) {
        if(CollectionUtils.isEmpty(chargingStationList)) return Collections.emptyList();
        return chargingStationRepository.saveAll(chargingStationList);
    }
}
