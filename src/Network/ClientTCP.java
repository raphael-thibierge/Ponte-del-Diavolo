package Network;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by raphael on 10/10/2015.
 */
public class ClientTCP
{
    private String name;
    private String address;
    private int port;

    private Socket socket;
    private InputStream input;
    private OutputStream output;

    boolean connected;


    public ClientTCP(String name, int port, String address)
    {
        this.name = name;
        this.port = port;
        this.address = address;
        this.connected = false;
    }

    public String read(){
        String readed = "";

        if (isConnected()) {
            try {

                System.out.println("TCP Client is reading");
//                int receive = input.read();
//                while (receive >= 0 && receive <= 255){
//                    readed += (char)receive;
//                    receive =input.read();


                byte[] b = new byte[1000]; //définition d'un tableau pour lire les données arrivées
                int bitsRecus = input.read(b); //il n'est pas sûr que l'on recoive 1000 bits
                if(bitsRecus>0) {
                    readed = new String(b,0, bitsRecus);
                }
                System.out.println("TCP Client : " + readed);
                System.out.println("TCP Client end reading");

            } catch (IOException ie) {

            }
        }
        return readed;
    }

    public void write(String string) {
        if (isConnected()) {
            try {
                System.out.println("Try write : " + string);
                output = socket.getOutputStream();
                int toSend;
//                for (char c : string.toCharArray()){
//                    toSend = (int)c;
//                    output.write(toSend);
//                }

                output.write(string.getBytes());
                System.out.println("End write" );
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else System.out.println("euuuh..");
    }

    public boolean isConnected()
    {
        if (!this.connected)
        {
            try {
                this.socket = new Socket(this.address ,this.port);

                // open input and output
                input = socket.getInputStream();
                output = socket.getOutputStream();

                this.connected = true;

            } catch (IOException e) {
                e.printStackTrace();
                this.connected = false;
            }
        }
        return this.connected;
    }

    public void disconnect()
    {
        if (this.connected){
            try {
                socket.close();
                this.connected = false;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
