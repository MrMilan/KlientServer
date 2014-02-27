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
                OutputStream sockOut = null;
                try {
                    InputStream sockIn;
                    sockIn = clientSocket.getInputStream();
                    DataInputStream dis = new DataInputStream(sockIn);
                    byte cinnost = dis.readByte();
                    switch (cinnost) {
                        case 0x2A:
                            dis.available();
                            dos.writeInt(dis.readInt() / 2);
                            System.out.println("Bylo provedeno deleni dvema");
                            break;
                        case 0x2B:
                            dis.available();
                            dos.writeInt(dis.readInt() / 2);
                            break;
                        case 0x2C:
                            dis.available();
                            dos.writeInt(dis.readInt() / 2);
                            break;
                        default: ;
                            break;
                    }
                    sockOut = clientSocket.getOutputStream();
                } catch (IOException exc) {
                    System.err.println(exc.getMessage());
                }
                sockOut.close();
            } catch (IOException exc) {
                System.err.println(exc.getMessage());
            }
        }
    }
}
