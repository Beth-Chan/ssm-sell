package com.cxf.sell;

import com.cxf.sell.utils.DatePattern;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.converter.Converter;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.util.StringUtils;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.text.SimpleDateFormat;
import java.util.Date;

@EnableScheduling//定时任务
@EnableAsync // 开启异步任务支持
@SpringBootApplication
@MapperScan("com.cxf.sell.repository")
@ConfigurationProperties("classpath:application.yml") //接
public class SellApplication extends SpringBootServletInitializer implements EmbeddedServletContainerCustomizer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SellApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(SellApplication.class, args);
    }


    public void customize(ConfigurableEmbeddedServletContainer configurableEmbeddedServletContainer) {
        // configurableEmbeddedServletContainer.setPort(9090);
    }

    // 跨域
    private CorsConfiguration buildConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*"); // 1
        corsConfiguration.addAllowedHeader("*"); // 2
        corsConfiguration.addAllowedMethod("*"); // 3
        return corsConfiguration;
    }
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", buildConfig()); // 4
        return new CorsFilter(source);
    }

    @Bean
    public Converter<String, Date> addNewConvert() {
        return new Converter<String, Date>() {
            @Override
            public Date convert(String source) {
                if(StringUtils.isEmpty(source)){
                    return null;
                }
                SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                if(source.matches(DatePattern.DATE_ONLY.getRegex())){
                    sdf =new SimpleDateFormat(DatePattern.DATE_ONLY.getPattern());
                }else if(source.matches(DatePattern.DATE_ONLY2.getRegex())){
                    sdf =new SimpleDateFormat(DatePattern.DATE_ONLY2.getPattern());
                }else if(source.matches(DatePattern.YEAR_MONTH.getRegex())){
                    sdf =new SimpleDateFormat(DatePattern.DATE_ONLY.getPattern());
                }else if(source.matches(DatePattern.ISO_MINUTE.getRegex())){
                    sdf =new SimpleDateFormat(DatePattern.ISO_MINUTE.getPattern());
                }

                Date date = null;
                try {
                    date = sdf.parse((String) source);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return date;
            }
        };
    }
}