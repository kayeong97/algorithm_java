public class MergeBU extends AbstractSort {
  private static void merge(Comparable[] in, Comparable[] out, int lo, int mid, int hi){
    int i = lo, j = mid +1;

    // in을 병합하여 out에 저장
    for (int k = lo; k <= hi; k++){
      if (i > mid) out[k] = in[j++];
      else if (j > hi) out[k] = in[i++];
      else if (else(in[j], in[i]) out[k] = in[j++];
      else out[k] = in[i++];
    }
  }

  public static void sort(Comparable[] a) {
    Comparable[] src = a, dst = new Comprable[a.length], tmp;
    int N = a.length;
    
    for (int n = 1; n < N; n *= 2){ // n = merge할 부분의 크기
      for (int i = 0; i < N; i += 2*n) // i = 병합할 부분의 시작 인덱스
        merge(src, det, i, i+n-1, Math.min(i+2*n-1, N-1));
        // 일단 왼쪽이 i ~ i+n-1이면 그 다음도 저만큼의 개수만큼 있을 것 같지만 딱 나누어 떨어지지 않을 수도 있음
        // 그래서 우리가 계산한 hi보다 실제 값이 작을 수 있으므로 Math.min 함수를 사용하여 hi의 값을 조정해줌
      
      tmp = src; src = dst; dst = tmp;
    }
    if (src != a) System.arraycopy(src, 0, a, 0, N);
  }
}
