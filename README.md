# 基于Spring Boot的外卖系统

### 项目编写过程记录

> - 数据库设计
> - 日志编写
> - 买家类目(ProductCategory)DAO层设计与开发
> - 买家类目Service层设计与开发

#### 数据库设计
(采用Navicat for MySQL操作，可以新建查询，或者可视化操作）
```
CREATE TABLE `product_info` (
    `product_id` VARCHAR(32) NOT NULL,
    `product_name` VARCHAR(64) NOT NULL,
    `product_price` DECIMAL(8, 2) NOT NULL,
    `product_stock` INT NOT NULL,
    `product_description` VARCHAR(64),
    `product_icon` VARCHAR(512),
    `product_status` INT NOT NULL,
    `category_type` INT NOT NULL,
    `create_time` TIMESTAMP NOT NULL DEFAULT current_timestamp,
    `update_time` TIMESTAMP  NOT NULL DEFAULT current_timestamp ON UPDATE current_timestamp
);

CREATE TABLE `product_category` (
    `category_id` INT NOT NULL AUTO_INCREMENT,
    `category_name` VARCHAR(64) NOT NULL,
    `category_type` INT NOT NULL,
    `create_time` TIMESTAMP NOT NULL DEFAULT current_timestamp,
    `update_time` TIMESTAMP  NOT NULL DEFAULT current_timestamp ON UPDATE current_timestamp,
    PRIMARY KEY(`category_id`),
    UNIQUE KEY unique_category_type(`category_type`)
);

CREATE TABLE `order_master` (
    `order_id` VARCHAR(32) NOT NULL,
    `buyer_name` VARCHAR(32) NOT NULL,
    `buyer_phone` VARCHAR(32) NOT NULL,
    `buyer_address` VARCHAR(128) NOT NULL,
    `buyer_openid` VARCHAR(32) COMMENT '买家微信id',
    `order_amount` DECIMAL(8, 2) NOT NULL,
    `order_status` TINYINT(3) NOT NULL DEFAULT '0' COMMENT '默认0新下单',
    `pay_status` TINYINT(3) NOT NULL DEFAULT '0' COMMENT '默认0是未支付',
    `create_time` TIMESTAMP NOT NULL DEFAULT current_timestamp,
    `update_time` TIMESTAMP  NOT NULL DEFAULT current_timestamp ON UPDATE current_timestamp,
    PRIMARY KEY(`order_id`),
    KEY `idx_buyer_openid` (`buyer_openid`)
);

CREATE TABLE `order_detail` (
    `detail_id` VARCHAR(32) NOT NULL,
    `order_id` VARCHAR(32) NOT NULL,
    `product_id` VARCHAR(32) NOT NULL,
    `product_name` VARCHAR(64) NOT NULL,
    `product_price` DECIMAL(8, 2) NOT NULL,
    `product_quantity` INT NOT NULL,
    `product_icon` VARCHAR(512),
    `create_time` TIMESTAMP NOT NULL DEFAULT current_timestamp,
    `update_time` TIMESTAMP  NOT NULL DEFAULT current_timestamp ON UPDATE current_timestamp,
    PRIMARY KEY(`detail_id`),
    KEY `idx_order_id` (`order_id`)
);
```

#### 日志编写

pom.xml里添加依赖：    
```
<!--此插件支持日志和getter，setter和toString-->
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
</dependency>

（resources/logback-spring.xml）

<?xml version="1.0" encoding="UTF-8" ?>
<configuration>
    <!--配置输出控制台日志-->
    <appender name="consoleLog" class="ch.qos.logback.core.ConsoleAppender">
        <layout class="ch.qos.logback.classic.PatternLayout">
            <pattern>
                %d - %msg%n
            </pattern>
        </layout>
    </appender>

    <!--配置输出文件日志，分为Info文件和Error文件-->
    <appender name="fileInfoLog" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <filter class="ch.qos.logback.classic.filter.LevelFilter">
            <level>ERROR</level>
            <onMatch>DENY</onMatch>
            <onMismatch>ACCEPT</onMismatch>
        </filter>
        <encoder>
            <pattern>
                %msg%n
            </pattern>
        </encoder>
        <!--滚动策略-->
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <!--路径-->
            <fileNamePattern>var/log/tomcat/sell/info.%d.log</fileNamePattern>
        </rollingPolicy>
    </appender>

    <appender name="fileErrorLog" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <filter class="ch.qos.logback.classic.filter.ThresholdFilter">
            <level>ERROR</level>
        </filter>
        <encoder>
            <pattern>
                %msg%n
            </pattern>
        </encoder>
        <!--滚动策略-->
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <!--路径-->
            <fileNamePattern>var/log/tomcat/sell/error.%d.log</fileNamePattern>
        </rollingPolicy>
    </appender>

    <root level="info">
        <appender-ref ref="consoleLog" />
        <appender-ref ref="fileInfoLog" />
        <appender-ref ref="fileErrorLog" />
    </root>
</configuration>
```


#### 商品类目ProductCategory**DAO**层设计与开发

**第一个表product_category的操作分五步：**

第一步.pom.xml里添加依赖
```
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
</dependency>

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
```


第二步.添加数据库相关配置，修改application.properties为application.yml，树形结构更简洁：
```
spring:
  datasource:
    driver-class-name: com.mysql.jdbc.Driver
    username: ********(此处要改成数据库对应的用户名）
    password: ********(此处要改成对应的密码）
    url: jdbc:mysql://123.207.95.134/sell?characterEncoding=utf-8&useSSL=false
  jpa:
    show-sql: true
```


第三步.创建dataobject文件夹存放Entity实体对象，创建数据库对象（product_category表的映射）<br>
ProductCategory类：<br>
```
@Entity
@DynamicUpdate
@Data /* lombok自动生成getter, setter and toString() */
public class ProductCategory {
    /** 类目id. */
    @Id /* 主键 */
    @GeneratedValue /* 自增 */
    private Integer categoryId;

    private String categoryName;

    private Integer categoryType;

    public ProductCategory() {
    }

    public ProductCategory(String categoryName, Integer categoryType) {
        this.categoryName = categoryName;
        this.categoryType = categoryType;
    }
}
```
@Entity表示当前类是实体类；@Id表示当前属性是主键；@GeneratedValue表示自增


第四步.创建repository文件夹存放DAO Bean，写DAO层的代码（直接extends JpaRepository，连SQL语句都不用写）<br>
ProductCategoryRepository是一个接口：
```
// ProductCategory和Integer是对象和主键类型
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer> {

    // 通过category_type查商品列表
    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);
}
```


第五步.进行单元测试
```
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryRepositoryTest {

   @Autowired
   private ProductCategoryRepository repository;

   @Test
   public void findOneTest() {
       // 找出id为1的记录
       ProductCategory productCategory = repository.findOne(1);
       System.out.println(productCategory.toString());
   }

   @Test
   @Transactional /* 测试完数据库不要插入数据 */
   public void saveTest() { // 新增或修改的话都是用saveTest
       // 更新往往是先查出来，再判断权限等信息，再可以更改
       // ProductCategory productCategory = repository.findOne(2);
       // productCategory.setCategoryType(10);
       // repository.save(productCategory);

       // // 新增得先构造对象
       // ProductCategory productCategory = new ProductCategory();
       // // 修改要加setCategoryId，新增可以不用，因为默认自增id
       // productCategory.setCategoryId(2);
       // productCategory.setCategoryName("精选热菜");
       // productCategory.setCategoryType(3);
       // repository.save(productCategory);

       ProductCategory productCategory = new ProductCategory("女生最爱", 5);
       ProductCategory result = repository.save(productCategory);
       Assert.assertNotNull(result);
       // 等价于Assert.assertNotEquals(null, result);
   }

   @Test
   public void findByCategoryTypeInTest() {
       List<Integer> list = Arrays.asList(2, 3, 4);
       List<ProductCategory> result = repository.findByCategoryTypeIn(list);
       Assert.assertNotEquals(0, result.size());
   }
}
```

#### 买家类目Service层设计与开发
创建service文件夹，编写CategoryService接口
```
public interface CategoryService {
    /* 后台管理 */
    ProductCategory findOne(Integer categoryId);
    List<ProductCategory> findAll();

    /* 买家端 */
    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);
    // 新增和更新，删除暂时用不到
    ProductCategory save(ProductCategory productCategory);
}
```

写实现类：
```
@Service
public class CategoryServiceImpl implements CategoryService {

    // 引入DAO
    @Autowired
    private ProductCategoryRepository repository;

    @Override
    public ProductCategory findOne(Integer categoryId) {
        return repository.findOne(categoryId);
    }

    @Override
    public List<ProductCategory> findAll() {
        return repository.findAll();
    }

    @Override
    public List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList) {
        return repository.findByCategoryTypeIn(categoryTypeList);
    }

    @Override
    public ProductCategory save(ProductCategory productCategory) {
        return repository.save(productCategory);
    }
}
```


写测试：
```
@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryServiceImplTest {

    @Autowired
    CategoryServiceImpl categoryService;

    @Test
    public void findOne() {
        ProductCategory productCategory = categoryService.findOne(1);
        Assert.assertEquals(new Integer(1), productCategory.getCategoryId());
    }

    @Test
    public void findAll() {
        List<ProductCategory> productCategoryList = categoryService.findAll();
        Assert.assertNotEquals(0, productCategoryList.size());
    }

    @Test
    public void findByCategoryTypeIn() {
        List<ProductCategory> productCategoryList = categoryService.findByCategoryTypeIn(Arrays.asList(1, 2, 3, 4));
        Assert.assertNotEquals(0, productCategoryList.size());
    }

    @Test
    public void save() {
        ProductCategory productCategory = new ProductCategory("热销榜", 1);
        ProductCategory result = categoryService.save(productCategory);
        Assert.assertNotNull(result);
    }
}
```
     
     
#### 商品信息ProductInfo相关操作(包括DAO、Service和Controller)

第一步：
```
@Entity
@Data
public class ProductInfo {
    @Id
    private String productId;
    private String productName;
    // 数据库是decimal(8,2)，要对应BigDecimal
    private BigDecimal productPrice;
    private Integer productStock;
    private String productDescription;
    private String productIcon;
    private Integer productStatus;
    private Integer categoryType;
}
```

第二步：
```
public interface ProductInfoRepository extends JpaRepository<ProductInfo, String> {
    List<ProductInfo> findByProductStatus(Integer productStatus);
}
```

第三步：
```
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoRepositoryTest {

    @Autowired
    private ProductInfoRepository repository;

    @Test
    public void saveTest() {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("1234");
        productInfo.setProductName("皮蛋瘦肉粥");
        productInfo.setProductPrice(new BigDecimal(10));
        productInfo.setProductStock(30);
        productInfo.setProductDescription("很好喝的粥");
        productInfo.setProductIcon("http://xxxx.jpg");
        productInfo.setProductStatus(ProductStatusEnum.UP.getCode());
        productInfo.setCategoryType(3);

        ProductInfo result = repository.save(productInfo);
        Assert.assertNotNull(result);
    }

    @Test
    public void findByProductStatus(){
        List<ProductInfo>  productInfoList = repository.findByProductStatus(0);
        Assert.assertNotEquals(0, productInfoList.size());
    }
}
```

第四步：
```
public interface ProductService {
    ProductInfo findOne(String productId);

    // 买家查询所有在架商品列表
    List<ProductInfo> findUpAll();
    // 后台管理可以查看所有商品列表
    Page<ProductInfo> findAll(Pageable pageable);

    ProductInfo save(ProductInfo productInfo);

    // 增加库存

    // 减少库存
}
```

