package sample.core;

public class Route {
    String collection;
    String delivery;
    String name;

    public static Route routeBirminghamToLondon(){
        Country country = Country.getInstance();

        Route route = new Route();
        route.collection = country.london;
        route.delivery = country.birmingham;

        return route;
    }
}
