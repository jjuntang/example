package com.example.jpa2;

import org.springframework.data.repository.CrudRepository;

public interface ChargerLastStatusDtlRepository extends CrudRepository<ChargerLastStatusDtl, Long> {

    ChargerLastStatusDtl findChargerLastStatusDtlByChargerId(String chargerId);

}

