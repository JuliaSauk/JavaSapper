package sweeper;

 class Bomb {
    private Matrix bombMap;
     private int totalBombs;

     Bomb(int totalBombs) {
         this.totalBombs = totalBombs;
         fixBombsCount();
     }

     void start() {
         bombMap = new Matrix(Box.ZERO);
         for (int j = 0; j < totalBombs; j++)
         placeBomb();
     }

     Box get(Coordinate coordinate) {
         return bombMap.get(coordinate);
     }

     private void fixBombsCount() {
         int maxBombs = Ranges.getSize().x * Ranges.getSize().y / 2; // максимальное к-во бомб - половина поля
         if (totalBombs > maxBombs)
             totalBombs = maxBombs;
     }

     //разместить бомбу
     private void placeBomb() {

         while (true) { //выбирает координату в которой бомбы нет
             Coordinate coordinate = Ranges.getRandomCoord();
             if (Box.BOMB == bombMap.get(coordinate)) // если в координате уже есть бомба
                 continue; //ищем другую координату
             bombMap.set(coordinate, Box.BOMB);
             incNumbersAroundBomb(coordinate);
             break;
         }
     }

     private void incNumbersAroundBomb(Coordinate coordinate) {
         for (Coordinate around : Ranges.getCoordsAround(coordinate))
            if (Box.BOMB != bombMap.get(around))
             bombMap.set(around, bombMap.get(around).getNextNumberBox());

     }

     int getTotalBombs() {
         return totalBombs;
     }
 }
