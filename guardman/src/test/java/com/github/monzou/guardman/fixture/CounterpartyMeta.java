package com.github.monzou.guardman.fixture;

//CHECKSTYLE:OFF
import com.github.monzou.guardman.model.BeanProperty;

/**
 * CounterpartyMeta
 */
@SuppressWarnings("serial")
public final class CounterpartyMeta implements java.io.Serializable {

    private static final CounterpartyMeta INSTANCE = new CounterpartyMeta();

    public static CounterpartyMeta get() {
        return INSTANCE;
    }

    public final transient BeanProperty<Counterparty, String> code = new BeanProperty<Counterparty, String>() {

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

    public final transient BeanProperty<Counterparty, String> name = new BeanProperty<Counterparty, String>() {

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

    private Object readResolve() {
        return INSTANCE;
    }

    private CounterpartyMeta() {
    }

}
