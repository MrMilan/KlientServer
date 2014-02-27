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
        byte ctrl = 0x2A;
        //BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
        try {
            Socket clientSocket = new Socket(hostName, portNumber);
            try {
                DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());

                InputStream is = clientSocket.getInputStream();
                try {
                    DataInputStream dis = new DataInputStream(is);
                    outToServer.writeByte(ctrl);
                    outToServer.writeInt(4);
                    //nacteme zpet zpravu
                    int solv = dis.readInt();

                    System.out.println(solv);
                } catch (IOException exc) {
                    System.err.println("Umrel jsem na odesilani ci prijimani ->"+exc.getMessage());
                }
            } catch (IOException exc) {
                System.err.println("Umrel jsem na Input streamu ->"+exc.getMessage());
            }

        } catch (IOException exc) {
            System.err.println("Umrel jsem na spojeni ->"+exc.getMessage());
        }
    }
}