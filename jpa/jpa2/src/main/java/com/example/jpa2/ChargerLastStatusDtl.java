package com.example.jpa2;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Getter
@Setter
@SuperBuilder(toBuilder = true)
@ToString(callSuper = true)
@RequiredArgsConstructor
@Entity
public class ChargerLastStatusDtl extends ChangeAudit{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long idx;
    private String chargerId;
    private String chgTypeCd;
    private String connIp;
    private Integer connPort;
    private String lastUpdDt;
    private String linkServerInfo;
    private String statusCd;
    private String ocppMsg;
    private String ocppStatus;
    private Integer vLocal;
}
