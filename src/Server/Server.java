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
import java.net.Socket;
import java.net.ServerSocket;
import java.util.Scanner;

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
        //nepodarilo se mi rozchodit ukoncovani na serveru
        //Scanner sc = new Scanner(System.in);
        //System.out.print("Pro ukonceni vlozte x ");
        
        //nacitani dat
        while (/*sc.hasNext()&&!sc.equals("x")*/true) {

            try {
                Socket clientSocket = serverSocket.accept();
                System.err.println("Spojeni bylo akceptovano s ");

                DataOutputStream dos = new DataOutputStream(clientSocket.getOutputStream());
                try {
                    InputStream sockIn;
                    sockIn = clientSocket.getInputStream();
                    DataInputStream dis = new DataInputStream(sockIn);
                    byte cinnost = dis.readByte();
                    if (cinnost == 0x66) {
                        break;
                    }
                    if (cinnost == 0x1A || cinnost == 0x1B || cinnost == 0x1C || cinnost == 0x1D) {
                        dis.available();
                        String clovo = dis.readUTF();
                        String outpus = null;
                        switch (cinnost) {
                            case 0x1A:
                                outpus = Reverzos(clovo);
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
                                        System.out.println("Bylo provedeno pricteni e ");
                                        break;
                                    case 0x3B:
                                        outpus = dis.readDouble() + Math.PI;
                                        System.out.println("Bylo provedeno nasobeni pi");
                                        break;
                                }
                                dos.writeDouble(outpus);
                            } else {
                                System.out.println("Spatny vstup");
                                dos.writeUTF("Spatny vstup");
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
            //sc.next();
        }
        System.err.println("Zavren  server");
        serverSocket.close();
    }

    public static String Reverzos(String inputString) {
        String revertString = null;
        int countSpace = 0;
        String[] split = inputString.split(" ");
        countSpace=split.length;
        int checkSum = 0;
        int rewrite=0;
        for (String part : inputString.split(" ")) {
            if(rewrite==0)
            {
                revertString = new StringBuilder(part).reverse().toString();
            }
            else
            {
            revertString += new StringBuilder(part).reverse().toString();
            }
            if (countSpace > 0 && (checkSum < countSpace - 1)) {
                revertString += " ";
                checkSum++;
            }
            rewrite++;
        }
        return revertString;

    }
}
