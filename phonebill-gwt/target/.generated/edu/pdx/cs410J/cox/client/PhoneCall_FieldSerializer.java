package edu.pdx.cs410J.cox.client;

import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.client.rpc.SerializationStreamReader;
import com.google.gwt.user.client.rpc.SerializationStreamWriter;
import com.google.gwt.user.client.rpc.impl.ReflectionHelper;

@SuppressWarnings("deprecation")
public class PhoneCall_FieldSerializer implements com.google.gwt.user.client.rpc.impl.TypeHandler {
  private static native java.util.Date getCallEnd(edu.pdx.cs410J.cox.client.PhoneCall instance) /*-{
    return instance.@edu.pdx.cs410J.cox.client.PhoneCall::callEnd;
  }-*/;
  
  private static native void setCallEnd(edu.pdx.cs410J.cox.client.PhoneCall instance, java.util.Date value) 
  /*-{
    instance.@edu.pdx.cs410J.cox.client.PhoneCall::callEnd = value;
  }-*/;
  
  private static native java.util.Date getCallStart(edu.pdx.cs410J.cox.client.PhoneCall instance) /*-{
    return instance.@edu.pdx.cs410J.cox.client.PhoneCall::callStart;
  }-*/;
  
  private static native void setCallStart(edu.pdx.cs410J.cox.client.PhoneCall instance, java.util.Date value) 
  /*-{
    instance.@edu.pdx.cs410J.cox.client.PhoneCall::callStart = value;
  }-*/;
  
  private static native java.lang.String getCallee(edu.pdx.cs410J.cox.client.PhoneCall instance) /*-{
    return instance.@edu.pdx.cs410J.cox.client.PhoneCall::callee;
  }-*/;
  
  private static native void setCallee(edu.pdx.cs410J.cox.client.PhoneCall instance, java.lang.String value) 
  /*-{
    instance.@edu.pdx.cs410J.cox.client.PhoneCall::callee = value;
  }-*/;
  
  private static native java.lang.String getCaller(edu.pdx.cs410J.cox.client.PhoneCall instance) /*-{
    return instance.@edu.pdx.cs410J.cox.client.PhoneCall::caller;
  }-*/;
  
  private static native void setCaller(edu.pdx.cs410J.cox.client.PhoneCall instance, java.lang.String value) 
  /*-{
    instance.@edu.pdx.cs410J.cox.client.PhoneCall::caller = value;
  }-*/;
  
  public static void deserialize(SerializationStreamReader streamReader, edu.pdx.cs410J.cox.client.PhoneCall instance) throws SerializationException {
    setCallEnd(instance, (java.util.Date) streamReader.readObject());
    setCallStart(instance, (java.util.Date) streamReader.readObject());
    setCallee(instance, streamReader.readString());
    setCaller(instance, streamReader.readString());
    
    edu.pdx.cs410J.AbstractPhoneCall_FieldSerializer.deserialize(streamReader, instance);
  }
  
  public static edu.pdx.cs410J.cox.client.PhoneCall instantiate(SerializationStreamReader streamReader) throws SerializationException {
    return new edu.pdx.cs410J.cox.client.PhoneCall();
  }
  
  public static void serialize(SerializationStreamWriter streamWriter, edu.pdx.cs410J.cox.client.PhoneCall instance) throws SerializationException {
    streamWriter.writeObject(getCallEnd(instance));
    streamWriter.writeObject(getCallStart(instance));
    streamWriter.writeString(getCallee(instance));
    streamWriter.writeString(getCaller(instance));
    
    edu.pdx.cs410J.AbstractPhoneCall_FieldSerializer.serialize(streamWriter, instance);
  }
  
  public Object create(SerializationStreamReader reader) throws SerializationException {
    return edu.pdx.cs410J.cox.client.PhoneCall_FieldSerializer.instantiate(reader);
  }
  
  public void deserial(SerializationStreamReader reader, Object object) throws SerializationException {
    edu.pdx.cs410J.cox.client.PhoneCall_FieldSerializer.deserialize(reader, (edu.pdx.cs410J.cox.client.PhoneCall)object);
  }
  
  public void serial(SerializationStreamWriter writer, Object object) throws SerializationException {
    edu.pdx.cs410J.cox.client.PhoneCall_FieldSerializer.serialize(writer, (edu.pdx.cs410J.cox.client.PhoneCall)object);
  }
  
}
