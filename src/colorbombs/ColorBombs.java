package colorbombs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ColorBombs {

    public static void main(String[] args) {
        BufferedReader reader = null;
        reader = new BufferedReader(new InputStreamReader(System.in));

        int row = 0, col = 0;
        String[] ColorsByRow = null;
        char[][] Matrix = null;
        Island island = null;

        try {
            System.out.print("row = ");
            row = Integer.parseInt(reader.readLine());
            System.out.print("col = ");
            col = Integer.parseInt(reader.readLine());
        } catch (IOException ex) {
            Logger.getLogger(ColorBombs.class.getName()).log(Level.SEVERE, null, ex);
        }

        ColorsByRow = new String[row];
        Matrix = new char[row][col];
        for (int i = 0; i < row; i++) {

            for (int j = 0; j < col; j++) {
                try {
                    Matrix[i][j] = (char) reader.read();
                } catch (IOException ex) {
                    Logger.getLogger(ColorBombs.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        island = new Island(row, col, Matrix);
        Set<Character> set = UniqueChars(Matrix);
        int amount = 0;
        for (char ch : set) {
            amount += island.countIslands(ch);
        }
        System.out.println("Amount = " + amount);

    }

    
    public static Set<Character> UniqueChars(char[][] Matrix) {
        Set<Character> set = new HashSet<>();
        for (int i = 0; i < Matrix.length; i++) {
            for (int j = 0; j < Matrix[i].length; j++) {
                if (!set.contains(Matrix[i][j])) {
                    set.add(Matrix[i][j]);
                }
            }
        }
        return set;
    }
    public static class Island {

        private int row, col;
        private char[][] matrix = null;

        public Island(int row, int col, char[][] matrix) {
            this.matrix = matrix;
            this.row = row;
            this.col = col;
        }

        boolean isSafe(int row, int col, boolean visited[][], char ch) {
            return (row >= 0) && (row < this.row)
                    && (col >= 0) && (col < this.col)
                    && (Character.toLowerCase(this.matrix[row][col]) == Character.toLowerCase(ch) && !visited[row][col]);
        }

        void DFS(int row, int col, boolean visited[][], char ch) {

            int rowNbr[] = new int[]{-1, -1, -1, 0, 0, 1, 1, 1};
            int colNbr[] = new int[]{-1, 0, 1, -1, 1, -1, 0, 1};

            visited[row][col] = true;

            for (int k = 0; k < 8; ++k) {
                if (isSafe(row + rowNbr[k], col + colNbr[k], visited, ch)) {
                    DFS(row + rowNbr[k], col + colNbr[k], visited, ch);
                }
            }
        }

        int countIslands(char ch) {

            boolean visited[][] = new boolean[row][col];

            int count = 0;
            for (int i = 0; i < row; ++i) {
                for (int j = 0; j < col; ++j) {
                    if (matrix[i][j] == ch && !visited[i][j]) {
                        DFS(i, j, visited, ch);
                        ++count;
                    }
                }
            }
            return count;
        }

    }

}
