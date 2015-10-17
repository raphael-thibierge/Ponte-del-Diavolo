package Network;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by raphael on 10/10/2015.
 */
public class ClientTCP
{
    private InetAddress address;
    private int port;

    private Socket socket = null;
    private InputStream input = null;
    private OutputStream output = null;

    boolean connected = false;


    public ClientTCP(String address, int port) throws IOException
    {
        this.port = port;

        // get InetAdress, throw execption if can't connect !
        this.address = InetAddress.getByName(address);

        // try connect
        this.connect();

    }

    public String read(){
        String readed = "";

        if (connected) {
            try {

                System.out.println("TCP Client is reading");
//                int receive = input.read();
//                while (receive >= 0 && receive <= 255){
//                    readed += (char)receive;
//                    receive =input.read();

//                byte[] b = new byte[10]; //définition d'un tableau pour lire les données arrivées
//                int bitsRecus = input.read(b); //il n'est pas sûr que l'on recoive 1000 bits
//                if(bitsRecus>0) {
//                    readed = new String(b,0, bitsRecus);
//                }
                readed += (char) input.read();
                while (input.available() > 0){
                    //System.out.println("Restant : " + input.available());
                    readed += (char) input.read();
                }

                System.out.println("TCP Client : " + readed);
                System.out.println("TCP Client end reading");

            } catch (IOException ie) {
                ie.printStackTrace();
            }
        }
        return readed;
    }

    public void write(String string) {
        if (connected) {
            try {
                System.out.println("Try write : " + string);
                output = socket.getOutputStream();
                int toSend;
                for (char c : string.toCharArray()){
                    toSend = (int)c;
                    output.write(toSend);
                }

                //output.write(string.getBytes());
                System.out.println("End write" );
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else System.out.println("euuuh..");
    }

    public boolean isConnected()
    {
        if (!this.connected) {

            try {
                connect();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        return this.connected;
    }

    public void connect() throws IOException
    {
        if (!this.connected) {
            // connect socket
            this.socket = new Socket(this.address, this.port);

            // init input and output stream
            this.input = socket.getInputStream();
            this.output = socket.getOutputStream();

            this.connected = true;
        }
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
