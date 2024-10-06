package com.tanishk.auxalis.datasets.catalogs;

public enum ValueSource {
  DEFAULT,
  INFERRED,
  SUPPLIED;

  public static ValueSource combine(ValueSource a, ValueSource b){
    return ValueSource.values()[Math.min(a.ordinal(), b.ordinal())];
  }

}
