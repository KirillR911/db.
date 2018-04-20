package clBrain.db;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by olpyh on 13.03.2018.
 */

public class SwipeEvent {
    private final static int NOTHING = -1;
    private final static int RIGHT_SWIPE = 0;
    private final static int LEFT_SWIPE = 1;
    private final static int UP_SWIPE = 2;
    private final static int DOWN_SWIPE = 3;

    private final View v;

    SwipeEvent(final View v) {
        this.v = v;
    }

    private final static int[][] rightSwipeDots =
            {
                    {0, 1, 2, 3, 4, 5, 6, 7, 8, 9},
                    {0, 1, 2, 3, 4, 5, 6, 7, 8, 9},
                    {0, 1, 2, 3, 4, 5, 6, 7, 8, 9},
                    {0, 1, 2, 3, 4, 5, 6, 7, 8, 9},
                    {0, 1, 2, 3, 4, 5, 6, 7, 8, 9},
                    {0, 1, 2, 3, 4, 5, 6, 7, 8, 9},
                    {0, 1, 2, 3, 4, 5, 6, 7, 8, 9},
                    {0, 1, 2, 3, 4, 5, 6, 7, 8, 9},
                    {0, 1, 2, 3, 4, 5, 6, 7, 8, 9},
                    {0, 1, 2, 3, 4, 5, 6, 7, 8, 9},
            };
    private final static int[][] leftSwipeDots =
            {
                    {9, 8, 7, 6, 5, 4, 3, 2, 1, 0},
                    {9, 8, 7, 6, 5, 4, 3, 2, 1, 0},
                    {9, 8, 7, 6, 5, 4, 3, 2, 1, 0},
                    {9, 8, 7, 6, 5, 4, 3, 2, 1, 0},
                    {9, 8, 7, 6, 5, 4, 3, 2, 1, 0},
                    {9, 8, 7, 6, 5, 4, 3, 2, 1, 0},
                    {9, 8, 7, 6, 5, 4, 3, 2, 1, 0},
                    {9, 8, 7, 6, 5, 4, 3, 2, 1, 0},
                    {9, 8, 7, 6, 5, 4, 3, 2, 1, 0},
                    {9, 8, 7, 6, 5, 4, 3, 2, 1, 0},
            };
    private final static int[][] upSwipeDots =
            {
                    {9, 9, 9, 9, 9, 9, 9, 9, 9, 9},
                    {8, 8, 8, 8, 8, 8, 8, 8, 8, 8},
                    {7, 7, 7, 7, 7, 7, 7, 7, 7, 7},
                    {6, 6, 6, 6, 6, 6, 6, 6, 6, 6},
                    {5, 5, 5, 5, 5, 5, 5, 5, 5, 5},
                    {4, 4, 4, 4, 4, 4, 4, 4, 4, 4},
                    {3, 3, 3, 3, 3, 3, 3, 3, 3, 3},
                    {2, 2, 2, 2, 2, 2, 2, 2, 2, 2},
                    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            };
    private final static int[][] downSwipeDots =
            {
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                    {2, 2, 2, 2, 2, 2, 2, 2, 2, 2},
                    {3, 3, 3, 3, 3, 3, 3, 3, 3, 3},
                    {4, 4, 4, 4, 4, 4, 4, 4, 4, 4},
                    {5, 5, 5, 5, 5, 5, 5, 5, 5, 5},
                    {6, 6, 6, 6, 6, 6, 6, 6, 6, 6},
                    {7, 7, 7, 7, 7, 7, 7, 7, 7, 7},
                    {8, 8, 8, 8, 8, 8, 8, 8, 8, 8},
                    {9, 9, 9, 9, 9, 9, 9, 9, 9, 9},
            };

    private int chooseSwipe(int down, int up, int right, int left) {
        if (down == left || down == right) {
            return NOTHING;
        }
        if (up == left || up == right) {
            return NOTHING;
        }
        int max = Math.max(Math.max(left, right), Math.max(down, up));
        if (max > 2) {
            if (max == up) return UP_SWIPE;
            if (max == right) return RIGHT_SWIPE;
            if (max == down) return DOWN_SWIPE;
            if (max == left) return LEFT_SWIPE;
        }
        return NOTHING;
    }


    private int getSwipeType(int[][] coordinates) {
        int leftRes = 0, rightRes = 0, upRes = 0, downRes = 0;
        int prevLeft = -1, prevRight = -1, prevUp = -1, prevDown = -1;
        for (int i = 0; i < 10; i++) {
            if (coordinates[i][0] == -1 || coordinates[i][0] == -1) break;
            if (coordinates[i][0] < 10 && coordinates[i][1] < 10) {
                int up = upSwipeDots[coordinates[i][1]][coordinates[i][0]], down = downSwipeDots[coordinates[i][1]][coordinates[i][0]],
                        right = rightSwipeDots[coordinates[i][1]][coordinates[i][0]], left = leftSwipeDots[coordinates[i][1]][coordinates[i][0]];
                if (up > prevUp) upRes++;
                else upRes -= 10;
                if (down > prevDown) downRes++;
                else downRes -= 10;
                if (left > prevLeft) leftRes++;
                else leftRes -= 10;
                if (right > prevRight) rightRes++;
                else rightRes -= 10;
                prevDown = down;
                prevLeft = left;
                prevRight = right;
                prevUp = up;
            }
        }
        return chooseSwipe(downRes, upRes, rightRes, leftRes);
    }

    private int getSwipeType(float x1, float y1, float x2, float y2) {
        double c = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
        Log.e("C:", "" + c);
        if (Math.abs((y1 - y2) / c) <= 0.5) {
            if (x1 > x2) return LEFT_SWIPE;
            else return RIGHT_SWIPE;
        }
        if (Math.abs((y1 - y2) / c) >= 0.866) {
            if (y1 > y2) return UP_SWIPE;
            else return DOWN_SWIPE;
        }
        return NOTHING;
    }

    private int getX(double dx, float x) {
        return (int) Math.round(Math.floor(x / dx));
    }

    private int getY(double dy, float y) {
        return (int) Math.round(Math.floor(y / dy));
    }

    public void setOnSwipeListener(final onSwipeEventListener listener) {
        v.setOnTouchListener(new View.OnTouchListener() {

            /*int prevX = -1, prevY = -1;
            int i = -1;
            int[][] coordinates = new int[10][2];*/
            float fx, fy, ex, ey;
            boolean isMoved = false;

            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int width = v.getWidth();
                int height = v.getHeight();
                /*double dx = ((double) width / 10);
                double dy = ((double) height / 10);*/
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        /*i = 0;
                        prevX = getX(dx, motionEvent.getX());
                        prevY = getY(dy, motionEvent.getY());
                        coordinates[i][0] = getX(dx, prevX);
                        coordinates[i][1] = getY(dy, prevY);*/
                        fx = motionEvent.getX();
                        fy = motionEvent.getY();
                        isMoved = false;
                        break;
                    }
                    case MotionEvent.ACTION_MOVE: {
                        if ((Math.abs(fx - motionEvent.getX()) >= width / 4 || Math.abs(fy - motionEvent.getY()) >= height / 4) && !isMoved) {
                            ex = motionEvent.getX();
                            ey = motionEvent.getY();
                            switch (getSwipeType(fx, fy, ex, ey)) {
                                case DOWN_SWIPE: {
                                    listener.onDownSwipe();
                                    break;
                                }
                                case UP_SWIPE: {
                                    listener.onUpSwipe();
                                    break;
                                }
                                case LEFT_SWIPE: {
                                    listener.onLeftSwipe();
                                    break;
                                }
                                case RIGHT_SWIPE: {
                                    listener.onRightSwipe();
                                    break;
                                }
                                case NOTHING: {
                                }
                            }
                            isMoved = true;
                        }
                        /*if (i < 9 && (prevY != getY(dy, motionEvent.getY()) || prevX != getX(dx, motionEvent.getX()))){
                            i++;
                            coordinates[i][0] = getX(dx, motionEvent.getX());
                            coordinates[i][1] = getY(dy, motionEvent.getY());
                            prevX = coordinates[i][0];
                            prevY = coordinates[i][1];
                        }*/
                        break;
                    }
                    case MotionEvent.ACTION_CANCEL:
                    case MotionEvent.ACTION_UP: {
                        /*i++;
                        if (i < 10) {
                            coordinates[i][0] = -1;
                            coordinates[i][1] = -1;
                        }
                        Log.i("COORD", "" + fx + " " + fy + " " + ex + " " + ey);

                        switch (getSwipeType(coordinates)){
                            case DOWN_SWIPE:{
                                listener.onDownSwipe();
                                break;
                            }
                            case UP_SWIPE:{
                                listener.onUpSwipe();
                                break;
                            }
                            case LEFT_SWIPE:{
                                listener.onLeftSwipe();
                                break;
                            }
                            case RIGHT_SWIPE:{
                                listener.onRightSwipe();
                                break;
                            }
                            case NOTHING:{}
                        }*/
                        break;
                    }
                }
                return true;
            }
        });
    }

    public interface onSwipeEventListener {
        void onLeftSwipe();

        void onRightSwipe();

        void onDownSwipe();

        void onUpSwipe();
    }
}
