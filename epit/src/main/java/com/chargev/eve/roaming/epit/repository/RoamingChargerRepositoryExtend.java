package com.chargev.eve.roaming.epit.repository;

import com.chargev.eve.roaming.epit.model.RoamingCharger;

import java.util.List;

public interface RoamingChargerRepositoryExtend {
    void saveAll(List<RoamingCharger> roamingChargers);

    List<RoamingCharger> findAllByBusinessIdAndRoamingBid(String businessId, int roamingBid);

    List<RoamingCharger> findUpdatedByBusinessIdAndRoamingBid(String businessId, int roamingBid);

    int deleteChargersInfoHistory();
}