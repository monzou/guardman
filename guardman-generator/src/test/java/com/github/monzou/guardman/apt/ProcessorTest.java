package com.github.monzou.guardman.apt;

import java.nio.charset.Charset;

import org.junit.Before;
import org.seasar.aptina.unit.AptinaTestCase;

/**
 * ProcessorTest
 */
public class ProcessorTest extends AptinaTestCase {

    @Before
    public void setUp() throws Exception {
        addSourcePath("src/test/java");
        setCharset(Charset.forName("UTF-8"));
    }

    public void testProcessor1() throws Exception {
        addProcessor(new Processor());
        addCompilationUnit(TradeBean.class);
        compile();
        assertEqualsGeneratedSourceWithResource("com/github/monzou/guardman/apt/Processor1.expected", "com.github.monzou.guardman.apt.meta.TradeBeanMeta");
    }

    public void testProcessor2() throws Exception {
        addProcessor(new Processor());
        addCompilationUnit(TradeBean.class);
        addOption(String.format("-A%s=test.meta", Constants.Options.PACKAGE));
        compile();
        assertEqualsGeneratedSourceWithResource("com/github/monzou/guardman/apt/Processor2.expected", "test.meta.TradeBeanMeta");
    }

    public void testProcessor3() throws Exception {
        addProcessor(new Processor());
        addCompilationUnit(TradeBean.class);
        addOption(String.format("-A%s=foo", Constants.Options.PACKAGE_SUFFIX), String.format("-A%s=Foo", Constants.Options.CLASS_SUFFIX));
        compile();
        assertEqualsGeneratedSourceWithResource("com/github/monzou/guardman/apt/Processor3.expected", "com.github.monzou.guardman.apt.foo.TradeBeanFoo");
    }

}
