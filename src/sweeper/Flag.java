package sweeper;

//верхнее поле (с флажками)
class Flag {
    private Matrix flagMap;
    private int countOfClosedBoxex;

    void start() {
        flagMap = new Matrix(Box.CLOSED);
        countOfClosedBoxex = Ranges.getSize().x * Ranges.getSize().y;

    }

    Box get(Coordinate coordinate) {
        return flagMap.get(coordinate);
    }

    void setOpenedToBox(Coordinate coordinate) {
        flagMap.set(coordinate, Box.OPENED);
        countOfClosedBoxex --;
    }

    //помечяем флажками поле
    void toggleFlagToBox(Coordinate coordinate) {
        switch (flagMap.get(coordinate)) {
            case FLAGED:
                setClosedBox(coordinate);
                break;
            case CLOSED:
                setFlagToBox(coordinate);
                break;
        }
    }

    //убрать флажок
    void setClosedBox(Coordinate coordinate) {
        flagMap.set(coordinate,Box.CLOSED);
    }

    //установить флажок
     private void setFlagToBox(Coordinate coordinate) {
        flagMap.set(coordinate,Box.FLAGED);
    }

    // сколько закрытых ячеек
    int getCountOfClosedBoxes() {
        return countOfClosedBoxex;
    }

    // установить взорвавщиеся бомбы
    void setBombedToBox(Coordinate coordinate) {
        flagMap.set(coordinate, Box.BOMBED);
    }

    //открыть ячейку на закрытой бомбе
    public void setOpenedToClosedBombBox(Coordinate coordinate) {
        if (flagMap.get(coordinate) == Box.CLOSED)
            flagMap.set(coordinate, Box.OPENED);
    }

    // флаг есть, а бомбы нет
    public void setNoBombToFlagedSafeBox(Coordinate coordinate) {
        if (flagMap.get(coordinate) == Box.FLAGED)
            flagMap.set(coordinate, Box.NOBOMB);
    }

    //сколько флажков
    int getCountOfFlagedBoxesAround(Coordinate coordinate) {
        int count = 0;
        for (Coordinate around: Ranges.getCoordsAround(coordinate))
            if (flagMap.get(around) == Box.FLAGED)
                count ++;
        return count;
    }
}
