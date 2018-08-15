package edu.pdx.cs410J.cox.client;

import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.client.rpc.SerializationStreamReader;
import com.google.gwt.user.client.rpc.SerializationStreamWriter;
import com.google.gwt.user.client.rpc.impl.ReflectionHelper;

@SuppressWarnings("deprecation")
public class PhoneBill_FieldSerializer implements com.google.gwt.user.client.rpc.impl.TypeHandler {
  private static native java.util.Collection getCalls(edu.pdx.cs410J.cox.client.PhoneBill instance) /*-{
    return instance.@edu.pdx.cs410J.cox.client.PhoneBill::calls;
  }-*/;
  
  private static native void setCalls(edu.pdx.cs410J.cox.client.PhoneBill instance, java.util.Collection value) 
  /*-{
    instance.@edu.pdx.cs410J.cox.client.PhoneBill::calls = value;
  }-*/;
  
  private static native java.lang.String getCustomer(edu.pdx.cs410J.cox.client.PhoneBill instance) /*-{
    return instance.@edu.pdx.cs410J.cox.client.PhoneBill::customer;
  }-*/;
  
  private static native void setCustomer(edu.pdx.cs410J.cox.client.PhoneBill instance, java.lang.String value) 
  /*-{
    instance.@edu.pdx.cs410J.cox.client.PhoneBill::customer = value;
  }-*/;
  
  public static void deserialize(SerializationStreamReader streamReader, edu.pdx.cs410J.cox.client.PhoneBill instance) throws SerializationException {
    setCalls(instance, (java.util.Collection) streamReader.readObject());
    setCustomer(instance, streamReader.readString());
    
    edu.pdx.cs410J.AbstractPhoneBill_FieldSerializer.deserialize(streamReader, instance);
  }
  
  public static edu.pdx.cs410J.cox.client.PhoneBill instantiate(SerializationStreamReader streamReader) throws SerializationException {
    return new edu.pdx.cs410J.cox.client.PhoneBill();
  }
  
  public static void serialize(SerializationStreamWriter streamWriter, edu.pdx.cs410J.cox.client.PhoneBill instance) throws SerializationException {
    streamWriter.writeObject(getCalls(instance));
    streamWriter.writeString(getCustomer(instance));
    
    edu.pdx.cs410J.AbstractPhoneBill_FieldSerializer.serialize(streamWriter, instance);
  }
  
  public Object create(SerializationStreamReader reader) throws SerializationException {
    return edu.pdx.cs410J.cox.client.PhoneBill_FieldSerializer.instantiate(reader);
  }
  
  public void deserial(SerializationStreamReader reader, Object object) throws SerializationException {
    edu.pdx.cs410J.cox.client.PhoneBill_FieldSerializer.deserialize(reader, (edu.pdx.cs410J.cox.client.PhoneBill)object);
  }
  
  public void serial(SerializationStreamWriter writer, Object object) throws SerializationException {
    edu.pdx.cs410J.cox.client.PhoneBill_FieldSerializer.serialize(writer, (edu.pdx.cs410J.cox.client.PhoneBill)object);
  }
  
}
