package com.google.gwt.user.client.rpc.core.java.util.concurrent;

import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.client.rpc.SerializationStreamReader;
import com.google.gwt.user.client.rpc.SerializationStreamWriter;
import com.google.gwt.user.client.rpc.impl.ReflectionHelper;

@SuppressWarnings("deprecation")
public class CancellationException_FieldSerializer implements com.google.gwt.user.client.rpc.impl.TypeHandler {
  public static void deserialize(SerializationStreamReader streamReader, java.util.concurrent.CancellationException instance) throws SerializationException {
    
    com.google.gwt.user.client.rpc.core.java.lang.IllegalStateException_FieldSerializer.deserialize(streamReader, instance);
  }
  
  public static java.util.concurrent.CancellationException instantiate(SerializationStreamReader streamReader) throws SerializationException {
    return new java.util.concurrent.CancellationException();
  }
  
  public static void serialize(SerializationStreamWriter streamWriter, java.util.concurrent.CancellationException instance) throws SerializationException {
    
    com.google.gwt.user.client.rpc.core.java.lang.IllegalStateException_FieldSerializer.serialize(streamWriter, instance);
  }
  
  public Object create(SerializationStreamReader reader) throws SerializationException {
    return com.google.gwt.user.client.rpc.core.java.util.concurrent.CancellationException_FieldSerializer.instantiate(reader);
  }
  
  public void deserial(SerializationStreamReader reader, Object object) throws SerializationException {
    com.google.gwt.user.client.rpc.core.java.util.concurrent.CancellationException_FieldSerializer.deserialize(reader, (java.util.concurrent.CancellationException)object);
  }
  
  public void serial(SerializationStreamWriter writer, Object object) throws SerializationException {
    com.google.gwt.user.client.rpc.core.java.util.concurrent.CancellationException_FieldSerializer.serialize(writer, (java.util.concurrent.CancellationException)object);
  }
  
}
