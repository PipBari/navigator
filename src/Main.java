import entity.Route;
import entity.RouteData;
import entity.comporator.SortedRoutes;
import navigator.impl.NavigatorImpl;

import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        RouteData routeData = new RouteData();
        SortedRoutes sortedRoutes = new SortedRoutes();
        NavigatorImpl navigator = new NavigatorImpl(routeData, sortedRoutes);
        sortedRoutes.addAll(routeData.getRoutes());

        for (Route route : routeData.getRoutes()) {
            navigator.addRoute(route);
        }

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Выберите действие:");
            System.out.println("1 - Просмотреть маршрут");
            System.out.println("2 - Вывести все маршруты");
            System.out.println("3 - Добавить маршрут в избранное");
            System.out.println("4 - Вывести ТОП-5 популярных маршрута");
            System.out.println("5 - Вывести все избранные маршруты");
            System.out.println("6 - Удалить маршрут");
            System.out.println("7 - Добавить новый маршрут");
            System.out.println("0 - Выход");

            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    System.out.println("Введите ID маршрута:");
                    String routeId = scanner.next();
                    Route route = navigator.getRoute(routeId);
                    if (route != null) {
                        navigator.chooseRoute(routeId);
                        printRoute(route);
                    } else {
                        System.out.println("Маршрут не найден.");
                    }
                    break;
                case 2:
                    SortedRoutes sortedRoutesInstance = new SortedRoutes();
                    List<Route> routeList = new ArrayList<>(routeData.getRoutes());
                    sortedRoutesInstance.addAll(routeList);
                    sortedRoutesInstance.getSortedRoutes().forEach(Main::printRoute);
                    break;
                case 3:
                    System.out.println("Введите ID маршрута для добавления в избранное:");
                    String favRouteId = scanner.next();
                    Route favRoute = navigator.getRoute(favRouteId);
                    if (favRoute != null) {
                        favRoute.setFavorite(true);
                        System.out.println("Маршрут добавлен в избранное.");
                    } else {
                        System.out.println("Маршрут не найден.");
                    }
                    break;
                case 4:
                    List<Route> allRoutes = routeData.getRoutes();
                    List<Route> top3Routes = allRoutes.stream()
                            .sorted((route1, route2) -> {
                                int popularityComparison = Integer.compare(route2.getPopularity(), route1.getPopularity());
                                if (popularityComparison != 0) {
                                    return popularityComparison;
                                }
                                return Double.compare(route1.getDistance(), route2.getDistance());
                            })
                            .limit(5)
                            .collect(Collectors.toList());

                    top3Routes.forEach(Main::printRoute);
                    break;
                case 5:
                    System.out.println("Избранные маршруты:");
                    List<Route> favoriteRoutes = routeData.getRoutes().stream()
                            .filter(Route::isFavorite)
                            .sorted(Comparator.comparingDouble(Route::getDistance))
                            .collect(Collectors.toList());
                    favoriteRoutes.forEach(Main::printRoute);
                    break;
                case 6:
                    System.out.println("Введите ID маршрута для удаления:");
                    String delRouteId = scanner.next();
                    boolean removed = navigator.removeRoute(delRouteId);
                    if (removed) {
                        sortedRoutes.removeRoute(delRouteId);
                        System.out.println("Маршрут удален.");
                    } else {
                        System.out.println("Маршрут с таким ID не найден.");
                    }
                    break;
                case 7:
                    addNewRoute(navigator, routeData, sortedRoutes);
                    break;
                case 0:
                    System.out.println("Выход из программы.");
                    scanner.close();
                    return;
                default:
                    System.out.println("Неверный выбор. Пожалуйста, попробуйте снова.");
                    break;
            }
        }
    }

    private static void addNewRoute(NavigatorImpl navigator, RouteData routeData, SortedRoutes sortedRoutes) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите ID маршрута:");
        String routeId = scanner.next();
        if (routeData.getRouteById(routeId) != null) {
            System.out.println("Маршрут с таким ID уже существует. Пожалуйста, выберите другой ID.");
            return;
        }
        System.out.println("Введите количество городов в маршруте (максимум 4):");
        int numCities = Math.min(scanner.nextInt(), 4);

        List<String> cities = new ArrayList<>();
        System.out.println("Введите названия городов:");
        for (int i = 0; i < numCities; i++) {
            cities.add(scanner.next());
        }
        System.out.println("Введите расстояние маршрута:");
        double distance = scanner.nextDouble();

        if (!routeData.isRouteExists(cities)) {
            Route newRoute = new Route(routeId, distance, 0, false, cities);
            navigator.addRoute(newRoute);
            routeData.addRoute(newRoute);
            sortedRoutes.addRoute(newRoute);
            System.out.println("Маршрут добавлен.");
        } else {
            System.out.println("Такой маршрут уже существует.");
        }
    }

    private static void printRoute(Route route) {
        System.out.println("ID: " + route.getId() + ", Расстояние: " + route.getDistance() +
                ", Популярность: " + route.getPopularity() + ", Избранный: " + route.isFavorite() +
                ", Точки маршрута: " + route.getLocationPoints());
    }
}
