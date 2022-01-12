package com.example.jpa_ul;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;
import java.util.TimeZone;

@Data
@Entity
@Table(name = "charging_transaction")
public class ChargingTransaction implements Serializable {

    // 키 인덱스
    @Id
    @Column(name = "idx")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idx;

    // 충전기 키
    @Column(name = "charger_idx")
    private Integer chargerIdx;

    // ChargePoint 시리얼번호 (식별자)
    @Column(name = "serial_number")
    private String serialNumber;

    // 카드번호
    @Column(name = "id_tag")
    private String idTag;

    // 충전시작일시
    @Column(name = "start_date")
    private Calendar startDate;

    // 충전종료일시
    @Column(name = "finish_date")
    private Calendar finishDate;

    // 시작메터값
    @Column(name = "meter_start")
    private Integer meterStart;

    // 충전종료메터값
    @Column(name = "meter_stop")
    private Integer meterStop;

    // 트랜잭션 중지사유
    @Column(name = "reason")
    @Enumerated(EnumType.STRING)
    private Reason reason;

    // 충전제한시간
    @Column(name = "duration")
    private long duration;

    // 등록일자
    @Column(name = "create_date")
    private Calendar createDate;

    // 최종수정일자
    @Column(name = "modify_date")
    private Calendar modifyDate;

    public void setStartDate(Calendar startDate) {
        this.startDate = startDate;
        this.startDate.setTimeZone(TimeZone.getTimeZone("GMT+09:00"));
    }

    public void setFinishDate(Calendar finishDate) {
        this.finishDate = finishDate;
        this.finishDate.setTimeZone(TimeZone.getTimeZone("GMT+09:00"));
    }
}
