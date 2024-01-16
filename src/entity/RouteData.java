package entity;

import entity.Route;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RouteData {
    private List<Route> routes;
    private Random random = new Random();

    public RouteData() {
        this.routes = new ArrayList<>();
        createRoutes();
    }

    private void createRoutes() {
        String[] cities = Cities.getCities();
        for (int i = 1; i <= 10; i++) {
            int numberOfCities = random.nextInt(3) + 2;
            List<String> routeCities = new ArrayList<>();
            for (int j = 0; j < numberOfCities; j++) {
                routeCities.add(cities[random.nextInt(cities.length)]);
            }
            routes.add(new Route(String.valueOf(i), 100.0 * i, 0, false, routeCities));
        }
    }

    public List<Route> getRoutes() {
        return routes;
    }

    public void addRoute(Route route) {
        routes.add(route);
    }

    public void removeRoute(String routeId) {
        routes.removeIf(route -> route.getId().equals(routeId));
    }

    public boolean isRouteExists(List<String> cities) {
        for (Route route : routes) {
            if (route.getLocationPoints().equals(cities)) {
                return true;
            }
        }
        return false;
    }

    public Route getRouteById(String routeId) {
        for (Route route : routes) {
            if (route.getId().equals(routeId)) {
                return route;
            }
        }
        return null;
    }
}
