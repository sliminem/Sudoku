import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Test extends JFrame {

	Sudoku s;
	boolean btnPressed = false;

	// http://static.guim.co.uk/sys-images/Guardian/Pix/pictures/2012/12/13/1355398426971/Sudoku2373medium.png
	static int[][] sudoku = { { 0, 0, 4, 0, 0, 0, 0, 0, 0 },
			{ 0, 7, 0, 2, 0, 0, 5, 9, 0 }, { 0, 6, 0, 0, 3, 5, 0, 0, 1 },
			{ 0, 0, 9, 0, 0, 0, 0, 7, 0 }, { 0, 0, 6, 0, 0, 0, 2, 0, 0 },
			{ 0, 1, 0, 0, 0, 0, 9, 0, 0 }, { 5, 0, 0, 4, 9, 0, 0, 1, 0 },
			{ 0, 4, 2, 0, 0, 3, 0, 6, 0 }, { 0, 0, 0, 0, 0, 0, 4, 0, 0 } };

	static int[][] sudoku2 = { { 0, 0, 4, 0, 0, 0, 0, 6, 7 },
			{ 3, 0, 0, 4, 7, 0, 0, 0, 5 }, { 1, 5, 0, 8, 2, 0, 0, 0, 3 },

			{ 0, 0, 6, 0, 0, 0, 0, 3, 1 }, { 8, 0, 2, 1, 0, 5, 6, 0, 4 },
			{ 4, 1, 0, 0, 0, 0, 9, 0, 0 },

			{ 7, 0, 0, 0, 8, 0, 0, 4, 6 }, { 6, 0, 0, 0, 1, 2, 0, 0, 0 },
			{ 9, 3, 0, 0, 0, 0, 7, 1, 0 } };

	/**
	 * @param args
	 */
	public Test(boolean enableButton) {
		JPanel controlPanel = new JPanel();
		if (enableButton) {
			JButton solveButton = new JButton("Solve!");
			controlPanel = new JPanel();
			controlPanel.setLayout(new FlowLayout());
			controlPanel.add(solveButton);
			solveButton.addActionListener(new SolveListener());
		}

		s = new Sudoku(sudoku);
		System.out.println(s);
		s.solve();
		System.out.println(s);

		DrawSudoku d = new DrawSudoku(s);
		JPanel content = new JPanel();
		content.setLayout(new BorderLayout());
		content.add(d, BorderLayout.CENTER);
		if (enableButton)
			content.add(controlPanel, BorderLayout.NORTH);

		setContentPane(content);
		setTitle("Sudoku Viewer");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false); // Don't let user resize it.
		pack();
		setLocationRelativeTo(null); // Center it.
	}

	public class SolveListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			if (!btnPressed) {
				Test t = new Test(false);
				t.setVisible(true);
				btnPressed = true;
			}

		}

	}

	public static void main(String[] args) {
		Test t = new Test(true);
		t.setVisible(true);
	}

}
