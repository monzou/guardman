package com.github.monzou.guardman.fixture;

//CHECKSTYLE:OFF
import com.github.monzou.grinder.BeanPropertyAccessor;

/**
 * CounterpartyMeta
 */
public class CounterpartyMeta {

    public static final BeanPropertyAccessor<Counterparty, String> code = new BeanPropertyAccessor<Counterparty, String>() {

        /** {@inheritDoc} */
        @Override
        public String getName() {
            return "code";
        }

        /** {@inheritDoc} */
        @Override
        public String apply(Counterparty bean) {
            return bean.getCode();
        }

    };

    public static final BeanPropertyAccessor<Counterparty, String> name = new BeanPropertyAccessor<Counterparty, String>() {

        /** {@inheritDoc} */
        @Override
        public String getName() {
            return "name";
        }

        /** {@inheritDoc} */
        @Override
        public String apply(Counterparty bean) {
            return bean.getName();
        }

    };

}
