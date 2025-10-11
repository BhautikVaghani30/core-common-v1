package com.core.v1.common.config.adapter;

import com.core.v1.common.config.adapter.model.CoreAdapter;

@FunctionalInterface
public interface CoreAdapterFactory {
  CoreAdapter getAdapter(String adapterName);
}
