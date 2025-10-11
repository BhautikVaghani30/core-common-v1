package com.core.v1.common.config.adapter;

import org.springframework.beans.factory.config.ServiceLocatorFactoryBean;
import org.springframework.context.annotation.Bean;

public class CoreAdapterConfig {
  @Bean("coreAdapterProviderFactory")
  public ServiceLocatorFactoryBean coreAdapterProviderFactory() {
    ServiceLocatorFactoryBean serviceLocatorFactoryBean = new ServiceLocatorFactoryBean();
    serviceLocatorFactoryBean.setServiceLocatorInterface(CoreAdapterProviderFactory.class);
    return serviceLocatorFactoryBean;
  }

  @Bean("coreAdapterFactory")
  public ServiceLocatorFactoryBean coreAdapterFactory() {
    ServiceLocatorFactoryBean serviceLocatorFactoryBean = new ServiceLocatorFactoryBean();
    serviceLocatorFactoryBean.setServiceLocatorInterface(CoreAdapterFactory.class);
    return serviceLocatorFactoryBean;
  }
}
