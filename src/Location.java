public class Location {
    private String name;
    private String latitude;
    private String longitude;

    public Location(String name, String latitude, String longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.name=name;
    }

    public double getLatitude() {
        double lat=Double.parseDouble(latitude);;
        return lat;
    }

    public double getLongitude() {
        double lon=Double.parseDouble(longitude);;
        return lon;
    }

    public static Location createLocation(String name){
        LocationAPI api=new LocationAPI();
        String [] coordinates=api.getCoordinates(name);
        String lat=coordinates[0];
        String lon=coordinates[1];
        Location loc=new Location(name,lat,lon);
        return loc;
    }

    public String getName(){return this.name;}
    @Override
    public String toString(){
        return name+","+latitude+","+longitude;
    }
}
