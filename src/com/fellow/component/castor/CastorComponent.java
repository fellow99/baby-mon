package com.fellow.component.castor;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.exolab.castor.mapping.MappingException;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.ValidationException;

public interface CastorComponent {
  
  <T> T read(InputStream mapper, InputStream xml, Class<T> clazz) throws MappingException, MarshalException, ValidationException;
  
  void write(InputStream mapper, OutputStream xml, Object object) throws MappingException, MarshalException, ValidationException, IOException;

}
