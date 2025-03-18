// 기본적인 insertion sort
public class Insertion extends AbstractSort{
  public static void sort(Comparable[] a){
    int N = a.length;
    for(int i = 1; i < N; i++){
      for(int j = i; j > 0 && less(a[j], a[j-1]); j--){
        exch(a, j, j-1);
      }
    }
    assert isSorted(a);
  }
}
/*
개선 방안
  정렬할 배열을 1-base인 배열로 만들어서 인덱스 0번에는 key값을 저장
  그렇게 하면 j가 0보다 작은지 계속 확인할 필요가 없어짐
*/

// binary insertion sort
public class BinaryInsertion extends AbstractSort {
    public static void sort(Comparable[] a) {
        int N = a.length;
        for (int i = 1; i < N; i++) {
            Comparable key = a[i];
            int left = 0, right = i - 1;

            // 이진 탐색을 사용하여 삽입할 위치 찾기
            while (left <= right) {
                int mid = left + (right - left) / 2;
                if (less(key, a[mid])) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            }

            // 요소를 한 칸씩 뒤로 이동하여 자리 확보
            for (int j = i; j > left; j--) {
                a[j] = a[j - 1];
            }
            
            // 찾은 위치에 key 삽입
            a[left] = key;
        }
        
        assert isSorted(a);
    }
}
