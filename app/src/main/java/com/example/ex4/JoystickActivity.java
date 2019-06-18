package com.example.ex4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import static com.example.ex4.JoystickFunc.STICK_DOWN;
import static com.example.ex4.JoystickFunc.STICK_UP;

public class JoystickActivity extends AppCompatActivity {

    RelativeLayout layout_joystick;
    ImageView image_joystick, image_border;
    TextView textView1, textView2, textView3, textView4, textView5;
    JoystickFunc js;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joystick);

//        textView1 = (TextView) findViewById(R.id.textView1);
//        textView2 = (TextView) findViewById(R.id.textView2);
//        textView3 = (TextView) findViewById(R.id.textView3);
//        textView4 = (TextView) findViewById(R.id.textView4);
//        textView5 = (TextView) findViewById(R.id.textView5);

        layout_joystick = (RelativeLayout) findViewById(R.id.layout_joystick);

        js = new JoystickFunc(getApplicationContext(), layout_joystick, R.drawable.image_button);
        js.setStickSize(150, 150);
        js.setLayoutSize(500, 500);
        js.setLayoutAlpha(150);
        js.setStickAlpha(100);
        js.setOffset(110);
        js.setMinimumDistance(50);
        js.startJoistick();
        layout_joystick.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View arg0, MotionEvent arg1) {
                js.drawStick(arg1);
                if (arg1.getAction() == MotionEvent.ACTION_DOWN
                        || arg1.getAction() == MotionEvent.ACTION_MOVE) {
//                    textView1.setText("X : " + String.valueOf(js.getX()));
//                    textView2.setText("Y : " + String.valueOf(js.getY()));
//                    textView3.setText("Angle : " + String.valueOf(js.getAngle()));
//                    textView4.setText("Distance : " + String.valueOf(js.getDistance()));

                    int direction = js.get8Direction();
                    if (direction == STICK_UP) {
                        //textView5.setText("Direction : Up");
                    } else if (direction == JoystickFunc.STICK_UPRIGHT) {
                        //textView5.setText("Direction : Up Right");
                    } else if (direction == JoystickFunc.STICK_RIGHT) {
                        //textView5.setText("Direction : Right");
                    } else if (direction == JoystickFunc.STICK_DOWNRIGHT) {
                        //textView5.setText("Direction : Down Right");
                    } else if (direction == STICK_DOWN) {
                        //textView5.setText("Direction : Down");
                    } else if (direction == JoystickFunc.STICK_DOWNLEFT) {
                        //textView5.setText("Direction : Down Left");
                    } else if (direction == JoystickFunc.STICK_LEFT) {
                        //textView5.setText("Direction : Left");
                    } else if (direction == JoystickFunc.STICK_UPLEFT) {
                        //textView5.setText("Direction : Up Left");
                    } else if (direction == JoystickFunc.STICK_NONE) {
                        //textView5.setText("Direction : Center");
                    }
                } else if (arg1.getAction() == MotionEvent.ACTION_UP) {
//                    textView1.setText("X :");
//                    textView2.setText("Y :");
//                    textView3.setText("Angle :");
//                    textView4.setText("Distance :");
//                    textView5.setText("Direction :");
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
