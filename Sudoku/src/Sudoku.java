import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/** 
 * @author Iliyan Ivanov
 * </br></br>
 * The Class Sudoku. Follows the human strategy for solving sudoku.
 */
public class Sudoku {

	/** The sudoku. */
	private int[][] sudoku;

	/** The unsolved sudoku. */
	private final int[][] unsolved = new int[9][9];

	/** The debug prints more information if true. */
	private boolean DEBUG = false;

	/**
	 * The list of sets which keep track of all possible values for each cell in
	 * the grid.
	 */
	List<Set<Integer>> lists = new ArrayList<Set<Integer>>();

	/**
	 * Instantiates a new sudoku and a copy of the initial matrix. Validates the
	 * dimensions of the array. Instantiates a new List of Sets of integers
	 * which keep the possible vallues for each cell.
	 * 
	 * @param matrix
	 *            the matrix
	 */
	public Sudoku(int[][] matrix) {
		this.sudoku = matrix;
		// Performing a deep copy of matrix
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				this.unsolved[i][j] = matrix[i][j];
			}
		}
		validate(sudoku);
		// adding 81 empty ArrayLists to keep
		// possibilities for each cell
		for (int i = 0; i < 81; i++) {
			Set<Integer> l = new TreeSet<Integer>();
			lists.add(l);
		}
	}

	/**
	 * Solve.
	 * 
	 * Loops over the three methods for solving until they stop writing to the
	 * sudoku.
	 * 
	 */
	public void solve() {
		boolean repeat = true;
		int pass = 1;
		while (repeat) {
			System.out.println("Pass: " + pass);
			pass++;
			repeat = false;
			firstPass();
			for (int i = 0; i < 9; i += 3) {
				for (int j = 0; j < 9; j += 3) {
					if (write3x3(i, j))
						repeat = true;
					;
				}
			}
			for (int i = 0; i < 9; i++) {
				if (writeColumn(i))
					repeat = true;
			}
			for (int i = 0; i < 9; i++) {
				if (writeRow(i))
					repeat = true;
			}

		}
	}

	/**
	 * Write3x3.
	 * 
	 * This method for solving sudoku involves the 3 by 3 squares and the
	 * possible values that each cell inside can take.
	 * 
	 * @param ii
	 *            - point to the leftmost/top cell in a 3x3
	 * @param jj
	 *            - point to the leftmost/top cell in a 3x3
	 * @return true, if something has been written.
	 */
	private boolean write3x3(int ii, int jj) {
		boolean written = false;
		for (int k = 1; k < 10; k++) {
			int writeK = 0;
			int w_i = 0;
			int w_j = 0;
			for (int i = ii; i < ii + 3; i++) {
				for (int j = jj; j < jj + 3; j++) {
					if (sudoku[i][j] == 0) {
						if (!lists.get(9 * i + j).contains(k)) {
							writeK++;
							w_i = i;
							w_j = j;
							if (DEBUG)
								System.out.println("3x3 Writing " + k + " to "
										+ w_i + ", " + w_j);
						}
					}
				}
			}
			if (writeK == 1) {
				System.out.println("3x3 Writing " + k + " to " + w_i + ", "
						+ w_j);
				sudoku[w_i][w_j] = k;
				written = true;
			}
		}
		return written;
	}

	/**
	 * Write column.
	 * 
	 * Loops over the values of a column considering the missing numbers in the
	 * column and the possible values for each cell in the column.
	 * 
	 * @param col
	 *            the position of the column
	 * @return true, if successful
	 */
	private boolean writeColumn(int col) {
		boolean written = false;
		for (int n = 1; n < 10; n++) {
			int writeN = 0;
			int w_i = 0;
			if (!containedInCol(col, n)) {
				for (int i = 0; i < 9; i++) {
					if (sudoku[i][col] == 0
							&& !lists.get(9 * i + col).contains(n)) {
						writeN++;
						w_i = i;
					}
				}
			}
			if (writeN == 1) {
				System.out.println("Column Writing " + n + " to " + w_i + ", "
						+ col);
				sudoku[w_i][col] = n;
				written = true;
			}
		}
		return written;
	}

	/**
	 * Write row.
	 * 
	 * Loop over the values in a row, considering the missing values in the row
	 * and the possible values for each cell in the row.
	 * 
	 * @param row
	 *            the row
	 * @return true, if successful
	 */
	private boolean writeRow(int row) {
		boolean written = false;
		for (int n = 1; n < 10; n++) {
			int writeN = 0;
			int w_j = 0;
			if (!containedInRow(row, n)) {
				for (int j = 0; j < 9; j++) {
					if (sudoku[row][j] == 0
							&& !lists.get(9 * row + j).contains(n)) {
						writeN++;
						w_j = j;
					}
				}
			}
			if (writeN == 1) {
				System.out.println("Row Writing " + n + " to " + row + ", "
						+ w_j);
				sudoku[row][w_j] = n;
				written = true;
			}
		}
		return written;
	}

	/**
	 * Contained in col.
	 * 
	 * Return true is a value is contained in a column.
	 * 
	 * @param col
	 *            the column
	 * @param val
	 *            the value
	 * @return true, if successful
	 */
	private boolean containedInCol(int col, int val) {
		boolean contained = false;
		for (int i = 0; i < 9; i++) {
			if (sudoku[i][col] == val) {
				contained = true;
			}
		}
		return contained;
	}

	/**
	 * Return true is a value is contained in a row.
	 * 
	 * @param row
	 *            the row
	 * @param val
	 *            the value
	 * @return true, if successful
	 */
	private boolean containedInRow(int row, int val) {
		boolean contained = false;
		for (int i = 0; i < 9; i++) {
			if (sudoku[row][i] == val) {
				contained = true;
			}
		}
		return contained;
	}

	/**
	 * First pass.
	 * 
	 * Loops over each cell and call the workCell() method on it.
	 * 
	 */
	private void firstPass() {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (sudoku[i][j] == 0) {
					workCell(i, j);
				}
			}
		}
		for (int i = 0; i < 81; i++) {
			if (DEBUG)
				System.out.println(i + ": " + lists.get(i));
		}
	}

	/**
	 * Work cell.
	 * 
	 * Records all the possible values for a cell.
	 * 
	 * @param i
	 *            the i coord
	 * @param j
	 *            the j coord
	 */
	private void workCell(int i, int j) {
		int cell = 9 * i + j;
		for (int k = 0; k < 9; k++) {
			int x = sudoku[i][k];
			if (x != 0) {
				lists.get(cell).add(x);
			}
		}
		for (int k = 0; k < 9; k++) {
			int x = sudoku[k][j];
			if (x != 0) {
				lists.get(cell).add(x);
			}
		}
		int s1 = calcFirst(i, j)[0];
		int s2 = calcFirst(i, j)[1];
		for (int k = s1; k < s1 + 3; k++) {
			for (int l = s2; l < s2 + 3; l++) {
				int x = sudoku[k][l];
				if (x != 0) {
					if (DEBUG)
						System.out.println("i=" + i + ", j=" + j + " removing "
								+ x);
					lists.get(cell).add(x);
				}
			}
		}

	}

	/**
	 * Calc first.
	 * 
	 * @param i
	 *            the i
	 * @param j
	 *            the j
	 * @return the leftmost/top cell in a 3x3
	 */
	private int[] calcFirst(int i, int j) {
		int n1 = i - i % 3;
		int n2 = j - j % 3;
		if (DEBUG)
			System.out.println("calcFirst :" + n1 + ", " + n2);
		int[] ret = { n1, n2 };
		return ret;
	}

	/**
	 * Validate.
	 * 
	 * Validate the dimensions and size of the initial matrix input.
	 * 
	 * @param matrix
	 *            the matrix
	 * @return true, if successful
	 */
	private boolean validate(int[][] matrix) {
		for (int i = 0; i < 9; i++) {
			if (matrix[i].length != 9 || matrix.length != 9) {
				System.out.println("Validation failed! i = " + i);
				return false;
			}
		}
		System.out.println("Matrix is valid!");
		return true;
	}

	/*
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		String str = "";
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (j == 8) {
					str += sudoku[i][j] + "\n";
				} else if (j % 3 == 2) {
					str += sudoku[i][j] + " | ";
				} else {
					str += sudoku[i][j] + " ";
				}
			}
			if (i % 3 == 2 && i != 8) {
				str += "---------------------\n";
			}
		}
		return str;
	}

	/**
	 * Gets the unsolved sudoku.
	 * 
	 * @return the unsolved
	 */
	public int[][] getUnsolved() {
		return unsolved;
	}

}
