package com.tanishk.auxalis.datasets.catalogs;

//  TODO everything in here is very unscientific
//  try to calculate from each other?  guess better?
public class PlanetDefaults {

  public static final UnitValue DEFAULT_SEMI_MAJOR_AXIS = new UnitValue(0.387098, Unit.AU); //  mercury AUs
  public static final UnitValue DEFAULT_ECCENTRICITY = new UnitValue(0.0, Unit.NONE);
  public static final UnitValue DEFAULT_ORBITAL_PERIOD = new UnitValue(88.0, Unit.DAY); //  mercury
  public static final UnitValue DEFAULT_INCLINATION = new UnitValue(0.0, Unit.DEGREE_GEOM);
  public static final UnitValue DEFAULT_MASS = new UnitValue(1.0, Unit.MASS_JUP);
  public static final UnitValue DEFAULT_RADIUS = new UnitValue(1.0, Unit.RADIUS_EARTH);
  public static final UnitValue DENSITY = new UnitValue(1.326, Unit.G_PER_CC); // jupiter
  public static final UnitValue DEFAULT_LONG_ASCENDING = new UnitValue(0.0, Unit.DEGREE_GEOM);
  public static final UnitValue DEFAULT_ARGUMENT_PERHELION = new UnitValue(0.0, Unit.DEGREE_GEOM);
  public static final UnitValue DEFAULT_AXIAL_TILT = new UnitValue(0.0, Unit.DEGREE_GEOM);
}
