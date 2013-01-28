package org.gesis.zl.marshalling.csv;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

import org.gesis.zl.marshalling.Unmarshaller;
import org.junit.Before;
import org.junit.Test;

public class UnmarshallerTest {

	private Unmarshaller<Row> unmarshaller;
	private CsvAnnotationReader<Row> reader;

	@Before
	public void testUnmarshaller() throws FileNotFoundException
	{
		reader = new CsvAnnotationReaderImpl<Row>( Row.class );
		assertNotNull( reader );

		unmarshaller = new CsvUnmarshaller<Row>( reader, new FileReader( new File( "src/test/resources/test-input.csv" ) ) );
		assertNotNull( unmarshaller );
	}

	@Test
	public void testGetAll() throws FileNotFoundException
	{
		List<Row> all = unmarshaller.getAll();
		assertNotNull( all );
		assertEquals( 2, all.size() );
	}

	@Test
	public void testGetNext()
	{
		Row row = unmarshaller.getNext();
		assertNotNull( row );
		assertEquals( "test1", row.getName() );
		assertEquals( "test2", row.getDescription() );

		row = unmarshaller.getNext();
		assertNotNull( row );
		assertEquals( "test2", row.getName() );
		assertEquals( "test3", row.getDescription() );

		row = unmarshaller.getNext();
		assertNull( row );
	}
}
