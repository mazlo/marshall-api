package org.gesis.zl.marshalling.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.gesis.zl.marshalling.annotations.DataType.Type;

@Retention( RetentionPolicy.RUNTIME )
@Target( ElementType.FIELD )
public @interface InputField {

	String name() default "column_name";

	int position();

	String[] ignoreValues() default {};

	DataType dataType() default @DataType( type = Type.STRING );

}
