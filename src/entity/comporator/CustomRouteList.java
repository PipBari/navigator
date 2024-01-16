package entity.comporator;

import entity.Route;

import java.util.ArrayList;
import java.util.List;

public class CustomRouteList {
    private RouteNode head;

    public CustomRouteList() {
        head = null;
    }

    public void add(Route route) {
        RouteNode newNode = new RouteNode(route);
        if (head == null) {
            head = newNode;
        } else {
            RouteNode current = head;
            while (current.getNext() != null) {
                current = current.getNext();
            }
            current.setNext(newNode);
        }
    }

    public void remove(String routeId) {
        if (head != null && head.getRoute().getId().equals(routeId)) {
            head = head.getNext();
            return;
        }

        RouteNode current = head;
        while (current != null && current.getNext() != null) {
            if (current.getNext().getRoute().getId().equals(routeId)) {
                current.setNext(current.getNext().getNext());
                return;
            }
            current = current.getNext();
        }
    }

    public List<Route> toList() {
        List<Route> list = new ArrayList<>();
        RouteNode current = head;
        while (current != null) {
            list.add(current.getRoute());
            current = current.getNext();
        }
        return list;
    }
}
