package com.tanishk.auxalis.datasets.catalogs;

import java.util.Collection;

import com.tanishk.auxalis.datasets.PlanetData;
import com.tanishk.auxalis.datasets.StarRecord;
import com.google.common.collect.Multimap;

public interface ExoplanetCatalog {
  public Multimap<Integer, PlanetData> getAllPlanetsByStarID(Collection<StarRecord> stars);
}
