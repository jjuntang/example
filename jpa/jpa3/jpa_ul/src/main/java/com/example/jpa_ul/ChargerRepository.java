package com.example.jpa_ul;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Calendar;
import java.util.List;

/**
 * 충전기 관리 리파지토리
 *
 * @author Dualcat
 */
//public interface ChargerRepository extends JpaRepository<Charger, Long>, ChargerRepositoryExtends {
public interface ChargerRepository extends JpaRepository<Charger, Long> {

    /**
     * 충전기 전체내역 조회
     *
     * @param useYn 사용여부
     * @return List<Charger> 충전기 리스트
     */
    List<Charger> findAllByUseYn(IsYn useYn);

    Charger findBySerialNumber(String sn);

//    @Transactional
//    Integer setStatusFor(@Param("status") ChargePointStatus status,
//                         @Param("errorCode") ChargePointErrorCode errorCode,
//                         @Param("info") String info,
//                         @Param("vendorId") String vendorId,
//                         @Param("vendorErrorCode") String vendorErrorCode,
//                         @Param("modifyDate") Calendar modifyDate,
//                         @Param("idx") Integer Idx);

}