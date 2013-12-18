package com.github.monzou.guardman.fixture;

import java.math.BigDecimal;
import java.util.Date;

/**
 * CashFlow
 */
public class CashFlow {

    private int seqNo;

    private BigDecimal amount;

    private Date startDate;

    private Date endDate;

    public int getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(int seqNo) {
        this.seqNo = seqNo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

}
