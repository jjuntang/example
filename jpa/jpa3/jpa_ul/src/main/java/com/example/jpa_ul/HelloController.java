package com.example.jpa_ul;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.GregorianCalendar;
import java.util.List;

@Controller
@ResponseBody
public class HelloController {

    @Autowired
    private ChargerSession chargerSession;

    @Autowired
    private ChargerRepository chargerRepository;

    @RequestMapping("hello")
    public String hello() {

//        // 1. 전체 충전기 리스트 불러오기
//        List<Charger> chargerList = chargerRepository.findAllByUseYn(IsYn.Y);
//        chargerList.forEach(charger -> {
//            // 연결상태 Default N
//            charger.setConnectYn(IsYn.N);
//
//            // 신규세션으로 등록
//            chargerSession.add(charger);
//        });

        // 세션 상태 체크
//        chargerSession.getAll().forEach((uuid, charger) ->
//                //LOGGER.info(MARKER_CSMS, "Message: {}, serialNumber: {}", "Charger Session", charger.getSerialNumber()));
//                System.out.println("serialNumber : " +  charger.getSerialNumber())
//        );
//
//        Charger charger = chargerSession.getChargerBySerialNumber("TT00000011");
//        if (null != charger) {
//            return charger.getSerialNumber();
//        } else {
//            return "no found";
//        }

        // 2. serial 번호로 charger 가져오기
        Charger ch = null;
        try {
            ch = chargerRepository.findBySerialNumber("TT00000011");
        } catch (Exception e) {
            e.printStackTrace();
        }
//        if (null != ch) {
//            return ch.getSerialNumber();
//        } else {
//            return "no found";
//        }

        // 3.
        ch.setModifyDate(new GregorianCalendar());
        chargerRepository.save(ch);


        return "hello";
    }
}
