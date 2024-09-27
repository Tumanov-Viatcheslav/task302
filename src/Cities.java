import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

//Algorithm source: https://www.geeksforgeeks.org/closest-pair-of-points-using-divide-and-conquer-algorithm/
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
        Arrays.sort(cities, (c1, c2) -> {
            if (c1.x == c2.x)
                return 0;
            else return c1.x < c2.x ? -1 : 1;
        });
        //TODO
        return 0;
    }

    private static double findMinDistance(City[] cities) {
        //Min distance for 2 and 3 cities
        if (cities.length == 2)
            return cities[0].distanceToCity(cities[1]);
        if (cities.length == 3)
            return Math.min(
                    Math.min(
                            cities[0].distanceToCity(cities[1]),
                            cities[0].distanceToCity(cities[2])
                    ),
                    cities[1].distanceToCity(cities[2])
            );

        //Sort by x
        Arrays.sort(cities, (c1, c2) -> {
            if (c1.x == c2.x)
                return 0;
            else return c1.x < c2.x ? -1 : 1;
        });

        //Calculate distances of left and right parts of coordinate plane and get their minimum ('d')
        double dl, dr, d;
        dl = findMinDistance(Arrays.copyOfRange(cities, 0, cities.length / 2));
        dr = findMinDistance(Arrays.copyOfRange(cities, cities.length / 2, cities.length));
        d = Math.min(dl, dr);

        int leftStripBorder, rightStripBorder;
        leftStripBorder = Arrays.binarySearch(cities, new City(cities[cities.length / 2 - 1].x - d, 0), (c1, c2) -> {
            if (c1.x == c2.x)
                return 0;
            else return c1.x < c2.x ? -1 : 1;
        });
        rightStripBorder = Arrays.binarySearch(cities, new City(cities[cities.length / 2 - 1].x - d, 0), (c1, c2) -> {
            if (c1.x == c2.x)
                return 0;
            else return c1.x < c2.x ? -1 : 1;
        });
        City[] strip = Arrays.copyOfRange(cities, leftStripBorder, rightStripBorder);


        return 0;
    }

    public static void main(String[] args) {
        City[] cities = readFile("input.txt");
        double d = findMaxMinDistance(cities);
        System.out.println(d);
    }
}