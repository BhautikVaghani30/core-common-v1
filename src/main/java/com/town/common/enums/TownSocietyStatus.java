package com.town.common.enums;

import java.util.stream.Stream;
import lombok.Getter;

/** Represents the status of a society or town. */
@Getter
public enum TownSocietyStatus {

  /** The society is in a draft state and not yet active. */
  DRAFT("draft"),

  /** The society is active and fully operational. */
  ACTIVE("active"),

  /** The society has been suspended and is temporarily inactive. */
  SUSPENDED("suspended");

  private final String value;

  /**
   * Constructs a new TownSocietyStatus with the specified value.
   *
   * @param value The string representation of the status.
   */
  TownSocietyStatus(String value) {
    this.value = value;
  }

  /**
   * Finds the TownSocietyStatus enum constant for a given string value.
   *
   * @param value The status value to find.
   * @return The corresponding TownSocietyStatus, or null if not found.
   */
  public static TownSocietyStatus fromValue(String value) {
    if (value == null) {
      return null;
    }
    return Stream.of(TownSocietyStatus.values())
        .filter(status -> status.value.equalsIgnoreCase(value))
        .findFirst()
        .orElse(null);
  }

  @Override
  public String toString() {
    return this.value;
  }
}
