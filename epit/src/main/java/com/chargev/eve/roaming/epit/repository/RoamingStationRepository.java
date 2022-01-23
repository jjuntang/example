package com.chargev.eve.roaming.epit.repository;

import com.chargev.eve.roaming.epit.model.RoamingStation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoamingStationRepository extends JpaRepository<RoamingStation, String>, RoamingStationRepositoryExtend {
}
