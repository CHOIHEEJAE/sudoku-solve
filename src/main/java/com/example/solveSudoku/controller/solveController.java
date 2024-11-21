package com.example.solveSudoku.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@Slf4j
@RestController
@RequestMapping("/sudoku")
public class solveController {

    @PostMapping("/solve")
    public int[][] solve(@RequestBody int[][] board) {
        log.info("solve request board ::: {} ", Arrays.deepToString(board));

        int[][] result = solveSudoku(board) ? board : null;

        log.info("solveSudoku response board ::: {} ", Arrays.deepToString(result));
        return result;  // 해결 가능하면 보드 반환, 아니면 null 반환
    }

    /**
     * 스도쿠 풀이 로직 (백트래킹 방식)
     */
    private boolean solveSudoku(int[][] board) {

        int[] emptyCell = findEmptyCell(board);
        if (emptyCell == null) return true;  // 더 이상 빈 칸이 없으면 해결됨

        int row = emptyCell[0];
        int col = emptyCell[1];

        for (int num = 1; num <= 9; num++) {
            if (isSafe(board, row, col, num)) {
                board[row][col] = num;
                if (solveSudoku(board)) return true;
                board[row][col] = 0; // Backtrack
            }
        }

        return false; // No valid number found
    }

    // 빈 칸을 찾는 메서드 (row, col 반환)
    private int[] findEmptyCell(int[][] board) {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (board[row][col] == 0) {
                    return new int[]{row, col};  // 첫 번째 빈 칸 반환
                }
            }
        }
        return null; // 빈 칸이 없으면 null 반환
    }

    private boolean isSafe(int[][] board, int row, int col, int num) {
        // 가로, 세로, 3x3 구역에 같은 숫자가 있는지 한 번에 확인
        for (int i = 0; i < 9; i++) {
            if (board[row][i] == num || board[i][col] == num) return false;
            // 3x3 구역 확인
            int boxRow = (row / 3) * 3 + i / 3;
            int boxCol = (col / 3) * 3 + i % 3;
            if (board[boxRow][boxCol] == num) return false;
        }
        return true;
    }

}


//findEmptyCell 메서드: 스도쿠 보드에서 첫 번째 빈 칸을 찾아 반환하는 메서드입니다. row와 col을 배열로 반환합니다. 이로 인해 solveSudoku 메서드 내에서 두 개의 중첩된 for 문을 없애고, 빈 칸을 찾는 과정이 더 명확해졌습니다.
//isSafe 메서드 간소화: isSafe에서 세로, 가로, 그리고 3x3 구역의 숫자가 충돌하는지 확인하는 부분을 하나의 for문으로 합쳤습니다. 구체적으로 3x3 구역은 boxRow와 boxCol을 계산하여 3x3 영역을 체크합니다. 이렇게 하면 3개의 for 문을 하나의 for문으로 줄일 수 있습니다.
//백트래킹 로직 개선: solveSudoku는 이제 빈 칸을 찾고, 숫자를 테스트하는 방식으로 진행됩니다. 더 이상 2개의 중첩된 for문을 사용할 필요가 없습니다. 빈 칸을 찾으면 해당 위치에 숫자를 넣고 재귀적으로 해결을 시도합니다.
