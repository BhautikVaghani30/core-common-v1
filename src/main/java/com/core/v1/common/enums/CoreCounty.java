package com.core.v1.common.enums;

import lombok.Getter;

/**
 * Represents the supported countries within the application.
 *
 * <p>This enum provides a type-safe way to handle country-specific logic and configurations.
 */
@Getter
public enum CoreCounty {
  /** United States. */
  US("us"),
  /** Canada. */
  CA("ca"),
  /** Default value, typically resolves to US. */
  DEFAULT("us");

  /** The lowercase, two-letter string representation of the country code. */
  private final String values;

  CoreCounty(String values) {
    this.values = values;
  }

  @Override
  public String toString() {
    return this.values;
  }
}
