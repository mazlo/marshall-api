### About

This package defines a generic api for an annotation-driven approach in order to marshall and unmarshall data, which is stored in "fields" of static data files.

### Goals

Each data format enables the definition of fields or cells, that data is stored in. These cells are of final length. 

Engaging Java-annotations, the idea behind this api is to provide basic interfaces and methods to enable mappings of 

* data contained in certain cells of a source data file, to fields in a plain old java object (POJO), aka unmarshalling
* values of fields of POJOs to cells in the target data file, aka marshalling

This api provides only basic methods and interfaces.

There is still an implementation needed. 

### Available Implementations

Please see these packages for available implementations:

* [marshall-csv](https://github.com/mazlo/marshall-csv), provides an implementation for data stored in CSV-format (Comma Separated Values).

### Use of Annotations

The annotations are applied to fields of a plain POJO. For the mapping, two types of annotations are provided:

* `@InputField`, for unmarshalling
* `@OutputField`, for marshalling

Place these annotations on the fields of the POJO and

**implement getter- and setter-methods**. 

For instance,

    public class BeanClass {
    
        @InputField( position=0 )
        private String description;
        
        @OutputField ( name="comment" )
        private String target_description;
        
        // getter-/setter-methods
    }

### Provided Classes and Interfaces

The main interfaces are

* `Marshaller<T>`
* `Unmarshaller<T>`
* `AnnotationInterpreter<T>`

with the generic type `T`. `T` stands for the class where the annotations can be found.

For the annotations to be recognized, a so called `AnnotationInterpreter` is needed. A `DefaultAnnotationInterpreter` provides a default implementation.

A method in Factory-class `AnnotationInterpreterFactory` simplifies the creation of an instance.

    AnnotationInterpreterFactory.createDefaultAnnotationInterpreter( BeanClass.class );

### Individual Implementation

If you want to create your own implementation, e.g. for new data format, please stick to the convention to 

* Create your own interface by extending the generic interfaces `Marshaller` and `Unmarshaller`, e.g.

    `public interface NewFormatUnmarshaller<T> extends Unmarshaller<T> { ... }`

* If you need an individual interpreter for the annotations provided above, please extend the interface `AnnotationInterpreter` and provide your own implementation, e.g.

    `public interface SpecialAnnotationInterpreter<T> extends AnnotationInterpreter { ... }`
    
    and 
    
    `public class SpecialAnnotationInterpreterImpl<T> implements SpecialAnnotationInterpreter<T>`

* Finally, provide Factory-classes for simple instantiation of your Marshaller/Unmarshaller and AnnotationInterpreter

    `NewFormatUnmarshallerFactory.createNewFormatUnmarshaller( ... )`

A sample implementation is [marshall-csv](https://github.com/mazlo/marshall-csv).

Happy coding.
