package com.cxf.sell;

import com.cxf.sell.utils.RegexUtils;
import org.junit.Test;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class MybatisGeneratorPO {
@Test
public void test()  throws Exception{
	 List<String> warnings = new ArrayList<String>();
	   boolean overwrite = true;
	   File configFile = new File("src/main/resources/generatorConfig.xml");
	   ConfigurationParser cp = new ConfigurationParser(warnings);
	   Configuration config = cp.parseConfiguration(configFile);
	   DefaultShellCallback callback = new DefaultShellCallback(overwrite);
	   MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
	   myBatisGenerator.generate(null);
   }
	@Test
	public void test1()  throws Exception{
	String name= RegexUtils.humpToLine2(this.getClass().getSimpleName());
	name=name.substring(1,name.length()-4);
		System.out.println(name);

	}
	@Test
	public void test2(){
		List list=new ArrayList();
		list.add("asd");
		list.add("123");
		System.out.println(list.get(1));
		System.out.println(list.get(new Integer(1)-1));
	}
}