// Top-Down Merge Sort
// 재귀를 활용
public class MergeTD extends AbstractSort{
  // 제자리 병합
  private static void merge(Comparable[] a, Comparable[] aux, int lo, int mid, int hi) {
    // a[lo .. mid] and a[mid+1 .. hi] 는이미정렬
    for (int k = lo; k <= hi; k++) aux[k] = a[k]; 
    // aux[] 배열에 a[]의 내용을 일단 다시 복사
    // 병합을 a에 할 거기 때문데 추가 공간이 필요
  
    int i = lo, j = mid+1;
    for (int k = lo; k <= hi; k++) {
      if (i > mid) a[k] = aux[j++]; // 왼쪽 복사 완료
      else if (j > hi) a[k] = aux[i++]; // 오른쪽 복사 완료
      else if (less(aux[j], aux[i])) a[k] = aux[j++]; // 오른쪽이 작음
      else a[k] = aux[i++]; // 왼쪽이 작음
    }
  }

  public static void sort(Comparable[] a) {
    Comparable[] aux = new Comparable[a.length];
    sort(a, aux, 0, a.length - 1);
  }

  private static void sort(Comparable[] a, Comparable[] aux, int lo, int hi){
    if (hi <= lo) return; // 재귀 종료 조건
    int mid = lo + (hi - lo) / 2;
    sort(a, aux, lo, mid);
    sort(a, aux, mid + 1, hi);
    merge(a, aux, mid, hi);
  }
}

/*
성능 개선 방안
  1. cutoff 설정하여 7개 이하가 되면 insertion sort 사용
   -> 재귀라는 것에 대한 부담감을 감소

  재귀 종료 조건으로 이 코드를 넣어줌
   if (hi <= lo + CUTOFF - 1)  {
     Insertion.sort(a, lo, hi);
     return;
   }

  2. 앞 부분의 제일 큰 원소 <= 뒷 부분의 제일 작은 원소 인 경우 병합을 하지 않음

    병합 전 검사함
    if (less(a[mid], a[mid+1]))
     return;

  3. a와 aux를 교대로 사용하면서 복사하는 시간을 없앰

    sort(aux, a, lo, mid);
    sort(aux, a, mid + 1, hi);
    이렇게 호출하면 a와 aux의 역할이 바뀜
    
