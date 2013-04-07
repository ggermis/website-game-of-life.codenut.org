package org.codenut.game_of_life;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;
import org.codenut.game_of_life.pattern.BeeHive;
import org.codenut.game_of_life.pattern.Blinker;
import org.codenut.game_of_life.pattern.Block;
import org.codenut.game_of_life.pattern.Glider;


public class GameOfLifeView extends SurfaceView implements SurfaceHolder.Callback, View.OnTouchListener {

    private final int PADDING = 1;
    private final int CELL_SIZE = 15;
    private final long SLEEP = 50;

    private Paint gridPainter;
    private Paint cellPainter;

    private World world;
    private GameThread thread;


    private class GameThread extends Thread {
        private GameOfLifeView view;
        private boolean shouldRun = true;

        public GameThread(GameOfLifeView view) {
            this.view = view;
        }

        public void setRunning(final boolean shouldRun) {
            this.shouldRun = shouldRun;
        }

        @Override
        public void run() {
            while (shouldRun) {
                Canvas canvas = null;
                try {
                    canvas = getHolder().lockCanvas();
                    synchronized (getHolder()) {
                        view.onDraw(canvas);
                    }
                    world.tick();
                    sleep(SLEEP);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    if (canvas != null) {
                        getHolder().unlockCanvasAndPost(canvas);
                    }
                }
            }
        }
    }

    public GameOfLifeView(Context context, AttributeSet attributes) {
        super(context, attributes);
        setOnTouchListener(this);
        getHolder().addCallback(this);
        gridPainter = new Paint();
        gridPainter.setColor(Color.DKGRAY);
        cellPainter = new Paint();
        cellPainter.setColor(Color.CYAN);
        thread = new GameThread(this);
        world = new World();
        new Glider().draw(world, 5, 3);
        new Blinker().draw(world, 23, 12);
        new Block().draw(world, 27, 29);
        new BeeHive().draw(world, 13, 25);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        if (world == null) {
            return; // nothing to draw
        }

        super.onDraw(canvas);

        int width = Math.min(world.getWidth() * CELL_SIZE, getMeasuredWidth());
        int height = Math.min(world.getHeight() * CELL_SIZE, getMeasuredHeight());

        // draw canvas
        canvas.drawColor(Color.BLACK);
        for (int y = 0; y <= height; y += CELL_SIZE) {
            canvas.drawLine(0, y, width, y, gridPainter);
        }
        for (int x = 0; x <= width; x += CELL_SIZE) {
            canvas.drawLine(x, 0, x, height, gridPainter);
        }

        // draw living cells
        for (Cell cell : world.getLivingCells()) {
            int left = cell.getPosition().getX() * CELL_SIZE + PADDING;
            int top = cell.getPosition().getY() * CELL_SIZE + PADDING;
            int right = left + CELL_SIZE - 2 * PADDING;
            int bottom = top + CELL_SIZE - 2 * PADDING;
            canvas.drawRect(new Rect(left, top, right, bottom), cellPainter);
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        thread.setRunning(false);
        while (retry) {
            try {
                thread.interrupt();
                thread.join();
                retry = false;
            } catch (InterruptedException e) {
                // retry
            }
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            Toast.makeText(getContext(), "Stop touching me!", Toast.LENGTH_LONG).show();
        }
        return true;
    }
}

