package com.chargev.eve.roaming.epit.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Calendar;
import java.util.Objects;

@Data
@Entity
@Table(name = "charger_mst_roaming")
public class RoamingCharger {

    // 충전기 복합키
    @Id
    //@Indexed
    private RoamingChargerKey id;

    //충전기 아이디
    @NotEmpty(message = "cid는 필수입니다.")
    @Length(max = 20)
    private String cid;

    //충전기 명
    @NotEmpty
    @Length(max = 200)
    private String cname;

    //충전기 타입
    @NotEmpty
    @Length(min = 2, max = 2)
    private String ctype;

    //충전기 제조사
//    @NotEmpty
//    @Length(min = 1, max = 2)
    private String cmaker;

    //예약 가능 여부 Y: 예약가능, N : 예약 불가능
    @NotEmpty
    @Length(min = 1, max = 1)
    private String creservation;

    //충전기 위치 설명
    @Length(max = 200)
    private String clocation;

    //위치정보(GPS 위도, 경도)
    @Length(max = 100)
    private String cgps;

    //충전기 상태
    @Length(min = 1, max = 1)
    @NotEmpty
    private String cstatus;

    //비고
    @Length(max = 1000)
    private String cremark;

    //운영 시작일자
//    @Length(min = 8, max = 8)
    @DateTimeFormat(pattern = "yyyyMMdd")
    private String cstartdate;

    //운영 종료일자
//    @Length(min = 8, max = 8)
    @DateTimeFormat(pattern = "yyyyMMdd")
    private String cenddate;

    //운영중지 여부 Y:중지, N:운영중
    @NotEmpty
    @Length(min = 1, max = 1)
    private String cclose;

    // 로밍여부
    @JsonIgnore
    private String roamingYn;

    /**
     * cregdatetime, cupddatetime 자체 저장 메시지, 조회시만 출력한다.
     **/
    //충전기 최초 등록 일시
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Transient
    private String cregdatetime;

    //충전기 최종 수정 일시
    @Transient
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String cupddatetime;

    // 충전소 ID
    @Column(name = "sid", updatable = false, insertable = false)
    private String sid;

    // 사업자코드
    @Column(name = "bid", updatable = false, insertable = false)
    private String bid;

    // 상태변경 여부
    @Column(name = "status_changed", updatable = false, insertable = false)
    private String statusChanged;

    // 속성변경 여부
    @Column(name = "attr_changed", updatable = false, insertable = false)
    private String attrChanged;

    @Column(name = "resultcd")
    private String resultCd;

    @Column(name = "resultmsg")
    private String resultMsg;

    @Column(name = "send_dt")
    private Calendar sendDt;

    @Column(name = "max_power")
    private float maxPower;

    public boolean isChangedStatus() {
        return Objects.nonNull(this.statusChanged) && this.statusChanged.equals("Y");
    }

    public boolean isChangedAttr() {
        return Objects.nonNull(this.attrChanged) && this.attrChanged.equals("Y");
    }
}
