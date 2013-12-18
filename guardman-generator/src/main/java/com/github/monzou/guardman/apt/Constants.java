package com.github.monzou.guardman.apt;

/**
 * Constants
 */
final class Constants {

    static final String ANNOTATION = "com.github.monzou.guardman.annotation.Guard";

    /**
     * Options
     */
    static final class Options {

        static final String PACKAGE = "com.github.monzou.guardman.processor.package";

        static final String PACKAGE_SUFFIX = "com.github.monzou.guardman.processor.package.suffix";

        static final String CLASS_SUFFIX = "com.github.monzou.guardman.processor.class.suffix";

        private Options() {
        }

    }

    private Constants() {
    }

}
