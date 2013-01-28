package org.gesis.zl.marshalling;

public interface AnnotationReader<T> {

	/**
	 * Returns the annotated class <i>T</i>.
	 * 
	 * @return
	 */
	public abstract Class<T> getAnnotatedClass();

}