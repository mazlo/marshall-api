package org.gesis.zl.marshalling.annotations;

public class AnnotationInterpreterFactory
{

	/**
	 * Returns an instance of DefaultAnnotationInterpreter.
	 * 
	 * @param annotatedClass
	 * @return
	 */
	public static <T> AnnotationInterpreter<T> createDefaultAnnotationInterpreter(Class<T> annotatedClass)
	{
		return new DefaultAnnotationInterpreter<T>( annotatedClass );
	}
}
