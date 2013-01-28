package org.gesis.zl.marshalling.csv;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.io.StringWriter;

import org.gesis.zl.marshalling.Marshaller;
import org.junit.Before;
import org.junit.Test;

public class MarshallerTest {

	private Marshaller<Row> marshaller;
	private CsvAnnotationReader<Row> reader;

	@Before
	public void testMarshaller() throws IOException
	{
		reader = new CsvAnnotationReaderImpl<Row>( Row.class );
		assertNotNull( reader );

		marshaller = new CsvMarshaller<Row>( Row.class, new StringWriter() );
		assertNotNull( marshaller );
	}

	@Test
	public void testWriteHeaders()
	{
		marshaller.writeHeader();
		String[] columns = marshaller.getWriter().toString().trim().split( "," );

		assertEquals( "\"name\"", columns[0] );
		assertEquals( "\"description\"", columns[1] );
	}

}
