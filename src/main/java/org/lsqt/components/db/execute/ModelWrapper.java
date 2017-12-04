package org.lsqt.components.db.execute;

import java.io.File;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.lsqt.components.db.DbException;
import org.lsqt.components.db.IdGenerator;
import org.lsqt.components.db.annotation.Column;
import org.lsqt.components.db.annotation.Gid;
import org.lsqt.components.db.annotation.Id;
import org.lsqt.components.db.annotation.Id.Type ;
import org.lsqt.components.db.annotation.UpdateTime;
import org.lsqt.components.db.annotation.Table;
import org.lsqt.components.db.execute.util.CacheReflectUtil;
import org.lsqt.components.util.bean.BeanUtil;
import org.lsqt.components.util.collection.ArrayUtil;
import org.lsqt.components.util.lang.StringUtil;

/**
 * 模型对象与数据库表映射的元信息
 * @author yuanmm
 *
 */
public class ModelWrapper {
	private  static final Logger log = Logger.getLogger(ModelWrapper.class.getName());

	private Object originalObject; // 当前原始模型对象句柄
	private Class<?> type ;
	
	private final IdGenerator idGenerator = new AnnotationIdGenerator() ;
	
	public ModelWrapper(Object originalObject) {
		this.originalObject = originalObject;
		this.type = originalObject.getClass();
		
	}
	
	public void toGernarteID(){
		if(isDbGernarteID()) return ;
		
		Type tp = getIdGenerateType();
		if(Type.NANOTIME == tp){
			setIdValue(System.nanoTime());
		}else if(Type.UUID64 == tp) {
			setIdValue(idGenerator.getUUID64());
		} else if(Type.UUID58 == tp) {
			setIdValue(idGenerator.getUUID58());
		} else {
			throw new DbException("unsupport id gernate type, please choose Auto、 UUID32、NANOTIME、UUID58");
		}
	}
	
	public Object toOriginalObject() {
		return originalObject;
	}
	
	
	public String getSchema() {
		Table table = type.getAnnotation(Table.class);
		if ((table != null) && (table.schema() != null)) {
			return  table.schema();
		}

		return null ;
	}

	public String getTable() {
		Table table = type.getAnnotation(Table.class);
		return table.name();
		
	}
	
	public String getFullTable() {
		return getFullTable(type);
	}
	
	public static String getFullTable(Class<?> type) {
		Table table = type.getAnnotation(Table.class);
		if (table != null && StringUtil.isNotBlank(table.name())) {
			if (StringUtil.isBlank(table.schema())) {
				return table.name();
			}

			if (StringUtil.isNotBlank(table.schema(), table.name())) {
				return table.schema() + "." + table.name();
			}
		}
		return null;
	}
	
	public static String getIdColumn(Class<?> type) {
		final String idName = "id";
		List<Field> list = CacheReflectUtil.getBeanField(type);
		if(list == null || list.size() == 0) return null;
		
		for(Field f: list) {
			Id id=f.getAnnotation(Id.class);
			if(id!=null){
				return "".equals(id.name()) ? idName:id.name();
			}
		}
		return null;
	}
	
	public String getIdFieldName() {
		List<Field> list =	CacheReflectUtil.getBeanField(type);
		if(list == null || list.size() == 0) return null;
		
		for(Field f: list) {
			if(f.isAnnotationPresent(Id.class)){
				return f.getName();
			}
		}
		return  null;
	}
	
	public String getIdColumn() {
		return getIdColumn(type);
	}

	public Type getIdGenerateType() {
		List<Field> list = CacheReflectUtil.getBeanField(type);
		if(list == null || list.size() == 0) return null;
		
		for(Field f: list) {
			Id id=f.getAnnotation(Id.class);
			if(id!=null){
				return id.type();
			}
		}
		
		return null;
	}
	
	 

	public java.io.Serializable getIdValue() {
		List<Field> list = CacheReflectUtil.getBeanField(type);
		if(list == null || list.size() == 0) return null;
		
		for(Field f: list) {
			Id id=f.getAnnotation(Id.class);
			if (id != null) {
				boolean isAccess = f.isAccessible();
				try {
					f.setAccessible(true);
					return (Serializable) f.get(this.originalObject);
				} catch (Exception ex) {
					throw new DbException("can't get id value from " + type.getName(), ex);
				} finally {
					f.setAccessible(isAccess);
				}
			}
		}
		
		return null;
	}

	public void setIdValue(Serializable idValue) {
		List<Field> list = CacheReflectUtil.getBeanField(type);
		if(list == null || list.size() == 0) return;
		
		for(Field f: list) {
			Id id=f.getAnnotation(Id.class);
			if(id!=null){
				boolean isAccess = f.isAccessible() ;
				try {
					f.setAccessible(true);
					f.set(this.originalObject, idValue);
					break;
				} catch (Exception ex) {
					throw new DbException("can't set id value for "+type.getName(),ex);
				} finally {
					f.setAccessible(isAccess);
				}
			}
		}
	}
	
	
	/**
	 * 当前模型id字段是否为DB层自增长
	 * @return
	 */
	public boolean isDbGernarteID(){
		Type idType = getIdGenerateType();
		if (idType == Type.AUTO) {
			return true;
		} else if (idType == Type.UUID58 ||idType == Type.UUID64 ||idType == Type.NANOTIME) {
			return false;
		} else {
			throw new DbException("unsupport id generate type, please choose AUTO、UUID58、UUID32、NANOTIME~!");
		}
	} 
	

	/**
	 * 跟据指定的属性生成当前模型的insert的SQL语句
	 * 注：id字段是否指定值,根据注解类型判断
	 * @param prop
	 * @return
	 */
	public String getInsertSql(String ...  prop) {
		List<Field> rs = getField(prop);
		
		boolean isDbGernerate = isDbGernarteID();
		
		List<String> rt = new ArrayList<>(); // 域所对应的数据库字段
		for (Field f : rs) {
			if (f.isAnnotationPresent(Column.class)) {
				Column col = f.getAnnotation(Column.class);
				rt.add(col.name());
			} else if (f.isAnnotationPresent(Id.class) && !isDbGernerate) {
				Id col = f.getAnnotation(Id.class);
				rt.add(col.name());
			} else if (f.isAnnotationPresent(Gid.class)) {
				Gid gid = f.getAnnotation(Gid.class);
				rt.add(gid.name());
			} else if (f.isAnnotationPresent(UpdateTime.class)) {
				UpdateTime lastModified = f.getAnnotation(UpdateTime.class);
				rt.add(lastModified.name());
			}
		}
		
		
		String sqlFmt = "insert into %s (%s) values (%s)";
		String sql = String.format(sqlFmt, getFullTable(), ArrayUtil.join(rt, ","),
				getParamHolder(rt.toArray()));

		return sql;
	}

	/**
	 * 跟据给定的属性名获取域，用于生成insert和update语句
	 * 注：id字段是否指定值,根据注解类型判断
	 * @param prop
	 * @return
	 */
	private List<Field> getField(String... prop) {
		boolean isFull = false;
		if (prop == null || prop.length == 0) {
			isFull = true;
		}
		
		@SuppressWarnings("unchecked")
		List<Field> fs = ArrayUtil.EMPTY_LIST;
		if (isFull) {
			fs = CacheReflectUtil.getBeanField(type);
		} else {
			fs = CacheReflectUtil.getBeanField(type, Arrays.asList(prop).toArray(new String[prop.length]));
		}

		if(fs == null || fs.size()==0) return null;
		
		List<Field> result = new ArrayList<>();
		
		
		boolean isDbGernarteID = !isDbGernarteID(); // 是否包含id字段
		if (isDbGernarteID) {
			for (Field f : fs) {
				if (f.isAnnotationPresent(Id.class) || f.isAnnotationPresent(Column.class)
						|| f.isAnnotationPresent(Gid.class) || f.isAnnotationPresent(UpdateTime.class)) {
					result.add(f);
				}
			}
			return result;
		}
		
		List<Field> rs = new ArrayList<>();
		for (Field f : fs) {
			if(f.isAnnotationPresent(Id.class)) continue;
			rs.add(f);
		}
		
		//只获取注解了@ID和@Column、@Gid、@LastModified的字段,  @Parent和@Child单独处理
		for (Field f : rs) {
			if (f.isAnnotationPresent(Id.class) || f.isAnnotationPresent(Column.class)
					|| f.isAnnotationPresent(Gid.class) || f.isAnnotationPresent(UpdateTime.class)) {
				result.add(f);
			}
		}
		return result;
	}
	
	private static String getParamHolder(Object[] paramValues) {
		String holdString = "";
		int holdLength = paramValues.length;
		for (int i = 0; i < holdLength; ++i)
			if (i != holdLength - 1)
				holdString = "?," + holdString;
			else
				holdString = holdString + "?";

		return holdString;
	}
	
	/**
	 * 根据给定的属性获取当前模型的insert语句值
	 * @param prop
	 * @return
	 */
	public Object [] getInsertValues(String ...prop) {
		List<Field> fs = getField(prop);
		if(fs == null || fs.size()==0) return null;
		
		List<Object> paramValues = new ArrayList<>();
		for(Field f: fs) {
			boolean isAccess = f.isAccessible();
			try{
				f.setAccessible(true);
				Object value = null;
				if(f.isAnnotationPresent(UpdateTime.class)){ // 最后修改时间
					f.set(this.originalObject, generateValueLastModify(f));
					paramValues.add(generateValueLastModify(f));
					continue;
				}
				
				if(f.isAnnotationPresent(Gid.class)){ // 全局唯一编码
					f.set(this.originalObject, generateValueGid(f));
					paramValues.add(generateValueGid(f));
					continue;
				}
				
				if(isCanBeBaseType(f.getType())){ //基本类型
					value = prepareBaseValue(f.getType(), f.get(this.originalObject));
				}
				paramValues.add(value);
			}catch(Exception ex){
				throw new DbException("can't get value , field: "+f.getName()+ " , type: "+type.getName(),ex);
			} finally{
				f.setAccessible(isAccess);
			}
		}
		return paramValues.toArray();
	}
	
	private Object generateValueLastModify(Field f) {
		Object value = null;
		if(f.isAnnotationPresent(UpdateTime.class)){ // 最后修改时间
			if(f.getType() == Long.class || Long.class.isAssignableFrom(f.getType())) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
				return Long.valueOf(sdf.format(new Date()));
				
			} else if(f.getType() == Date.class || Date.class.isAssignableFrom(f.getType())) {
				return  new Date();
			}
		}
		
		return value;
	}
	
	private Object generateValueGid(Field f) {
		return idGenerator.getUUID58();
	}
	
	public String getUpdateSql(String...prop){
		List<Field> fs = getField(prop);
		if(fs == null || fs.size()==0) return null;
		
		String sql = "update %s set %s where %s = ?";
		List<Field> updateCols= new ArrayList<>();
		for(Field f: fs) {
			if(f.isAnnotationPresent(Id.class)) continue; // 不显示更新id
			updateCols.add(f);
		}
		
		List<String> rs = new ArrayList<>();
		for(Field f : updateCols) {
			String curCol = null ;
			
			if (f.isAnnotationPresent(Column.class)) {
				Column c = f.getAnnotation(Column.class);
				curCol = c.name();
				
			} else if (f.isAnnotationPresent(Gid.class)) {
				Gid gid = f.getAnnotation(Gid.class);
				curCol = gid.name();
				
			} else if (f.isAnnotationPresent(UpdateTime.class)) {
				UpdateTime lm = f.getAnnotation(UpdateTime.class);
				curCol = lm.name();
				
			}
			rs.add(curCol);
		}
		
		StringBuilder setCols = new StringBuilder();
		for (int i = 0; i < rs.size(); i++) {
			setCols.append(rs.get(i) + " = ?");
			if (i != rs.size() - 1) {
				setCols.append(", ");
			}
		}
		
		sql = String.format(sql, getFullTable(), setCols, getIdColumn());
		return sql;
	}
	
	public Object [] getUpdateValues(String ...prop) {
		List<Field> fs = getField(prop);
		if(fs == null || fs.size()==0) return null;
		
		List<Field> updateCols= new ArrayList<>();
		for(Field f: fs) {
			if(f.isAnnotationPresent(Id.class)) continue; //不显示更新ID
			updateCols.add(f);
		}
		
		List<Object> params = new ArrayList<>();
		for(Field f : updateCols) {
			boolean isAcess = f.isAccessible();
			try{
				f.setAccessible(true);
				Object value = null;
				if (f.isAnnotationPresent(Column.class)) {
					value = prepareBaseValue(f.getType(), f.get(this.originalObject)) ;
					
				} else if (f.isAnnotationPresent(Gid.class)) {
					Object temp = f.get(this.originalObject) ;
					/* 用户强行改全局唯一id
					if((temp+"").length()<20) {
						temp = generateValueGid(f);
					}*/
					value = (temp == null ? generateValueGid(f):temp);
					f.set(this.originalObject, value);
					
				} else if (f.isAnnotationPresent(UpdateTime.class)) {
					 value = generateValueLastModify(f);
					 f.set(this.originalObject, value);
				}
				params.add(value);
			}catch(Exception ex){
				log.info(ex.getMessage());
				throw new DbException("can't get value for "+f.getName()+" ,type :"+type.getName());
			}finally{
				f.setAccessible(isAcess);
			}
		}
		params.add(getIdValue());
		return params.toArray();
	}
	
	public String getDeleteSql() {
		String sql = "delete from %s where %s = ?";
		sql = String.format(sql,  getFullTable(), getIdColumn() );
		return sql;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T toModel(Class<T> type,Map<String, Object> row)  {
		String processCurrFiled = "";
		try{
			Object model = type.newInstance();

			List<Field> fields = CacheReflectUtil.getBeanField(type);

			Iterator<String> iter = row.keySet().iterator();
			while (iter.hasNext()) {
				String calumn = iter.next();

				if (fields == null || fields.size() == 0) {
					continue;
				}

				for (int i = 0; i < fields.size(); i++) {
					Field e = fields.get(i);
					
					Id pk = e.getAnnotation(Id.class);
					if ((pk != null) && (pk.name().equals(calumn))) {
						processCurrFiled = e.getName();
						BeanUtil.forceSetProperty(model, e.getName(), prepareBaseValue(e.getType(), row.get(calumn)));
						break;
					}

					Column c = e.getAnnotation(Column.class);
					if ((c != null) && (c.name().equals(calumn))) {
						processCurrFiled = e.getName();
						BeanUtil.forceSetProperty(model, e.getName(),prepareBaseValue(e.getType(), row.get(calumn)));
						break;
					}
					
					Gid gid = e.getAnnotation(Gid.class);
					if ((gid != null) && (gid.name().equals(calumn))) {
						processCurrFiled = e.getName();
						BeanUtil.forceSetProperty(model, e.getName(),prepareBaseValue(e.getType(), row.get(calumn)));
						break;
					}
					
					UpdateTime modify = e.getAnnotation(UpdateTime.class);
					if ((modify != null) && (modify.name().equals(calumn))) {
						processCurrFiled = e.getName();
						BeanUtil.forceSetProperty(model, e.getName(),prepareBaseValue(e.getType(), row.get(calumn)));
						break;
					}
				}
			}

			return (T) model;
		}catch(Exception ex){
			log.info(ex.getMessage());
			throw new DbException(ex.getMessage().concat(" ; processing field:"+processCurrFiled), ex);
		}
		
	}
	
	
	
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~辅助方法~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	public static boolean isCanBeBaseType(Class<?> currType) {
		if (currType.isAnnotation() 
				|| currType.isArray()
				|| currType.isArray()
				|| currType.isEnum()
				|| currType.isInterface()) return false;
		
		if (currType == Object.class) return false;

		// 过滤集合、文件、单独处理
		if (Collection.class.isAssignableFrom(currType)) return false;
		if (File.class.isAssignableFrom(currType)) return false;
		//if (Date.class.isAssignableFrom(currType)) return false;
		
		
		if (String.class.isAssignableFrom(currType) //字符串
				
				//数字
				|| Integer.class.isAssignableFrom(currType) || int.class == currType
				|| Long.class.isAssignableFrom(currType) 	|| long.class == currType
				|| Float.class.isAssignableFrom(currType) 	|| float.class == currType
				|| Double.class.isAssignableFrom(currType) 	|| double.class == currType
				|| Byte.class.isAssignableFrom(currType) 	|| byte.class == currType
				|| Short.class.isAssignableFrom(currType)	|| short.class == currType
				/*
				|| BigInteger.class.isAssignableFrom(currType)
				|| BigDecimal.class.isAssignableFrom(currType)
				*/
				
				//布尔型
				|| Boolean.class.isAssignableFrom(currType) || boolean.class == currType
				
				
				//日期型
				|| Date.class.isAssignableFrom(currType)) {
			return true;
		}
		return false;
	}
	
	public static Object prepareBaseValue(Class<?> baseType,Object value){
		if(value == null) return null; 
		
		if (String.class.isAssignableFrom(baseType)) { //字符串
			return String.valueOf(value);
			
		
			
		} else if (Integer.class.isAssignableFrom(baseType)) { //数字
			return value == null ? null : Integer.valueOf(value.toString());
		} else if (baseType == int.class) {
			return value == null ? 0 : Integer.valueOf(value.toString());
		}

		else if (Long.class.isAssignableFrom(baseType)) {
			return value == null ? null : Long.valueOf(value.toString());
		} else if (baseType == long.class) {
			return value == null ? 0L : Long.valueOf(value.toString());
		}

		else if (Float.class.isAssignableFrom(baseType)) {
			return value == null ? null : Float.valueOf(value.toString());
		} else if (baseType == float.class) {
			return value == null ? 0F : Float.valueOf(value.toString());
		}

		else if (Double.class.isAssignableFrom(baseType)) {
			return value == null ? null : Double.valueOf(value.toString());
		} else if (baseType == double.class) {
			return value == null ? 0D : Double.valueOf(value.toString());
		}

		else if (Byte.class.isAssignableFrom(baseType)) {
			return value == null ? null : Byte.valueOf(value.toString());
		} else if (baseType == byte.class) {
			return value == null ? (byte) 0 : Byte.valueOf(value.toString());
		}
		/*
		else if(BigInteger.class.isAssignableFrom(baseType)) {
			return value == null ? null:BigInteger.valueOf(Long.valueOf(value.toString()));
		}
		
		else if(BigDecimal.class.isAssignableFrom(baseType)) {
			return value == null ? null:BigDecimal.valueOf(Long.valueOf(value.toString()));
		}
		*/
		
		
		else if (Boolean.class.isAssignableFrom(baseType)) { // 布尔型
			return  value == null ? null:Boolean.valueOf(value.toString());
		} else if (baseType == boolean.class) {
			return value == null ? false:Boolean.valueOf(value.toString());
		}
		
		/**/
		else if (Date.class.isAssignableFrom(baseType)) { // 日期类型
			return (Date) value;
		} 
		
		
		
		
		/*
		else if (Object.class.isAssignableFrom(baseType)) { // 字段声明为Object, 如 : private Object salary = new Double(25000D);
			if(value!=null) {
				if(value instanceof String) {
					return value.toString();
				}else {
					//字面意义
					 String valueStr = value.toString();
					 Pattern pattern = Pattern.compile("-?[0-9]+.?[0-9]+");
					 Matcher isNum = pattern.matcher(valueStr);
					
					if (!isNum.matches()) { //数字
						Double.valueOf(valueStr);
					}
				}
			}
		}*/
		return null;
	}
	
}


