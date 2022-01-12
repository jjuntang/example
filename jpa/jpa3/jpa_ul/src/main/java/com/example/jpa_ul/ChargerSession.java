package com.example.jpa_ul;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.UUID;

@Component
public class ChargerSession extends Observable {

    private HashMap<UUID, Charger> chargers;

    public ChargerSession() {
        this.chargers = new HashMap<>();
    }

    public UUID getSessionIndexBySerialNumber(String serialNumber) {
        UUID key = null;

        for (Map.Entry<UUID, Charger> entry : this.chargers.entrySet()) {
            if (entry.getValue().getSerialNumber().equals(serialNumber)) {
                key = entry.getKey();
                break;
            }
        }

        return key;
    }

    public Charger getChargerBySerialNumber(String serialNumber) {
        return this.chargers.get(getSessionIndexBySerialNumber(serialNumber));
    }

    /**
     * 신규세션 등록
     *
     * @param charger 충전기정보
     */
    public void add(Charger charger) {
        // 임의 UUID 등록
        put(UUID.randomUUID(), charger);
    }

    /**
     * 충전기정보 추가
     *
     * @param sessionIndex 세션인덱스
     * @param charger      충전기정보
     */
    private void put(UUID sessionIndex, Charger charger) {
        this.chargers.put(sessionIndex, charger);
        //notifyChargerSessionChanged(sessionIndex);
    }

    /**
     * 충전기목록 전체 리턴
     *
     * @return chargers
     */
    public HashMap<UUID, Charger> getAll() {
        return this.chargers;
    }
}