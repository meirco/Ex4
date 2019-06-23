package com.example.ex4;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

public class JoystickFunctionality {

    public static final int STICK_DOWNRIGHT = 4;
    public static final int STICK_DOWN = 5;
    public static final int STICK_DOWNLEFT = 6;
    public static final int STICK_LEFT = 7;
    public static final int STICK_UPLEFT = 8;
    public static final int STICK_NONE = 0;
    public static final int STICK_UP = 1;
    public static final int STICK_UPRIGHT = 2;
    public static final int STICK_RIGHT = 3;

    private int LAYOUT_ALPHA = 200;
    private int STICK_ALPHA = 200;


    private int OFFSET = 0;
    private boolean shouldMove = false;

    private int position1 = 200;
    private int position2 = -200;

    private Context mContext;
    private ViewGroup mLayout;
    private ViewGroup.LayoutParams params;
    private int stick_width, stick_height;
    private int calWidth, calHeight;

    private DrawCanvas draw;
    private Paint paint;
    private Bitmap stick;

    int min_distance = 0;
    private float distance = 0, angle = 0;
    private float position_x = 0, position_y = 0;




    private boolean touch_state = true;

    public JoystickFunctionality(Context context, ViewGroup layout, int stick_res_id) {
        mContext = context;

        stick = BitmapFactory.decodeResource(mContext.getResources(), stick_res_id);

        stick_width = stick.getWidth();
        stick_height = stick.getHeight();

        draw = new DrawCanvas(mContext);
        paint = new Paint();
        mLayout = layout;
        params = mLayout.getLayoutParams();
        calHeight = stick_height;
        calWidth = stick_width;
    }
    public void playJoystick(){
        draw.position(params.width / 2, params.height / 2);
        draw();
    }
    public void drawStick(MotionEvent argOne) {
        position_y = (int) (argOne.getY() - (params.height / 2));
        position_x = (int) (argOne.getX() - (params.width / 2));
        angle = (float) calAngle(position_x, position_y);
        distance = (float) Math.sqrt(Math.pow(position_x, 2) + Math.pow(position_y, 2));
        if ( argOne.getAction()== MotionEvent.ACTION_DOWN) {
            if (!(distance > (params.width / 2) - OFFSET)) {
                draw.position(argOne.getX(), argOne.getY());
                draw();
                touch_state = true;
            }
        } else if ((argOne.getAction() == MotionEvent.ACTION_MOVE) && touch_state) {
            if (!(distance > (params.width / 2) - OFFSET)) {
                draw.position(argOne.getX(), argOne.getY());
                draw();
            } else if (distance > (params.width / 2) - OFFSET) {
                float x = (float) (Math.cos(Math.toRadians(calAngle(position_x, position_y)))
                        * ((params.width / 2) - OFFSET));
                float y = (float) (Math.sin(Math.toRadians(calAngle(position_x, position_y)))
                        * ((params.height / 2) - OFFSET));
                y += (params.height / 2);
                x += (params.width / 2);
                draw.position(x, y);
                draw();
            } else {
                mLayout.removeView(draw);
            }
        } else if (argOne.getAction() == MotionEvent.ACTION_UP) {
            draw.position(params.width / 2, params.height / 2);
            draw();
        }
    }

    public float normal(float x){
        float i = x + 200;
        float normal = i/400;
        float midNormal = normal * 2;
        normal= midNormal -1;
        return normal;
    }


    public float getY() {
        if (distance > min_distance && touch_state) {
            if (position_y < position2) {
                return 1;
            }

            if (position_y > position1) {
                return -1;
            }
            return -(normal(position_y));
        }
        return 0;
    }

    public float getX() {
        if (distance > min_distance && touch_state) {
            if (position_x < position2) {
                return -1;
            }
            if (position_x > position1 ) {
                return 1;
            }

            return normal(position_x);
        }
        return 0;
    }






    public int get8Direction() {
        shouldMove = true;
        if (distance > min_distance && touch_state) {
            if (angle >= 247.5 && angle < 292.5) {
                return STICK_UP;
            } else if (angle >= 202.5 && angle < 247.5) {
                return STICK_UPLEFT;
            } else if (angle >= 157.5 && angle < 202.5) {
                return STICK_LEFT;
            } else if (angle >= 112.5 && angle < 157.5) {
                return STICK_DOWNLEFT;
            } else if (angle >= 67.5 && angle < 112.5) {
                return STICK_DOWN;
            } else if (angle >= 22.5 && angle < 67.5) {
                return STICK_DOWNRIGHT;
            } else if (angle >= 337.5 || angle < 22.5) {
                return STICK_RIGHT;
            } else if (angle >= 292.5 && angle < 337.5) {
                return STICK_UPRIGHT;
            }
        } else if (distance <= min_distance && touch_state) {
            return STICK_NONE;
        }
        return 0;
    }

    public int getAllDirections() {
        if (distance > min_distance && touch_state) {
            if (angle >= 225 && angle < 315) {
                return STICK_UP;
            } else if (angle >= 315 || angle < 45) {
                return STICK_RIGHT;
            } else if (angle >= 45 && angle < 135) {
                return STICK_DOWN;
            } else if (angle >= 135 && angle < 225) {
                return STICK_LEFT;
            }
        } else if (distance <= min_distance && touch_state) {
            return STICK_NONE;
        }
        return 0;
    }

    private double calAngle(float x, float y) {
        if (x >= 0 && y >= 0)
            return Math.toDegrees(Math.atan(y / x));
        else if (x < 0 && y >= 0)
            return Math.toDegrees(Math.atan(y / x)) + 180;
        else if (x < 0 && y < 0)
            return Math.toDegrees(Math.atan(y / x)) + 180;
        else if (x >= 0 && y < 0)
            return Math.toDegrees(Math.atan(y / x)) + 360;
        return 0;
    }

    public void setMinDistance(int minDistance) {
        min_distance = minDistance;
    }

    public void setOffset(int offset) {
        OFFSET = offset;
    }

    public void setStickAlpha(int alpha) {
        STICK_ALPHA = alpha;
        paint.setAlpha(alpha);
    }

    public void setStickSize(int width, int height) {
        stick = Bitmap.createScaledBitmap(stick, width, height, false);
        stick_width = stick.getWidth();
        stick_height = stick.getHeight();
    }

    public void setLayoutSize(int width, int height) {
        params.width = width;
        params.height = height;
    }

    public void setLayoutAlpha(int LAYOUT_ALPHA) {
        this.LAYOUT_ALPHA = LAYOUT_ALPHA;
    }



    private void draw() {
        try {
            mLayout.removeView(draw);
        } catch (Exception e) {
        }
        mLayout.addView(draw);
    }

    private class DrawCanvas extends View {
        float x, y;

        private DrawCanvas(Context mContext) {
            super(mContext);
        }

        public void onDraw(Canvas canvas) {
            canvas.drawBitmap(stick, x, y, paint);
        }

        private void position(float x, float y) {
            this.x = x - (stick_width / 2);
            this.y = y - (stick_height / 2);

        }
    }
}
