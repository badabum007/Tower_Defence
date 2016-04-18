package application;

import java.io.*;
import java.net.*;

/**
 * Класс, описывающий поведение клиента в сетевой игре
 * 
 * @author pixxx
 */
public class Client {

  static Socket fromServer;
  static BufferedReader in;

  /**
   * Метод подключает клиент к серверу
   *
   */
  public Client() {
    fromServer = null;
    try {
      fromServer = new Socket("localhost", 3333);
    } catch (UnknownHostException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    try {
      in = new BufferedReader(new InputStreamReader(fromServer.getInputStream()));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Метод принимает данные с сервера
   */
  void recieve() {
    String Body = null;
    String ArrayBody[] = new String[3];
    int x = -1, y = -1;
    try {
      Body = in.readLine();
      if (Body.isEmpty())
        return;
      System.out.println(Body);
    } catch (IOException e) {
      e.printStackTrace();
    }
    if (!Body.isEmpty()) {
      ArrayBody = Body.split(" ");
      x = Integer.parseInt(ArrayBody[0]);
      y = Integer.parseInt(ArrayBody[1]);
      Main.gameRoot.Towers.add(new Tower(x, y, 150));
    }
  }
}
