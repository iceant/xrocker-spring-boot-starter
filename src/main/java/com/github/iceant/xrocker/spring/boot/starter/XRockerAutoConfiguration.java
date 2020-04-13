package com.github.iceant.xrocker.spring.boot.starter;

import com.fizzed.rocker.runtime.RockerBootstrap;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@ConditionalOnClass(RockerBootstrap.class)
@ConditionalOnProperty(prefix = "spring.rocker", name = {"enabled"}, havingValue = "true")
@EnableConfigurationProperties(RockerProperties.class)
public class XRockerAutoConfiguration {
    final RockerProperties properties;
    final ApplicationContext applicationContext;

    public XRockerAutoConfiguration(RockerProperties properties, ApplicationContext applicationContext) {
        this.properties = properties;
        this.applicationContext = applicationContext;
        rocker.$.setApplicationContextManually(applicationContext);
    }


    @Bean
    @ConditionalOnMissingBean(RockerBootstrap.class)
    public RockerBootstrap rockerBootstrap(){
        return new SpringReloadingRockerBootstrap(properties);
    }

    @Bean
    @ConditionalOnMissingBean(RockerViewResolver.class)
    public RockerViewResolver rockerViewResolver(RockerBootstrap rockerBootstrap){
        RockerViewResolver viewResolver = new RockerViewResolver(rockerBootstrap, properties);
        viewResolver.setPrefix(properties.getPrefix());
        viewResolver.setSuffix(properties.getSuffix());
        viewResolver.setOrder(properties.getTemplateResolverOrder());
        return viewResolver;
    }

}
