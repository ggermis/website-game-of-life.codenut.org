package org.codenut.game_of_life;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import org.codenut.game_of_life.pattern.BeeHive;
import org.codenut.game_of_life.pattern.Blinker;
import org.codenut.game_of_life.pattern.Block;
import org.codenut.game_of_life.pattern.Glider;


public class GameOfLifeView extends SurfaceView implements Runnable, View.OnTouchListener {

    private final int PADDING = 1;
    private final int CELL_SIZE = 15;
    private final long SLEEP = 50;


    private Paint gridPainter;
    private Paint cellPainter;
    private Paint dirtyLiveCellPainter;
    private Paint dirtyDeadCellPainter;

    private World world;
    private Thread thread;

    private boolean shouldRun;
    private final SurfaceHolder holder;


    public GameOfLifeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        gridPainter = new Paint();
        gridPainter.setColor(Color.argb(150, 100, 100, 100));
        cellPainter = new Paint();
        cellPainter.setAntiAlias(true);
        cellPainter.setColor(Color.argb(255, 100, 200, 200));
        dirtyLiveCellPainter = new Paint();
        dirtyLiveCellPainter.setAntiAlias(true);
        dirtyLiveCellPainter.setColor(Color.BLACK);
        dirtyDeadCellPainter = new Paint();
        dirtyDeadCellPainter.setAntiAlias(true);
        dirtyDeadCellPainter.setColor(Color.argb(120, 100, 200, 200));
        holder = getHolder();
    }


    @Override
    public void run() {
        while (shouldRun) {
            // Make sure there is a surface to draw on
            if (!holder.getSurface().isValid()) {
                SystemClock.sleep(50);
                continue;
            }

            world.applyRules();
            draw();
            if (! world.transition()) {
                setRunning(false);
            }

            // Wait
            SystemClock.sleep(SLEEP);
        }
    }


    public void pause() {
        setRunning(false);
        while(true) {
            try {
                thread.join();
                break;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        thread = null;
    }

    public void resume() {
        setRunning(true);
        thread = new Thread(this);
        thread.start();
    }


    public void setRunning(final boolean running) {
        this.shouldRun = running;
    }


    private void draw() {
        Canvas canvas = null;
        try {
            canvas = holder.lockCanvas();
            synchronized (holder) {
                drawTheWorld(canvas);
            }
        } finally {
            if (canvas != null) {
                holder.unlockCanvasAndPost(canvas);
            }
        }
    }

    private void drawTheWorld(Canvas canvas) {

        int width = world.getWidth() * CELL_SIZE;
        int height = world.getHeight() * CELL_SIZE;

        // draw grid
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


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        world = getAppropriatelySizedWorld(getMeasuredWidth(), getMeasuredHeight());
    }

    private World getAppropriatelySizedWorld(int measuredWidth, int measuredHeight) {
        if (world != null) {
            return world;
        }
        World world = new World(measuredWidth / CELL_SIZE, measuredHeight / CELL_SIZE);
        new Glider().draw(world, 13, 16);
        new Blinker().draw(world, 13, 12);
        new Block().draw(world, 14, 10);
        new BeeHive().draw(world, 17, 15);
        world.transition();
        return world;
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        SystemClock.sleep(10);

        Cell cell = world.getCellAt((int)(event.getX() / CELL_SIZE), (int)(event.getY() / CELL_SIZE));

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                world.toggle(cell);
                break;
            case MotionEvent.ACTION_MOVE:
                world.toggle(cell);
                break;
        }

        draw();
        return true;
    }
}

