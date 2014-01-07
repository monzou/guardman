package com.github.monzou.guardman.fixture;

//CHECKSTYLE:OFF
import java.util.List;

import com.github.monzou.grinder.BeanPropertyAccessor;
import com.github.monzou.guardman.fixture.Trade.Status;

/**
 * TradeMeta
 */
public class TradeMeta {

    public static final BeanPropertyAccessor<Trade, Status> status = new BeanPropertyAccessor<Trade, Status>() {

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

    public static final BeanPropertyAccessor<Trade, String> tradeNo = new BeanPropertyAccessor<Trade, String>() {

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

    public static final BeanPropertyAccessor<Trade, String> remarks = new BeanPropertyAccessor<Trade, String>() {

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

    public static final BeanPropertyAccessor<Trade, Counterparty> counterparty = new BeanPropertyAccessor<Trade, Counterparty>() {

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

    public static final BeanPropertyAccessor<Trade, List<CashFlow>> cashFlows = new BeanPropertyAccessor<Trade, List<CashFlow>>() {

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

}
