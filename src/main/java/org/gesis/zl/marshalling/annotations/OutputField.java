package org.gesis.zl.marshalling.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention( RetentionPolicy.RUNTIME )
@Target( ElementType.FIELD )
public @interface OutputField {

	public static final String DEFAULT_COLUMN_NAME = "column";

	String name() default OutputField.DEFAULT_COLUMN_NAME;

	int position();
}
