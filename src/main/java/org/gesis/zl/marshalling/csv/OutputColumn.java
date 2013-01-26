package org.gesis.zl.marshalling.csv;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention( RetentionPolicy.RUNTIME )
@Target( ElementType.FIELD )
public @interface OutputColumn {

	public static final String DEFAULT_COLUMN_NAME = "column";

	String name() default OutputColumn.DEFAULT_COLUMN_NAME;

	int position();
}
