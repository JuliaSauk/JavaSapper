import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import sweeper.Box;
import sweeper.Coordinate;
import sweeper.Game;
import sweeper.Ranges;

public class JavaSweeper extends JFrame {

    private Game game;

    private JPanel panel;
    private JLabel label;

    private final int COLS = 9;
    private final int ROWS = 9;
    private final int BOMBS = 10;
    private final int IMAGE_SIZE = 50;

    public static void main(String[] args) {
        new JavaSweeper();
    }

    private JavaSweeper() {
        game = new Game(COLS, ROWS, BOMBS);
        game.start();
        setImages();
        initLabel();
        initPanel();
        initFrame();
    }

    // добавляет метку на экран
    private void initLabel() {
        label = new JLabel(" Welcome!");
        add(label, BorderLayout.SOUTH);
    }

    private void initPanel() {

        panel = new JPanel(){
            @Override
            protected void paintComponent(Graphics graphics) {
                super.paintComponent(graphics);
                for (Coordinate coordinate: Ranges.getAllCoordinates()) {
                    graphics.drawImage((Image) game.getBox(coordinate).image,
                            coordinate.x * IMAGE_SIZE, coordinate.y * IMAGE_SIZE, this);
                }
            }
        };

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {

                int x = e.getX() / IMAGE_SIZE; //получаем координаты куда мышка ткнуал
                int y = e.getY() / IMAGE_SIZE;
                Coordinate coordinate = new Coordinate(x, y); // получаем координату нажатия мышки
                if (e.getButton() == MouseEvent.BUTTON1)  // левая кнопка
                    game.pressLeftButtom(coordinate);
                if (e.getButton() == MouseEvent.BUTTON3)  // правая кнопка
                    game.pressRightButtom(coordinate);
                label.setText(getMessage());
                panel.repaint();
            }
        }); // добавляем управление мышкой

        panel.setPreferredSize(new Dimension(
                Ranges.getSize().x * IMAGE_SIZE,
                Ranges.getSize().y * IMAGE_SIZE));//устанавливаем размеры панели
        add(panel); // добавлям панель
    }

    private String getMessage() {
        switch (game.getState()) {
            case PLAYED:
                return "Think twice!";
            case BOMBED:
                return "You LOSE!";
            case WINNER:
                return "Congratulations";
                default:
                    return "Welcome!";
        }
    }

    private void initFrame() {

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // выход
        setTitle("Java Sweeper"); // название программы
        setResizable(false); // не нужно будет изменять размер окна
        setVisible(true);
        pack(); //минимальный размер контейнера (панельки) для расположения всех компонентов
        setLocationRelativeTo(null); // расположение по центру
        setIconImage(getImage("icon"));
    }

    private void setImages() {
        for (Box box : Box.values()){
            box.image = getImage(box.name().toLowerCase());
        }
    }

    private Image getImage(String name) {
        String filename = "img/" + name + ".png";
        ImageIcon icon = new ImageIcon(getClass().getResource(filename));
        return icon.getImage();
    }
}
