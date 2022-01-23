package com.chargev.eve.roaming.epit.model;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

@Data
@Entity
@Table(name = "station_mst_roaming")
public class RoamingStation {

    // 충전소 복합키
    @EmbeddedId
    private RoamingStationKey id;

    //충전소 명
    private String sname;

    //도로명 주소 우편번호
    private String spost;

    //도로명 주소
    private String saddress1;

    //도로명 주소 상세
    private String saddress2;

    //충전소 위치 설명
    private String slocation;

    //위치정보(GPS 위도,경도)
    private String sgps;

    //위치정보(GPS 위도,경도)
    private String sgps_entr;

    //운영시간
    private String sopentime;

    //개방 시간.
    private String spublictime;

    //로밍운영시간과 구분하기 위한 시간. ㅡㅡ;
    private String suseopentime;

    //주차비 무료 여부, Y:무료, N:상세참고
    @Length(max = 1)
    private String sparkingfeefree;

    //주차비 상세 정보
    private String sparkingfeedet;

    //운영기관
    private String smanagename;

    //운영기관 연락처
    @Length(max = 50)
    private String smanagecall;

    // 고속도로 여부 Y : 고속도로에 있음, 없음 여부
    @Length(max = 1)
    private String highway_yn;

    // 거점용충전소 여부 Y : 거점용 : Y, 공용 : N
    @Length(max = 1)
    private String focus_yn;

    // 회원카드사용여부 Y : 사용, N : 미사용
    @Length(max = 1)
    private String membership_yn;

    //비고
    private String sremark;

    //운영 시작일자
    private String sstartdate;

    //운영 종료일자
    private String senddate;

    //운영중지 여부, Y:중지, N:운영중
    private String sclose;

    //충전소 최초 등록일시
    @Transient
    private String sregdatetime;

    //충전소 최종 수정일시
    @Transient
    private String supddatetime;

    @Column(name = "resultcd")
    private String resultCd;

    @Column(name = "resultmsg")
    private String resultMsg;

    @Column(name = "send_dt")
    private Calendar sendDt;

    //충전기 목록
    @Transient
    private List<RoamingCharger> clist = Collections.emptyList();

//    @Transient
//    private String stationId;
//
//    public void stationIdGet(){
//        this.stationId = this.id.getCbid() + "-" + this.id.getSid();
//    }

    public void addClist(RoamingCharger roamingCharger) {
        if (clist.isEmpty()) {
            clist = new ArrayList<>();
        }

        clist.add(roamingCharger);
    }


}
