/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

/**
 *
 * @author polacmi1
 */
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.ServerSocket;

public class Server {

    public static void main(String[] args) throws Exception {
// nastavovani serveru
        int port = 20666;
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
            System.err.println("Server nabehl na portu " + port);
        } catch (IOException exc) {
            System.err.println(exc.getMessage());
        }

        //nacitani dat
        while (true) {
            try {
                Socket clientSocket = serverSocket.accept();
                System.err.println("Spojeni bylo akceptovano s ");

                DataOutputStream dos = new DataOutputStream(clientSocket.getOutputStream());
                try {
                    InputStream sockIn;
                    sockIn = clientSocket.getInputStream();
                    DataInputStream dis = new DataInputStream(sockIn);
                    byte cinnost = dis.readByte();
                    if (cinnost == 0x1A || cinnost == 0x1B || cinnost == 0x1C || cinnost == 0x1D) {
                        dis.available();
                        String clovo = dis.readUTF();
                        String outpus = null;
                        switch (cinnost) {
                            case 0x1A:
                                for (String part : clovo.split(" ")) {
                                    outpus += (new StringBuilder(part).reverse().toString() + " ").toString();
                                }

                                System.out.println("Bylo provedena akce Reverze");
                                break;
                            case 0x1B:
                                outpus = clovo.substring(0, 5);
                                System.out.println("Bylo provedeno vraceni podretezce substring(0,6)");
                                break;
                            case 0x1C:
                                outpus = clovo.toLowerCase();
                                System.out.println("Bylo provedeno vraceni retezce v lowercase");
                                break;
                            case 0x1D:
                                outpus = clovo.toUpperCase();
                                System.out.println("Bylo provedeno vraceni retezce v uppercase");
                                break;
                        }
                        dos.writeUTF(outpus);

                    } else {
                        if (cinnost == 0x2A || cinnost == 0x2B) {
                            int outpus = 0;
                            dis.available();
                            switch (cinnost) {
                                case 0x2A:
                                    outpus = dis.readInt() / 2;
                                    System.out.println("Bylo provedeno deleni 2");
                                    break;
                                case 0x2B:
                                    outpus = dis.readInt() - 5;
                                    System.out.println("Bylo provedeno odecteni 5");
                                    break;
                            }
                            dos.writeInt(outpus);
                        } else {
                            if (cinnost == 0x3A || cinnost == 0x3B) {
                                double outpus = 0;
                                dis.available();
                                switch (cinnost) {

                                    case 0x3A:
                                        outpus = dis.readDouble() + Math.E;
                                        System.out.println("Bylo provedeno pricteni e");
                                        break;
                                    case 0x3B:
                                        outpus = dis.readDouble() + Math.PI;
                                        System.out.println("Bylo provedeno nasobeni pi");
                                        break;
                                }
                                dos.writeDouble(outpus);
                            } else {
                                System.out.println("Bylo provedeno nasobeni pi");
                                dos.writeUTF("Bad input");
                                break;
                            }
                        }
                        System.err.println("Zaviram vstupy vystupy");
                        dos.close();
                        dis.close();
                    }
                } catch (IOException exc) {
                    System.err.println(exc.getMessage());
                }
                
clientSocket.close();
System.err.println("Zavren client");
            } catch (IOException exc) {
                System.err.println(exc.getMessage());
            }
        }
    }
}
