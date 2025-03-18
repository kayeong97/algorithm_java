public class Selection extends AbstractSort{
  public static void sort(Comparable[] a){
    int N = a.length(); // 정렬할 배열의 길이

    for(int i = 0; i < N - 1; i++){
      int min = a[i];
      for(int j = i + 1; j < N; j++){
        if(less(a[j], a[min])) min = j; // 제일 작은 것을 선택
      }
      exch(a, i, min); // 현재 자리에 선택된 수를 넣음
    }
    assert isSorted(a);
  }
}
