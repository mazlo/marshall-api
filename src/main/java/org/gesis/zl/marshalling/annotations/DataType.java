package org.gesis.zl.marshalling.annotations;

public @interface DataType {

	enum Type {
		BOOLEAN, STRING, INT
	};

	Type type();

	String defaultValue() default "";
}
