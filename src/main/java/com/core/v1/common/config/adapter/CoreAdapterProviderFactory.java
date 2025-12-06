package com.core.v1.common.config.adapter;

import com.core.v1.common.config.adapter.model.CoreAdapter;
import com.core.v1.common.config.adapter.model.CoreProperties;
import java.util.function.Function;

@FunctionalInterface
public interface CoreAdapterProviderFactory {
  Function<CoreProperties, CoreAdapter> getProvider(String providerName);
}
