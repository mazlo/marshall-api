package org.gesis.zl.marshalling.annotations;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.gesis.zl.marshalling.annotations.DataType.Type;

/**
 * Default annotation interpreter. Implements all methods defined in
 * AnnotationInterpreter-interface.
 * 
 * @author matthaeus
 * 
 * @param <T>
 *            The annotated bean class.
 */
public class DefaultAnnotationInterpreter<T> implements AnnotationInterpreter<T>
{

	private Class<T> annotatedClass;

	private List<String> inputFieldNames;
	private List<String> outputFieldNames;

	public DefaultAnnotationInterpreter(Class<T> annotatedClass)
	{
		this.annotatedClass = annotatedClass;

		this.inputFieldNames = new ArrayList<String>();
		this.outputFieldNames = new ArrayList<String>();

		// temporary maps to store the positions of the fields
		Map<Integer, String> o_fieldNames = new TreeMap<Integer, String>();
		Map<Integer, String> o_columnNames = new TreeMap<Integer, String>();

		// have a look on the declared fields, i.e. attributes
		for ( Field field : annotatedClass.getDeclaredFields() )
		{
			// read the InputField property
			if ( field.isAnnotationPresent( InputField.class ) )
			{
				this.inputFieldNames.add( field.getName() );
			}
			// read the OutputField property
			else if ( field.isAnnotationPresent( OutputField.class ) )
			{
				int position = field.getAnnotation( OutputField.class ).position();

				o_fieldNames.put( position, field.getName() );

				// use field name as column name if not specified by the "name"
				// property of OutputField
				String columnName = field.getAnnotation( OutputField.class ).name();
				if ( StringUtils.equals( columnName, OutputField.DEFAULT_COLUMN_NAME ) )
					columnName = field.getName();

				o_columnNames.put( position, columnName );
			}
		}

		this.outputFieldNames = new ArrayList<String>( o_fieldNames.values() );
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.gesis.zl.marshalling.csv.CsvAnnotationReader#getInputFieldNames()
	 */
	public List<String> getInputFieldNames()
	{
		return this.inputFieldNames;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.gesis.zl.marshalling.csv.CsvAnnotationReader#getOutputFieldNames()
	 */
	public List<String> getOutputFieldNames()
	{
		return this.outputFieldNames;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.gesis.zl.marshalling.csv.CsvAnnotationReader#getPositionOf(java.lang
	 * .String)
	 */
	public int getPositionOf(String fieldName)
	{
		if ( StringUtils.isEmpty( fieldName ) )
			return -1;

		// have a look on the declared fields, i.e. attributes
		for ( Field field : annotatedClass.getDeclaredFields() )
		{
			if ( !StringUtils.equals( fieldName, field.getName() ) )
				continue;

			// read the properties
			if ( field.isAnnotationPresent( InputField.class ) )
				return field.getAnnotation( InputField.class ).position();
			else if ( field.isAnnotationPresent( OutputField.class ) )
				return field.getAnnotation( OutputField.class ).position();
		}

		return -1;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.gesis.zl.marshalling.csv.CsvAnnotationReader#getIgnoredValues(java
	 * .lang.String)
	 */
	public Set<String> getIgnoredValues(String fieldName)
	{
		try
		{
			Field field = annotatedClass.getDeclaredField( fieldName );

			String[] ignoreValues = field.getAnnotation( InputField.class ).ignoreValues();
			return new HashSet<String>( Arrays.asList( ignoreValues ) );
		} catch (SecurityException e)
		{
			return new HashSet<String>();
		} catch (NoSuchFieldException e)
		{
			return new HashSet<String>();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.gesis.zl.marshalling.annotations.AnnotationInterpreter#isBooleanType
	 * (java.lang.String)
	 */
	public boolean isBooleanType(String fieldName)
	{
		DataType dataType = getDataType( fieldName );

		if ( dataType == null )
			return false;

		if ( dataType.type() != Type.BOOLEAN )
			return false;

		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.gesis.zl.marshalling.annotations.AnnotationInterpreter#
	 * getDefaultValueForType(java.lang.String)
	 */
	public String getDefaultValueForType(String fieldName)
	{
		DataType dataType = getDataType( fieldName );

		return dataType.defaultValue();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.gesis.zl.marshalling.annotations.AnnotationInterpreter#getDataType
	 * (java.lang.String)
	 */
	public DataType getDataType(String fieldName)
	{
		try
		{
			Field field = annotatedClass.getDeclaredField( fieldName );

			DataType dataType = field.getAnnotation( InputField.class ).dataType();
			
			if ( dataType == null )
				return null;

			return dataType;
		} catch (SecurityException e)
		{
		} catch (NoSuchFieldException e)
		{
		}

		return null;
	}

}
