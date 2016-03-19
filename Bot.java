package application;

public class Bot {
	int[] CoordX;
	int[] CoordY;
	int Count = 0;
	String AllowSymbols = "UDLRE";
	
	public Bot(){
		int max, countOfSymbols; 
		int arrayOfCounts[] = new int[1000];
		for (int i = 1;  i<LevelData.levels[0].length - 1; i++){
			String PrevLine = LevelData.levels[0][i-1];
			String Line = LevelData.levels[0][i];
			String NextLine = LevelData.levels[0][i+1];
			for (int j = 1; j<Line.length() - 1; j++){
				countOfSymbols = 0;
				if (CheckString(PrevLine.charAt(j - 1))) countOfSymbols++;
				if (CheckString(PrevLine.charAt(j))) countOfSymbols++;
				if (CheckString(PrevLine.charAt(j + 1))) countOfSymbols++;
				if (CheckString(Line.charAt(j - 1))) countOfSymbols++;
				if (CheckString(Line.charAt(j + 1))) countOfSymbols++;
				if (CheckString(NextLine.charAt(j - 1))) countOfSymbols++;
				if (CheckString(NextLine.charAt(j))) countOfSymbols++;
				if (CheckString(NextLine.charAt(j + 1))) countOfSymbols++;
				arrayOfCounts[Count] = countOfSymbols;
				Count++;
			}		
		}
		for (int i = 0; i<Count; i++){
			
		}
	}
	
	boolean CheckString(char symbol){
		for(int i=0; i<AllowSymbols.length(); i++){
			if (AllowSymbols.charAt(i) == symbol) return true;
		}
		return false;
	}
}
