package com.github.monzou.guardman.fixture;

//CHECKSTYLE:OFF
import java.math.BigDecimal;
import java.util.Date;

import com.github.monzou.grinder.BeanPropertyAccessor;

/**
 * CashFlowMeta
 */
public class CashFlowMeta {

    public static final BeanPropertyAccessor<CashFlow, Integer> seqNo = new BeanPropertyAccessor<CashFlow, Integer>() {

        /** {@inheritDoc} */
        @Override
        public String getName() {
            return "seqNo";
        }

        /** {@inheritDoc} */
        @Override
        public Integer apply(CashFlow bean) {
            return bean.getSeqNo();

        }

    };

    public static final BeanPropertyAccessor<CashFlow, BigDecimal> amount = new BeanPropertyAccessor<CashFlow, BigDecimal>() {

        /** {@inheritDoc} */
        @Override
        public String getName() {
            return "amount";
        }

        /** {@inheritDoc} */
        @Override
        public BigDecimal apply(CashFlow bean) {
            return bean.getAmount();
        }

    };

    public static final BeanPropertyAccessor<CashFlow, Date> startDate = new BeanPropertyAccessor<CashFlow, Date>() {

        /** {@inheritDoc} */
        @Override
        public String getName() {
            return "startDate";
        }

        /** {@inheritDoc} */
        @Override
        public Date apply(CashFlow bean) {
            return bean.getStartDate();
        }

    };

    public static final BeanPropertyAccessor<CashFlow, Date> endDate = new BeanPropertyAccessor<CashFlow, Date>() {

        /** {@inheritDoc} */
        @Override
        public String getName() {
            return "endDate";
        }

        /** {@inheritDoc} */
        @Override
        public Date apply(CashFlow bean) {
            return bean.getEndDate();
        }

    };

}
