package org.gesis.zl.marshalling.csv;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * @author matthaeus
 * 
 * @param <T>
 *            The annotated class.
 */
public class CsvAnnotationReader<T> {

	private Class<T> annotatedClass;

	public CsvAnnotationReader( Class<T> annotatedClass )
	{
		this.annotatedClass = annotatedClass;
	}

	/**
	 * Reads the annotations on the desired resulting bean class. In particular,
	 * this method analyses the <b>InputColum</b> annotations.
	 * 
	 * @return
	 */
	public Map<Integer, String> getInputNamesByPositions()
	{
		Map<Integer, String> namesByPositions = new HashMap<Integer, String>();

		// have a look on the declared fields, i.e. attributes
		for ( Field field : annotatedClass.getDeclaredFields() )
		{
			// skip all other annotations but InputColumn
			if ( !field.isAnnotationPresent( InputColumn.class ) )
				continue;

			// read the properties
			int position = field.getAnnotation( InputColumn.class ).position();

			namesByPositions.put( position, field.getName() );
		}

		return namesByPositions;
	}

	/**
	 * Reads the annotations on the desired resulting bean class. In particular,
	 * this method analyses the <b>OutputColum</b> annotations.
	 * 
	 * @return
	 */
	public Map<String, Integer> getOutputPositionsByNames()
	{
		// LinkedHashMap since the order is importance
		Map<String, Integer> positionsByNames = new LinkedHashMap<String, Integer>();

		// have a look on the declared fields, i.e. attributes
		for ( Field field : annotatedClass.getDeclaredFields() )
		{
			// skip all other annotations but OutputColumn
			if ( !field.isAnnotationPresent( OutputColumn.class ) )
				continue;

			// read the properties
			int position = field.getAnnotation( OutputColumn.class ).position();
			String name = field.getAnnotation( OutputColumn.class ).name();

			// if property "name" wasn't set just take the field name itself
			if ( StringUtils.isNotEmpty( name ) && !StringUtils.equals( OutputColumn.DEFAULT_COLUMN_NAME, name ) )
				positionsByNames.put( name, position );
			else
				positionsByNames.put( field.getName(), position );
		}

		return positionsByNames;
	}

	/**
	 * Returns the value of the <i>skipFirstLine</i>-property, configurated in
	 * the class for which the CsvConfiguration-Annotation was applied.
	 * 
	 * @return
	 */
	public boolean skipFirstLine() {
		CsvConfiguration annotation = annotatedClass.getAnnotation( CsvConfiguration.class );

		// default is to skip the line
		if ( annotation == null )
			return CsvConfiguration.DEFAULT_SKIP_FIRST_LINE;

		return annotation.skipFirstLine();
	}

	/**
	 * Returns the value of the <i>separator</i>-property, configurated in the
	 * class for which the CsvConfiguration-Annotation was applied.
	 * 
	 * @return
	 */
	public char getSeparator()
	{
		CsvConfiguration annotation = annotatedClass.getAnnotation( CsvConfiguration.class );

		// default is to skip the line
		if ( annotation == null )
			return CsvConfiguration.DEFAULT_SEPARATOR;

		return annotation.separator();
	}
}
