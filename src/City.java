public class City {
    double x, y;

    public City(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public City() {
    }

    public double distanceToCity(City another) {
        return Math.sqrt((another.x - x) * (another.x - x) + (another.y - y) * (another.y - y));
    }
}
