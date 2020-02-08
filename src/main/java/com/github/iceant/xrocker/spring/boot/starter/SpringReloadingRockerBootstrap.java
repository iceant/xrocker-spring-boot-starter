package com.github.iceant.xrocker.spring.boot.starter;

import com.fizzed.rocker.RenderingException;
import com.fizzed.rocker.RockerModel;
import com.fizzed.rocker.TemplateBindException;
import com.fizzed.rocker.TemplateNotFoundException;
import com.fizzed.rocker.compiler.RockerConfiguration;
import com.fizzed.rocker.compiler.RockerOptions;
import com.fizzed.rocker.compiler.TokenException;
import com.fizzed.rocker.reload.ReloadingRockerBootstrap;
import com.fizzed.rocker.runtime.DefaultRockerModel;
import com.fizzed.rocker.runtime.DefaultRockerTemplate;
import com.fizzed.rocker.runtime.RockerBootstrap;

import java.io.File;

public class SpringReloadingRockerBootstrap implements RockerBootstrap {
    private RockerProperties properties;
    private ReloadingRockerBootstrap rockerBootstrap;

    private RockerConfiguration makeConfiguration(RockerProperties properties){
        RockerConfiguration configuration = new RockerConfiguration();
        if(properties.getTemplateDirectory()!=null){
            configuration.setTemplateDirectory(new File(properties.getTemplateDirectory()));
        }
        if(properties.getClassDirectory()!=null){
            configuration.setClassDirectory(new File(properties.getClassDirectory()));
        }
        if(properties.getOutputDirectory()!=null){
            configuration.setOutputDirectory(new File(properties.getOutputDirectory()));
        }
        RockerOptions options = configuration.getOptions();
        options.setDiscardLogicWhitespace(properties.isDiscardLogicWhitespace());
        if(properties.getExtendsClass()!=null) {
            options.setExtendsClass(properties.getExtendsClass());
        }
        if(properties.getExtendsModelClass()!=null) {
            options.setExtendsModelClass(properties.getExtendsModelClass());
        }
        options.setOptimize(properties.isOptimize());
        options.setTargetCharset(properties.getTargetCharset());
        try {
            options.setJavaVersion(properties.getJavaVersion());
        } catch (TokenException e) {
            e.printStackTrace();
        }
        configuration.setOptions(options);
        return configuration;
    }


    public SpringReloadingRockerBootstrap(RockerProperties properties) {
        this.properties = properties;
        rockerBootstrap = new ReloadingRockerBootstrap(makeConfiguration(properties));
    }

    @Override
    public RockerModel model(String templatePath) throws TemplateNotFoundException, TemplateBindException {
        return rockerBootstrap.model(templatePath);
    }

    @Override
    public DefaultRockerTemplate template(Class modelType, DefaultRockerModel defaultRockerModel) throws RenderingException {
        return rockerBootstrap.template(modelType, defaultRockerModel);
    }
}
