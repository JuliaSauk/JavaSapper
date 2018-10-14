package sweeper;

public class Game {

    private Bomb bomb;
    private Flag flag;

    public GameState getState() {
        return state;
    }

    private GameState state;


    public Game(int cols, int rows, int bombs) { 
        Ranges.setSize(new Coordinate(cols, rows));
        bomb = new Bomb(bombs);
        flag = new Flag();
    }

    //
    public void start() {
     bomb.start();
     flag.start();
     state = GameState.PLAYED;
    }

    public Box getBox(Coordinate coordinate) {
        if (flag.get(coordinate) == Box.OPENED)
            return bomb.get(coordinate);
        else
            return flag.get(coordinate);
    }

    public void pressLeftButtom(Coordinate coordinate) {
        if (gameOver()) return;
        openBox(coordinate);
        checkWinner();
    }

    private void checkWinner() {
        if (state == GameState.PLAYED)
            if (flag.getCountOfClosedBoxes() == bomb.getTotalBombs()) // к-во закрытых клеток == к-во бомб
                state = GameState.WINNER;
    }

    private void openBox(Coordinate coordinate) {
        switch (flag.get(coordinate)) {
            case OPENED: setOpenedToClosedBoxesAroundNumber(coordinate); return;
            case FLAGED: return;
            case CLOSED:
                switch (bomb.get(coordinate)) {
                    case ZERO: openBoxesAround(coordinate); return;
                    case BOMB: openBombs(coordinate); return;
                        default: flag.setOpenedToBox(coordinate); return; 
                }
        }
    }

   void setOpenedToClosedBoxesAroundNumber(Coordinate coordinate) {
        if (bomb.get(coordinate) != Box.BOMB)
            if(flag.getCountOfFlagedBoxesAround(coordinate) == bomb.get(coordinate).getNumber())
                for (Coordinate around: Ranges.getCoordsAround(coordinate))
                    if (flag.get(around) == Box.CLOSED)
                        openBox(around);
    }

    private void openBombs(Coordinate bombed) {
        state = GameState.BOMBED;
        flag.setBombedToBox(bombed);
        for (Coordinate coordinate: Ranges.getAllCoordinates())
            if (bomb.get(coordinate) == Box.BOMB)
                flag.setOpenedToClosedBombBox(coordinate);
            else
                flag.setNoBombToFlagedSafeBox(coordinate);
    }

    private void openBoxesAround(Coordinate coordinate) {
        flag.setOpenedToBox(coordinate); 
        for (Coordinate around : Ranges.getCoordsAround(coordinate)) {
            openBox(around);
        } 
    }

    public void pressRightButtom(Coordinate coordinate) {
        if (gameOver()) return;
        flag.toggleFlagToBox(coordinate);
    }

    private boolean gameOver() {
        if (state == GameState.PLAYED)
            return false;
        start();
        return true;
    }
}
