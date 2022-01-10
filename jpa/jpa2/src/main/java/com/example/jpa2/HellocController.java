package com.example.jpa2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HellocController {

    @Autowired
    ChargerLastStatusDtlRepository chargerLastStatusDtlRepository;

    @GetMapping("/getParam")
    @ResponseBody
    public String getParameter() {
        //last
        String chargePointId = "OC0001001";
        //String chargePointId = "OC0001002";
        ChargerLastStatusDtl dtl = chargerLastStatusDtlRepository.findChargerLastStatusDtlByChargerId(chargePointId);
        if(dtl != null){
            dtl.setOcppMsg("BootNotification");
            chargerLastStatusDtlRepository.save(dtl);
        }else{
//            log.warn("dtl is null");
            System.out.println("dtl is null");
        }
        return "Hello Spring";
    }
}
