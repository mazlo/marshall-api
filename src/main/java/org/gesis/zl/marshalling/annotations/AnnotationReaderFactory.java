package org.gesis.zl.marshalling.annotations;

import org.gesis.zl.marshalling.csv.CsvAnnotationReader;
import org.gesis.zl.marshalling.csv.CsvAnnotationReaderImpl;

public class AnnotationReaderFactory
{

	/**
	 * Creates an annotation reader for the csv specific configurations.
	 * 
	 * @param annotatedClass
	 * @return
	 */
	public static <T> CsvAnnotationReader<T> createCsvAnnotationReader(Class<T> annotatedClass)
	{
		return new CsvAnnotationReaderImpl<T>( annotatedClass );
	}
}
