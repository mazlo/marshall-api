package org.gesis.zl.marshalling.annotations;

public interface AnnotationInterpreter<T> {

	/**
	 * Returns the annotated class <i>T</i>.
	 * 
	 * @return
	 */
	public abstract Class<T> getAnnotatedClass();

}