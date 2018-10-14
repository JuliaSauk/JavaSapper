package sweeper;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;

public class Ranges {

    private static Coordinate size; //размер экрана
    private static ArrayList<Coordinate> allCoordinates;
    private static Random random = new Random();

    public static Coordinate getSize() {
        return size;
    }

    static void setSize(Coordinate _size) {
        size = _size;
        allCoordinates = new ArrayList<>();
        for (int y = 0; y < size.y; y++)
            for (int x = 0; x < size.x; x++) {
                allCoordinates.add(new Coordinate(x, y));
        }
    }

    public static ArrayList<Coordinate> getAllCoordinates() {
        return allCoordinates;
    }

    //проверяем находится ли координата в пределах нашего экрана
    static boolean inRange(Coordinate coordinate) {
        return coordinate.x >= 0 && coordinate.x < size.x &&
                coordinate.y >= 0 && coordinate.y < size.y;
    }

    static Coordinate getRandomCoord() {
        return new Coordinate(random.nextInt(size.x),
                              random.nextInt(size.y));
    }

    static ArrayList<Coordinate> getCoordsAround(Coordinate coordinate) {
        Coordinate around;
        ArrayList<Coordinate> list = new ArrayList<>(); //координаты вокруг
        for (int x = coordinate.x - 1; x <= coordinate.x + 1; x++)
            for (int y = coordinate.y - 1; y <= coordinate.y + 1; y++)
                if (inRange(around = new Coordinate(x,y))) //координата присутствует на экране
                    if (!around.equals(coordinate))
                        list.add(around);
        return list;
    }
}
