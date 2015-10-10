import java.io.*;
import java.net.ServerSocket;
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
    private BufferedReader input;
    private DataOutputStream output;

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

                readed = input.readLine();

            } catch (IOException ie) {

            }
        }
        return readed;
    }

    public void write(String string) {
        if (isConnected()) {
            try {

                output.writeBytes(string + '\n');

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isConnected()
    {
        if (!this.connected)
        {
            try {
                this.socket = new Socket(this.address ,this.port);

                // open input and output
                input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                output = new DataOutputStream(socket.getOutputStream());

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
