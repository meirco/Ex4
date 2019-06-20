package com.example.ex4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import static com.example.ex4.JoystickFunctionality.STICK_DOWN;
import static com.example.ex4.JoystickFunctionality.STICK_UP;

public class JoystickActivity extends AppCompatActivity {

    RelativeLayout layout_joystick;
    JoystickFunctionality js;

    // 'onDestroy' implementation
    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("destroy");
        TcpClient.getInstance().stopClient();
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joystick);

        layout_joystick = (RelativeLayout) findViewById(R.id.layout_joystick);

        js = new JoystickFunctionality(getApplicationContext(), layout_joystick, R.drawable.image_button);
        js.setStickSize(150, 150);
        js.setLayoutSize(500, 500);
        js.setLayoutAlpha(150);
        js.setStickAlpha(100);
        js.setOffset(110);
        js.setMinimumDistance(50);
        js.playJoystick();
        layout_joystick.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View arg0, MotionEvent arg1) {
                js.drawStick(arg1);
                if (arg1.getAction() == MotionEvent.ACTION_DOWN
                        || arg1.getAction() == MotionEvent.ACTION_MOVE) {
//
                    int direction = js.get8Direction();
                    if (direction == STICK_UP) {
                    } else if (direction == JoystickFunctionality.STICK_UPRIGHT) {
                    } else if (direction == JoystickFunctionality.STICK_RIGHT) {
                    } else if (direction == JoystickFunctionality.STICK_DOWNRIGHT) {
                    } else if (direction == STICK_DOWN) {
                    } else if (direction == JoystickFunctionality.STICK_DOWNLEFT) {
                    } else if (direction == JoystickFunctionality.STICK_LEFT) {
                    } else if (direction == JoystickFunctionality.STICK_UPLEFT) {
                    } else if (direction == JoystickFunctionality.STICK_NONE) {
                    }
                } else if (arg1.getAction() == MotionEvent.ACTION_UP) {
//
                }
                int direction1 = js.get4Direction();
                sendData(js.getX(), js.getY(), direction1);
                return true;
            }
        });
    }

    public void sendData(float x, float y, int direction) {

        if ((direction == 1) || (direction == 5)) {

            String massage = "set controls/flight/elevator" + " " + String.valueOf(y);
            TcpClient.getInstance().sendMessage(massage);
        } else {
            String massage = "set controls/flight/aileron" + " " + String.valueOf(x);
            TcpClient.getInstance().sendMessage(massage);
        }
    }
}
