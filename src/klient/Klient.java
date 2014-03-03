/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package klient;

/**
 *
 * @author polacmi1
 */
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class Klient {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {

        int portNumber = 20666;
        String hostName = "localhost";
        String inputStigos = "AAA2 1bbbb";

        //BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
        try {
            Socket clientSocket = new Socket(hostName, portNumber);
            try {
                DataOutputStream dos = new DataOutputStream(clientSocket.getOutputStream());
                DataInputStream dis = new DataInputStream(clientSocket.getInputStream());
                try {

                    SentString(dos, inputStigos, (byte) 0x1A);
                    String solv = dis.readUTF();
                    System.out.println("Vysledek prikazu " + solv);
                    System.err.println("Zaviram vstupy vystupy");
                    dis.close();
                    dos.close();
                    SentString(dos, inputStigos, (byte) 0x1B);
                    String solv = dis.readUTF();
                    System.out.println("Vysledek prikazu " + solv);
                    System.err.println("Zaviram vstupy vystupy");
                    dis.close();
                    dos.close();
                    SentString(dos, inputStigos, (byte) 0x1C);
                    String solv = dis.readUTF();
                    System.out.println("Vysledek prikazu " + solv);
                    System.err.println("Zaviram vstupy vystupy");
                    dis.close();
                    dos.close();
                    SentString(dos, inputStigos, (byte) 0x1D);
                    String solv = dis.readUTF();
                    System.out.println("Vysledek prikazu " + solv);
                    System.err.println("Zaviram vstupy vystupy");
                    dis.close();
                    dos.close();
                    //nacteme zpet zpravu
                    String solv = dis.readUTF();
                    System.out.println("Vysledek prikazu " + solv);
                    System.err.println("Zaviram vstupy vystupy");
                    dis.close();
                    dos.close();

                } catch (IOException exc) {
                    System.err.println("Umrel jsem na odesilani ci prijimani ->" + exc.getMessage());
                }
            } catch (IOException exc) {
                System.err.println("Umrel jsem na Input streamu ->" + exc.getMessage());
            }
            System.err.println("Zaviram clienta");
            clientSocket.close();
        } catch (IOException exc) {
            System.err.println("Umrel jsem na spojeni ->" + exc.getMessage());
        }
    }

    public static void SentString(DataOutputStream dos, String words, byte control) {
        try {

            switch (control) {
                case 0x1A:
                    dos.writeByte(control);

                    System.out.println("Byla odelsno k akci Reverze");
                    break;
                case 0x1B:
                    dos.writeByte(control);
                    System.out.println("Byla odelsno k akci podretezce substring(0,6)");
                    break;
                case 0x1C:
                    dos.writeByte(control);
                    System.out.println("Byla odelsno k akci retezce v lowercase");
                    break;
                case 0x1D:
                    dos.writeByte(control);
                    System.out.println("Byla odelsno k akci retezce v uppercase");
                    break;
            }
            dos.writeUTF(words);
        } catch (IOException exc) {
            System.err.println("Umrel jsem na odesilani" + exc.getMessage());
        }
    }

   public static void SentIntNumber(DataOutputStream dos, int number, byte control) {
        try {

            switch (control) {
                case 0x2A:
                    dos.writeByte(control);
                    System.out.println("Byla odelsno k akci deleni 2");
                    break;
                case 0x2B:
                    dos.writeByte(control);
                    System.out.println("Byla odelsno k akci odecteni 5");
                    break;
            }
            dos.writeInt(number);
        } catch (IOException exc) {
            System.err.println("Umrel jsem na odesilani" + exc.getMessage());
        }

    }

    public static void SentDoubleNumber(DataOutputStream dos, double number, byte control) {
        try {

            switch (control) {

                case 0x3A:
                    dos.writeByte(control);
                    System.out.println("Byla odelsno k akci pricteni e ");
                    break;
                case 0x3B:
                    dos.writeByte(control);
                    System.out.println("Byla odelsno k akci nasobeni pi");
                    break;
            }
            dos.writeDouble(number);
        } catch (IOException exc) {
            System.err.println("Umrel jsem na odesilani" + exc.getMessage());
        }
    }
     public static void SentRecieveString(Socket clientSocket, String words, byte control) throws IOException {
        try {
            try (DataOutputStream dos = new DataOutputStream(clientSocket.getOutputStream()); DataInputStream dis = new DataInputStream(clientSocket.getInputStream())) {
                SentString(dos, words, control);
                String solv = dis.readUTF();
                System.out.println("Vysledek prikazu " + solv);
                System.err.println("Zaviram vstupy vystupy");
            }
        }
    catch (IOException exc) {
            System.err.println("Umrel jsem na odesilani ci prijimani" + exc.getMessage());
    }
}
      public static void SentRecieveInt(Socket clientSocket, int number, byte control) throws IOException {
        try {
            try (DataOutputStream dos = new DataOutputStream(clientSocket.getOutputStream()); DataInputStream dis = new DataInputStream(clientSocket.getInputStream())) {
                SentIntNumber(dos, number, control);
                int solv = dis.readInt();
                System.out.println("Vysledek prikazu " + solv);
                System.err.println("Zaviram vstupy vystupy");
            }
        }
    catch (IOException exc) {
            System.err.println("Umrel jsem na odesilani ci prijimani" + exc.getMessage());
    }
}
       public static void SentRecieveDouble(Socket clientSocket, int number, byte control) throws IOException {
        try {
            try (DataOutputStream dos = new DataOutputStream(clientSocket.getOutputStream()); DataInputStream dis = new DataInputStream(clientSocket.getInputStream())) {
                SentDoubleNumber(dos, number, control);
                double solv = dis.readDouble();
                System.out.println("Vysledek prikazu " + solv);
                System.err.println("Zaviram vstupy vystupy");
            }
        }
    catch (IOException exc) {
            System.err.println("Umrel jsem na odesilani ci prijimani" + exc.getMessage());
    }
}
}
