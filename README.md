# 基于Spring Boot的外卖系统

### 项目编写过程记录

- 数据库设计
- 日志编写
- 买家端DAO层设计与开发

#### 数据库设计

    CREATE TABLE `product_info` (
        `product_id` VARCHAR(32) NOT NULL,
        `product_name` VARCHAR(64) NOT NULL,
        `product_price` DECIMAL(8, 2) NOT NULL,
        `product_stock` INT NOT NULL,
        `product_description` VARCHAR(64),
        `product_icon` VARCHAR(512),
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


#### 日志使用Logback
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



#### 买家端**DAO**层设计与开发

pom.xml里添加依赖

    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
    </dependency>
    
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>


修改application.properties为application.yml，树形结构更简洁：

    spring:
      datasource:
        driver-class-name: com.mysql.jdbc.Driver
        username: ********(此处要改成数据库对应的用户名）
        password: ********(此处要改成对应的密码）
        url: jdbc:mysql://123.207.95.134/sell?characterEncoding=utf-8&useSSL=false
      jpa:
        show-sql: true


##### 创建dataobject文件夹存放Entity实体对象

ProductCategory类：<br>
数据库表名是product_category
属性（此处省略setter和getter)：

    Integer categoryId; 
    private String categoryName;
    private Integer categoryType

@Entity表示当前类是实体类；@Id表示当前属性是主键；@GeneratedValue表示自增

##### 创建repository文件夹存放DAO Bean

    // ProductCategory和Integer是对象和主键类型
    public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer> {
    
    }


##### 单元测试

    @RunWith(SpringRunner.class)
    @SpringBootTest
    public class ProductCategoryRepositoryTest {
    
        @Autowired
        private ProductCategoryRepository repository;
    
        @Test
        public void findOneTest() {
            // 写个1，就自动出来id:
            ProductCategory productCategory = repository.findOne(1);
            System.out.println(productCategory.toString());
        }
    
        @Test
        public void saveTest() { // 新增或修改的话都是用saveTest
            // 新增得先构造对象
            ProductCategory productCategory = new ProductCategory();
            // 修改要加setCategoryId，新增可以不用，因为默认自增id
            productCategory.setCategoryId(2);
            productCategory.setCategoryName("精选热菜");
            productCategory.setCategoryType(3);
            repository.save(productCategory);
        }
    }