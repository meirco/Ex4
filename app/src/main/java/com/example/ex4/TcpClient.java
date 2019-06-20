package com.example.ex4;

import android.util.Log;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class TcpClient {
    public static final String TAG = TcpClient.class.getSimpleName();
    private String SERVER_IP;
    private int SERVER_PORT;

    public void setSERVER_IP(String SERVER_IP) {
        this.SERVER_IP = SERVER_IP;
    }

    public void setSERVER_PORT(int SERVER_PORT) {
        this.SERVER_PORT = SERVER_PORT;
    }

    private static TcpClient singleInstance = null;

    public static TcpClient getInstance() {
        if (singleInstance == null) {
            singleInstance = new TcpClient();
        }
        return singleInstance;
    }

    private String mServerMessage;
    private OnMessageReceived mMessageListener = null;
    private boolean mRun = false;
    private PrintWriter mBufferOut;
    private BufferedReader mBufferIn;

    /**
     * Constructor of the class. OnMessagedReceived listens for the messages received from server
     */
    private TcpClient() {

    }

    /**
     * Sends the message entered by client to the server
     *
     * @param message text entered by client
     */
    public void sendMessage(final String message) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (mBufferOut != null) {
                    Log.d(TAG, "Sending: " + message);
                    mBufferOut.println(message);
                    mBufferOut.flush();
                }
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }

    /**
     * Close the connection and release the members
     */
    public void stopClient() {
        mRun = false;

        if (mBufferOut != null) {
            mBufferOut.flush();
            mBufferOut.close();
        }

        mMessageListener = null;
        mBufferIn = null;
        mBufferOut = null;
        mServerMessage = null;
    }

    public void run() {

        mRun = true;

        try {
            InetAddress serverAddr = InetAddress.getByName(SERVER_IP);
            Log.d("TCP Client", "C: Connecting...");
            Socket socket = new Socket(serverAddr, SERVER_PORT);

            try {
                mBufferOut = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                mBufferIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            } catch (Exception e) {
                Log.e("TCP", "S: Error", e);
            }

        } catch (Exception e) {
            Log.e("TCP", "C: Error", e);
        }

    }


    public interface OnMessageReceived {
        public void messageReceived(String message);
    }

}
