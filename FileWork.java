package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import application.QuickSort;

public class FileWork {
  /** ��������� ���� ��� ���������� */
  String LoadFile;

  public void CreateTempFile(String nameOfFile) {
    File TempFile = new File(nameOfFile);
    try {
      FileWriter out = new FileWriter(TempFile.getAbsoluteFile());
      out.write("");
      out.close();
    } catch (IOException e1) {
      e1.printStackTrace();
    }
    try {
      TempFile.createNewFile();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void AddToFile(String nameOfFile, String addingPart) {
    File file = new File(nameOfFile);
    try {
      FileWriter out = new FileWriter(file.getAbsoluteFile(), true);
      out.write(addingPart);
      out.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void CreateSave() throws IOException {
    String nameOfFile =
        new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime()) + "_"
            + Main.gameRoot.Towers.size() + ".save";
    File file = new File(nameOfFile);
    try {
      file.createNewFile();
    } catch (IOException e) {
      e.printStackTrace();
    }
    try {
      BufferedReader reader = new BufferedReader(new FileReader("positions.txt"));
      FileWriter out = new FileWriter(new File(nameOfFile).getAbsoluteFile(), true);
      String line;
      while ((line = reader.readLine()) != null) {
        out.write(line + "\n");
      }
      out.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  public File[] GetSaveList() {
    File allFiles[];
    File saveFiles[];
    File filesPath = new java.io.File(new File(".").getAbsolutePath());
    allFiles = filesPath.listFiles();
    int j = 0;
    for (int i = 0; i < allFiles.length; i++) {
      String tempNameOfFile = allFiles[i].getName();
      if (tempNameOfFile.endsWith(".save")) {
        j++;
      }
    }
    saveFiles = new File[j];
    j = 0;
    for (int i = 0; i < allFiles.length; i++) {
      String tempNameOfFile = allFiles[i].getName();
      if (tempNameOfFile.endsWith(".save")) {
        saveFiles[j] = allFiles[i];
        j++;
      }
    }
    return saveFiles;
  }

  public File[] GetSortedJavaList() {
    File saveFiles[] = GetSaveList();
    int countOfTowers[] = new int[saveFiles.length];
    for (int i=0; i<saveFiles.length; i++){
      countOfTowers[i] = GetAmountOfTowers(saveFiles[i].getName());
    }
    quickSort(countOfTowers, saveFiles, 0, countOfTowers.length - 1);
    return saveFiles;
  }
  
  public File[] GetSortedScalaList(){
    File saveFiles[] = GetSaveList();
    int countOfTowers[] = new int[saveFiles.length];
    for (int i=0; i<saveFiles.length; i++){
      countOfTowers[i] = GetAmountOfTowers(saveFiles[i].getName());
    }
    QuickSort qSortObject = new QuickSort();
    qSortObject.sort(countOfTowers, saveFiles);
    return saveFiles;
  }

  private int GetAmountOfTowers(String nameOfFile) {
    String mas[] = nameOfFile.split("_");
    mas[2] = mas[2].replace(".save", "");
    return (Integer.parseInt(mas[2]));
  }
  
  int Partition(int arr[], File files[], int left, int right)
  {
        int i = left, j = right;
        int tmp;
        File temp;
        int pivot = arr[(left + right) / 2];
       
        while (i <= j) {
              while (arr[i] < pivot)
                    i++;
              while (arr[j] > pivot)
                    j--;
              if (i <= j) {
                    tmp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = tmp;
                    temp = files[i];
                    files[i] = files[j];
                    files[j] = temp;
                    i++;
                    j--;
              }
        };
        return i;
  }
   
  void quickSort(int arr[], File files[], int left, int right) {
        int index = Partition(arr, files, left, right);
        if (left < index - 1)
              quickSort(arr, files, left, index - 1);
        if (index < right)
              quickSort(arr, files, index, right);
  }
}
