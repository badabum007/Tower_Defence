package application;

import java.io.*;
import java.net.*;

/**
 * �����, ����������� ��������� ������� � ������� ����
 * 
 * @author pixxx
 */
public class Client {

  static Socket fromServer;
  static BufferedReader in;

  /**
   * ����� ���������� ������ � �������
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
   * ����� ��������� ������ � �������
   */
  void recieve() {
    String body = null;
    String[] arrayBody = new String[3];
    int x = -1, y = -1;
    try {
      body = in.readLine();
      if (body.isEmpty())
        return;
      System.out.println(body);
    } catch (IOException e) {
      e.printStackTrace();
    }
    if (!body.isEmpty()) {
      arrayBody = body.split(" ");
      x = Integer.parseInt(arrayBody[0]);
      y = Integer.parseInt(arrayBody[1]);
      Main.gameRoot.towers.add(new Tower(x, y, 150));
    }
  }
}
