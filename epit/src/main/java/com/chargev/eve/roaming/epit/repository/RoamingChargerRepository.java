package com.chargev.eve.roaming.epit.repository;

import com.chargev.eve.roaming.epit.model.RoamingCharger;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoamingChargerRepository extends JpaRepository<RoamingCharger, String>, RoamingChargerRepositoryExtend {
}
