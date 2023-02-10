import com.google.protobuf.ByteString;
import com.google.protobuf.Descriptors.EnumDescriptor;
import com.google.protobuf.Descriptors.EnumValueDescriptor;
import com.google.protobuf.ProtocolMessageEnum;
import java.util.Date;
import java.util.List;
import org.modelmapper.ModelMapper;

public class JDK11011Defect {

  public static void main(String[] args) {
    System.out.println("Reproducing defect...");
    new ModelMapper()
        .typeMap(Message.class, SomeModelClass.class)
        .setConverter(mappingContext -> new SomeModelClass());
    System.out.println("Defect is NOT reproduced. Try one more time.");
  }

  // class that represents typical JavaBean 'model'
  public static class SomeModelClass {
    private String field1;
    private String field2;
    private Date field3;
    private Date field4;

    public String getField1() { return field1; }
    public void setField1(String field1) { this.field1 = field1; }
    public String getField2() { return field2; }
    public void setField2(String field2) { this.field2 = field2; }
    public Date getField3() { return field3; }
    public void setField3(Date field3) { this.field3 = field3; }
    public Date getField4() { return field4; }
    public void setField4(Date field4) { this.field4 = field4; }
  }

  /*
   * All the following code originally was an automatically generated 20k lines protobuf class that represented API message.
   * For minimizing logic of reproducing defect the following changes were made for it:
   * - all code comments are dropped
   * - implementation of all methods that are returning smth are replaced with return NULL/false/0
   * - dropped as as much as possible methods.
   * - class/method names are replaced with a meaningless names (for obfuscating purpose)
   * - Entry of the original **Protos class is included into this JDK11011Defect class
   */
  public static class Message {
    public Type1 getField1() { return null; }
    public TypeOrBuilder1 getField2() { return null; }
    public Type2 getField3() { return null; }
    public TypeOrBuilder2 getField4() { return null; }
    public Type2 getField5() { return null; }
    public TypeOrBuilder2 getField6() { return null; }
    public Type3 getField7() { return null; }
    public TypeOrBuilder3 getField8() { return null; }
    public Type4 getField9() { return null; }
    public TypeOrBuilder4 getField10() { return null; }
  }

  public interface TypeOrBuilder4 {
    List<Type5> getValueList();
    Type5 getValue(int index);
    int getValueCount();
    List<? extends Type6> getValueOrBuilderList();
    Type6 getValueOrBuilder( int index);
    List<Type5> getOldValueList();
    Type5 getOldValue(int index);
    int getOldValueCount();
    List<? extends Type6> getOldValueOrBuilderList();
    Type6 getOldValueOrBuilder(int index);
  }

  public static final class Type4 implements TypeOrBuilder4 {
    public List<Type5> getValueList() { return null; }
    public List<? extends Type6> getValueOrBuilderList() { return null; }
    public int getValueCount() { return 0; }
    public Type5 getValue(int index) { return null; }
    public Type6 getValueOrBuilder(int index) { return null; }
    public List<Type5> getOldValueList() { return null; }
    public List<? extends Type6> getOldValueOrBuilderList() { return null; }
    public int getOldValueCount() { return 0; }
    public Type5 getOldValue(int index) { return null; }
    public Type6 getOldValueOrBuilder(int index) { return null; }
  }

  public interface Type6 {
    String getField11();
    ByteString getField12();
    String getField13();
    ByteString getField14();
    boolean hasField15();
    Type7 getField16();
    Type8 getField17();
  }

  public static final class Type5 implements Type6 {
    public String getField11() { return null; }
    public ByteString getField12() { return null; }
    public String getField13() { return null; }
    public ByteString getField14() { return null; }
    public boolean hasField15() { return false; }
    public Type7 getField16() { return null; }
    public Type8 getField17() { return null; }
  }

  public interface Type8 {
    int getDay();
    int getMonth();
    int getYear();
  }

  public  static final class Type7 implements Type8 {
    public int getDay() { return 0; }
    public int getMonth() { return 0; }
    public int getYear() { return 0; }
  }

  public interface TypeOrBuilder2 {
    Type2.Enum1 getValue();
    Type2.Enum1 getOldValue();
  }

  public  static final class Type2 implements TypeOrBuilder2 {
    public Enum1 getValue() { return null; };
    public Enum1 getOldValue() { return null; };

    public enum Enum1 implements ProtocolMessageEnum {
      ENUM_VAL_1(0),
      ENUM_VAL_2(1),
      ENUM_VAL_3(2),
      UNRECOGNIZED(-1);
      final int value;

      Enum1(int value) { this.value = value; }
      public final int getNumber() { return value; }
      @Override public EnumValueDescriptor getValueDescriptor() { return null; }
      @Override public EnumDescriptor getDescriptorForType() { return null; }
    }
  }

  public interface TypeOrBuilder3 {
    Type3.Enum2 getValue();
    Type3.Enum2 getOldValue();
  }

  public  static final class Type3 implements TypeOrBuilder3 {
    public Enum2 getValue() { return null; }
    public Enum2 getOldValue() { return null; }

    public enum Enum2 implements ProtocolMessageEnum {
      ENUM_VAL_4(0),
      ENUM_VAL_5(1),
      ENUM_VAL_6(2),
      UNRECOGNIZED(-1);
      final int value;

      Enum2(int value) { this.value = value; }
      public final int getNumber() { return value; }
      @Override public EnumValueDescriptor getValueDescriptor() { return null; }
      @Override public EnumDescriptor getDescriptorForType() { return null; }
    }
  }

  public interface TypeOrBuilder1 {
    Type1.Enum3 getValue();
    Type1.Enum3 getOldValue();
  }

  public static final class Type1 implements TypeOrBuilder1 {
    public Enum3 getValue() { return null; }
    public Enum3 getOldValue() { return null; }

    public enum Enum3 {
      ENUM_VAL_7, ENUM_VAL_8, ENUM_VAL_9, UNRECOGNIZED
    }
  }

}
