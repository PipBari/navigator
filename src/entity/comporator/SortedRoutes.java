package entity.comporator;

import entity.Route;

import java.util.*;
import java.util.stream.Collectors;

public class SortedRoutes {
    private CustomRouteList routeList;

    public SortedRoutes() {
        this.routeList = new CustomRouteList();
    }

    public void addRoute(Route route) {
        routeList.add(route);
    }

    public void addAll(List<Route> routes) {
        for (Route route : routes) {
            routeList.add(route);
        }
    }

    public void removeRoute(String routeId) {
        routeList.remove(routeId);
    }

    public List<Route> getSortedRoutes() {
        List<Route> unsortedRoutes = routeList.toList();
        return unsortedRoutes.stream()
                .sorted(new Comparator<Route>() {
                    @Override
                    public int compare(Route route1, Route route2) {
                        int favoriteComparison = Boolean.compare(route2.isFavorite(), route1.isFavorite());
                        if (favoriteComparison != 0) {
                            return favoriteComparison;
                        }
                        int popularityComparison = Integer.compare(route2.getPopularity(), route1.getPopularity());
                        if (popularityComparison != 0) {
                            return popularityComparison;
                        }
                        int distanceComparison = Double.compare(route1.getDistance(), route2.getDistance());
                        if (distanceComparison != 0) {
                            return distanceComparison;
                        }
                        return Integer.compare(route1.getLocationPoints().size(), route2.getLocationPoints().size());
                    }
                })
                .collect(Collectors.toList());
    }
}

