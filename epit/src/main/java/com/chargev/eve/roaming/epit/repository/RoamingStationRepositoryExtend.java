package com.chargev.eve.roaming.epit.repository;

import com.chargev.eve.roaming.epit.model.RoamingStation;

import java.util.List;

public interface RoamingStationRepositoryExtend {
    void saveAll(List<RoamingStation> roamingStations);

    List<RoamingStation> findAllByBusinessIdAndRoamingBid(String businessId, int roamingBid);

    List<RoamingStation> findUpdatedByBusinessIdAndRoamingBid(String businessId, int roamingBid);

    int deleteStationInfoHistory();
}
