package application;


/**
 * �����, ����������� ����
 * 
 * @author pixxx
 */
public class Bot {
  /** ���������� ����� */
  int[] coordX;
  int[] coordY;

  /** ���������� ��������� ����� */
  int count = 0;

  /** ���������� ��� ����������� ����� */
  int iterator = 0;
  String allowSymbols = "UDLRE";

  /** �����, ��������� ���� */
  public Bot() {
    int countOfSymbols, temp;
    int maxCount = LevelData.levels[0].length * LevelData.levels[0][0].length() + 10;
    coordX = new int[maxCount];
    coordY = new int[maxCount];
    int arrayOfCounts[] = new int[maxCount];
    for (int i = 1; i < LevelData.levels[0].length - 1; i++) {
      String prevLine = LevelData.levels[0][i - 1];
      String line = LevelData.levels[0][i];
      String nextLine = LevelData.levels[0][i + 1];
      for (int j = 1; j < line.length() - 1; j++) {
        /**
         * ��������� ��� �������� ������ �� ������� �������� 'U','D','L','R','E' � �����������
         * countOfSymbols �� ������ �������� ������ �.�. ��� ������ countOfSymbols -> ��� ����
         * ��������� ���������� ����� � ������ ������
         */
        if (line.charAt(j) == '0') {
          countOfSymbols = 0;
          if (checkString(prevLine.charAt(j - 1)))
            countOfSymbols++;
          if (checkString(prevLine.charAt(j)))
            countOfSymbols++;
          if (checkString(prevLine.charAt(j + 1)))
            countOfSymbols++;
          if (checkString(line.charAt(j - 1)))
            countOfSymbols++;
          if (checkString(line.charAt(j + 1)))
            countOfSymbols++;
          if (checkString(nextLine.charAt(j - 1)))
            countOfSymbols++;
          if (checkString(nextLine.charAt(j)))
            countOfSymbols++;
          if (checkString(nextLine.charAt(j + 1)))
            countOfSymbols++;
          /**
           * ��������� ���-�� countOfSymbols ��� ������ ������ � �����. ����������
           */
          arrayOfCounts[count] = countOfSymbols;
          coordX[count] = i;
          coordY[count] = j;
          count++;
        }
      }
    }
    /** ���������� ������ �� �������� countOfSymbols */
    for (int i = 0; i < count; i++) {
      for (int j = i; j < count; j++) {
        if (arrayOfCounts[j] > arrayOfCounts[i]) {
          temp = arrayOfCounts[i];
          arrayOfCounts[i] = arrayOfCounts[j];
          arrayOfCounts[j] = temp;
          temp = coordX[i];
          coordX[i] = coordX[j];
          coordX[j] = temp;
          temp = coordY[i];
          coordY[i] = coordY[j];
          coordY[j] = temp;
        }
      }
    }
  }

  /**
   * �����, �����������, ���� �� � ������ AllowSymbols ������ char
   * 
   * @param symbol - ������� ������
   * @return
   */
  boolean checkString(char symbol) {
    for (int i = 0; i < allowSymbols.length(); i++) {
      if (allowSymbols.charAt(i) == symbol)
        return true;
    }
    return false;
  }

  /**
   * ����� �������� �����
   */
  void createTower() {
    Tower tower =
        new Tower(coordY[iterator] * Main.BLOCK_SIZE, coordX[iterator] * Main.BLOCK_SIZE, 150);
    Main.gameRoot.towers.add(tower);
    iterator++;
  }
}
