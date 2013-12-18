package com.github.monzou.guardman;

import static com.github.monzou.guardman.GuardMan.alphaNumeric;
import static com.github.monzou.guardman.GuardMan.digits;
import static com.github.monzou.guardman.GuardMan.eq;
import static com.github.monzou.guardman.GuardMan.matches;
import static com.github.monzou.guardman.GuardMan.max;
import static com.github.monzou.guardman.GuardMan.maxLength;
import static com.github.monzou.guardman.GuardMan.min;
import static com.github.monzou.guardman.GuardMan.minLength;
import static com.github.monzou.guardman.GuardMan.required;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.github.monzou.guardman.BeanValidationContext.PropertyValidation;
import com.github.monzou.guardman.fixture.CashFlow;
import com.github.monzou.guardman.fixture.CashFlowMeta;
import com.github.monzou.guardman.fixture.Counterparty;
import com.github.monzou.guardman.fixture.CounterpartyMeta;
import com.github.monzou.guardman.fixture.Trade;
import com.github.monzou.guardman.fixture.Trade.Status;
import com.github.monzou.guardman.fixture.TradeMeta;
import com.github.monzou.guardman.model.ValidationSeverity;
import com.github.monzou.guardman.model.Violation;
import com.google.common.collect.Lists;

/**
 * BeanValidationContextTest
 */
public class BeanValidationContextTest {

    private Trade trade;

    @Before
    public void setUp() {

        trade = new Trade();
        trade.setTradeNo("100-000001");
        trade.setStatus(Status.PLAN);
        Counterparty cp = new Counterparty();
        cp.setCode("001");
        cp.setName("Counterparty 001");
        trade.setCounterparty(cp);
        List<CashFlow> cashFlows = Lists.newArrayList();
        {
            CashFlow cashFlow = new CashFlow();
            cashFlow.setSeqNo(1);
            cashFlow.setAmount(new BigDecimal("100000000"));
            cashFlow.setStartDate(parse("2013/12/01"));
            cashFlow.setEndDate(parse("2014/05/31"));
            cashFlows.add(cashFlow);
        }
        {
            CashFlow cashFlow = new CashFlow();
            cashFlow.setSeqNo(2);
            cashFlow.setAmount(new BigDecimal("110000000"));
            cashFlow.setStartDate(parse("2014/06/01"));
            cashFlow.setEndDate(parse("2014/11/30"));
            cashFlows.add(cashFlow);
        }
        trade.setCashFlows(cashFlows);

    }

    @Test
    public void testValidation1() {
        BeanValidationContext<Trade> context = validate(trade);
        context.property(TradeMeta.get().status).validate(required(), eq(Status.AUTHORIZED).severity(ValidationSeverity.WARNING));
        assertThat(context.getViolations().size(), is(3));
        assertThat(context.getViolations("tradeNo").size(), is(1));
        assertThat(context.getViolations("tradeNo").iterator().next().getSeverity(), is(ValidationSeverity.ERROR));
        assertThat(context.getViolations("status").size(), is(1));
        assertThat(context.getViolations("status").iterator().next().getSeverity(), is(ValidationSeverity.WARNING));
        assertThat(context.getViolations("cashFlows.amount").size(), is(1));
        assertThat(context.getViolations("cashFlows.amount").iterator().next().getSeverity(), is(ValidationSeverity.ERROR));
    }

    @Test
    public void testValidation2() {
        trade.setTradeNo(null);
        trade.setCashFlows(Collections.<CashFlow> emptyList());
        BeanValidationContext<Trade> context = validate(trade);
        assertThat(context.getViolations().size(), is(2));
        assertThat(context.getViolations("tradeNo").size(), is(1));
        assertThat(context.getViolations((String) null).size(), is(1));
    }

    @Test
    public void testValidation3() {
        for (CashFlow cashFlow : trade.getCashFlows()) {
            Date startDate = cashFlow.getEndDate();
            cashFlow.setEndDate(cashFlow.getStartDate());
            cashFlow.setStartDate(startDate);
        }
        BeanValidationContext<Trade> context = validate(trade);
        assertThat(context.getViolations().size(), is(6));
        assertThat(context.getViolations("cashFlows.startDate").size(), is(2));
        assertThat(context.getViolations("cashFlows.endDate").size(), is(2));
    }

    private BeanValidationContext<Trade> validate(Trade trade) {

        TradeMeta m = TradeMeta.get();
        BeanValidationContext<Trade> context = new BeanValidationContext<Trade>(trade);
        context.property(m.tradeNo).required().validate(maxLength(10), alphaNumeric(false));
        context.property(m.remarks).validate(maxLength(1000));

        CounterpartyMeta cm = CounterpartyMeta.get();
        BeanValidationContext<Counterparty> cc = new BeanValidationContext<Counterparty>("Counterparty", trade.getCounterparty());
        cc.property(cm.code).required().validate(matches("^\\d{3}$"));
        cc.property(cm.name).required().validate(minLength(1), maxLength(100));
        context.addViolations("counterparty", cc);

        if (trade.getCashFlows().size() == 0) {
            context.error("CashFlows must not be empty.");
        }
        CashFlowMeta cfm = CashFlowMeta.get();
        for (CashFlow cashFlow : trade.getCashFlows()) {
            BeanValidationContext<CashFlow> c = //
            new BeanValidationContext<CashFlow>(String.format("CashFlow %d", cashFlow.getSeqNo()), cashFlow);
            c.property(cfm.seqNo).required().validate(max(100));
            c.property(cfm.amount).required().validate( //
                    min(BigDecimal.ZERO), //
                    max(new BigDecimal("100000000")), //
                    digits(9, 5));
            c.property(cfm.startDate).required().lt(cfm.endDate, "End Date");
            c.property(cfm.endDate).required().gt(cfm.startDate, "Start Date");
            context.addViolations("cashFlows", c);
        }

        return context;

    }

    @Test
    public void testPropertyValidation() {
        TradeMeta m = TradeMeta.get();
        BeanValidationContext<Trade> context = new BeanValidationContext<Trade>(trade);
        validateTradeNo(context, context.property(m.tradeNo));
        validateStatus(context, context.property(m.status));
        validateRemarks(context, context.property(m.remarks));
    }

    @Test
    public void testReflectionPropertyValidation() {
        BeanValidationContext<Trade> context = new BeanValidationContext<Trade>(trade);
        validateTradeNo(context, context.<String> property("tradeNo"));
        validateStatus(context, context.<Status> property("status"));
        validateRemarks(context, context.<String> property("remarks"));
    }

    private void validateTradeNo(BeanValidationContext<Trade> context, PropertyValidation<Trade, String> validation) {
        validation.required().validate(matches("\\d{3}"), maxLength(3));
        List<Violation> tradeNoViolations = context.getViolations("tradeNo");
        assertThat(tradeNoViolations.size(), is(2));
    }

    private void validateStatus(BeanValidationContext<Trade> context, PropertyValidation<Trade, Status> validation) {
        validation.required().eq(Status.AUTHORIZED);
        List<Violation> statusViolations = context.getViolations("status");
        assertThat(statusViolations.size(), is(1));
    }

    private void validateRemarks(BeanValidationContext<Trade> context, PropertyValidation<Trade, String> validation) {
        validation.required().validate(minLength(100));
        List<Violation> remarksViolations = context.getViolations("remarks");
        assertThat(remarksViolations.size(), is(1));
    }

    private Date parse(String yyyyMMdd) {
        try {
            return new SimpleDateFormat("yyyy/MM/dd").parse(yyyyMMdd);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

}
