package com.example.jpa_ul;

public enum ChargePointErrorCode {

    ConnectorLockFailure(),

    EVCommunicationError(),

    GroundFailure(),

    HighTemperature(),

    InternalError(),

    LocalListConflict(),

    NoError(),

    OtherError(),

    OverCurrentFailure(),

    OverVoltage(),

    PowerMeterFailure(),

    PowerSwitchFailure(),

    ReaderFailure(),

    ResetFailure(),

    UnderVoltage(),

    WeakSignal(),;

//    private static final eu.chargetime.ocpp.model.core.ChargePointErrorCode[] $VALUES;
//
//    private ChargePointErrorCode(hargePointErrorCode(java.lang.String arg0, int arg1) {
//    }
}