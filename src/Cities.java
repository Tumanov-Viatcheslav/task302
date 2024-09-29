import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;

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

    private static void build(City[] cities, TreeNode root, int depth) {
        if (cities.length == 1) {
            root.key = cities[0];
            return;
        }
        if (depth % 2 == 0)
            Arrays.sort(cities, Comparator.comparingDouble(c -> c.x));
        else
            Arrays.sort(cities, Comparator.comparingDouble(c -> c.y));
        int median = cities.length / 2;
        root.key = cities[median - 1];
        root.left = new TreeNode();
        root.right = new TreeNode();
        build(Arrays.copyOfRange(cities, 0, median), root.left, depth + 1);
        build(Arrays.copyOfRange(cities, median, cities.length), root.right, depth + 1);
    }

    private static TreeNode buildKDTree(City[] cities) {
        TreeNode root = new TreeNode();
        build(cities, root, 0);
        return root;
    }

    private static double searchNearestNeighbor(TreeNode tree, City city, int depth) {
        double best = Double.MAX_VALUE;
        boolean searchedLeft = false, checkSelf = city.equals(tree.key), isAxisX = depth % 2 == 0;

        if (tree.left == null && tree.right == null)
            return checkSelf ? best : city.distanceToCity(tree.key);
        if (checkSelf) {
            best = tree.left == null ? best : searchNearestNeighbor(tree.left, city, depth + 1);
            searchedLeft = true;
        }
        else if (isAxisX) {
            if (city.x < tree.key.x) {
                best = tree.left == null ? city.distanceToCity(tree.key) : searchNearestNeighbor(tree.left, city, depth + 1);
                searchedLeft = true;
            }
            else
                best = tree.right == null ? city.distanceToCity(tree.key) : searchNearestNeighbor(tree.right, city, depth + 1);
        }
        else {
            if (city.y < tree.key.y) {
                best = tree.left == null ? city.distanceToCity(tree.key) : searchNearestNeighbor(tree.left, city, depth + 1);
                searchedLeft = true;
            }
            else
                best = tree.right == null ? city.distanceToCity(tree.key) : searchNearestNeighbor(tree.right, city, depth + 1);
        }
        if (!checkSelf)
            best = Math.min(city.distanceToCity(tree.key), best);

        double otherBest = best;
        TreeNode otherChild = searchedLeft ? tree.right : tree.left;
        if (isAxisX && Math.abs(tree.key.x - city.x) <= best)
            otherBest = otherChild == null ? best : searchNearestNeighbor(otherChild, city, depth + 1);
        if (!isAxisX && Math.abs(tree.key.y - city.y) <= best)
            otherBest = otherChild == null ? best : searchNearestNeighbor(otherChild, city, depth + 1);

        return Math.min(best, otherBest);
    }

    private static double findMaxMinDistance(TreeNode tree, City[] cities) {
        double max = 0;
        for (int i = 0; i < cities.length; i++) {
            max = searchNearestNeighbor(tree, cities[i], 0);
        }
        return max;
    }

    public static void main(String[] args) {
        City[] cities = readFile("input.txt");
        TreeNode tree = buildKDTree(cities);
        double d1 = findMaxMinDistance(cities);
        double d2 = findMaxMinDistance(tree, cities);
        System.out.printf("Brute force = %.2f \nKD-Tree = %.2f", d1, d2);
    }
}