public class Shell extends AbstractSort{
  public static void sort(Comparable[] a){
    int N = a.length;
    int h = 1;
    
    while (h < N/3) h = 3*h + 1;
    // h는 1, 4, 13, 40, ...
    
    while (h >= 1){
      for (int i = h; i < N; i++)
        for (int j = i; j >= h && less(a[j], a[j-h]); j-=h)
          exch(a, j, j-h);
      h /= 3;
    }
  }
}

/*
2번째 for문이 이해가 잘 안 됐는데 h = 4 라고 가정했을 때
배열의 10번째와 6번째를 비교 후 6번째와 2번째의 비교도
필요하기 때문에 j = i에서부터 h씩 줄어가고
a[j-h]를 접근할 거기 때문에 j>=h라는 조건도 추가로 필요
*/
