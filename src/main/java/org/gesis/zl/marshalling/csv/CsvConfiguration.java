package org.gesis.zl.marshalling.csv;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention( RetentionPolicy.RUNTIME )
@Target( ElementType.TYPE )
public @interface CsvConfiguration {

	public static final char DEFAULT_SEPARATOR = ',';
	public static final boolean DEFAULT_SKIP_FIRST_LINE = true;

	char separator() default DEFAULT_SEPARATOR;

	boolean skipFirstLine() default true;

}
