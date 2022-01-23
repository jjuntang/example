package com.chargev.eve.roaming.epit.service;

import com.chargev.eve.roaming.epit.model.CodeDetail;
import com.chargev.eve.roaming.epit.model.RoamingCharger;
import com.chargev.eve.roaming.epit.model.RoamingStation;
import com.chargev.eve.roaming.epit.repository.CodeDetailRepository;
import com.chargev.eve.roaming.epit.repository.RoamingChargerRepository;
import com.chargev.eve.roaming.epit.repository.RoamingStationRepository;
import com.chargev.eve.roaming.epit.repository.RoamingStationRepositoryExtend;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;

@Service
@Slf4j(topic = "[NDH] RoamingService")
public class RoamingService {

    private CodeDetailRepository codeDetailRepository;
    private RoamingStationRepository roamingStationRepository;
    private RoamingChargerRepository roamingChargerRepository;

    /**
     * 충전소 정보 갱신
     */
    public void updateStationsInfo() {

        fluxBusinessId()
                .delayElements(Duration.ofMillis(200))
                .flatMap(codeDetail -> listUpdatedStations(
                        codeDetail.getFirstStrReference(),
                        codeDetail.getIdx()))
                .subscribe(this::sendStationsInfoToPOI);
    }


    /**
     * roamingBid에 해당하는 충전소와 충전기 를 조회하여 매칭 후 결과 충전소 객체를 리턴한다.
     *
     * @param businessId 사업자코드
     * @param roamingBid 로밍사업자 ID
     * @return Mono<List < RoamingStation>>
     */
    private Mono<List<RoamingStation>> listUpdatedStations(String businessId, int roamingBid) {
        log.info("Start listUpdatedStations data loading => businessId : {}, roamingIdx : {}",
                businessId, roamingBid);

        List<RoamingStation> stations = roamingStationRepository.findUpdatedByBusinessIdAndRoamingBid(
                businessId, roamingBid);

        if (stations.isEmpty()) {
            return Mono.empty();
        } else {
            List<RoamingCharger> chargers = roamingChargerRepository.findAllByBusinessIdAndRoamingBid(
                    businessId, roamingBid);

//            log.info("Target roamingStations - stations size :: {}", stations.size());
//            log.info("Target roamingStations - chargers size :: {}", chargers.size());

            // 충전소 변경 정보와 해당 충전소의 충전기 정보가 매칭되지 않을 수 있다.
            return matchedStationWithCharger(Flux.fromIterable(stations), Flux.fromIterable(chargers));
        }
    }

    /**
     * 유효한 BusinessId 전체 리스트를 조회한다.
     *
     * @return Flux<CodeDetail>
     */
    private Flux<CodeDetail> fluxBusinessId() {
        return Flux.fromIterable(codeDetailRepository.findByGroupIdxAndStatusIdx(
                CodeDetail.GROUP_IDX_BUSINESS_ID,
                CodeDetail.STATUS_IDX_AVAILABLE));
    }

    /**
     * 충전소 상태변경 정보 POI로 전송
     *
     * @param roamingStations 충전소 상태변경 객체
     */
    private void sendStationsInfoToPOI(List<RoamingStation> roamingStations) {

        // POI Proxy 서버로 전송
//        if(roamingStations.size() > 0){
//            log.info("POI Proxy  => businessId : {}, roamingIdx : {}",
//                    roamingStations.get(0).getSname(), roamingStations.get(0).getHighway_yn());
//        }


//        Flux.fromIterable(roamingStations)
//                .flatMap(roamingStation -> Mono.just(StationConverter.convert(roamingStation)))
//                .collectList()
//                .flatMap(stations -> {
//                    if (stations.isEmpty()) {
//                        return Mono.empty();
//                    }
//
//                    StationsChargers stationsChargers = new StationsChargers();
//                    stationsChargers.setStationlist(stations);
//
//                    // StationsChargersHandler 핸들러 saveStationsChargers 메소드 참조.
//                    return stationsChargersService.saveStationsChargers(Mono.just(stationsChargers))
//                            .collectList()
//                            .flatMap(stationsList -> {
//                                StationsChargers sendData = new StationsChargers();
//                                sendData.setStationlist(stationsList);
//
//                                return noticeService.noticeStationChargers(sendData)
//                                        .flatMap(poiResponse -> {
//                                            if (1000 == poiResponse.getStatus_code()) {
//                                                SaveStationsChargersResponse responseOK = new SaveStationsChargersResponse();
//                                                responseOK.setResultcd(Result.RESULT_SUCCESS);
//                                                responseOK.setResultmsg(Result.RESULT_SUCCESS_MSG);
//                                                return Result.makeServerResponse(Result.successWithData(responseOK));
//                                            } else {
//                                                return Result.makeServerResponse(Result.fail(Result.RESULT_SERVER_ERROR));
//                                            }
//                                        })
//                                        .doOnSuccess(poiResponse -> {
//                                            log.info("Send POI Server success");
//                                            Calendar sendDate = new GregorianCalendar();
//                                            List<RoamingCharger> roamingChargers = new ArrayList<>();
//                                            roamingStations.forEach(roamingStation -> {
//                                                roamingStation.setSendDt(sendDate);
//                                                roamingStation.setResultCd("100");
//
//                                                roamingStation.getClist().forEach(roamingCharger -> {
//                                                    roamingCharger.setSendDt(sendDate);
//                                                    roamingCharger.setResultCd("100");
//
//                                                    roamingChargers.add(roamingCharger);
//                                                });
//                                            });
//
//                                            // 충전소 저장
//                                            roamingStationRepository.saveAll(roamingStations);
//                                            // 충전기 저장
//                                            roamingChargerRepository.saveAll(roamingChargers);
//                                        });
//                            })
//                            .onErrorResume(Result::ServerResponseException);
//                })
//                .subscribe();
    }

    /**
     * 충전소 리스트와 충전기 리스트를 받아 각 충전소에 매칭한다.
     *
     * @param stations 충전소목록
     * @param chargers 충전기목록
     * @return Mono<List < RoamingStation>>
     */
    private Mono<List<RoamingStation>> matchedStationWithCharger(Flux<RoamingStation> stations, Flux<RoamingCharger> chargers) {
        // 충전소 변경 정보와 해당 충전소의 충전기 정보가 매칭되지 않을 수 있다.
        return stations
                .flatMap(roamingStation -> chargers
                        .filter(roamingCharger -> roamingCharger.getId().getStationIdx() == roamingStation.getId().getStationIdx())
                        .flatMap(roamingCharger -> {
//                            roamingStation.stationIdGet();
                            roamingStation.addClist(roamingCharger);
                            return Mono.just(roamingStation);
                        }))
                // 중복 충전소 제거
                .distinct(RoamingStation::getId)
                .collectList();
    }
}
