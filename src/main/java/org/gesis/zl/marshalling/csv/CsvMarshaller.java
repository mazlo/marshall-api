package org.gesis.zl.marshalling.csv;

import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.gesis.zl.marshalling.Marshaller;

import au.com.bytecode.opencsv.CSVWriter;

public class CsvMarshaller<T> implements Marshaller<T> {

	private CsvAnnotationReaderImpl<T> annotationReader;
	private CSVWriter csvWriter;

	private Writer writer;

	public CsvMarshaller( Class<T> annotatedBean, Writer writer ) throws IOException
	{
		this.annotationReader = new CsvAnnotationReaderImpl<T>( annotatedBean );

		this.writer = writer;
		this.csvWriter = new CSVWriter( writer, this.annotationReader.getSeparator() );
	}

	/* (non-Javadoc)
	 * @see org.gesis.zl.spelling.paradata.Marshaller#writeAll(java.util.List)
	 */
	public void writeAll( List<T> beans )
	{
		this.csvWriter = createCsvWriter();

		writeHeader();

		// write all the beans to the csv file
		for ( T bean : beans )
		{
			writeNext( bean );
		}

		try
		{
			this.csvWriter.flush();
			this.csvWriter.close();
		} catch ( IOException e )
		{
			System.err.println( "Unable to flush the writers content." );
		}
	}

	/**
	 * Creates a new csv writer instance.
	 * 
	 * @return
	 */
	private CSVWriter createCsvWriter()
	{
		return new CSVWriter( this.writer, this.annotationReader.getSeparator() );
	}

	/* (non-Javadoc)
	 * @see org.gesis.zl.spelling.paradata.Marshaller#writeNext(T)
	 */
	public void writeNext( T instance )
	{
		if ( instance == null )
			return; // the instance cannot be null

		// the mapping of field names to positions
		Map<String, Integer> mappings = this.annotationReader.getOutputPositionsByNames();

		// resulting values
		String[] csvValues = new String[mappings.size()];

		// populates the array with values
		for ( String fieldName : mappings.keySet() )
		{
			// the position for the field
			int position = mappings.get( fieldName );

			if ( position < 0 )
				continue;

			String value = "";

			try
			{
				value = BeanUtils.getProperty( instance, fieldName );

			} catch ( IllegalAccessException e )
			{
				System.err.println( "Could not access the setter method for the field of the instance" );
				continue;
			} catch ( InvocationTargetException e )
			{
				System.err.println( "Could not access the setter method for the field of the instance" );
				continue;
			} catch ( NoSuchMethodException e )
			{
				System.err.println( "Could not set the value for the field:" + fieldName + ", at column position: " + position );
				continue;
			}

			// set the value for the position
			csvValues[position] = value;
		}

		this.csvWriter.writeNext( csvValues );
	}

	/* (non-Javadoc)
	 * @see org.gesis.zl.spelling.paradata.Marshaller#writeHeader()
	 */
	public boolean writeHeader()
	{
		if ( csvWriter == null )
			return false;

		Map<String, Integer> mappings = annotationReader.getOutputPositionsByNames();

		String[] headers = new String[mappings.size()];

		for ( String column : mappings.keySet() )
		{
			int position = mappings.get( column );
			headers[position] = column;
		}

		csvWriter.writeNext( headers );

		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.gesis.zl.marshalling.Marshaller#getWriter()
	 */
	public Writer getWriter()
	{
		return this.writer;
	}
}
