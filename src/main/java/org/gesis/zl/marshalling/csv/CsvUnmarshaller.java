package org.gesis.zl.marshalling.csv;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.gesis.zl.marshalling.Unmarshaller;

import au.com.bytecode.opencsv.CSVReader;

public class CsvUnmarshaller<T> implements Unmarshaller<T> {

	private CsvAnnotationReader<T> annotationReader;
	private Class<T> bean;

	private CSVReader csvReader;

	private Reader reader;

	boolean firstLineSkipped = false;

	public CsvUnmarshaller( Class<T> annotatedBean, Reader reader ) throws FileNotFoundException
	{
		this.annotationReader = new CsvAnnotationReader<T>( annotatedBean );
		this.bean = annotatedBean;

		// check if first line should be skipped
		this.firstLineSkipped = !this.annotationReader.skipFirstLine();

		this.reader = reader;
		this.csvReader = createCsvReader();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.gesis.zl.spelling.paradata.Unmarshaller#getAll()
	 */
	public List<T> getAll()
	{
		this.csvReader = createCsvReader();

		// return set of beans
		List<T> beans = new ArrayList<T>();

		while ( true )
		{
			T instance = getNext();

			// continue unmarshalling until no more data can be read
			if ( instance == null )
				break;

			beans.add( instance );
		}

		try
		{
			this.csvReader.close();
		} catch ( IOException e )
		{
			System.err.println( "Could not close the reader. The data should be able to be accessed." );
		}

		return beans;
	}

	/**
	 * Creates a new csv reader instance.
	 * 
	 * @return
	 */
	private CSVReader createCsvReader()
	{
		return new CSVReader( reader, this.annotationReader.getSeparator() );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.gesis.zl.spelling.paradata.Unmarshaller#getNext()
	 */
	public T getNext()
	{
		String[] values = getValuesFromCsvReader();

		if ( values == null || values.length == 0 )
			return null;

		// get the field mappings
		Map<Integer, String> fieldMappings = this.annotationReader.getInputNamesByPositions();

		// the bean instance, i.e. new row
		T instance;

		try
		{
			instance = bean.newInstance();

			// set the values for the fields of the bean
			for ( int i = 0; i < values.length; i++ )
			{
				String fieldName = fieldMappings.get( Integer.valueOf( i ) );

				BeanUtils.setProperty( instance, fieldName, values[i] );
			}

			return instance;

		} catch ( InstantiationException e1 )
		{
			System.err.println( "Could not instantiate bean for class:" + bean.getCanonicalName() );
		} catch ( IllegalAccessException e )
		{
			System.err.println( "Could not access setter method for a field." );
		} catch ( InvocationTargetException e )
		{
			System.err.println( "Could not invoke setter method for a field." );
		}

		return null;

	}

	/**
	 * Returns the values from the csv reader.
	 * 
	 * @return
	 */
	private String[] getValuesFromCsvReader()
	{
		String[] values = new String[0];

		try
		{
			// skip the first line if configurated so
			if ( !this.firstLineSkipped && this.annotationReader.skipFirstLine() )
			{
				this.csvReader.readNext();
				this.firstLineSkipped = true;
			}

			// get values in a row
			values = this.csvReader.readNext();
		} catch ( IOException e )
		{
			System.err.println( "Could not retrieve values from csv reader." );
		}

		return values;
	}
}
