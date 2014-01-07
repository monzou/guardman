package com.github.monzou.guardman.util;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Test;

import com.github.monzou.guardman.fixture.CashFlow;
import com.github.monzou.guardman.fixture.Counterparty;
import com.github.monzou.guardman.fixture.Trade;
import com.github.monzou.guardman.fixture.Trade.Status;
import com.google.common.collect.Lists;

/**
 * BeansTest
 */
public class BeansTest {

    @Test
    public void testGetPropertyNullValue() {
        Trade trade = new Trade();
        Object status = Beans.getProperty(trade, "status");
        assertNull(status);
    }

    @Test
    public void testSetPropertyNullValue() {
        Trade trade = new Trade();
        trade.setStatus(Status.AUTHORIZED);
        Beans.setProperty(trade, "status", null);
        assertNull(trade.getStatus());
    }

    @Test
    public void testGetPropertyNonNullValue() {
        Trade trade = new Trade();
        {
            trade.setStatus(Status.AUTHORIZED);
        }
        Status status = (Status) Beans.getProperty(trade, "status");
        assertThat(status, is(Status.AUTHORIZED));
    }

    @Test
    public void testSetPropertyNonNullValue() {
        Trade trade = new Trade();
        {
            trade.setStatus(Status.AUTHORIZED);
        }
        Beans.setProperty(trade, "status", Status.PLAN);
        assertThat(trade.getStatus(), is(Status.PLAN));
    }

    @Test
    public void testGetPropertyCompositeValue() {
        Trade trade = new Trade();
        {
            Counterparty cp = new Counterparty();
            cp.setCode("CP");
            trade.setCounterparty(cp);
        }
        Counterparty cp = (Counterparty) Beans.getProperty(trade, "counterparty");
        assertNotNull(cp);
        assertThat(cp.getCode(), is("CP"));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testGetPropertyCompositeCollectionValue() {
        Trade trade = new Trade();
        {
            CashFlow cashFlow1 = new CashFlow();
            cashFlow1.setSeqNo(1);
            cashFlow1.setAmount(new BigDecimal("1"));
            CashFlow cashFlow2 = new CashFlow();
            cashFlow2.setSeqNo(2);
            cashFlow2.setAmount(new BigDecimal("2"));
            trade.setCashFlows(Lists.newArrayList(cashFlow1, cashFlow2));
        }
        List<CashFlow> cashFlows = (List<CashFlow>) Beans.getProperty(trade, "cashFlows");
        assertNotNull(cashFlows);
        assertThat(cashFlows.size(), is(2));
        assertThat(cashFlows.get(0).getSeqNo(), is(1));
        assertThat(cashFlows.get(0).getAmount(), is(new BigDecimal("1")));
        assertThat(cashFlows.get(1).getSeqNo(), is(2));
        assertThat(cashFlows.get(1).getAmount(), is(new BigDecimal("2")));
    }

}
