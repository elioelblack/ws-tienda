package com.tienda.exception;

import javax.xml.ws.WebFault;

/**
 *
 * @author eliezer
 */
@WebFault(name="CheckVerifyFault",
    targetNamespace="http://localhost:8080/ws-tienda/TiendaWS")
public class CheckVerifyFault extends Exception{
    private CheckFaultBean faultInfo;

    public CheckVerifyFault(String message, CheckFaultBean faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    public CheckVerifyFault(String message, CheckFaultBean faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
    }

    public CheckFaultBean getFaultInfo() {
        return faultInfo;
    }
}
