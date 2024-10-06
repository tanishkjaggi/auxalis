package com.tanishk.auxalis.api;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

import com.tanishk.auxalis.datasets.PlanetData;
import com.tanishk.auxalis.datasets.StarRecord;
import com.tanishk.auxalis.datasets.catalogs.ExoplanetCatalog;
import com.tanishk.auxalis.datasets.catalogs.StarCatalog;
import com.google.common.collect.Multimap;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class StarCatalogServlet extends HttpServlet {

  private final Gson gson = new Gson();
  private final StarCatalog catalog;
  private final ExoplanetCatalog exoplanets;

  public StarCatalogServlet(StarCatalog catalog,
                            ExoplanetCatalog exoplanets) {
    this.catalog = catalog;
    this.exoplanets = exoplanets;
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    try {
      JSONArray stars = new JSONArray();
      Collection<StarRecord> allStars = catalog.getAllStars(Double.parseDouble(req.getParameter("max_lys")));
      Multimap<Integer, PlanetData> planetObjs = exoplanets.getAllPlanetsByStarID(allStars);

      for (StarRecord star : allStars) {
        stars.put(new JSONObject(gson.toJson(star)));
      }

      JSONArray planets = new JSONArray();

      for (PlanetData planetData : planetObjs.values()) {
        planets.put(new JSONObject(gson.toJson(planetData)));
      }

      resp.getWriter().write(new JSONObject()
          .put("stars", stars)
          .put("planets", planets)
          .toString());
    } catch (JSONException e) {
      throw new RuntimeException(e);
    }
  }
}
