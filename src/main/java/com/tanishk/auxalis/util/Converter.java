package com.tanishk.auxalis.util;

import com.tanishk.auxalis.datasets.catalogs.Unit;
import com.tanishk.auxalis.datasets.catalogs.UnitValue;
import java.util.HashMap;
import java.util.Map;

public class Converter {

  private static final Map<UnitPair, Double> CONVERSIONS = new HashMap<>();

  static class UnitPair {
    private Unit from;
    private Unit to;

    public UnitPair(Unit from, Unit to) {
      this.from = from;
      this.to = to;
    }

    @Override
    public int hashCode() {
      return from.hashCode() + to.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      }
      if (obj == null || getClass() != obj.getClass()) {
        return false;
      }
      UnitPair other = (UnitPair) obj;
      return from == other.from && to == other.to;
    }
  }

  static void addConversion(Unit from, Unit to, Double factor) {
    CONVERSIONS.put(new UnitPair(from, to), factor);
    CONVERSIONS.put(new UnitPair(to, from), 1.0 / factor);
  }

  static {
    for (Unit unit : Unit.values()) {
      addConversion(unit, unit, 1.0);
    }

    addConversion(Unit.AU, Unit.LY, 1.58125e-5);
    addConversion(Unit.MASS_JUP, Unit.KG, 1.898e27);
    addConversion(Unit.RADIUS_JUP, Unit.KM, 69911.0);
    addConversion(Unit.RADIUS_JUP, Unit.LY, 7.3896e-9);
    addConversion(Unit.RADIUS_EARTH, Unit.RADIUS_JUP, 0.146822127);
    addConversion(Unit.RADIUS_EARTH, Unit.LY, 6.734e-10);
  }

  public static UnitValue convert(UnitValue value, Unit to) {
    return convert(value.getQuantity(), value.getUnit(), to);
  }

  public static UnitValue convert(double value, Unit from, Unit to) {
    Double factor = CONVERSIONS.get(new UnitPair(from, to));

    if (factor == null) {
      throw new RuntimeException("Cannot convert from " + from + " to " + to);
    }

    return new UnitValue(value * factor, to);
  }
}