package org.gesis.zl.marshalling.csv;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.gesis.zl.marshalling.annotations.CsvConfiguration;
import org.gesis.zl.marshalling.annotations.InputField;
import org.gesis.zl.marshalling.annotations.OutputField;

/**
 * Interprets the annotations <i>InputField</i> and <i>OutputField</i> in a
 * csv-like manner.
 * 
 * 
 * @author matthaeus
 * 
 * @param <T>
 *            The annotated class.
 */
public class CsvAnnotationReaderImpl<T> implements CsvAnnotationReader<T> {

	private Class<T> annotatedClass;

	public CsvAnnotationReaderImpl( Class<T> annotatedClass )
	{
		this.annotatedClass = annotatedClass;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.gesis.zl.marshalling.csv.GenericCsvAnnotationReader#
	 * getInputNamesByPositions()
	 */
	public Map<Integer, String> getInputNamesByPositions()
	{
		Map<Integer, String> namesByPositions = new HashMap<Integer, String>();

		// have a look on the declared fields, i.e. attributes
		for ( Field field : annotatedClass.getDeclaredFields() )
		{
			// skip all other annotations but InputColumn
			if ( !field.isAnnotationPresent( InputField.class ) )
				continue;

			// read the properties
			int position = field.getAnnotation( InputField.class ).position();

			namesByPositions.put( position, field.getName() );
		}

		return namesByPositions;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.gesis.zl.marshalling.csv.GenericCsvAnnotationReader#
	 * getOutputPositionsByNames()
	 */
	public Map<String, Integer> getOutputPositionsByNames()
	{
		// LinkedHashMap since the order is importance
		Map<String, Integer> positionsByNames = new LinkedHashMap<String, Integer>();

		// have a look on the declared fields, i.e. attributes
		for ( Field field : annotatedClass.getDeclaredFields() )
		{
			// skip all other annotations but OutputColumn
			if ( !field.isAnnotationPresent( OutputField.class ) )
				continue;

			// read the properties
			int position = field.getAnnotation( OutputField.class ).position();
			String name = field.getAnnotation( OutputField.class ).name();

			// if property "name" wasn't set just take the field name itself
			if ( StringUtils.isNotEmpty( name ) && !StringUtils.equals( OutputField.DEFAULT_COLUMN_NAME, name ) )
				positionsByNames.put( name, position );
			else
				positionsByNames.put( field.getName(), position );
		}

		return positionsByNames;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.gesis.zl.marshalling.csv.CsvAnnotationReader#skipFirstLine()
	 */
	public boolean skipFirstLine() {
		CsvConfiguration annotation = annotatedClass.getAnnotation( CsvConfiguration.class );

		// default is to skip the line
		if ( annotation == null )
			return CsvConfiguration.DEFAULT_SKIP_FIRST_LINE;

		return annotation.skipFirstLine();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.gesis.zl.marshalling.csv.CsvAnnotationReader#getSeparator()
	 */
	public char getSeparator()
	{
		CsvConfiguration annotation = annotatedClass.getAnnotation( CsvConfiguration.class );

		// default is to skip the line
		if ( annotation == null )
			return CsvConfiguration.DEFAULT_SEPARATOR;

		return annotation.separator();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.gesis.zl.marshalling.AnnotationReader#getAnnotatedClass()
	 */
	public Class<T> getAnnotatedClass()
	{
		return this.annotatedClass;
	}

}
