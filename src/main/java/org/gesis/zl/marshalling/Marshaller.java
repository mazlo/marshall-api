package org.gesis.zl.marshalling;

import java.io.Writer;
import java.util.List;

/**
 * Generic interface for a Marshaller.
 * 
 * @author matthaeus
 * 
 * @param <T>
 *            The annotated class.
 */
public interface Marshaller<T> {

	/**
	 * Writes all beans in <i>beans</i> to the data source writer.
	 * 
	 * @param beans
	 */
	public abstract void writeAll( List<T> beans );

	/**
	 * Writes the fields of the <i>instance</i> according to the mapping of
	 * class <i>T</i> to an array of values. This array is then passed to the
	 * data source writer.
	 * 
	 * @param instance
	 */
	public abstract void writeNext( T instance );

	/**
	 * Writes the columns names to the data source writer.
	 * 
	 * @return
	 */
	public abstract boolean writeHeader();

	/**
	 * Returns the writer that is used in this marshaller instance.
	 * 
	 * @return
	 */
	public abstract Writer getWriter();

}