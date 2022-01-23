package com.chargev.eve.roaming.epit.repository;

import com.chargev.eve.roaming.epit.model.RoamingCharger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class RoamingChargerRepositoryImpl implements RoamingChargerRepositoryExtend {

    @PersistenceContext
    EntityManager em;

    /**
     * 충전기 정보 일괄등록
     *
     * @param roamingChargers 로밍충전기 정보 객체
     */
    @Transactional
    public void saveAll(List<RoamingCharger> roamingChargers) {
        int i = 0;
        for (RoamingCharger roamingCharger : roamingChargers) {
            em.persist(roamingCharger);

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
     * 로밍사업자 ID로 전체 충전기 리스트 조회
     * select ref_str1, idx code_detail where group_idx=90
     * @param businessId 사업자코드 - PI (ref_str1)
     * @param roamingBid 로밍사업자 ID - 460 (idx)
     * @return List<RoamingCharger>
     */
    public List<RoamingCharger> findAllByBusinessIdAndRoamingBid(String businessId, int roamingBid) {
        return em.createNativeQuery("SELECT\n" +
                                "     :businessId AS bid" +
//                        "    , IF(RIGHT(s.station_id, 5) = 'EMPTY', s.station_id_, s.station_id) AS sid\n" +
//                        "    , IF(RIGHT(a.charger_id, 2) = 'ID', a.charger_id_, a.charger_id) AS cid\n" +
                                "    , IF(d.roaming_bid=460, s.station_id, IF(s.station_id_kp!='',s.station_id_kp,s.station_id_)) AS sid\n" +
                                "    , IF(d.roaming_bid=460, a.charger_id, IF(a.charger_id_kp!='',a.charger_id_kp,a.charger_id)) AS cid\n" +
                                "    , IFNULL(a.name, '') AS cname\n" +
                                "    , IFNULL(b.r_type_cd, '') AS ctype\n" +
                                "    , IFNULL(c.maker_code, '') AS cmaker\n" +
                                "    , IF(d.roaming_bid = 460, 'Y', 'N') AS creservation \n" +
                                "    , IFNULL(a.detail_location, '') AS clocation\n" +
                                "    , '' AS cgps\n" +
                                "    , '' AS cgps_entr\n" +
                                "    , CASE \n" +
                                "        WHEN d.roaming_bid = 460 THEN\n" +
                                "        CASE \n" +
                                "            -- 운영중지\n" +
                                "            WHEN a.status_idx > 1 OR a.mode_stop = 'Y' THEN\n" +
                                "                6\n" +
                                "            -- 고장\n" +
                                "            WHEN a.mode_checking='Y' OR a.mode_testing='Y' OR a.mode_poweroff='Y' OR a.mode_run='N' OR a.status_emergency='N' OR a.status_in_comm='N' OR a.status_watthourmeter='N' OR a.status_car_relay='N' OR a.RCD_trip='N' THEN\n" +
                                "                5\n" +
                                "            -- 통신이상\n" +
                                "            WHEN a.tele_error = 'Y' THEN\n" +
                                "                4\n" +
                                "            -- 충전중\n" +
                                "            WHEN a.mode_charging = 'Y' THEN\n" +
                                "                2\n" +
                                "            -- 예약중\n" +
                                "            WHEN (SELECT COUNT(1) FROM use_point WHERE charger_idx=a.idx AND status_idx=345) > 0 THEN\n" +
                                "                3\n" +
                                "            -- 충전대기\n" +
                                "            WHEN a.mode_stay = 'Y' THEN\n" +
                                "                1\n" +
                                "            ELSE 6\n" +
                                "            END\n" +
                                "    -- POSCO 충전기가 아닌경우/\n" +
                                "    ELSE \n" +
                                "        CASE \n" +
                                "            -- 운영중지\n" +
                                "            WHEN a.status_idx > 1 OR a.mode_stop = 'Y' THEN\n" +
                                "                6\n" +
                                "            -- 고장\n" +
                                "            WHEN a.mode_checking = 'Y' THEN\n" +
                                "                5\n" +
                                "            -- 통신이상\n" +
                                "            WHEN a.tele_error = 'Y' THEN\n" +
                                "                4\n" +
                                "            -- 충전중\n" +
                                "            WHEN a.mode_charging = 'Y' THEN\n" +
                                "                2\n" +
                                "            -- 충전대기\n" +
                                "            WHEN a.mode_stay = 'Y' THEN\n" +
                                "                1\n" +
                                "            ELSE 6\n" +
                                "        END\n" +
                                "    END AS cstatus\n" +
                                "    , IFNULL(a.remark,'') AS cremark\n" +
                                "    , '' AS cstartdate\n" +
                                "    , '' AS cenddate\n" +
                                "    , (CASE WHEN d.roaming_bid = 460 THEN\n" +
                                "        IF(a.status_idx = 1,'N','Y')\n" +
                                "    ELSE\n" +
                                "        IF(a.r_show = 'Y', 'N', 'Y')\n" +
                                "    END) AS cclose\n" +
                                "   , DATE_FORMAT(a.create_dt,'%Y%m%d%H%i%s') AS cregdatetime\n" +
                                "   , DATE_FORMAT(a.modify_dt,'%Y%m%d%H%i%s') AS cupddatetime\n" +
                                "   , IFNULL(a.r_show,'N') AS roaming_yn\n" +
                                "   , a.idx AS charger_idx\n" +
                                "   , d.idx AS company_idx\n" +
                                "   , a.station_idx\n" +
                                "   , a.idx\n" +
                                "   , 'Y' AS status_changed\n" +
                                "   , 'Y' AS attr_changed\n" +
                                "   , '' AS resultcd\n" +
                                "   , '' AS resultmsg\n" +
                                "   , '' AS send_dt\n" +
                                "   , IF(a.kwh > 0, a.kwh, 7.00)  AS max_power \n" +
                                "FROM charger_mst a\n" +
                                "   JOIN company_mst d ON d.idx = a.company_idx\n" +
                                "   JOIN charger_class b ON b.idx = a.charger_class_idx\n" +
                                "   JOIN company_mst c ON c.idx = b.company_idx\n" +
                                "   JOIN station_mst s ON s.idx = a.station_idx\n" +
                                "   JOIN company_mst s1 ON s1.idx = s.company_idx AND s1.roaming_bid =:roamingBid\n" +
                                " WHERE (\n" +
                                " a.name not like '%블루핸즈%'\n" +
                                " or a.name not like '%기아오토큐%'\n" +
                                " or a.name not like '%닛산%'\n" +
                                " or a.name not like '%재규어%'\n" +
                                " or a.name not like '%르노%'\n" +
                                " or a.name not like '%모비스%'\n" +
                                " or a.name not like '%모텔%'\n" +
                                ") group by bid,sid,cid\n"
                        ,
                        RoamingCharger.class)
                .setParameter("businessId", businessId)
                .setParameter("roamingBid", roamingBid)
                .getResultList();
    }

    /**
     * 로밍사업자 ID로 변경된 충전기 리스트 조회
     *
     * @param businessId 사업자코드
     * @param roamingBid 로밍사업자 ID
     * @return List<RoamingCharger>
     */
    public List<RoamingCharger> findUpdatedByBusinessIdAndRoamingBid(String businessId, int roamingBid) {
        return em.createNativeQuery("SELECT * FROM \n" +
                        "  (SELECT A.*\n" +
                        "  -- 상태만 바뀐경우\n" +
                        "  , IF (B.cname = A.cname \n" +
                        "    AND B.ctype = A.ctype \n" +
                        "    AND B.cmaker = A.cmaker \n" +
                        "    AND B.creservation = A.creservation \n" +
                        "    AND B.clocation = A.clocation \n" +
                        "    AND B.cstatus != A.cstatus\n" +
                        "    AND B.roaming_yn = A.roaming_yn" +
                        "    AND B.cclose = A.cclose, 'Y', 'N') AS status_changed\n" +
                        "  -- 상태제외한 속성이 바뀐경우\n" +
                        "  , IF (B.cname=A.cname \n" +
                        "    AND B.ctype=A.ctype \n" +
                        "    AND B.cmaker=A.cmaker \n" +
                        "    AND B.creservation=A.creservation \n" +
                        "    AND B.clocation=A.clocation \n" +
                        "    AND B.roaming_yn=A.roaming_yn \n" +
                        "    AND B.max_power = A.max_power \n" +
                        "    AND B.cclose = A.cclose, 'N', 'Y') AS attr_changed\n" +

                        "  FROM (SELECT \n" +
                        "     :businessId AS bid" +
//                "    , IF(RIGHT(s.station_id, 5) = 'EMPTY', s.station_id_, s.station_id) AS sid\n" +
//                "    , IF(RIGHT(a.charger_id, 2) = 'ID', a.charger_id_, a.charger_id) AS cid\n" +
                        "    , IF(d.roaming_bid=460, s.station_id, IF(s.station_id_kp!='',s.station_id_kp,s.station_id_)) AS sid\n" +
                        "    , IF(d.roaming_bid=460, a.charger_id, IF(a.charger_id_kp!='',a.charger_id_kp,a.charger_id)) AS cid\n" +
                        "    , IFNULL(a.name, '') AS cname\n" +
                        "    , IFNULL(b.r_type_cd, '') AS ctype\n" +
                        "    , IFNULL(c.maker_code, '') AS cmaker\n" +
                        "    , IF(d.roaming_bid = 460, 'Y', 'N') AS creservation\n" +
                        "    , IFNULL(a.detail_location, '') AS clocation\n" +
                        "    , '' AS cgps\n" +
                        "    , '' AS cgps_entr\n" +
                        "    -- 차지비 충전기\n" +
                        "    , CASE \n" +
                        "      WHEN d.roaming_bid = 460 then\n" +
                        "      CASE \n" +
                        "        -- 운영중지\n" +
                        "        WHEN a.status_idx > 1 OR a.mode_stop = 'Y' THEN 6\n" +
                        "        -- 고장\n" +
                        "        WHEN a.mode_checking = 'Y' OR a.mode_testing = 'Y' OR a.mode_poweroff = 'Y' \n" +
                        "          OR a.mode_run = 'N' OR a.status_emergency = 'N' OR a.status_in_comm = 'N' \n" +
                        "          OR a.status_watthourmeter = 'N' OR a.status_car_relay = 'N' OR a.RCD_trip = 'N' THEN 5\n" +
                        "        -- 통신이상\n" +
                        "        WHEN a.tele_error = 'Y' THEN 4\n" +
                        "        -- 충전중\n" +
                        "        WHEN a.mode_charging = 'Y' THEN 2\n" +
                        "        -- 예약중\n" +
                        "        WHEN (SELECT COUNT(1) FROM use_point WHERE charger_idx = a.idx AND status_idx = 345) > 0 THEN 3\n" +
                        "        -- 충전대기\n" +
                        "        WHEN a.mode_stay = 'Y' THEN 1\n" +
                        "        ELSE 6\n" +
                        "      END\n" +
                        "    -- POSCO 충전기가 아닌경우\n" +
                        "    ELSE \n" +
                        "      CASE \n" +
                        "        -- 운영중지\n" +
                        "        WHEN a.status_idx > 1 OR a.mode_stop = 'Y' THEN 6\n" +
                        "        -- 고장\n" +
                        "        WHEN a.mode_checking = 'Y' THEN 5\n" +
                        "        -- 통신이상\n" +
                        "        WHEN a.tele_error = 'Y' THEN 4\n" +
                        "        -- 충전중\n" +
                        "        WHEN a.mode_charging = 'Y' THEN 2\n" +
                        "        -- 충전대기\n" +
                        "        WHEN a.mode_stay = 'Y' THEN 1\n" +
                        "        ELSE 6\n" +
                        "      END\n" +
                        "    END AS cstatus\n" +
                        "    , IFNULL(a.remark, '') AS cremark\n" +
                        "    , '' AS cstartdate\n" +
                        "    , '' AS cenddate\n" +
                        "    , CASE WHEN d.roaming_bid = 460 THEN\n" +
                        "      IF(a.status_idx = 1, 'N', 'Y')\n" +
                        "    ELSE\n" +
                        "      IF(a.r_show = 'Y', 'N', 'Y')\n" +
                        "    END AS cclose\n" +
                        "    , DATE_FORMAT(a.create_dt, '%Y%m%d%H%i%s') AS cregdatetime\n" +
                        "    , DATE_FORMAT(a.modify_dt, '%Y%m%d%H%i%s') AS cupddatetime\n" +
                        "    , IFNULL(a.r_show, 'N') AS roaming_yn\n" +
                        "    , a.idx AS charger_idx\n" +
                        "    , d.idx AS company_idx\n" +
                        "    , a.station_idx\n" +
                        "    , '' AS resultcd" +
                        "    , '' AS resultmsg" +
                        "    , '' AS send_dt" +
                        "    , IF(a.kwh > 0, a.kwh, 7.00)  AS max_power \n" +
                        "    FROM charger_mst a\n" +
                        "      JOIN company_mst d ON d.idx=a.company_idx\n" +
                        "      JOIN charger_class b ON b.idx=a.charger_class_idx\n" +
                        "      JOIN company_mst c ON c.idx=b.company_idx\n" +
                        "      JOIN station_mst s ON s.idx = a.station_idx\n" +
                        "      JOIN company_mst s1 ON s1.idx = s.company_idx AND s1.roaming_bid =:roamingBid \n" +
                        "    WHERE \n "+
                        "    (s.name not like '%블루핸즈%'\n" +
                        " or s.name not like '%기아오토큐%'\n" +
                        " or s.name not like '%닛산%'\n" +
                        " or s.name not like '%재규어%'\n" +
                        " or s.name not like '%르노%'\n" +
                        " or s.name not like '%모비스%'\n" +
                        " or s.name not like '%모텔%'\n" +
                        ") group by bid,sid,cid\n" +
//                "     WHERE s.company_idx IN (SELECT idx FROM company_mst WHERE roaming_bid = :roamingBid)\n" +
                        "    ) A\n" +
                        "      LEFT JOIN charger_mst_roaming B\n" +
                        "        ON B.station_idx = A.station_idx\n" +
                        "        AND B.charger_idx = A.charger_idx\n" +
                        "        AND B.company_idx = A.company_idx\n" +
                        "        AND B.cid = A.cid\n" +
                        "        AND B.resultcd = '100'\n" +
                        "        AND B.idx = (SELECT idx FROM charger_mst_roaming \n" +
                        "              WHERE charger_idx = A.charger_idx \n" +
                        "              AND resultcd = '100' \n" +
                        "              AND company_idx = A.company_idx ORDER BY idx DESC LIMIT 1)) AA\n" +
                        "  WHERE status_changed = 'Y' OR attr_changed = 'Y' and cid<>''", RoamingCharger.class)
                .setParameter("businessId", businessId)
                .setParameter("roamingBid", roamingBid)
                .getResultList();
    }

    /**
     * 최신등록된 충전기 갱신정보를 제외한 나머지 데이터를 삭제한다.
     */
    @Transactional
    public int deleteChargersInfoHistory() {
        return em.createNativeQuery("DELETE A FROM charger_mst_roaming A, \n" +
                        "  (SELECT station_idx, company_idx, charger_idx, MAX(idx) AS idx \n" +
                        "  FROM charger_mst_roaming \n" +
                        "  GROUP BY station_idx, charger_idx, company_idx) B\n" +
                        "WHERE A.station_idx = B.station_idx\n" +
                        "AND A.company_idx = B.company_idx\n" +
                        "AND A.charger_idx = B.charger_idx\n" +
                        "AND A.idx < B.idx;")
                .executeUpdate();
    }
}

