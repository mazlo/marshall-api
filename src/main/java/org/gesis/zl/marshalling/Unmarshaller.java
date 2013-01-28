package org.gesis.zl.marshalling;

import java.util.List;

/**
 * Generic interface for an Unmarshaller.
 * 
 * @author matthaeus
 * 
 * @param <T>
 *            The annotated class.
 */
public interface Unmarshaller<T> {

	/**
	 * Reads all the lines from the data source and creates a bean for each
	 * line, according to the mappings of class <i>T</i>.
	 * 
	 * @return An empty list of there are no lines in the data source.
	 */
	public abstract List<T> getAll();

	/**
	 * Reads the next line from the data source and populates a fresh instance
	 * of <i>T</i> with the values. The fields of the instance of <i>T</i> are
	 * populated according to the mappings of class <i>T</i>.
	 * 
	 * @return <i>null</i> if the line could not be read from data source.
	 */
	public abstract T getNext();

}