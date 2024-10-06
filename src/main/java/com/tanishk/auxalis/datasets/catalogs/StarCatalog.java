package com.tanishk.auxalis.datasets.catalogs;

import com.tanishk.auxalis.datasets.StarRecord;

import java.util.Collection;

public interface StarCatalog {
  public Collection<StarRecord> getAllStars(final double maxLyDistance);
}
