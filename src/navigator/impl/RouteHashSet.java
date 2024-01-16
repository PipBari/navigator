package navigator.impl;

import entity.Route;

import java.util.*;

public class RouteHashSet {
    private final HashSet<Route> routes = new HashSet<>();

    public void add(Route route) {
        routes.add(route);
    }

    public boolean remove(String routeId) {
        Iterator<Route> iterator = routes.iterator();
        while (iterator.hasNext()) {
            Route route = iterator.next();
            if (route.getId().equals(routeId)) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }

    public boolean contains(Route route) {
        return routes.contains(route);
    }

    public int size() {
        return routes.size();
    }

    public Route get(String routeId) {
        for (Route route : routes) {
            if (route.getId().equals(routeId)) {
                return route;
            }
        }
        return null;
    }

    public void choose(String routeId) {
        for (Route route : routes) {
            if (route.getId().equals(routeId)) {
                route.setPopularity(route.getPopularity() + 1);
                return;
            }
        }
    }

    public Iterable<Route> searchRoutes(String startPoint, String endPoint) {
        List<Route> result = new ArrayList<>();
        for (Route route : routes) {
            List<String> points = route.getLocationPoints();
            if (points.contains(startPoint) && points.contains(endPoint) &&
                    points.indexOf(startPoint) < points.indexOf(endPoint)) {
                result.add(route);
            }
        }
        result.sort(Comparator.comparing((Route route) -> !route.isFavorite())
                .thenComparingInt(route -> computePointsBetween(route, startPoint, endPoint))
                .thenComparingInt(Route::getPopularity).reversed());
        return result;
    }

    private int computePointsBetween(Route route, String startPoint, String endPoint) {
        List<String> points = route.getLocationPoints();
        int startIndex = points.indexOf(startPoint);
        int endIndex = points.indexOf(endPoint);
        return endIndex - startIndex - 1;
    }

    public Iterable<Route> getFavoriteRoutes(String destinationPoint) {
        List<Route> result = new ArrayList<>();
        for (Route route : routes) {
            if (route.isFavorite() && route.getLocationPoints().contains(destinationPoint) &&
                    !route.getLocationPoints().get(0).equals(destinationPoint)) {
                result.add(route);
            }
        }
        result.sort(Comparator.comparingDouble(Route::getDistance)
                .thenComparingInt(Route::getPopularity).reversed());
        return result;
    }

    public Iterable<Route> getTop3Routes() {
        List<Route> list = new ArrayList<>(routes);
        list.sort(Comparator.comparingInt(Route::getPopularity).reversed()
                .thenComparingDouble(Route::getDistance)
                .thenComparingInt(route -> route.getLocationPoints().size()));
        return list.subList(0, Math.min(5, list.size()));
    }
}
