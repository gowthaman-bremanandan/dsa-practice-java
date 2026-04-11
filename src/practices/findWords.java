package practices;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class findWords {

    public static void main(String[] args) {
        char[][] board = new char[][]{
                {'o', 'a', 'a', 'n'},
                {'e', 't', 'a', 'e'},
                {'i', 'h', 'k', 'r'},
                {'i', 'f', 'l', 'v'}
        };
        List<String> words = Arrays.asList("oath", "eat", "rain");
        findTheWords(board, words);
    }

    private static void findTheWords(char[][] board, List<String> words) {
        List<String> result = new ArrayList<>();
        for (String word : words) {
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[0].length; j++) {
                    if (board[i][j] == word.charAt(0)) {
                        if (isExists(board, word, "", i, j, new boolean[board.length][board[0].length])) {
                            result.add(word);
                        }
                    }
                }
            }
        }
        System.out.println(result);
    }

    private static boolean isExists(char[][] board, String word, String formedWord, int row, int column, boolean[][] visited) {
        if (row >= board.length || row < 0 || column < 0 || column >= board[0].length || formedWord.length() >= word.length() || board[row][column] != word.charAt(formedWord.length())) {
            return false;
        }
        if (visited[row][column]) {
            return false;
        }
        visited[row][column] = true;
        formedWord = formedWord.concat(String.valueOf(board[row][column]));
        System.out.println(formedWord);
        if (word.equals(formedWord)) {
            return true;
        }

        return isExists(board, word, formedWord, row + 1, column, visited) ||
                isExists(board, word, formedWord, row - 1, column, visited) ||
                isExists(board, word, formedWord, row, column + 1, visited) ||
                isExists(board, word, formedWord, row, column - 1, visited);
    }
}
