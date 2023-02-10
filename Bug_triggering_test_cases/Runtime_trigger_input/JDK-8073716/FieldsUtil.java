
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class FieldsUtil
{
  static abstract class AbstractBean
  {
    public static final String[] ALL_FIELDS = {};

    private static final short NESTED_ELEMENTS_LIMIT = 10;

    private int nestedElementsCounter;

    public boolean reachedNestedElementsLimit()
    {
      return !(nestedElementsCounter < NESTED_ELEMENTS_LIMIT);
    }

    public void resetNestedElementsCounter()
    {
      nestedElementsCounter = 0;
    }

    public int incrementAndGetNestedElementsCounter()
    {
      return ++nestedElementsCounter;
    }

    public int getNestedElementsCounter()
    {
      return nestedElementsCounter;
    }

    public void setNestedElementsCounter(final int counter)
    {
      nestedElementsCounter = counter;
    }

    public Map<String, String> appendContent(final Map<String, String> content, final String prefix)
    {
      assert content != null;
      Class<?> clazz = getClass();
      do
      {
        FieldsUtil.appendFields(this, clazz, content, prefix);
        clazz = clazz.getSuperclass();
      }
      while (isInHierarchy(clazz));
      return content;
    }

    protected boolean isInHierarchy(final Class<?> clazz)
    {
      return clazz != AbstractBean.class;
    }
  }

  static class Address extends AddressData
  {
    private final ConfirmationSpan confSpan = new ConfirmationSpan();

    private static final String[] FIELD_NAMES = { "confSpan" };
  }

  static abstract class AddressData extends AbstractBean
  {
    private static final Object[][] FIELD_NAMES_SRC = {
        // name, isBusinessValue
        { "addressId", false }, //
        { "unitId", false }, //
        { "liableId", false }, //
        { "parentId", false }, //
        { "familyId", false }, //
        { "detailedLegalFormId", true }, //
        { "name", true }, //
        { "firstName", true }, //
        { "maidenName", false }, //
        { "title", false }, //
        { "street", true }, //
        { "house", true }, //
        { "houseAdd", true }, //
        { "city", true }, //
        { "zip", true }, //
        { "zipAdd", true }, //
        { "country", true }, //
        { "cityId", false }, //
        { "administrativeCityId", false }, //
        { "streetId", false }, //
        { "houseId", false }, //
        { "nationality", false }, //
        { "placeOfBirth", false }, //
        { "careOfName", false }, //
        { "profession", false }, //
        { "classification", false }, //
        { "status", false }, //
        { "addressInactiveSince", false }, //
        { "deliveryScore", false }, //
        { "languageId", false }, //
        { "regionId", false }, //
        { "placeOfBirthZip", false }, //
        { "noAdvertising", false }, //
        { "category", false },//
        { "region", false },//
    };

    private final static ConfigurableField[] FIELD_NAMES = ConfigurableFieldUtil
        .getConfigurableFields(AddressData.class);

    protected int parentId;

    protected int familyId;

    protected long addressId;

    protected long unitId;

    protected long liableId;

    protected String firstName;

    protected String maidenName;

    protected String name;

    protected String title;

    protected String category;

    protected String city;

    protected String street;

    protected String house;

    protected String houseAdd;

    protected String zip;

    protected String zipAdd;

    protected int cityId;

    protected int streetId;

    protected int houseId;

    protected String country;

    protected String nationality;

    protected String placeOfBirth;

    protected String profession;

    protected String careOfName;

    protected Character classification;

    protected int detailedLegalFormId;

    protected Integer status;

    protected String genericPurposeFlag;

    protected boolean standardized;

    protected Date addressInactiveSince;

    private Short deliveryScore;

    short languageId;

    Date verificationDate;

    short verificationSource;

    int verificationSourceInt;

    short noAdvertising;

    int regionId;

    int administrativeCityId;

    String placeOfBirthZip;

    protected String region;
  }

  static class ConfigurableField
  {
    private final String name;

    private final Boolean isBusiness;

    public ConfigurableField(final String name, final Boolean isBusiness)
    {
      this.name = name;
      this.isBusiness = isBusiness;
    }

    public String getName()
    {
      return name;
    }

    public Boolean getIsBusiness()
    {
      return isBusiness;
    }
  }

  static class ConfigurableFieldUtil
  {
    public static ConfigurableField[] getConfigurableFields(final Class clazz)
    {
      assert clazz != null;
      try
      {
        final Field fieldNamesField = clazz.getDeclaredField("FIELD_NAMES_SRC");
        fieldNamesField.setAccessible(true);
        if (!(fieldNamesField.get(clazz) instanceof Object[][]))
        {
          return null;
        }
        final Object[][] configurableFields = (Object[][]) fieldNamesField.get(clazz);
        if (configurableFields.length == 0)
        {
          return null;
        }
        final ConfigurableField[] result = new ConfigurableField[configurableFields.length];
        int indx = 0;
        for (final Object[] field : configurableFields)
        {
          if (!(field[0] instanceof String) || !(field[1] instanceof Boolean))
          {
            throw new IllegalArgumentException(
                "Class should contain table with String and Boolean values.");
          }
          result[indx++] = new ConfigurableField((String) field[0], (Boolean) field[1]);
        }

        return result;
      }
      catch (final SecurityException e)
      {
        throw new IllegalArgumentException(e);
      }
      catch (final NoSuchFieldException e)
      {
        throw new IllegalArgumentException(e);
      }
      catch (final IllegalAccessException e)
      {
        throw new IllegalArgumentException(e);
      }
    }
  }

  static class ConfirmationSpan extends AbstractBean
  {
    private static final String[] FIELD_NAMES = {
        //
        "insertSourceInt", //
        "updateSource", //
        "updateSourceInt", //
    };

    private int insertSourceInt;

    private short updateSource;

    private int updateSourceInt;
  }

  public static void main(final String[] args)
  {
    int i = 0;
    while (true)
    {
      final Address a = new Address();
      final Map<String, String> m = new HashMap<String, String>();
      appendFields(a, AddressData.class, m, null);
      appendFields(a, Address.class, m, null);
      System.out.println(i++);
    }
  }

  private FieldsUtil()
  {}

  public static class FieldDescription
  {}

  public static class ConfigurableFieldDescription extends FieldDescription
  {
    String name;

    Boolean isBussiness;

    public ConfigurableFieldDescription(final String name, final Boolean isBussiness)
    {
      super();
      this.name = name;
      this.isBussiness = isBussiness;
    }
  }

  public static class FieldDescriptionWrapped extends FieldDescription
  {
    public final String propName;

    public final Class collectionClass;

    public final Class wrappedClass;

    public FieldDescriptionWrapped(final String propName, final Class collectionClass,
        final Class wrappedClass)
    {
      assert propName != null;
      this.propName = propName;
      this.collectionClass = collectionClass;
      this.wrappedClass = wrappedClass;
    }

    @Override
    public String toString()
    {
      return '[' + propName + ',' + collectionClass + ',' + wrappedClass + ']';
    }
  }

  private abstract static class FieldProcedure
  {
    protected final Object obj;

    protected FieldProcedure(final Object obj)
    {
      this.obj = obj;
    }

    public abstract boolean execute(Field field, FieldDescription fd) throws IllegalAccessException;
  }

  private static class FieldProcedureAppendToMap extends FieldProcedure
  {
    public static String toStringGeneric(final Object o)
    {
      if (o == null)
      {
        return null;
      }
      return o.toString();
    }

    private final Map<String, String> map;

    private final String prefix;

    public FieldProcedureAppendToMap(final Object obj, final Map<String, String> map,
        final String prefix)
    {
      super(obj);
      this.map = map;
      this.prefix = prefix;
    }

    @Override
    public boolean execute(final Field field, final FieldDescription fd)
        throws IllegalAccessException
    {
      final Object value = field.get(obj);
      if (value == obj)
      {
        map.put(getFullFieldName(field), "(this)");
      }
      else if (AbstractBean.class.isAssignableFrom(field.getType()))
      {
        if (value != null)
        {
          final AbstractBean bean = (AbstractBean) value;
          if (!bean.reachedNestedElementsLimit())
          {
            bean.setNestedElementsCounter(((AbstractBean) obj)
                .incrementAndGetNestedElementsCounter());
            bean.appendContent(map, getFullFieldName(field));
          }
          else
          {
            map.put(getFullFieldName(field), "...");
            bean.resetNestedElementsCounter();
          }
        }
      }
      else
      {
        map.put(getFullFieldName(field), toStringGeneric(value));
      }
      return true;
    }

    private String getFullFieldName(final Field field)
    {
      return prefix == null ? field.getName() : (prefix + '.' + field.getName());
    }
  }

  private static final Map<Class, Map<Field, ConfigurableFieldDescription>> CONFIGURABLE_FIELDS = new HashMap<Class, Map<Field, ConfigurableFieldDescription>>();

  private static final Map<Class, Map<Field, FieldDescription>> FIELDS = new HashMap<Class, Map<Field, FieldDescription>>();

  static
  {
    final Collection<Class> cachedClasses = new ArrayList<Class>(Arrays.asList(new Class[] {
        //
        Address.class, //
        AddressData.class, //
    }));
    for (final Class element : cachedClasses)
    {
      final Class clazz = element;
      FIELDS.put(clazz, getFields0(clazz));
    }
  }

  private static void add(final Map<Field, FieldDescription> map, final Class clazz,
      final String fieldName, final FieldDescription fd) throws NoSuchFieldException
  {
    final Field field = clazz.getDeclaredField(fieldName);
    field.setAccessible(true);
    map.put(field, fd);
  }

  public static void appendFields(final Object o, final Class clazz, final Map<String, String> map,
      final String prefix)
  {
    forEachField(clazz, new FieldProcedureAppendToMap(o, map, prefix));
  }

  private static void forEachField(final Class clazz, final FieldProcedure fp)
  {
    try
    {
      for (final Map.Entry<Field, FieldDescription> entry : getFields(clazz).entrySet())
      {
        if (!fp.execute(entry.getKey(), entry.getValue()))
        {
          return;
        }
      }
    }
    catch (final IllegalAccessException e)
    {
      throw new RuntimeException(e);
    }
  }

  private static Object getFieldNames(final Class clazz) throws NoSuchFieldException,
      IllegalAccessException
  {
    assert clazz != null;

    final Field fieldNamesField = clazz.getDeclaredField("FIELD_NAMES");
    fieldNamesField.setAccessible(true);
    return fieldNamesField.get(clazz);
  }

  private static Map<Field, FieldDescription> getFields(final Class clazz)
  {
    final Map<Field, FieldDescription> fields = FIELDS.get(clazz);
    if (fields != null)
    {
      return fields;
    }
    return getFields0(clazz);
  }

  private static void nullifyNonBusinessElements4Clazz(final Object ad)
  {
    Class clazz = ad.getClass();
    while (clazz != AbstractBean.class)
    {
      nullifyNonBusinessElements(clazz, ad);
      clazz = clazz.getSuperclass();
    }
  }

  private static void nullifyNonBusinessElementsInCollection(final Collection ad)
  {
    if (ad == null)
    {
      return;
    }
    for (final Object obj : ad)
    {
      nullifyNonBusinessElements4Clazz(obj);
    }
  }

  private static void nullifyNonBusinessElements(final Class clazz, final Object ad)
  {
    FieldsUtil.forEachField(clazz, new FieldProcedure(ad)
    {
      private boolean nullifyFields(final Collection<ConfigurableFieldDescription> fields,
          final Field field) throws IllegalArgumentException, IllegalAccessException
      {
        for (final ConfigurableFieldDescription configurableField : fields)
        {
          if (configurableField.name.equals(field.getName()))
          {
            if (Collection.class.isAssignableFrom(field.getType()))
            {
              break;
            }
            return true;
          }
        }
        field.setAccessible(true);
        if (field.getType().isPrimitive())
        {
          if (field.getType().getSimpleName().equalsIgnoreCase("boolean"))
          {
            field.setBoolean(ad, false);
          }
          else if (field.getType().getSimpleName().equalsIgnoreCase("short"))
          {
            field.setShort(ad, (short) 0);
          }
          else
          {
            field.setInt(ad, 0);
          }
        }
        else if (Collection.class.isAssignableFrom(field.getType()))
        {
          field.setAccessible(true);
          nullifyNonBusinessElementsInCollection((Collection) field.get(ad));
        }
        else
        {
          field.set(ad, null);
        }
        return true;
      }

      @Override
      public boolean execute(final Field field, final FieldDescription fd)
          throws IllegalAccessException
      {
        final Map<Field, ConfigurableFieldDescription> fields = CONFIGURABLE_FIELDS.get(clazz);
        if (fields == null || fields.isEmpty())
        {
          return false;
        }
        return nullifyFields(fields.values(), field);
      }
    });
  }

  private static Map<Field, FieldDescription> getFields0(final Class clazz)
  {
    final Map<Field, FieldDescription> fields = new LinkedHashMap<Field, FieldDescription>();
    final Map<Field, ConfigurableFieldDescription> configurableFields = new LinkedHashMap<Field, ConfigurableFieldDescription>();
    try
    {
      final Object fieldNamesObject = getFieldNames(clazz);
      if (fieldNamesObject instanceof Object[][])
      {
        final Object[][] fieldNames = (Object[][]) fieldNamesObject;
        for (final Object[] fieldName : fieldNames)
        {
          final String propName = (String) fieldName[1];
          final FieldDescription fd = propName == null ? null : //
              new FieldDescriptionWrapped(propName, //
                  fieldName.length > 2 ? (Class) fieldName[2] : null,//
                  fieldName.length > 3 ? (Class) fieldName[3] : null);
          add(fields, clazz, (String) fieldName[0], fd);
        }
      }
      else if (fieldNamesObject instanceof String[])
      {
        final String[] fieldNames;
        if (fieldNamesObject == AbstractBean.ALL_FIELDS)
        {
          final Field[] declaredFields = clazz.getDeclaredFields();
          final List<String> nonStaticFields = new ArrayList<String>();
          for (int i = 0; i < declaredFields.length; i++)
          {
            if (!Modifier.isStatic(declaredFields[i].getModifiers()))
            {
              nonStaticFields.add(declaredFields[i].getName());
            }
          }
          Collections.sort(nonStaticFields);
          fieldNames = nonStaticFields.toArray(new String[nonStaticFields.size()]);
          for (final String fieldName : fieldNames)
          {
            configurableFields.put(clazz.getDeclaredField(fieldName),
                new ConfigurableFieldDescription(fieldName, Boolean.TRUE));
            add(fields, clazz, fieldName, new ConfigurableFieldDescription(fieldName, Boolean.TRUE));
          }
        }
        else
        {
          fieldNames = (String[]) fieldNamesObject;
          for (final String fieldName : fieldNames)
          {
            add(fields, clazz, fieldName, null);
          }
        }
      }
      else if (fieldNamesObject instanceof ConfigurableField[])
      {
        final ConfigurableField[] fieldNames;
        if (fieldNamesObject == AbstractBean.ALL_FIELDS)
        {
          final Field[] declaredFields = clazz.getDeclaredFields();
          final List<String> nonStaticFields = new ArrayList<String>();
          for (int i = 0; i < declaredFields.length; i++)
          {
            if (!Modifier.isStatic(declaredFields[i].getModifiers()))
            {
              nonStaticFields.add(declaredFields[i].getName());
            }
          }
          Collections.sort(nonStaticFields);
          fieldNames = nonStaticFields.toArray(new ConfigurableField[nonStaticFields.size()]);
        }
        else
        {
          fieldNames = (ConfigurableField[]) fieldNamesObject;
        }
        for (final ConfigurableField configurableField : fieldNames)
        {
          add(fields, clazz, configurableField.getName(), new ConfigurableFieldDescription(
              configurableField.getName(), configurableField.getIsBusiness()));
          if (configurableField.getIsBusiness())
          {
            configurableFields.put(clazz.getDeclaredField(configurableField.getName()),
                new ConfigurableFieldDescription(configurableField.getName(), Boolean.TRUE));
          }
        }
      }
      else
      {
        throw new RuntimeException("invalid FIELD_NAMES: " + fieldNamesObject);
      }
    }
    catch (final NoSuchFieldException e)
    {
      throw new RuntimeException("field not found in " + clazz, e);
    }
    catch (final IllegalAccessException e)
    {
      throw new RuntimeException(e);
    }
    if (configurableFields.size() > 0)
    {
      CONFIGURABLE_FIELDS.put(clazz, configurableFields);
    }
    return fields;
  }
}

