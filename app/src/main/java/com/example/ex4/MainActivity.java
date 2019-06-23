package com.example.ex4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private boolean isConnected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }



    public void OnClick(View view) {
        int portText;
        TcpClient tcpClient = TcpClient.getInstance();
        String ipText = ((EditText)findViewById(R.id.IpC)).getText().toString();
        portText= Integer.parseInt(((EditText)findViewById(R.id.PortC)).getText().toString());
        tcpClient.setSERVER_IP(ipText);
        tcpClient.setSERVER_PORT(portText);
        ConnectTask connectTask = new ConnectTask(tcpClient);
        connectTask.execute();
        Intent intent = new Intent(this, JoystickActivity.class);
        startActivity(intent);
        isConnected = true;
        tcpClient.sendMessage("hi connected!!!!!!!!!!!!!!!!!!!!!!!!!!!");


    }
}
