/**  
 * @Title: BasicServiceImpl.java
 * @Package com.yjw.andy.service.Impl
 * @Description: TODO
 * @author 余健文
 * @date 2016年9月19日
 */
package com.cxf.sell.service.impl;


import com.cxf.sell.dataobject.base.BasicExample;
import com.cxf.sell.dataobject.base.IBaseDBPO;
import com.cxf.sell.repository.BasicMapper;
import com.cxf.sell.service.BasicService;
import com.cxf.sell.utils.ObjectMapUtils;
import com.cxf.sell.utils.RegexUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Transactional /* 增删改 */
@Service("basicService")
public class BasicServiceImpl<T extends IBaseDBPO, E extends BasicExample> implements BasicService<T, E> {
 
	@Autowired
	private BasicMapper<T, E> basicMapper;

	@SuppressWarnings("unchecked")
	@Override
	public List<T> findAll(Class<T> t) throws Exception {
		return this.selectByExample(t,(E) new BasicExample());
	}

	@Override
	public int countByExample(E example) {
		return basicMapper.countByExample(example);
	}

	@Override
	public int deleteByExample(E example) {
		return basicMapper.deleteByExample(example);
	}

	@Override
	public int deleteByPrimaryKey(Class<T> clazz,List<String> id) throws InstantiationException, IllegalAccessException {
		return this.deleteByPrimaryKey(clazz, id.toArray(new String[id.size()]));
}
	@Override
	public int deleteByPrimaryKey(Class<T> clazz,String[] id) throws IllegalAccessException, InstantiationException {
		T t=clazz.newInstance();
		return basicMapper.deleteByPrimaryKey(id,t._getPKColumnName(),t._getTableName());
	}


    @Override
	public int deleteByPrimaryKey(Class<T> clazz,String id) throws InstantiationException, IllegalAccessException {
		return this.deleteByPrimaryKey(clazz, new String[] {id});
	}
	
	@Override
	public List<T> selectByExample(Class<T> clazz,E example) throws Exception{
		T t=clazz.newInstance();
		example.setPkName(t._getPKColumnName());
		example.setTName(t._getTableName());
		List<Map> mapL=   basicMapper.selectByExample(example);
		List<T> list=new ArrayList<>();
	    for(Map map:mapL) {
	    	T t1=(T) ObjectMapUtils.mapToObject(map, clazz, true);
	    	list.add(t1);
	    }
		return list;
	}
	@Override
	public T selectOneByExample(E example) throws Exception{
		List<Map> mapL=   basicMapper.selectByExample(example);
		List<T> list=new ArrayList<>();
		for(Map map:mapL) {
			T t1=(T) ObjectMapUtils.mapToObject(map, example.getClazz(), true);
			return t1;
		}
		return null;
	}

	@Override
	public List<T> selectByExample(E example) throws Exception{
		List<Map> mapL=   basicMapper.selectByExample(example);
		List<T> list=new ArrayList<>();
		for(Map map:mapL) {
			T t1=(T) ObjectMapUtils.mapToObject(map, example.getClazz(), true);
			list.add(t1);
		}
		return list;
	}

	@Override
	public List selectJoint(E example) throws Exception{
		List<Map> mapL=   basicMapper.selectJoint(example);
/*		Set<String> set0=null;
		if(mapL.size()>0){
			set0=mapL.get(0).entrySet();
			for(Map map:mapL) {
				for (Object s : set0) {
					map.put(RegexUtils.lineToHump((String) s),map.get(s));
					map.remove(s);
				}

			}
		}*/
		return mapL;
	}



	@Override
	public T selectByPrimaryKey(Class<T> clazz,String id) throws Exception{
		List<T> list =this.selectByPrimaryKey(clazz, new String[] {id});
		if(list.size()>0) {
			return list.get(0);
			}
		return null;
	}

	@Override
	public List<T> selectByPrimaryKey(Class<T> clazz,List<String> ids) throws Exception{
		List<T> list =this.selectByPrimaryKey(clazz, ids.toArray(new String[ids.size()]));
		if(list.size()>0) {
			return list;
			}
		return null;
	}

	@Override
	public List<T> selectByPrimaryKey(Class<T> clazz,String[] ids) throws Exception{
		if(ids==null||ids.length<=0)return new ArrayList<>();
		T t=clazz.newInstance();
		List<Map> mapL=  basicMapper.selectByPrimaryKey(ids,t._getPKColumnName(),t._getTableName());
		List<T> list=new ArrayList<>();
	    for(Map map:mapL) {
	    	T t1=(T) ObjectMapUtils.mapToObject(map, clazz, true);
	    	list.add(t1);
	    }
		return list;
	}
	@Override
	public int updateByExample(T record, E example) {
		return this.updateByExample(record, example,false);
	}

	@Override
	public int updateByExample(T record, E example,boolean selective) {
		Map<String,Object> map= new HashMap<>();
		map.put("_tableName", record._getTableName());
		String[] fieldNames = this.getFieldName(record);	
		Map<String, Object> idmap = new HashMap<>();
		for (String key:fieldNames) {
			if(key.equals(record._getPKColumnName()))continue;
			Object value = new Object[fieldNames.length];
			value = this.getFieldValueByName(key, record);			
			if(selective&&value==null)continue;
			idmap.put(RegexUtils.humpToLine(key) , value);
		}
		map.put("fieldData", idmap);
		map.put("example", example);
		return basicMapper.updateByExample(map);
	}
	
	@Override
	public int insertOne(T record) {
		List<T> list=new ArrayList<>();
		list.add(record);
		return insertBatch(list);
	}


	@Override
	public int insertBatch(List<T> list) {
		if (list == null || list.size() == 0) {
			return 0;
		}
		int dealData = 0;

		Map<String, Object> map = new HashMap<>();
		map.put("_tableName", list.get(0)._getTableName());
		String[] fieldName = getFieldName(list.get(0));
		for (int i = 0; i < fieldName.length; i++) {
			fieldName[i] = RegexUtils.humpToLine(fieldName[i]);
		}

		map.put("_tableVar", fieldName);

		int pointsDataLimit = 1000;// 限制条数
		Integer size = list.size();
		// 判断是否有必要分批
		int part = size / pointsDataLimit;// 分批数
		if (pointsDataLimit < size) {
			for (int i = 0; i < part; i++) {
				List<T> listPage = list.subList(0, pointsDataLimit);
				list.subList(0, pointsDataLimit).clear();

				List<Object[]> objArr = new ArrayList<>();
				for (T po : listPage) {
					objArr.add(getFieldValues(po));
				}
				map.put("_values", objArr);
				dealData += basicMapper.insertBatch(map);
			}
		} else {
			List<Object[]> objArr = new ArrayList<>();
			for (T po : list) {
				objArr.add(getFieldValues(po));
			}
			map.put("_values", objArr);
			dealData += basicMapper.insertBatch(map);
		}
		return dealData;
	}

	@Transactional
	@Override
	public int updateBatch(List<T> list,boolean selective) {
		if (list == null || list.size() == 0) {
			return 0;
		}
		int dealData = 0;

		Map<String, Object> map = new HashMap<>();
		map.put("_tableName", list.get(0)._getTableName());
		map.put("_tablePKName", list.get(0)._getPKColumnName());
		int pointsDataLimit = 1000;// 限制条数
		Integer size = list.size();
		// 判断是否有必要分批
		int part = size / pointsDataLimit;// 分批数
		if (pointsDataLimit < size) {
			System.out.println("共有 ： " + size + "条，！" + " 分为 ：" + part + "批");
			for (int i = 0; i < part; i++) {
				List<T> listPage = list.subList(0, pointsDataLimit);
				Map<String, Object> fmap =getFieldMapValues(listPage,selective);
				map.put("_PKs", fmap.get("_PKs"));
				fmap.remove("_PKs");
				map.put("_values", fmap);
				dealData += basicMapper.updateBatch(map);
			}
		} else {
			Map<String, Object> fmap =getFieldMapValues(list,selective);
			map.put("_PKs", fmap.get("_PKs"));
			fmap.remove("_PKs");
			map.put("_values", fmap);
			dealData += basicMapper.updateBatch(map);
		}
		return dealData;
	}

	@Override
	public int updateBatch(List<T> list) {
		return  this.updateBatch(list,false);
	}
	
	@Override
	public int updateOne(T t) {
		return  this.updateOne(t,false);
	}
	@Override
	public int updateOne(T t,boolean selective) {
		List<T> list=new ArrayList<>();
		list.add(t);
		return  this.updateBatch(list,selective);
	}

	public Map<String,Object> getFieldAndName(Object o) {
		Map<String,Object> map=new HashMap<>();
		Field[] fields = o.getClass().getDeclaredFields();
		String[] fieldNames = new String[fields.length];
		Object[] value = new Object[fieldNames.length];
		for (int i = 0; i < fields.length; i++) {
			fieldNames[i] = fields[i].getName();
			value[i] = this.getFieldValueByName(fieldNames[i], o);
		}
        map.put("fieldNames", fieldNames);
        map.put("fieldValues", value);
		return map;
		
	}
	
	@Override
	public Object getFieldValueByName(String fieldName, Object o) {
		try {
			String firstLetter = fieldName.substring(0, 1).toUpperCase();
			String getter = "get" + firstLetter + fieldName.substring(1);
			Method method = o.getClass().getMethod(getter, new Class[] {});
			Object value = method.invoke(o, new Object[] {});
			return value;
		} catch (Exception e) {
			System.out.println("属性不存在");
			return null;
		}
	}

	@Override
	public String[] getFieldName(Object o) {
		Field[] fields = o.getClass().getDeclaredFields();
		String[] fieldNames = new String[fields.length];
		for (int i = 0; i < fields.length; i++) {
			fieldNames[i] = fields[i].getName();
		}
		return fieldNames;
	}

	@Override
	public Object[] getFieldValues(Object o) {
		String[] fieldNames = this.getFieldName(o);
		Object[] value = new Object[fieldNames.length];
		for (int i = 0; i < fieldNames.length; i++) {
			value[i] = this.getFieldValueByName(fieldNames[i], o);
		}
		return value;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getFieldMapValues(List<T> list,boolean selective) {
		Map<String,  Object> fmap = new HashMap<>();
		
		String[] fieldNames = this.getFieldName(list.get(0));
		String[] _PKs=new String[list.size()];
		int j=0;
		for (T t : list) {			
			for (int i = 0; i < fieldNames.length; i++) {
				Map<String, Object> idmap = new HashMap<>();
				Object value = new Object[fieldNames.length];
				value = this.getFieldValueByName(fieldNames[i], t);
				
				if(selective&&value==null)continue;
				
				if(fmap.get(RegexUtils.humpToLine(fieldNames[i]))!=null){
					idmap =(Map<String, Object>)fmap.get(RegexUtils.humpToLine(fieldNames[i]));
				}
				
			   idmap.put(t._getPKValue(), value);
			   fmap.put(RegexUtils.humpToLine(fieldNames[i]), idmap);
				
			}
			_PKs[j++]=t._getPKValue();
		}
		fmap.put("_PKs", _PKs);
		return fmap;
	}
}
