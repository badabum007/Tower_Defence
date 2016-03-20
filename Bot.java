package application;


/**
 * �����, ����������� ����
 * @author pixxx
 */
public class Bot {
	/** ���������� ����� */
	int[] CoordX;
	int[] CoordY;
	
	/** ���������� ��������� ����� */
	int Count = 0;
	
	/** ���������� ��� ����������� ����� */
	int Iterator = 0;
	String AllowSymbols = "UDLRE";
	
	/** �����, ��������� ���� */
	public Bot(){
		int countOfSymbols, temp; 
		int MaxCount = LevelData.levels[0].length*LevelData.levels[0][0].length() + 10;
		CoordX = new int[MaxCount];
		CoordY = new int[MaxCount];
		int arrayOfCounts[] = new int[MaxCount];
		for (int i = 1;  i<LevelData.levels[0].length - 1; i++){
			String PrevLine = LevelData.levels[0][i-1];
			String Line = LevelData.levels[0][i];
			String NextLine = LevelData.levels[0][i+1];
			for (int j = 1; j<Line.length() - 1; j++){
				// ��������� ��� �������� ������ �� ������� �������� 'U','D','L','R','E'
				// � ����������� countOfSymbols �� ������ �������� ������
				// �.�. ��� ������ countOfSymbols -> ��� ���� ��������� ����������
				// ����� � ������ ������
				if (Line.charAt(j) == '0'){
					countOfSymbols = 0;
					if (CheckString(PrevLine.charAt(j - 1))) countOfSymbols++;
					if (CheckString(PrevLine.charAt(j))) countOfSymbols++;
					if (CheckString(PrevLine.charAt(j + 1))) countOfSymbols++;
					if (CheckString(Line.charAt(j - 1))) countOfSymbols++;
					if (CheckString(Line.charAt(j + 1))) countOfSymbols++;
					if (CheckString(NextLine.charAt(j - 1))) countOfSymbols++;
					if (CheckString(NextLine.charAt(j))) countOfSymbols++;
					if (CheckString(NextLine.charAt(j + 1))) countOfSymbols++;
					// ��������� ���-�� countOfSymbols ��� ������ ������ � �����.
					// ����������
					arrayOfCounts[Count] = countOfSymbols;
					CoordX[Count] = i; CoordY[Count] = j;
					Count++;
				}
			}		
		}
		// ���������� ������ �� �������� countOfSymbols
		for (int i = 0; i<Count; i++){
			for (int j = i; j<Count; j++){
				if (arrayOfCounts[j] > arrayOfCounts[i]){
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
	 * �����, �����������, ���� �� � ������ AllowSymbols ������ char
	 * @param symbol - ������� ������
	 * @return
	 */
	boolean CheckString(char symbol){
		for(int i=0; i<AllowSymbols.length(); i++){
			if (AllowSymbols.charAt(i) == symbol) return true;
		}
		return false;
	}
	
	/**
	 * ����� �������� �����
	 */
	void createTower(){
		Tower tower = new Tower(CoordY[Iterator] * Main.BLOCK_SIZE, 
												CoordX[Iterator] * Main.BLOCK_SIZE, 150);
		Main.gameRoot.Towers.add(tower);
		Iterator++;
	}
}
