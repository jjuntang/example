package com.example.jpa_ul;

import java.util.List;

public interface ChargerRepositoryExtends {

    /**
     * 충전기 정보 추가
     *
     * @param chargers 충전기 리스트
     */
    void saveAll(List<Charger> chargers);

    /**
     * 충전기 정보 갱신
     *
     * @param chargers 충전기 리스트
     */
    void updateAll(List<Charger> chargers);
}