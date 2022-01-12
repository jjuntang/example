package com.example.jpa_ul;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.net.InetSocketAddress;
import java.util.*;

@Data
@Entity
@Table(name = "charger")
public class Charger implements Serializable {

    // 키 인덱스
    @Id
    @Column(name = "idx")
    private Integer idx;

    // ChargePoint 모델명
    @Column(name = "model")
    private String model;

    // ChargePoint 시리얼번호 (식별자)
    @Column(name = "serial_number")
    private String serialNumber;

    // ChargePoint 벤더사
    @Column(name = "vendor")
    private String vendor;

    // ChargePoint 상태
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ChargePointStatus status;

    // 오류코드
    @Column(name = "error_code")
    @Enumerated(EnumType.STRING)
    private ChargePointErrorCode errorCode;

    // StatusNotification 발생 시의 오류 부가정보
    @Column(name = "info")
    private String info;

    // 충전기 제조사 ID
    @Column(name = "vendor_id")
    private String vendorId;

    // 충전기 제조사 오류코드
    @Column(name = "vendor_error_code")
    private String vendorErrorCode;

    // 모뎀 SIM카드 ICCID
    @Column(name = "iccid")
    private String iccid;

    // 모뎀 SIM카드 IMSI
    @Column(name = "imsi")
    private String imsi;

    // 메터 시리얼번호
    @Column(name = "meter_serial_number")
    private String meterSerialNumber;

    // 메터 타입
    @Column(name = "meter_type")
    private String meterType;

    // 펌웨어 버전
    @Column(name = "firmware_version")
    private String firmwareVersion;

    // 사용여부 Y/N
    @Column(name = "use_yn")
    @Enumerated(EnumType.STRING)
    private IsYn useYn;

    // 삭제여부 Y/N
    @Column(name = "delete_yn")
    @Enumerated(EnumType.STRING)
    private IsYn deleteYn;

    // 최종Heartbeat 수신일시
    @Column(name = "heartbeat")
    private Calendar heartbeat;

    // 진단파일 업로드 상태
    @Column(name = "diagnostics_status")
    @Enumerated(EnumType.STRING)
    private DiagnosticsStatus diagnosticsStatus;

    // 펌웨어 업데이트 진행상태
    @Column(name = "firmware_status")
    @Enumerated(EnumType.STRING)
    private FirmwareStatus firmwareStatus;

    // 등록일자
    @Column(name = "create_date")
    private Calendar createDate;

    // 최종수정일자
    @Column(name = "modify_date")
    private Calendar modifyDate;

    @Transient
    @Enumerated(EnumType.STRING)
    private IsYn connectYn;

    @Transient
    private HashMap<Integer, ConnectorStatus> connectorStatus = new HashMap<>();

    @Transient
    private ChargingTransaction chargingTransaction;

    @Transient
    private boolean isRemoteStartTransaction;

    @Transient
    private long chargingDuration;

    @Transient
    private InetSocketAddress ipAddress;

    public ChargePointStatus getStatus() {

        if (this.connectorStatus.isEmpty()) {
            return this.status;
        }
        else {
            List<ChargePointStatus> statusList = new ArrayList<>();

            connectorStatus.forEach((key, connectorStatus) -> {
                statusList.add(connectorStatus.getStatus());
            });

            // ConnectorStatus 에 Preparing, Charging, Finishing 상태가 하나라도 있을경우 그 상태를 리턴한다.
            if (statusList.contains(ChargePointStatus.Preparing)) {
                return ChargePointStatus.Preparing;
            } else if (statusList.contains(ChargePointStatus.Charging)) {
                return ChargePointStatus.Charging;
            } else if (statusList.contains(ChargePointStatus.Finishing)) {
                return ChargePointStatus.Finishing;
            } else {
                return this.status;
            }
        }
    }
}
