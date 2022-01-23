package com.chargev.eve.roaming.epit.repository;

import com.chargev.eve.roaming.epit.model.RoamingStation;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class RoamingStationRepositoryImpl implements RoamingStationRepositoryExtend {

    @PersistenceContext
    EntityManager em;

    /**
     * 충전소 정보 일괄등록
     *
     * @param roamingStations 로밍충전소 정보 객체
     */
    @Transactional
    public void saveAll(List<RoamingStation> roamingStations) {
        int i = 0;
        for (RoamingStation roamingStation : roamingStations) {
            em.persist(roamingStation);

            if (i % 100 == 0) {
                // Flush a batch of inserts and release memory.
                em.flush();
                em.clear();
            }
            i++;
        }
        em.flush();
        em.clear();
    }

    /**
     * 로밍사업자 ID로 전체 충전소 리스트 조회
     *
     * @param businessId 사업자코드
     * @param roamingBid 로밍사업자 ID
     * @return List<RoamingStation>
     */
    public List<RoamingStation> findAllByBusinessIdAndRoamingBid(String businessId, int roamingBid) {

        return em.createNativeQuery("SELECT " +
                        "    :businessId AS cbid" +
                        "    , sm.idx AS station_idx " +
//                "    , IF(RIGHT(sm.station_id,5)='EMPTY', sm.station_id_, sm.station_id) AS sid" +
                        "    , IF(cm.roaming_bid=460, sm.station_id, IF(sm.station_id_kp!='',sm.station_id_kp,sm.station_id_)) AS sid" +
                        "    , IFNULL(sm.name, '') AS sname" +
                        "    , IFNULL(sm.post_number, '') AS spost" +
                        "    , IFNULL(sm.address1, '') AS saddress1" +
                        "    , IFNULL(sm.address2, '') AS saddress2" +
                        "    , IFNULL(sm.detail_location, '') AS slocation" +
                        "    , CONCAT(IFNULL(sm.map_lat, ''), ',', IFNULL(sm.map_lng, '')) AS sgps" +

                        "    , CASE WHEN (tsi.new_lat_entr > 0 and tsi.new_lon_entr > 0) THEN CONCAT(IFNULL(tsi.new_lat_entr, ''), ',', IFNULL(tsi.new_lon_entr , '')) "+
                        "    WHEN (tsi.lat_entr > 0 and tsi.lon_entr > 0) THEN CONCAT(IFNULL(tsi.lat_entr, ''), ',', IFNULL(tsi.lon_entr , '')) "+
                        "    ELSE ',' "+
                        "    END as sgps_entr "+

                        "    , IF(sm.use_starttime != '' AND sm.use_endtime != '', CONCAT(sm.use_starttime, '~', sm.use_endtime), '') AS suseopentime" +
                        "    , IFNULL(sm.avail_time, '') AS sopentime" +
                        "    , IFNULL(sm.open_time, '') AS spublictime" +
                        "    , IF(sm.parking_idx = '0' OR sm.parking_idx = '', 'N', 'Y') AS sparkingfeefree" +
                        "    , IFNULL(sm.parking_amount, 0) AS sparkingfeedet" +
                        "    , IFNULL(sm.r_bname, '') AS smanagename" +
                        "    , IFNULL(sm.r_bcall, '') AS smanagecall" +
                        "    , IFNULL(sm.highway_yn, 'N') AS highway_yn" +
                        "    , IFNULL(sm.focus_yn, 'N') AS focus_yn"+
                        "    , IFNULL(IF(cd.idx IS NULL,'N','Y'), 'N') AS membership_yn"+
                        "    , IFNULL(sm.remark, '') AS sremark" +
                        "    , REPLACE(sm.open_date, '.', '') AS sstartdate" +
                        "    , REPLACE(sm.close_date, '.', '') AS senddate" +
                        "    , IF(sm.status_idx = 1, 'N', 'Y') AS sclose" +
                        "    , DATE_FORMAT(sm.create_dt, '%Y%m%d%H%i%s') AS sregdatetime" +
                        "    , DATE_FORMAT(sm.modify_dt, '%Y%m%d%H%i%s') AS supddatetime" +
                        "    , cm.idx AS company_idx" +
                        "    , '' AS resultcd" +
                        "    , '' AS resultmsg" +
                        "    , '' AS send_dt" +
                        "       FROM station_mst sm" +
                        "           JOIN company_mst cm ON sm.company_idx = cm.idx" +
                        "           LEFT JOIN tmap_station_info tsi ON tsi.station_idx = sm.idx "+
                        "           LEFT JOIN code_detail cd ON cm.roaming_bid = cd.idx and cd.group_idx=50 and cd.status_idx=1 " +
                        "       WHERE cm.roaming_bid = :roamingBid  " +
                        " and (sm.name not like '%블루핸즈%' " +
                        " or sm.name not like '%기아오토큐%' " +
                        " or sm.name not like '%닛산%' " +
                        " or sm.name not like '%재규어%' " +
                        " or sm.name not like '%르노%' " +
                        " or sm.name not like '%모텔%' " +
                        " or sm.name not like '%모비스%' " +
                        ") group by cbid,sid order by sm.idx desc", RoamingStation.class)
                .setParameter("businessId", businessId)
                .setParameter("roamingBid", roamingBid)
                .getResultList();
    }

    /**
     * 로밍사업자 ID로 변경된 충전소 리스트 조회
     *
     * @param businessId 사업자코드
     * @param roamingBid 로밍사업자 ID
     * @return List<RoamingStation>
     */
    public List<RoamingStation> findUpdatedByBusinessIdAndRoamingBid(String businessId, int roamingBid) {

        return em.createNativeQuery("SELECT A.*" +
                        "   FROM (SELECT " +
                        "    :businessId AS cbid" +
                        "    , sm.idx AS station_idx" +
//                "    , IF(RIGHT(sm.station_id,5)='EMPTY', sm.station_id_, sm.station_id) AS sid" +
                        "    , IF(cm.roaming_bid=460, sm.station_id, IF(sm.station_id_kp!='',sm.station_id_kp,sm.station_id_)) AS sid" +
                        "    , IFNULL(sm.name, '') AS sname" +
                        "    , IFNULL(sm.post_number, '') AS spost" +
                        "    , IFNULL(sm.address1, '') AS saddress1" +
                        "    , IFNULL(sm.address2, '') AS saddress2" +
                        "    , IFNULL(sm.detail_location, '') AS slocation" +
                        "    , CONCAT(IFNULL(sm.map_lat, ''), ',', IFNULL(sm.map_lng, '')) AS sgps" +
                        "    , CASE WHEN (tsi.new_lat_entr > 0 and tsi.new_lon_entr > 0) THEN CONCAT(IFNULL(tsi.new_lat_entr, ''), ',', IFNULL(tsi.new_lon_entr , '')) "+
                        "    WHEN (tsi.lat_entr > 0 and tsi.lon_entr > 0) THEN CONCAT(IFNULL(tsi.lat_entr, ''), ',', IFNULL(tsi.lon_entr , '')) "+
                        "    ELSE ',' "+
                        "    END as sgps_entr "+

                        "    , IF(sm.use_starttime != '' AND sm.use_endtime != '', CONCAT(sm.use_starttime, '~', sm.use_endtime), '') AS suseopentime" +
                        "    , IFNULL(sm.avail_time, '') AS sopentime" +
                        "    , IFNULL(sm.open_time, '') AS spublictime" +
                        "    , IF(sm.parking_idx = '0' OR sm.parking_idx = '', 'N', 'Y') AS sparkingfeefree" +
                        "    , IFNULL(sm.parking_amount, 0) AS sparkingfeedet" +
                        "    , IFNULL(sm.r_bname, '') AS smanagename" +
                        "    , IFNULL(sm.r_bcall, '') AS smanagecall" +
                        "    , IFNULL(sm.highway_yn, 'N') AS highway_yn" +
                        "    , IFNULL(sm.focus_yn, 'N') AS focus_yn"+
                        "    , IFNULL(IF(cd.idx IS NULL,'N','Y'), 'N') AS membership_yn"+
                        "    , IFNULL(sm.remark, '') AS sremark" +
                        "    , REPLACE(sm.open_date, '.', '') AS sstartdate" +
                        "    , REPLACE(sm.close_date, '.', '') AS senddate" +
                        "    , IF(sm.status_idx = 1, 'N', 'Y') AS sclose" +
                        "    , DATE_FORMAT(sm.create_dt, '%Y%m%d%H%i%s') AS sregdatetime" +
                        "    , DATE_FORMAT(sm.modify_dt, '%Y%m%d%H%i%s') AS supddatetime" +
                        "    , cm.idx AS company_idx" +
                        "    , '' AS resultcd" +
                        "    , '' AS resultmsg" +
                        "    , '' AS send_dt" +
                        "       FROM station_mst sm" +
                        "           JOIN company_mst cm ON sm.company_idx = cm.idx" +
                        "           LEFT JOIN tmap_station_info tsi ON tsi.station_idx = sm.idx "+
                        "           LEFT JOIN code_detail cd ON cm.roaming_bid = cd.idx and cd.group_idx=50 and cd.status_idx=1 " +
                        "       WHERE cm.roaming_bid = :roamingBid" +
                        " and (sm.name not like '%블루핸즈%' " +
                        " or sm.name not like '%기아오토큐%' " +
                        " or sm.name not like '%닛산%' " +
                        " or sm.name not like '%재규어%' " +
                        " or sm.name not like '%르노%' " +
                        " or sm.name not like '%모텔%' " +
                        " or sm.name not like '%모비스%' " +
                        ") group by cbid,sid " +
                        " order by sm.idx desc) A" +
                        "    WHERE (SELECT COUNT(1) FROM station_mst_roaming WHERE idx = (SELECT IFNULL(MAX(idx), 0)" +
                        "       FROM station_mst_roaming WHERE station_idx = A.station_idx " +
                        "           AND resultcd = '100'" +
                        "           AND cbid = A.cbid AND sid = A.sid" +
                        "           AND company_idx IN (SELECT idx FROM company_mst WHERE roaming_bid = :roamingBid)) " +
                        "           AND station_idx = A.station_idx AND resultcd = '100'" +
                        "           AND cbid = A.cbid AND sid = A.sid" +
                        "           AND company_idx IN (SELECT idx FROM company_mst WHERE roaming_bid = :roamingBid)" +
                        "               AND (sname = A.sname AND spost = A.spost AND saddress1 = A.saddress1" +
                        "               AND saddress2 = A.saddress2 AND slocation = A.slocation AND sgps = A.sgps AND sgps_entr = A.sgps_entr" +
                        "               AND suseopentime = A.suseopentime AND sopentime = A.sopentime" +
                        "               AND spublictime = A.spublictime AND sparkingfeefree = A.sparkingfeefree" +
                        "               AND sparkingfeedet = A.sparkingfeedet AND smanagename = A.smanagename" +
                        "               AND smanagecall = A.smanagecall AND sstartdate = A.sstartdate" +
                        "               AND senddate = A.senddate AND sclose = A.sclose AND highway_yn = A.highway_yn"+
                        "               AND focus_yn = A.focus_yn AND membership_yn = A.membership_yn "+
                        "               )) = 0", RoamingStation.class)
                .setParameter("businessId", businessId)
                .setParameter("roamingBid", roamingBid)
                .getResultList();
    }

    /**
     * 최신등록된 충전소 갱신정보를 제외한 나머지 데이터를 삭제한다.
     */
    @Transactional
    public int deleteStationInfoHistory() {
        return em.createNativeQuery("DELETE A FROM station_mst_roaming A, \n" +
                        "  (SELECT station_idx, company_idx, cbid, sid, MAX(idx) AS idx \n" +
                        "  FROM station_mst_roaming \n" +
                        "  GROUP BY station_idx, company_idx, cbid, sid) B\n" +
                        " WHERE A.station_idx = B.station_idx\n" +
                        " AND A.company_idx = B.company_idx\n" +
                        " AND A.cbid = B.cbid\n" +
                        " AND A.sid = B.sid\n" +
                        " AND A.idx < B.idx")
                .executeUpdate();
    }
}
