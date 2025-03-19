// 기수 정렬엔 MSD(높은 자리 우선 정렬)과 LSD(낮은 자리 우선 정렬)이 있는데 여기선 LSD를 구현함
public Radix extends AbstractSort{
  public static void sort(int[] a){
    int N = a.length;
    int i, m = a[0], exp = 1;
    int[] B = new int[N];

    for (i = 1; i < n; i++)
      if (A[i] > m) m = A[i];
      // 제일 큰 수를 찾음 왜냐하면 제일 큰 자리수가 무엇인지 봐야하기 때문에

    while (m / exp > 0){ // 제일 큰 수였던 수를 계속 10으로 나눠가며 언제 마지막 자리수인지 찾음
      int[] C = new int[10];
      for (i = 0; i < N; i++) C[(a[i] / exp) % 10] ++;
      // 현재 숫자의 개수를 셈
      // /exp 가 현재 이후를 날리고 %10 이 현재 이전을 날림
    
      for (i = 1; i < 10; i++) C[i] = C[i - 1] + C[i];
      // 인덱스 위치 저장
    
      for (i = N - 1; i >= 0; i--) B[--C[(a[i] / exp) % 10]] = a[i];
      // 개수를 저장했으니까 먼저 빼고 B에 넣어야 함

      for (i = 0; i < n; i++) A[i] = B[i];
      // 원래 배열에 다시 복사

      exp *= 10; 
      // 다음 높은 자리로 이동
    }
  }
}
