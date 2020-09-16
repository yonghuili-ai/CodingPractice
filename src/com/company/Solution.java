package com.company;
import java.util.*;

// Time O(2^n)
// Space O(n)
public class Solution {
    // sort in descending order and store result in the bottom part of input
    // input: result | unsorted elements
    // buffer: used to store elements temporarily when finding max each round
    public void sort(Deque<Integer> s1) {
        Deque<Integer> s2 = new ArrayDeque<>();
        // Write your solution here.
        int preMax = Integer.MAX_VALUE;
        while (!s1.isEmpty() && s1.peekFirst() < preMax) {
            Integer currMax = Integer.MIN_VALUE;
            int count = 0;
            while (!s1.isEmpty() && s1.peekFirst() < preMax) {
                int tmp = s1.pollFirst();
                if (tmp == currMax) {
                    count++;
                } else if (tmp > currMax) {
                    count = 1;
                    currMax = tmp;
                }
                s2.offerFirst(tmp);
            }
            while (count > 0) {
                s1.offerFirst(currMax);
                count--;
            }
            while (!s2.isEmpty()) {
                int tmp = s2.pollFirst();
                if (tmp != currMax) {
                    s1.offerFirst(tmp);
                }
            }
            preMax = currMax;
        }
    }
}