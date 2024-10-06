package com.tanishk.auxalis.datasets;

import java.util.Map;

import com.tanishk.auxalis.util.MapBuilder;

public class StarClassHelper {

  //  from https://en.wikipedia.org/wiki/Stellar_classification, taking medians
  private static final Map<SpectralClass, Integer> SPEC_CLASS_TO_TEMP = MapBuilder.of(SpectralClass.O, 30000)
      .put(SpectralClass.B, 20000) //  10,000–30,000 K
      .put(SpectralClass.A, 8750) //  7,500–10,000
      .put(SpectralClass.F, 6750) //  6,000–7,500
      .put(SpectralClass.G, 5600) //  5,200–6,000
      .put(SpectralClass.K, 4450) //  3,700–5,200
      .put(SpectralClass.M, 3050) //  2,400–3,700

      .put(SpectralClass.dM, 3050) // red dwarf -- pretend it's M?

      //  TODO brown dwarfs -- no idea
      .put(SpectralClass.L, 1000)
      .put(SpectralClass.T, 1000)
      .put(SpectralClass.Y, 1000)

      //  TODO mostly guessing  for most of these, in absence of numbers
      .put(SpectralClass.DA, 15000)
      .put(SpectralClass.DAV, 15000)
      .put(SpectralClass.DAP, 15000)
      .put(SpectralClass.DXP, 15000)
      .put(SpectralClass.DZ, 15000)
      .put(SpectralClass.DG, 15000)
      .put(SpectralClass.DK, 15000)
      .put(SpectralClass.DM, 15000)
      .put(SpectralClass.DQP, 15000)
      .put(SpectralClass.DX, 15000)
      .put(SpectralClass.DZA, 15000)
      .put(SpectralClass.DZQ, 15000)
      .put(SpectralClass.DQ, 15000)
      .put(SpectralClass.DO, 75000)
      .put(SpectralClass.DB, 20000)
      .put(SpectralClass.DBP, 20000)
      .put(SpectralClass.DC, 10000)

      .get();

  //  this is a fallback if we have no bv index.  ideally not used often.
  public static double getTemperatureEstimate(ParsedClassification classification) {

    if(classification.getWhiteDwarfTempIndex() != null){
      return 50400.0 / classification.getWhiteDwarfTempIndex();
    }

    //  dunno what the right answer is, but according to wikipedia class M stars are > 75% of all main sequence stars,
    //  so.... sure
    SpectralClass mainClass = classification.getMainClass();
    if(mainClass == null){
      mainClass = SpectralClass.M;
    }

    return SPEC_CLASS_TO_TEMP.get(mainClass);

  }

}
