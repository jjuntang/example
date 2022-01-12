package com.example.jpa_ul;

public enum ChargePointStatus {

    Available(),

    Preparing(),

    Charging(),

    SuspendedEVSE(),

    SuspendedEV(),

    Finishing(),

    Reserved(),

    Unavailable(),

    Faulted(),;

//    private static final eu.chargetime.ocpp.model.core.ChargePointStatus[] $VALUES;
//
//    private ChargePointStatus(hargePointStatus(java.lang.String arg0, int arg1) {
//    }
}