package com.qk365.bluetooth.net.response;

/**
 * Created by YanZi on 2017/7/5.
 * describe：
 * modify:
 * modify date:
 */
public class GetHistoryLogsResponse {


    /**
     * operation : sample string 1
     * operationTime : 2017-07-05 14:36:03
     * opPerson : sample string 2
     * opResult : sample string 3
     */

    private String operation;
    private String operationtime;
    private String opperson;
    private String opresult;

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getOperationtime() {
        return operationtime;
    }

    public void setOperationtime(String operationtime) {
        this.operationtime = operationtime;
    }

    public String getOpperson() {
        return opperson;
    }

    public void setOpperson(String opperson) {
        this.opperson = opperson;
    }

    public String getOpresult() {
        return opresult;
    }

    public void setOpresult(String opresult) {
        this.opresult = opresult;
    }
}
