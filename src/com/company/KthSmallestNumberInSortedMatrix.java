package com.company;
import java.util.*;

public class KthSmallestNumberInSortedMatrix {
    // the matrix is not null, N > 0 and M > 0
    // K > 0 and K <= N * M
    // Time O(k log k)
    // Space = O(k + mn)  while 循环 k次 每次heapsize净增加一 但都不超过k
    // Assumptions
    // 1. matrix is not null, matrix size N * M where N > 0 and M > 0
    // 2. 0 < k << N * M
    // de-dup matrix
    // Expansion/generation rule
    //      Expand input[i][j]
    //      Generate input[i][j+1]
    //      Generate input[i+1][j]
    // Termination condition
    //      when the k-th element is popped out for expansion
    // Deduplication: which nodes have been generated (not expanded)
    //      boolean[][] generated			Space = O(n^2)
    //      generate[i][j] = true
    //      or we can also use a HashSet	Space = O(k)
    public int kthSmallest(int[][] matrix, int k) {
        // Write your solution here
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0 || k == 0) {
            return -1;
        }
        PriorityQueue<Cell> minHeap = new PriorityQueue<>();
        boolean[][] visited = new boolean[matrix.length][matrix[0].length];
        minHeap.offer(new Cell(0, 0, matrix[0][0]));
        visited[0][0] = true;
        for (int i = 0; i < k - 1; i++) {
            Cell curr = minHeap.poll();
            if (curr.row + 1 < matrix.length && !visited[curr.row + 1][curr.col]) {
                minHeap.offer(new Cell(curr.row + 1, curr.col, matrix[curr.row + 1][curr.col]));
                visited[curr.row + 1][curr.col] = true;
            }
            if (curr.col + 1 < matrix[0].length && !visited[curr.row][curr.col + 1]) {
                minHeap.offer(new Cell(curr.row, curr.col + 1, matrix[curr.row][curr.col + 1]));
                visited[curr.row][curr.col + 1] = true;
            }
        }
        return minHeap.peek().value;
    }

    private class Cell implements Comparable<Cell>{
        int row;
        int col;
        int value;

        public Cell(int row, int col, int value) {
            this.row = row;
            this.col = col;
            this.value = value;
        }

        @Override
        public int compareTo(Cell other) {
            if (this.value == other.value) {
                return 0;
            }
            return this.value < other.value ? -1 : 1;
        }
    }
}