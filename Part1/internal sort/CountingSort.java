public class Counting {
  // 이 정렬은 배열의 인덱스를 사용 -> int만 가능 -> comparable 사용 불가
  public static int[] sort(int[] A, int K) { // 여기서 K는 배열의 max 값
    int i, N = A.length;
    int[] C = new int[K], B = new int[N];
    
    for (i = 0; i < N; i++) C[A[i]]++;
    // A에 나오는 수들의 빈도를 체크
    
    for (i = 1; i < K; i++) C[i] += C[i-1];
    // 저장될 위치를 계산
    
    for (i = N-1; i >= 0; i--) B[--C[A[i]]] = A[i]; 
    //정렬
    
    return B;
  }
}
