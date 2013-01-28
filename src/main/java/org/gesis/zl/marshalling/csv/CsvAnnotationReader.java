package org.gesis.zl.marshalling.csv;

import java.util.Map;

import org.gesis.zl.marshalling.AnnotationReader;

/**
 * Generic interface for all Csv annotation readers. Provides methods to
 * adequately interpret csv annotations.
 * 
 * @author matthaeus
 * 
 * @param <T>
 *            The annotated class <i>T</i>.
 */
public interface CsvAnnotationReader<T> extends AnnotationReader<T> {

	/**
	 * Reads the annotations on the desired resulting bean class. In particular,
	 * this method analyses the <b>InputColum</b> annotations.
	 * 
	 * @return
	 */
	public abstract Map<Integer, String> getInputNamesByPositions();

	/**
	 * Reads the annotations on the desired resulting bean class. In particular,
	 * this method analyses the <b>OutputColum</b> annotations.
	 * 
	 * @return
	 */
	public abstract Map<String, Integer> getOutputPositionsByNames();

	/**
	 * Returns the value of the <i>skipFirstLine</i>-property, configurated in
	 * the class for which the CsvConfiguration-Annotation was applied.
	 * 
	 * @return
	 */
	public abstract boolean skipFirstLine();

	/**
	 * Returns the value of the <i>separator</i>-property, configurated in the
	 * class for which the CsvConfiguration-Annotation was applied.
	 * 
	 * @return
	 */
	public abstract char getSeparator();
}
