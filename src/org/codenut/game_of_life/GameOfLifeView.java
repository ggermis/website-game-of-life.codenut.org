package org.codenut.game_of_life;

import android.content.Context;
import android.graphics.*;
import android.os.Handler;
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
    private final int CELL_SIZE = 10;
    private final long SLEEP = 50;

    private Paint gridPainter;
    private Paint cellPainter;
    private Paint dirtyLiveCellPainter;
    private Paint dirtyDeadCellPainter;

    private World world;
    private GameThread thread;


    private class GameThread extends Thread {
        private GameOfLifeView view;
        private boolean shouldRun = true;
        private Handler handler;

        public GameThread(GameOfLifeView view) {
            this.view = view;
            this.handler = new Handler();
        }

        public void setRunning(final boolean shouldRun) {
            this.shouldRun = shouldRun;
        }

        public boolean isRunning() {
            return shouldRun;
        }

        @Override
        public void run() {
            while (shouldRun) {
                try {
                    world.applyRules();
                    drawSurface(getHolder());
                    boolean worldChanged = world.transition();
                    if (!worldChanged) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getContext(), "Done", Toast.LENGTH_SHORT).show();
                            }
                        });
                        setRunning(false);
                    }
                    sleep(SLEEP);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public GameOfLifeView(Context context, AttributeSet attributes) {
        super(context, attributes);
        setOnTouchListener(this);
        getHolder().addCallback(this);
        gridPainter = new Paint();
        gridPainter.setColor(Color.argb(150, 100, 100, 100));
        cellPainter = new Paint();
        cellPainter.setAntiAlias(true);
        cellPainter.setColor(Color.argb(150, 100, 200, 200));
        dirtyLiveCellPainter = new Paint();
        dirtyLiveCellPainter.setAntiAlias(true);
        dirtyLiveCellPainter.setColor(Color.BLACK);
        dirtyDeadCellPainter = new Paint();
        dirtyDeadCellPainter.setAntiAlias(true);
        dirtyDeadCellPainter.setColor(Color.GREEN);
        dirtyDeadCellPainter.setAlpha(70);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // idea can't render the view otherwise
        if (world == null) {
            createAppropriatelySizedWorld(getMeasuredWidth(), getMeasuredHeight());
        }

        int width = world.getWidth() * CELL_SIZE;
        int height = world.getHeight() * CELL_SIZE;

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
            canvas.drawRect(left, top, right, bottom, cellPainter);

        }

        // draw dirty cells
        for (Cell cell : world.getDirtyCells()) {
            int left = cell.getPosition().getX() * CELL_SIZE + PADDING;
            int top = cell.getPosition().getY() * CELL_SIZE + PADDING;
            int right = left + CELL_SIZE - 2 * PADDING;
            int bottom = top + CELL_SIZE - 2 * PADDING;
            if (cell.isMarkedDead()) {
                canvas.drawLine(left, top, right, bottom, dirtyLiveCellPainter);
                canvas.drawLine(right, top, left, bottom, dirtyLiveCellPainter);
            } else {
                canvas.drawRect(left, top, right, bottom, dirtyDeadCellPainter);
            }
        }

    }

    private void createAppropriatelySizedWorld(int measuredWidth, int measuredHeight) {
        world = new World(measuredWidth / CELL_SIZE, measuredHeight / CELL_SIZE);
        new Glider().draw(world, 13, 16);
        new Blinker().draw(world, 13, 12);
        new Block().draw(world, 14, 10);
        new BeeHive().draw(world, 17, 15);
        world.tick();
    }


    protected void drawSurface(SurfaceHolder holder) {
        Canvas canvas = null;
        try {
            canvas = holder.lockCanvas();
            synchronized (holder) {
                onDraw(canvas);
            }
        } finally {
            if (canvas != null) {
                holder.unlockCanvasAndPost(canvas);
            }
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        createAppropriatelySizedWorld(getMeasuredWidth(), getMeasuredHeight());
        drawSurface(holder);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        stop();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            Toast.makeText(getContext(), "Stop touching me! " + event.getX() + ":" + event.getY(), Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    public void stop() {
        boolean retry = true;
        if (thread.isRunning()) {
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
    }

    public void start() {
        thread = new GameThread(this);
        thread.start();
    }
}

