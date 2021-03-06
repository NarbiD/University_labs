package A;

public class Location {
    private double longitude, latitude;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return Double.compare(location.longitude, longitude) == 0 &&
                Double.compare(location.latitude, latitude) == 0;
    }

    @Override
    public int hashCode() {

        return (int) (latitude*1000 + longitude);
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    Location(double longitude, double latitude) {
        if (latitude > 90 || latitude < -90 || longitude > 180 || longitude < -180) {
            throw new IllegalArgumentException("Заданы некорректные координаты");
        }
        this.longitude = longitude;
        this.latitude = latitude;
    }

    @Override
    public String toString() {
        return  "longitude=" + longitude +
                ", latitude=" + latitude;
    }
}
