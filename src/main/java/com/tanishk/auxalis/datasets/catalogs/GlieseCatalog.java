package com.tanishk.auxalis.datasets.catalogs;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Map;
import java.util.Scanner;
import java.util.zip.GZIPInputStream;

import com.tanishk.auxalis.datasets.AstroConvert;
import com.tanishk.auxalis.datasets.DatasetName;
import com.tanishk.auxalis.datasets.ExternalLinks;
import com.tanishk.auxalis.datasets.StarIdentifiers;
import com.tanishk.auxalis.datasets.StarRecord;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Maps;

/**
 * super hacky text dump of the Gliese 3 catalog
 * <p/>
 */
public class GlieseCatalog implements StarCatalog {

  private final Map<String, StarRecord> starsByName;

  public GlieseCatalog() throws IOException {

    starsByName = Maps.newHashMap();

    InputStream resourceAsStream = GlieseCatalog.class.getClassLoader().getResourceAsStream("com/tanishk/auxalis/datasets/gliese_3_catalog.txt.gz");
    GZIPInputStream gzis = new GZIPInputStream(resourceAsStream);
    Scanner scan = new Scanner(gzis);

    scan.nextLine();  //  source
    scan.nextLine();  //  blank
    scan.nextLine();  //  source
    scan.nextLine();  //  coordinate system
    scan.nextLine();  //  headers

    Map<DatasetName, String> sunIds = Maps.newHashMap();
    sunIds.put(DatasetName.GLIESE_ID, "Sun");
    sunIds.put(DatasetName.PROPER_NAME, "The Sun");

    StarIdentifiers sunIdentifiers = new StarIdentifiers();
    sunIdentifiers.setProperName("The Sun");
    sunIdentifiers.setGlieseId("Sun");

    starsByName.put("Sol",
        new StarRecord(
            sunIdentifiers,
            new ExternalLinks(),
            new ObjectValue(0.0, ValueSource.SUPPLIED, Unit.LY),
            new ObjectValue(0.0, ValueSource.SUPPLIED, Unit.RADIAN),
            new ObjectValue(0.0, ValueSource.SUPPLIED, Unit.RADIAN),
            new ObjectValue(4.83, ValueSource.SUPPLIED, Unit.MV),
            "G2V",
            0.65,
            null
        ));

    while (scan.hasNext()) {
      String line = scan.nextLine();
      String[] split = line.split("\\|");

      String glieseName = split[1].trim();

      StarIdentifiers identifiers = new StarIdentifiers();
      identifiers.setGlieseId(glieseName);

      String otherName = safeTrim(split[41]);
      if (otherName != null) {
        identifiers.setProperName(otherName);
      }

      Double parallax = parseOrNull(split[6]);
      if (parallax != null) {
        StarRecord record = new StarRecord(
            identifiers,
            new ExternalLinks(),
            new ObjectValue(AstroConvert.parallaxToLightyears(parseOrNull(split[6])), ValueSource.SUPPLIED, Unit.LY),
            new ObjectValue(AstroConvert.parseDegreeMinSec(split[2]), ValueSource.SUPPLIED, Unit.RADIAN),
            new ObjectValue(AstroConvert.parseDegreeMinSec(split[3]), ValueSource.SUPPLIED, Unit.RADIAN),
            new ObjectValue(parseOrNull(split[32]), ValueSource.SUPPLIED, Unit.MV),
            safeTrim(split[5]),
            parseOrNull(split[21]),
            null
        );

        starsByName.put(glieseName, record);
      }
    }
  }

  public StarRecord getStar(String name) {
    return starsByName.get(name);
  }


  private static String safeTrim(String s) {
    if (s == null) {
      return null;
    }
    return s.trim();
  }

  private static Double parseOrNull(String s) {
    if (s == null) {
      return null;
    }

    String trimmed = s.trim();

    if (trimmed.isEmpty()) {
      return null;
    }

    return Double.parseDouble(s);
  }

  @Override
  public Collection<StarRecord> getAllStars(final double maxLyDistance) {
    return Collections2.filter(starsByName.values(), new Predicate<StarRecord>() {
      @Override
      public boolean apply(StarRecord input) {
        return input.getSolDistance() <= maxLyDistance;
      }
    });
  }
}
