import java.util.Arrays;
import java.util.Stack;

public class Timsort {

    private static final int MAX_MERGE = 64;  // 기본적으로 32 ~ 64 사이에서 조정
    private static final int MIN_GALLOP = 7;  // Galloping 전환 기준

    /*------------------------------------------------
     * 1. minrun 계산
     *   - N < 64인 경우 전체를 삽입 정렬로 처리
     *   - 그 외에는 32 <= minrun <= 64
     *     N/minrun이 (거의) 2의 거듭제곱이 되도록
     *------------------------------------------------*/
    private static int minRunLength(int n) {
        int r = 0;
        while (n >= 64) {
            r |= (n & 1);
            n >>= 1;
        }
        return n + r;
    }

    /*------------------------------------------------
     * 2. 자연적인 run 탐색
     *   - a[i] <= a[i+1] 오름차순 or
     *   - a[i] >  a[i+1] 내림차순
     *   - 내림차순이면 reverse해서 오름차순으로 만듦
     *------------------------------------------------*/
    private static int[] findRun(int[] arr, int start) {
        int n = arr.length;
        if (start >= n - 1) {
            // 남은 원소가 1개 이하인 경우
            return new int[]{start, start};
        }
        int end = start + 1;

        // 오름차순인지 먼저 판별
        if (arr[start] <= arr[end]) {
            // 오름차순 run
            while (end < n - 1 && arr[end] <= arr[end + 1]) {
                end++;
            }
        } else {
            // 내림차순 run
            while (end < n - 1 && arr[end] > arr[end + 1]) {
                end++;
            }
            reverse(arr, start, end); // 뒤집어서 오름차순으로
        }
        return new int[]{start, end};
    }

    // 구간 [left, right] 역순으로 뒤집기
    private static void reverse(int[] arr, int left, int right) {
        while (left < right) {
            int tmp = arr[left];
            arr[left] = arr[right];
            arr[right] = tmp;
            left++;
            right--;
        }
    }

    /*------------------------------------------------
     * 3. 이진 탐색 기반 삽입 정렬 (작은 run 처리)
     *------------------------------------------------*/
    private static void binaryInsertionSort(int[] arr, int start, int end) {
        for (int i = start + 1; i <= end; i++) {
            int key = arr[i];

            // 이진 탐색으로 삽입 위치 찾기
            int low = start;
            int high = i - 1;
            while (low <= high) {
                int mid = (low + high) / 2;
                if (key < arr[mid]) {
                    high = mid - 1;
                } else {
                    low = mid + 1;
                }
            }

            // low가 key의 삽입 위치
            for (int j = i; j > low; j--) {
                arr[j] = arr[j - 1];
            }

            arr[low] = key;
        }
    }


    /*------------------------------------------------
     * 4. 병합 (Galloping Mode 포함)
     *   - merge_lo / merge_hi 를 하나로 통합
     *   - A <= B 인 경우 (merge_lo),
     *     A >  B 인 경우 (merge_hi) 로직 유사
     *------------------------------------------------*/
    private static void merge(int[] arr, int LeftStart, int LeftEnd, int RightEnd) {
        int len1 = LeftEnd - LeftStart + 1;
        int len2 = RightEnd - LeftEnd;

        // 임시 배열 복사
        int[] leftArr = new int[len1];
        int[] rightArr = new int[len2];
        System.arraycopy(arr, LeftStart, leftArr, 0, len1);
        System.arraycopy(arr, LeftEnd + 1, rightArr, 0, len2);

        int i = 0, j = 0;
        int k = LeftStart;
        int gallopCount = 0;
        int lastWinner = -1; // 0: left, 1: right

        while (i < len1 && j < len2) {
            if (leftArr[i] <= rightArr[j]) {
                arr[k++] = leftArr[i++];

                if (lastWinner == 0) {
                    gallopCount++;
                } else {
                    gallopCount = 1;
                    lastWinner = 0;
                }
            } else {
                arr[k++] = rightArr[j++];

                if (lastWinner == 1) {
                    gallopCount++;
                } else {
                    gallopCount = 1;
                    lastWinner = 1;
                }
            }

            // Galloping 모드 진입
            if (gallopCount >= MIN_GALLOP) {
                gallopCount = 0;

                if (i < len1 && j < len2) {
                    if (leftArr[i] <= rightArr[j]) {
                        int gCount = gallopLeft(rightArr, leftArr[i], j, len2 - j);
                        System.arraycopy(rightArr, j, arr, k, gCount);
                        j += gCount;
                        k += gCount;
                    } else {
                        int gCount = gallopRight(leftArr, rightArr[j], i, len1 - i);
                        System.arraycopy(leftArr, i, arr, k, gCount);
                        i += gCount;
                        k += gCount;
                    }
                }

                // galloping 이후 다시 비교부터 시작해야 하므로 승자 초기화
                lastWinner = -1;
            }
        }

        // 남은 요소 복사
        while (i < len1) arr[k++] = leftArr[i++];
        while (j < len2) arr[k++] = rightArr[j++];
    }

    // Gallop Left: key보다 작은 요소는 모두 스킵
    // (key 이하인 부분 길이 반환)
    private static int gallopLeft(int[] arr, int key, int base, int len) {
        int lastOffset = 0;
        int offset = 1;

        while (offset < len && arr[base + offset] < key) {
            lastOffset = offset;
            offset = (offset * 2) + 1;
            if (offset <= 0) offset = len; // overflow 방지
        }
        if (offset > len) offset = len;

        int low = lastOffset, high = offset;
        while (low < high) {
            int mid = (low + high) / 2;
            if (arr[base + mid] < key) low = mid + 1;
            else high = mid;
        }
        return low;
    }

    // Gallop Right: key보다 큰 요소 나오기 전까지 스킵
    private static int gallopRight(int[] arr, int key, int base, int len) {
        int lastOffset = 0;
        int offset = 1;

        while (offset < len && arr[base + offset] <= key) {
            lastOffset = offset;
            offset = (offset * 2) + 1;
            if (offset <= 0) offset = len;
        }
        if (offset > len) offset = len;

        int low = lastOffset, high = offset;
        while (low < high) {
            int mid = (low + high) / 2;
            if (arr[base + mid] <= key) low = mid + 1;
            else high = mid;
        }
        return low;
    }

    /*------------------------------------------------
     * 5. mergeCollapse
     *   - 스택에 저장된 run들에 대해
     *   - "A > B + C", "B > C" 규칙을 만족하지 않으면
     *     (즉 A <= B+C 이거나 B <= C 인 경우)
     *     -> 더 작은 쌍 병합.
     *------------------------------------------------*/
    private static void mergeCollapse(int[] arr, Stack<int[]> stack) {
        while (stack.size() > 2) {
            // 스택 top 3개 (C, B, A) 가져옴 (오른쪽이 C)
            int[] runC = stack.pop();
            int[] runB = stack.pop();
            int[] runA = stack.pop();

            int lenA = runA[1] - runA[0] + 1;
            int lenB = runB[1] - runB[0] + 1;
            int lenC = runC[1] - runC[0] + 1;

            // 불변식 #1: A > B + C  (위배 시 병합)
            // 불변식 #2: B > C     (위배 시 병합)
            boolean doMerge = false;
            // 먼저 #1 체크: A <= B + C ?
            if (lenA <= lenB + lenC) {
                // A와 C 중 더 작은 쪽을 B와 병합 (원문 설명)
                if (lenA < lenC) {
                    // merge A, B
                    merge(arr, runA[0], runA[1], runB[1]);
                    // 새 run을 A+B로 대체
                    int[] newAB = new int[]{runA[0], runB[1]};
                    stack.push(newAB);
                    // C는 다시 push
                    stack.push(runC);
                } else {
                    // merge B, C
                    merge(arr, runB[0], runB[1], runC[1]);
                    int[] newBC = new int[]{runB[0], runC[1]};
                    stack.push(runA);
                    stack.push(newBC);
                }
                doMerge = true;
            } else {
                if (lenB <= lenC) {
                    merge(arr, runB[0], runB[1], runC[1]);
                    int[] newBC = new int[]{runB[0], runC[1]};
                    stack.push(runA);
                    stack.push(newBC);
                    doMerge = true;
                } else {
                    stack.push(runA);
                    stack.push(runB);
                    stack.push(runC);
                    return;
                }
            }

            if (doMerge) {
                continue;
            } else {
                break;
            }
        }
    }

    /*------------------------------------------------
     * 6. 최종 timSort 함수
     *    - 배열 전체를 순회하며 run 찾고
     *    - run 길이가 minrun보다 작으면
     *      binaryInsertionSort
     *    - stack에 push -> mergeCollapse로
     *      A,B,C 규칙 검사/병합
     *    - 마지막에 남은 run도 모두 병합
     *------------------------------------------------*/
    public static void timSort(int[] arr) {
        int n = arr.length;
        if (n < 2) return;

        int minRun = minRunLength(n);
        Stack<int[]> stack = new Stack<>();

        int start = 0;
        while (start < n) {
            // 자연적 run 찾기
            int[] run = findRun(arr, start);
            int runStart = run[0];
            int runEnd = run[1];

            // minRun보다 작으면 확장해 삽입 정렬
            int runLen = runEnd - runStart + 1;
            if (runLen < minRun) {
                int end = Math.min(runStart + minRun - 1, n - 1);

                binaryInsertionSort(arr, runStart, end);

                int next = end + 1;
                while (next < n && arr[next - 1] <= arr[next]) {
                    next++;
                }
                runEnd = next - 1;
            }
            // 스택에 추가
            stack.push(new int[]{runStart, runEnd});

            // 규칙 검사 후 병합
            mergeCollapse(arr, stack);

            // 다음 run으로 이동
            start = runEnd + 1;
        }

        // 스택에 남은 run 전부 병합
        while (stack.size() > 1) {
            int[] top1 = stack.pop();
            int[] top2 = stack.pop();
            merge(arr, top2[0], top2[1], top1[1]);
            stack.push(new int[]{top2[0], top1[1]});
        }
    }

    // 간단 테스트
    public static void main(String[] args) {
        //int[] arr = {5, 21, 7, 23, 19, 10, 3, 8, 15, 6, 12};
        int[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 15, 14, 15, 16, 17, 18, 19, 21};
        System.out.println("정렬 전 : " + Arrays.toString(arr));

        timSort(arr);

        System.out.println("정렬 후 : " + Arrays.toString(arr));
    }
}
