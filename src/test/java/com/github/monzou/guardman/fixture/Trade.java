package com.github.monzou.guardman.fixture;

//CHECKSTYLE:OFF
import java.util.List;

/**
 * Trade
 */
public class Trade {

    public static enum Status {

        PLAN, AUTHORIZED,

    }

    private String tradeNo;

    private Status status;

    private String remarks;

    private Counterparty counterparty;

    private List<CashFlow> cashFlows;

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Counterparty getCounterparty() {
        return counterparty;
    }

    public void setCounterparty(Counterparty counterparty) {
        this.counterparty = counterparty;
    }

    public List<CashFlow> getCashFlows() {
        return cashFlows;
    }

    public void setCashFlows(List<CashFlow> cashFlows) {
        this.cashFlows = cashFlows;
    }

}
