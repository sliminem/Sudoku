import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JComponent;

/**
 * The Class DrawSudoku.
 * 
 * This class draws the unsolved sudoku first. When the Solve! button is pressed
 * is shows another window, containing the solved sudoku.
 */
public class DrawSudoku extends JComponent {

	private final int CELL = 40;
	private final int GRID = 9 * CELL;
	private final Sudoku s;
	private final Font TEXT_FONT = new Font(Font.MONOSPACED, Font.PLAIN, 24);
	private final int TEXT_OFFSET = 12;

	public DrawSudoku(Sudoku s) {
		setPreferredSize(new Dimension(GRID + 3, GRID + 3));
		setBackground(Color.WHITE);
		this.s = s;
	}

	public void paintComponent(Graphics g) {
		// ... Draw background.
		g.setColor(getBackground());
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(Color.BLACK);

		drawLines(g);
		drawNumbers(g);

	}

	private void drawLines(Graphics g) {
		for (int i = 0; i < 10; i++) {
			int acrossOrDown = i * CELL;
			g.drawLine(acrossOrDown, 0, acrossOrDown, GRID);
			g.drawLine(0, acrossOrDown, GRID, acrossOrDown);

			if (i % 3 == 0) {
				acrossOrDown += 2;
				g.drawLine(acrossOrDown, 0, acrossOrDown, GRID);
				g.drawLine(0, acrossOrDown, GRID, acrossOrDown);
			}
		}
	}

	private void drawNumbers(Graphics g) {
		g.setFont(TEXT_FONT);
		for (int i = 0; i < 9; i++) {
			int yDisplacement = (i + 1) * CELL - TEXT_OFFSET;
			for (int j = 0; j < 9; j++) {
				if (s.getUnsolved()[i][j] != 0) {
					int xDisplacement = j * CELL + TEXT_OFFSET;
					g.drawString("" + s.getUnsolved()[i][j], xDisplacement,
							yDisplacement);
				}
			}
		}
	}

}
