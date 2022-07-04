package jdk8;

import java.util.*;
import java.util.stream.Collectors;

public class Solution {
    public static String[] findRelativeRanks(int[] score) {
        String[] answer = new String[score.length];
        List<Integer> list = Arrays.stream(score).boxed().collect(Collectors.toList());
        Collections.sort(list, (t1, t2) -> t2 - t1);
        for (int i = 0; i < list.size(); i++) {
            if (i == 0) {
                answer[0] = "Gold Medal";
            } else if (i == 1) {
                answer[1] = "Silver Medal";
            } else if (i == 2) {
                answer[2] = "Bronze Medal";
            } else {
                answer[i] = String.valueOf(i + 1);
            }

        }
        return answer;
    }

    public static void main(String[] args) {
        int[] score = {10, 3, 8, 9, 4};
        findRelativeRanks(score);
    }
}
