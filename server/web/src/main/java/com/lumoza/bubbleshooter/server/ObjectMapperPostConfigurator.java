package com.lumoza.bubbleshooter.server;

import org.codehaus.jackson.Version;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.module.SimpleModule;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.util.List;

/**
 * Post-process Jackson {@link ObjectMapper}.
 */
public class ObjectMapperPostConfigurator implements BeanPostProcessor {

    private List<JsonSerializer<?>> registerSerializers;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        if (bean instanceof ObjectMapper) {
            configureObjectMapper((ObjectMapper) bean);
        }
        return bean;
    }

    private void configureObjectMapper(ObjectMapper mapper) {
        final SimpleModule module = new SimpleModule("AutoCreatedModuleForCustomSerializers", new Version(1, 0, 0, null));
        for (JsonSerializer<?> serializer : registerSerializers) {
            module.addSerializer(serializer);
        }
        mapper.registerModule(module);
    }

    public void setRegisterSerializers(List<JsonSerializer<?>> registerSerializers) {
        this.registerSerializers = registerSerializers;
    }
}
