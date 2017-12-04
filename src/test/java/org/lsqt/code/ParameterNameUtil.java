package org.lsqt.code;


import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ParameterNameUtil{
  private static final Map<Method, List<String>> METHOD_PARAMETER_NAME_MAPPING = new ConcurrentHashMap<Method, List<String>>();
  private static final Map<Constructor<?>, List<String>> CONSTRUCTOR_PARAMETER_NAME_MAPPING = new ConcurrentHashMap<Constructor<?>, List<String>>();

	public static List<String> getParameterNames(Method method) {
		try {
			if (METHOD_PARAMETER_NAME_MAPPING.containsKey(method)) {
				return METHOD_PARAMETER_NAME_MAPPING.get(method);
			}

			int size = method.getParameterTypes().length;
			if (size == 0)
				return new ArrayList<String>(0);
			List<String> list = getParameterNames(method.getDeclaringClass()).get(getKey(method));
			if ((list != null) && (list.size() != size))
				return list.subList(0, size);
			return list;
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
	}

	public static List<String> getParameterNames(Constructor<?> constructor) {
		try {
			if (CONSTRUCTOR_PARAMETER_NAME_MAPPING.containsKey(constructor)) {
				return CONSTRUCTOR_PARAMETER_NAME_MAPPING.get(constructor);
			}

			int size = constructor.getParameterTypes().length;
			if (size == 0)
				return new ArrayList<String>(0);
			List<String> list =  getParameterNames(constructor.getDeclaringClass()).get(getKey(constructor));
			if ((list != null) && (list.size() != size))
				return list.subList(0, size);
			return list;
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
	}

  public static Map<String, List<String>> getParameterNames(Class<?> clazz)
    throws IOException{
    InputStream in = clazz.getResourceAsStream("/" + clazz.getName().replace('.', '/') + ".class");
    return getParamNames(in);
  }

  private static Map<String, List<String>> getParamNames(InputStream in) throws IOException {
    byte flag;
    DataInputStream dis = new DataInputStream(new BufferedInputStream(in));
    Map<String,List<String>> names = new HashMap<String,List<String>>();
    Map<Integer,String> strs = new HashMap<Integer,String>();
    dis.skipBytes(4);
    dis.skipBytes(2);
    dis.skipBytes(2);

    int constant_pool_count = dis.readUnsignedShort();
    for (int i = 0; i < constant_pool_count - 1; ++i) {
      flag = dis.readByte();
      switch (flag)
      {
      case 7:
        dis.skipBytes(2);
        break;
      case 9:
      case 10:
      case 11:
        dis.skipBytes(2);
        dis.skipBytes(2);
        break;
      case 8:
        dis.skipBytes(2);
        break;
      case 3:
      case 4:
        dis.skipBytes(4);
        break;
      case 5:
      case 6:
        dis.skipBytes(8);
        ++i;
        break;
      case 12:
        dis.skipBytes(2);
        dis.skipBytes(2);
        break;
      case 1:
        int len = dis.readUnsignedShort();
        byte[] data = new byte[len];
        dis.read(data);
        strs.put(Integer.valueOf(i + 1), new String(data, "UTF-8"));
        break;
      case 15:
        dis.skipBytes(1);
        dis.skipBytes(2);
        break;
      case 16:
        dis.skipBytes(2);
        break;
      case 18:
        dis.skipBytes(2);
        dis.skipBytes(2);
        break;
      case 2:
      case 13:
      case 14:
      case 17:
      default:
        throw new RuntimeException("Impossible!! flag=" + flag);
      }
    }

    dis.skipBytes(2);
    dis.skipBytes(2);
    dis.skipBytes(2);

    int interfaces_count = dis.readUnsignedShort();
    dis.skipBytes(2 * interfaces_count);

    int fields_count = dis.readUnsignedShort();
    for (int i = 0; i < fields_count; ++i) {
      dis.skipBytes(2);
      dis.skipBytes(2);
      dis.skipBytes(2);
      int attributes_count = dis.readUnsignedShort();
      for (int j = 0; j < attributes_count; ++j) {
        dis.skipBytes(2);
        int attribute_length = dis.readInt();
        dis.skipBytes(attribute_length);
      }

    }

    int methods_count = dis.readUnsignedShort();
    for (int i = 0; i < methods_count; ++i) {
      dis.skipBytes(2);
      String methodName = (String)strs.get(Integer.valueOf(dis.readUnsignedShort()));
      String descriptor = (String)strs.get(Integer.valueOf(dis.readUnsignedShort()));
      short attributes_count = dis.readShort();
      for (int j = 0; j < attributes_count; ++j) {
        String attrName = (String)strs.get(Integer.valueOf(dis.readUnsignedShort()));
        int attribute_length = dis.readInt();
        if ("Code".equals(attrName)) {
          dis.skipBytes(2);
          dis.skipBytes(2);
          int code_len = dis.readInt();
          dis.skipBytes(code_len);
          int exception_table_length = dis.readUnsignedShort();
          dis.skipBytes(8 * exception_table_length);

          int code_attributes_count = dis.readUnsignedShort();
          for (int k = 0; k < code_attributes_count; ++k) {
            int str_index = dis.readUnsignedShort();
            String codeAttrName = (String)strs.get(Integer.valueOf(str_index));
            int code_attribute_length = dis.readInt();
            if ("LocalVariableTable".equals(codeAttrName)) {
              int local_variable_table_length = dis.readUnsignedShort();
              List<String> varNames = new ArrayList<String>( local_variable_table_length);
              for (int l = 0; l < local_variable_table_length; ++l) {
                dis.skipBytes(2);
                dis.skipBytes(2);
                String varName = strs.get(Integer.valueOf(dis.readUnsignedShort()));
                dis.skipBytes(2);
                dis.skipBytes(2);
                if (!("this".equals(varName)))
                  varNames.add(varName);
              }
              names.put(methodName + "," + descriptor, varNames);
            } else {
              dis.skipBytes(code_attribute_length); }
          }
        } else {
          dis.skipBytes(attribute_length); }
      }
    }
    dis.close();
    return names;
  }

  private static String getKey(Object obj)
  {
    StringBuilder sb = new StringBuilder();
    if (obj instanceof Method) {
      sb.append(((Method)obj).getName()).append(',');
      getDescriptor(sb, (Method)obj);
    } else if (obj instanceof Constructor) {
      sb.append("<init>,");
      getDescriptor(sb, (Constructor<?>)obj);
    } else {
      throw new RuntimeException("Not Method or Constructor!"); }
    return sb.toString();
  }

  private static void getDescriptor(StringBuilder sb, Method method) {
    Class<?>[] arrayOfClass;
    sb.append('(');
    int j = (arrayOfClass = method.getParameterTypes()).length; for (int i = 0; i < j; ++i) { Class<?> klass = arrayOfClass[i];
      getDescriptor(sb, klass); }
    sb.append(')');
    getDescriptor(sb, method.getReturnType());
  }

  private static void getDescriptor(StringBuilder sb, Constructor<?> constructor) {
    Class<?>[] arrayOfClass;
    sb.append('(');
    int j = (arrayOfClass = constructor.getParameterTypes()).length; for (int i = 0; i < j; ++i) { Class<?> klass = arrayOfClass[i];
      getDescriptor(sb, klass); }
    sb.append(')');
    sb.append('V');
  }

  private static void getDescriptor(StringBuilder buf, Class<?> c)
  {
    Class d = c;
    while (true) {
      if (d.isPrimitive())
      {
        char car;
        if (d == Integer.TYPE)
          car = 'I';
        else if (d == Void.TYPE)
          car = 'V';
        else if (d == Boolean.TYPE)
          car = 'Z';
        else if (d == Byte.TYPE)
          car = 'B';
        else if (d == Character.TYPE)
          car = 'C';
        else if (d == Short.TYPE)
          car = 'S';
        else if (d == Double.TYPE)
          car = 'D';
        else if (d == Float.TYPE)
          car = 'F';
        else
          car = 'J';

        buf.append(car);
        return; }
      if (!(d.isArray())) break;
      buf.append('[');
      d = d.getComponentType();
    }
    buf.append('L');
    String name = d.getName();
    int len = name.length();
    for (int i = 0; i < len; ++i) {
      char car = name.charAt(i);
      buf.append((car == '.') ? '/' : car);
    }
    buf.append(';');
  }
}