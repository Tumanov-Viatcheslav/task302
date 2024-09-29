import java.util.Objects;

public class City {
    double x, y;

    public City(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public City() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof City city)) return false;
        return Double.compare(x, city.x) == 0 && Double.compare(y, city.y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    public double distanceToCity(City another) {
        return Math.sqrt((another.x - x) * (another.x - x) + (another.y - y) * (another.y - y));
    }

    @Override
    public String toString() {
        return "City{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
