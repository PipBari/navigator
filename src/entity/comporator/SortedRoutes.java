package entity.comporator;

import entity.Route;

import java.util.*;
import java.util.stream.Collectors;

public class SortedRoutes {
    private List<Route> sortedRoutes;

    public void addRoute(Route route) {
        sortedRoutes.add(route);
    }

    public SortedRoutes() {
        this.sortedRoutes = new ArrayList<>();
    }

    public void addAll(List<Route> routes) {
        this.sortedRoutes.addAll(routes);
    }

    public void removeRoute(String routeId) {
        sortedRoutes.removeIf(route -> route.getId().equals(routeId));
    }

    public List<Route> getSortedRoutes() {
        return sortedRoutes.stream()
                .sorted((route1, route2) -> {
                    int favoriteComparison = Boolean.compare(route2.isFavorite(), route1.isFavorite());

                    if (favoriteComparison != 0) {
                        return favoriteComparison;
                    }
                    if (route1.isFavorite() && route2.isFavorite()) {
                        return Double.compare(route1.getDistance(), route2.getDistance());
                    }
                    int popularityComparison = Integer.compare(route2.getPopularity(), route1.getPopularity());

                    if (popularityComparison != 0) {
                        return popularityComparison;
                    }
                    return Double.compare(route1.getDistance(), route2.getDistance());
                })
                .collect(Collectors.toList());
    }
}

