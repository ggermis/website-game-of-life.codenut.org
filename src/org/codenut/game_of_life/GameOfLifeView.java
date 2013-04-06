package org.codenut.game_of_life;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.SurfaceView;
import android.view.View;

public class GameOfLifeView extends View {

    private final int PADDING = 1;
    private final int CELL_SIZE = 10;

    private Paint gridPainter;
    private Paint cellPainter;

    private World world;


    public GameOfLifeView(Context context, AttributeSet attributes) {
        super(context, attributes);
        init();
    }

    private void init() {
        gridPainter = new Paint();
        gridPainter.setColor(Color.DKGRAY);
        cellPainter = new Paint();
        cellPainter.setColor(Color.CYAN);
    }

    public void setWorld(final World world) {
        this.world = world;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (world == null) {
            return; // nothing to draw
        }

        super.onDraw(canvas);

        int width = Math.min(world.getWidth() * CELL_SIZE, getMeasuredWidth());
        int height = Math.min(world.getHeight() * CELL_SIZE, getMeasuredHeight());

        // draw grid
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
}

