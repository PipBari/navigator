package navigator.impl;

import entity.Route;
import entity.RouteData;
import entity.comporator.SortedRoutes;
import navigator.Navigator;

import java.util.*;
import java.util.stream.Collectors;

public class NavigatorImpl implements Navigator {
    private final RouteHashSet routeHashSet = new RouteHashSet();
    private final RouteData routeData;
    private final SortedRoutes sortedRoutes;

    public NavigatorImpl(RouteData routeData, SortedRoutes sortedRoutes) {
        this.routeData = routeData;
        this.sortedRoutes = sortedRoutes;
    }

    @Override
    public void addRoute(Route route) {
        routeHashSet.add(route);
    }

    @Override
    public boolean removeRoute(String routeId) {
        boolean removed = routeHashSet.remove(routeId);
        if (removed) {
            routeData.removeRoute(routeId);
            sortedRoutes.removeRoute(routeId);
        }
        return removed;
    }

    @Override
    public boolean contains(Route route) {
        return routeHashSet.contains(route);
    }

    @Override
    public int size() {
        return routeHashSet.size();
    }

    @Override
    public Route getRoute(String routeId) {
        return routeHashSet.get(routeId);
    }

    @Override
    public void chooseRoute(String routeId) {
        Route route = routeHashSet.get(routeId);
        if (route != null) {
            route.setPopularity(route.getPopularity() + 1);
            routeHashSet.choose(routeId);
            routeData.updateRoute(route);
        }
    }


        @Override
    public Iterable<Route> searchRoutes(String startPoint, String endPoint) {
        return routeHashSet.searchRoutes(startPoint, endPoint);
    }

    @Override
    public Iterable<Route> getFavoriteRoutes(String destinationPoint) {
        return routeHashSet.getFavoriteRoutes(destinationPoint);
    }

    @Override
    public Iterable<Route> getTop3Routes() {
        return routeHashSet.getTop3Routes();
    }
}
