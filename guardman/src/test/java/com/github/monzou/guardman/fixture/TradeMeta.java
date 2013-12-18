package com.github.monzou.guardman.fixture;

//CHECKSTYLE:OFF
import java.util.List;

import com.github.monzou.guardman.fixture.Trade.Status;
import com.github.monzou.guardman.model.BeanProperty;

/**
 * TradeMeta
 */
@SuppressWarnings("serial")
public final class TradeMeta implements java.io.Serializable {

    private static final TradeMeta INSTANCE = new TradeMeta();

    public static TradeMeta get() {
        return INSTANCE;
    }

    public final transient BeanProperty<Trade, Status> status = new BeanProperty<Trade, Status>() {

        /** {@inheritDoc} */
        @Override
        public String getName() {
            return "status";
        }

        /** {@inheritDoc} */
        @Override
        public Status apply(Trade bean) {
            return bean.getStatus();
        }

    };

    public final transient BeanProperty<Trade, String> tradeNo = new BeanProperty<Trade, String>() {

        /** {@inheritDoc} */
        @Override
        public String getName() {
            return "tradeNo";
        }

        /** {@inheritDoc} */
        @Override
        public String apply(Trade bean) {
            return bean.getTradeNo();
        }

    };

    public final transient BeanProperty<Trade, String> remarks = new BeanProperty<Trade, String>() {

        /** {@inheritDoc} */
        @Override
        public String getName() {
            return "remarks";
        }

        /** {@inheritDoc} */
        @Override
        public String apply(Trade bean) {
            return bean.getRemarks();
        }

    };

    public final transient BeanProperty<Trade, Counterparty> counterparty = new BeanProperty<Trade, Counterparty>() {

        /** {@inheritDoc} */
        @Override
        public String getName() {
            return "counterparty";
        }

        /** {@inheritDoc} */
        @Override
        public Counterparty apply(Trade bean) {
            return bean.getCounterparty();
        }

    };

    public final transient BeanProperty<Trade, List<CashFlow>> cashFlows = new BeanProperty<Trade, List<CashFlow>>() {

        /** {@inheritDoc} */
        @Override
        public String getName() {
            return "cashFlows";
        }

        /** {@inheritDoc} */
        @Override
        public List<CashFlow> apply(Trade bean) {
            return bean.getCashFlows();
        }

    };

    private Object readResolve() {
        return INSTANCE;
    }

    private TradeMeta() {
    }

}
