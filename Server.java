package application;

import java.io.*;
import java.net.*;

/**
 * Класс, описывающий поведение сервера в сетевой игре
 * 
 * @author pixxx
 */
public class Server {

  static BufferedReader in;
  static ServerSocket servers;
  static PrintWriter out;
  static Socket fromclient;

  /**
   * Метод включает сервер и ожидает подключения клиента
   */
  public Server() {
    System.out.println("Welcome to Server side");
    try {
      servers = new ServerSocket(3333);
    } catch (IOException e) {
      System.out.println("Couldn't listen to port 3333");
      System.exit(-1);
    }
    try {
      System.out.print("Waiting for a client...");
      fromclient = servers.accept();
      System.out.println("Client connected");
    } catch (IOException e) {
      System.out.println("Can't accept");
      System.exit(-1);
    }
    try {
      out = new PrintWriter(fromclient.getOutputStream(), true);
    } catch (IOException e1) {
      e1.printStackTrace();
    }
  }

  /**
   * Метод посылает координаты вышки на клиент
   */
  void sendCoordinates(int x, int y) {
    String output;

    output = Integer.toString(x) + " " + Integer.toString(y);
    out.println(output);
  }

  /**
   * Метод посылает пустую строку на клиент
   */
  void sendEmptyString() {
    String output = "";
    out.println(output);
  }
}
