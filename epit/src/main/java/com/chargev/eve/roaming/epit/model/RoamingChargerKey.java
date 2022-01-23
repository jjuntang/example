package com.chargev.eve.roaming.epit.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;
import java.io.Serializable;

@Data
@Embeddable
@NoArgsConstructor
public class RoamingChargerKey implements Serializable {

    @Transient
    private long idx;

    @Column(name = "charger_idx")
    private long chargerIdx;

    @Column(name = "station_idx")
    private long stationIdx;

    @Column(name = "company_idx")
    private long companyIdx;
}