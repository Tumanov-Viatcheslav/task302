import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class Cities {

    private static City[] readFile(String fileName) {
        City[] cities = null;
        try (BufferedReader input = new BufferedReader(new FileReader(fileName))) {
            cities = new City[Integer.parseInt(input.readLine())];
            String[] cityStr;
            for (int i = 0; i < cities.length; i++) {
                cityStr = input.readLine().split(" ");
                cities[i] = new City(Integer.parseInt(cityStr[0]), Integer.parseInt(cityStr[1]));
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return cities;
    }

    private static double findMaxMinDistance(City[] cities) {
        double max = 0, closestNeighbourDistance, distance;
        for (City c1 : cities) {
            closestNeighbourDistance = Double.MAX_VALUE;
            for (City c2 : cities) {
                if (c1.equals(c2))
                    continue;
                distance = c1.distanceToCity(c2);
                if (distance < closestNeighbourDistance)
                    closestNeighbourDistance = distance;
            }
            if (max < closestNeighbourDistance)
                max = closestNeighbourDistance;
        }
        return max;
    }

    public static void main(String[] args) {
        City[] cities = readFile("input.txt");
        double d = findMaxMinDistance(cities);
        System.out.printf("%.2f", d);
    }
}