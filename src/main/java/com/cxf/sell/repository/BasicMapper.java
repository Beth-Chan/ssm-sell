/**  
 * @Title: BasicMapper.java
 * @Package com.yjw.andy.dao
 * @Description: TODO
 * @author 余健文
 * @date 2016年9月19日
 */
package com.cxf.sell.repository;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository("basicMapper")
public interface BasicMapper<T ,E> {
    int countByExample(@Param("example") E example);

    int deleteByExample(@Param("example") E example);

    int deleteByPrimaryKey(@Param("pks") String[] pks, @Param("pkName") String pkName, @Param("tbName") String tbName);

    List<Map> selectByExample(@Param("example") E example);

    List<Map> selectJoint(@Param("example") E example);

    List<Map> selectByPrimaryKey(@Param("pks") String[] pks, @Param("pkName") String pkName, @Param("tbName") String tbName);


    int updateByExample(@Param("dataMap") Map<String, Object> map);
	public  int insertBatch(@Param("dataMap") Map<String, Object> map);
	public  int updateBatch(@Param("dataMap") Map<String, Object> map);

	int insert(@Param("dataMap") Map<String, Object> map);
}   
