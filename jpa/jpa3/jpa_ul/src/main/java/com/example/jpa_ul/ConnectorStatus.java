package com.example.jpa_ul;

import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;
import java.util.Calendar;

@Data
public class ConnectorStatus implements Serializable {

    // 커넥터 순번
    private Integer connectorId;

    // 충전기 상태
    @Enumerated(EnumType.STRING)
    private ChargePointStatus status;

    // 충전기 오류코드
    @Enumerated(EnumType.STRING)
    private ChargePointErrorCode errorCode;

    // 충전기 제조사 ID
    private String vendorId;

    // 충전기 제조사 오류코드
    private String vendorErrorCode;

    // 오류발생일시
    private Calendar reportDate;

    public ConnectorStatus setConnectorId(Integer connectorId) {
        this.connectorId = connectorId;
        return this;
    }

    public ConnectorStatus setStatus(ChargePointStatus status) {
        this.status = status;
        return this;
    }

    public ConnectorStatus setErrorCode(ChargePointErrorCode errorCode) {
        this.errorCode = errorCode;
        return this;
    }

    public ConnectorStatus setVendorId(String vendorId) {
        this.vendorId = vendorId;
        return this;
    }

    public ConnectorStatus setVendorErrorCode(String vendorErrorCode) {
        this.vendorErrorCode = vendorErrorCode;
        return this;
    }

    public ConnectorStatus setReportDate(Calendar reportDate) {
        this.reportDate = reportDate;
        return this;
    }
}
