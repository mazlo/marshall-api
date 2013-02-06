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

### How to Implement

The annotations are applied to fields of a plain POJO. For the mapping two types of annotations are provided:

* InputField
* OutputField

The main interfaces are

* Marshaller
* Unmarshaller
* AnnotationInterpreter

The class DefaultAnnotationInterpreter provides a default mechanism to interpret the annotations as they are applied to fields of a POJO.

If you want to create your own implementation, please stick to the convention to 

* create your own interface for your Marshaller/Unmarshaller by extending the generic interfaces
* provide Factory-classes for simple instantiation of your Marshaller/Unmarshaller and AnnotationInterpreter

A sample implementation is [marshall-csv](https://github.com/mazlo/marshall-csv).

Happy coding.
