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
public class RoamingStationKey implements Serializable {

    @Transient
    private long idx;

    @Column(name = "station_idx")
    private long stationIdx;

    @Column(name = "company_idx")
    private long companyIdx;

    //충전기 사업자 아이디
    private String cbid;

    //충전소 아이디
    private String sid;
}