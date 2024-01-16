package entity.comporator;

import entity.Route;

public class RouteNode {
    private Route route;
    private RouteNode next;

    public RouteNode(Route route) {
        this.route = route;
        this.next = null;
    }

    public Route getRoute() {
        return route;
    }

    public RouteNode getNext() {
        return next;
    }

    public void setNext(RouteNode next) {
        this.next = next;
    }
}