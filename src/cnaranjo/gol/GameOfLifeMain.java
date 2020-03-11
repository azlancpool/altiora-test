/**
 * 
 */
package cnaranjo.gol;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * @author Cnaranjo
 *
 */
public class GameOfLifeMain {

	private static int matrixLimit;
	private static boolean[][] universeResponse;

	/**
	 * @param args
	 */
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		System.out.println("Ingrese el tamaño de la matriz:");
		///cambios hotfix
		Scanner sc = new Scanner(System.in);
		matrixLimit = sc.nextInt();
		universeResponse = new boolean[matrixLimit][matrixLimit];

		boolean[][] universe = copyArray(universeResponse);
		printMatrix(universe);
		System.out.println(
				"Ingrese las posiciones iniciales \"vivas\" de la matriz, en pares ordenados (separados por comas) y separados por (-) entre pares ordenados (Ejemplo: 0,1-0,4,1,5):");
		ArrayList<String> alivePositionsPair = new ArrayList<>(Arrays.asList(sc.next().split("-")));
		for (String string : alivePositionsPair) {
			universe[Integer.valueOf(string.split(",")[0])][Integer.valueOf(string.split(",")[1])] = true;
		}
		System.out.println("MATRIZ INICIAL:");
		printMatrix(universe);

		System.out.println("Ingrese el número de iteraciones");
		int iteratorNumber = sc.nextInt();
		for (int k = 1; k <= iteratorNumber; k++) {
			System.out.println("Iteracion" + k);
			for (int i = 0; i < matrixLimit; i++) {
				for (int j = 0; j < matrixLimit; j++) {
					checkRules(universe, i, j);
				}
			}
			printMatrix(universeResponse);
			universe = copyArray(universeResponse);
		}
	}

	/**
	 * Print on console the matrix passed as argument.
	 * 
	 * @param matrix Matrix to print.
	 */
	private static void printMatrix(boolean[][] matrix) {
		System.out.println("////////////////////////////////////");
		for (int i = 0; i < matrixLimit; i++) {
			for (int j = 0; j < matrixLimit; j++) {
				System.out.print(matrix[i][j] ? " 1 " : " 0 ");
			}
			System.out.println();
		}
		System.out.println("////////////////////////////////////");
	}

	/**
	 * Check the rules:
	 * I - An alive cell die for Underpopulation (neighbours<2)
	 * II - An alive cell survival (neighbours== 2 || neighbours == 3)
	 * III An alive cell die for Overpopulation (neighbours>3)
	 * IV - An cell alive by reproduction rule (neighbours==3)
	 * 
	 * @param universe matrix with cells.
	 * @param row      row position on matrix.
	 * @param column   column position on matrix.
	 */
	private static void checkRules(boolean[][] universe, int row, int column) {
		int neighbours = 0;
		neighbours = neighbours + findNeighbours(universe, row - 1, column - 1);
		neighbours = neighbours + findNeighbours(universe, row - 1, column);
		neighbours = neighbours + findNeighbours(universe, row - 1, column + 1);
		neighbours = neighbours + findNeighbours(universe, row, column - 1);
		neighbours = neighbours + findNeighbours(universe, row, column + 1);
		neighbours = neighbours + findNeighbours(universe, row + 1, column - 1);
		neighbours = neighbours + findNeighbours(universe, row + 1, column);
		neighbours = neighbours + findNeighbours(universe, row + 1, column + 1);

		if (universe[row][column]) {
			if (neighbours < 2 || neighbours > 3) {
				// Die for Underpopulation or Overpopulation rules (I and III rules)
				universeResponse[row][column] = false;
			} else if (neighbours == 2 || neighbours == 3) {
				// Alive for Survival rule (II rule)
				universeResponse[row][column] = true;
			}
		} else if (neighbours == 3) {
			// Alive for Reproduction rule (IV rule)
			universeResponse[row][column] = true;
		}
	}

	/**
	 * Find neighbours through the row and column positions into matrix
	 * 
	 * @param universe Matrix.
	 * @param row      Postion for row.
	 * @param column   Postion for column.
	 * @return 1: if TRUE in universe[row][column] - 0: Other wise.
	 */
	private static int findNeighbours(boolean[][] universe, int row, int column) {
		row = row < 0 ? matrixLimit - 1 : row;
		row = row >= matrixLimit ? 0 : row;
		column = column < 0 ? matrixLimit - 1 : column;
		column = column >= matrixLimit ? 0 : column;

		return universe[row][column] ? 1 : 0;
	}

	/**
	 * Copy an array and return a new array.
	 * 
	 * @param universeFrom matrix to copy.
	 * @return new array copied.
	 */
	private static boolean[][] copyArray(boolean[][] universeFrom) {
		boolean[][] returnArray = new boolean[matrixLimit][matrixLimit];

		for (int i = 0; i < matrixLimit; i++)
			for (int j = 0; j < matrixLimit; j++)
				returnArray[i][j] = universeFrom[i][j];

		return returnArray;

	}

}
