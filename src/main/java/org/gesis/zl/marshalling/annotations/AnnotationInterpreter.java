package org.gesis.zl.marshalling.annotations;

import java.util.List;
import java.util.Set;

/**
 * Interprets the annotations given in the parameterized type <i>T</i>
 * 
 * @author matthaeus
 * 
 * @param <T>
 *            The annotated bean class.
 */
public interface AnnotationInterpreter<T> {

	/**
	 * Returns the annotated class <i>T</i>.
	 * 
	 * @return
	 */
	public abstract Class<T> getAnnotatedClass();

	/**
	 * Reads the annotations on the annotated bean class. In particular, this
	 * method analyses the <b>InputField</b> annotations on fields and returns
	 * their names. The list of field names is sorted with respect to the
	 * <i>position</i>-property.
	 * 
	 * @return
	 */
	public abstract List<String> getInputFieldNames();

	/**
	 * Reads the annotations on the annotated bean class. In particular, this
	 * method analyses the <b>OutputField</b> annotations on fields and returns
	 * their names. The list of field names is sorted with respect to the
	 * <i>position</i>-property.
	 * 
	 * @return
	 */
	public abstract List<String> getOutputFieldNames();

	/**
	 * Returns the value of the <i>position</i>-property for the given field.
	 * 
	 * @param fieldName
	 * @return
	 */
	public abstract int getPositionOf(String fieldName);

	/**
	 * Returns the set of ignored values, defined in the
	 * <i>ignoredValues</i>-property for the given field.
	 * 
	 * @return
	 */
	public abstract Set<String> getIgnoredValues(String fieldName);

}