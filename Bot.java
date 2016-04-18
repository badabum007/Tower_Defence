package application;


/**
 * Класс, реализующий бота
 * 
 * @author pixxx
 */
public class Bot {
  /** Координаты вышек */
  int[] CoordX;
  int[] CoordY;

  /** Количество возможных вышек */
  int Count = 0;

  /** Количество уже построенных вышек */
  int Iterator = 0;
  String AllowSymbols = "UDLRE";

  /** Метод, создающий бота */
  public Bot() {
    int countOfSymbols, temp;
    int MaxCount = LevelData.levels[0].length * LevelData.levels[0][0].length() + 10;
    CoordX = new int[MaxCount];
    CoordY = new int[MaxCount];
    int arrayOfCounts[] = new int[MaxCount];
    for (int i = 1; i < LevelData.levels[0].length - 1; i++) {
      String PrevLine = LevelData.levels[0][i - 1];
      String Line = LevelData.levels[0][i];
      String NextLine = LevelData.levels[0][i + 1];
      for (int j = 1; j < Line.length() - 1; j++) {
        // Проверяем все соседние клетки на наличие символов 'U','D','L','R','E'
        // И увеличиваем countOfSymbols на каждый найденый символ
        // Т.о. чем больше countOfSymbols -> тем выше приоритет постановки
        // Вышки в данную клетку
        if (Line.charAt(j) == '0') {
          countOfSymbols = 0;
          if (CheckString(PrevLine.charAt(j - 1)))
            countOfSymbols++;
          if (CheckString(PrevLine.charAt(j)))
            countOfSymbols++;
          if (CheckString(PrevLine.charAt(j + 1)))
            countOfSymbols++;
          if (CheckString(Line.charAt(j - 1)))
            countOfSymbols++;
          if (CheckString(Line.charAt(j + 1)))
            countOfSymbols++;
          if (CheckString(NextLine.charAt(j - 1)))
            countOfSymbols++;
          if (CheckString(NextLine.charAt(j)))
            countOfSymbols++;
          if (CheckString(NextLine.charAt(j + 1)))
            countOfSymbols++;
          // Сохраняем кол-во countOfSymbols для каждой клетки и соотв.
          // Координаты
          arrayOfCounts[Count] = countOfSymbols;
          CoordX[Count] = i;
          CoordY[Count] = j;
          Count++;
        }
      }
    }
    // Сортировка клеток по значению countOfSymbols
    for (int i = 0; i < Count; i++) {
      for (int j = i; j < Count; j++) {
        if (arrayOfCounts[j] > arrayOfCounts[i]) {
          temp = arrayOfCounts[i];
          arrayOfCounts[i] = arrayOfCounts[j];
          arrayOfCounts[j] = temp;
          temp = CoordX[i];
          CoordX[i] = CoordX[j];
          CoordX[j] = temp;
          temp = CoordY[i];
          CoordY[i] = CoordY[j];
          CoordY[j] = temp;
        }
      }
    }
  }

  /**
   * Метод, проверяющий, есть ли в строке AllowSymbols символ char
   * 
   * @param symbol - Искомый символ
   * @return
   */
  boolean CheckString(char symbol) {
    for (int i = 0; i < AllowSymbols.length(); i++) {
      if (AllowSymbols.charAt(i) == symbol)
        return true;
    }
    return false;
  }

  /**
   * Метод создания вышки
   */
  void createTower() {
    Tower tower =
        new Tower(CoordY[Iterator] * Main.BLOCK_SIZE, CoordX[Iterator] * Main.BLOCK_SIZE, 150);
    Main.gameRoot.Towers.add(tower);
    Iterator++;
  }
}
