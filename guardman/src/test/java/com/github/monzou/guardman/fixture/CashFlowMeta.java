package com.github.monzou.guardman.fixture;

//CHECKSTYLE:OFF
import java.math.BigDecimal;
import java.util.Date;

import com.github.monzou.guardman.model.BeanProperty;

/**
 * CashFlowMeta
 */
@SuppressWarnings("serial")
public final class CashFlowMeta implements java.io.Serializable {

    private static final CashFlowMeta INSTANCE = new CashFlowMeta();

    public static CashFlowMeta get() {
        return INSTANCE;
    }

    public final transient BeanProperty<CashFlow, Integer> seqNo = new BeanProperty<CashFlow, Integer>() {

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

    public final transient BeanProperty<CashFlow, BigDecimal> amount = new BeanProperty<CashFlow, BigDecimal>() {

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

    public final transient BeanProperty<CashFlow, Date> startDate = new BeanProperty<CashFlow, Date>() {

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

    public final transient BeanProperty<CashFlow, Date> endDate = new BeanProperty<CashFlow, Date>() {

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

    private Object readResolve() {
        return INSTANCE;
    }

    private CashFlowMeta() {
    }

}
