package com.example.ex4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // Called when user taps the Send button
//    public void sendMessage(View view) {
//        EditText editText =
//                (EditText)findViewById(R.id.editText);
//        Intent intent = new Intent(this,
//                JoystickActivity.class);
//        startActivity(intent);
//
//    }

    public void OnClick(View view) {
        TcpClient tcpClient = TcpClient.getInstance();
        String ipText = ((EditText)findViewById(R.id.IpC)).getText().toString();
        int portText;
        portText= Integer.parseInt(((EditText)findViewById(R.id.PortC)).getText().toString());
        tcpClient.setSERVER_IP(ipText);
        tcpClient.setSERVER_PORT(portText);
        ConnectTask connectTask = new ConnectTask(tcpClient);
        connectTask.execute();
        Intent intent = new Intent(this, JoystickActivity.class);
        startActivity(intent);
        tcpClient.sendMessage("ggggggggggggggggggggggggggggggggggg");
        tcpClient.sendMessage("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");


    }
}
