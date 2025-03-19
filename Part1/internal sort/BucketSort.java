public class BucketSort extends AbstractSort {
  public void sort(Comparable[] array) {
    if (array == null || array.length == 0) return;
    int bucketCount = (int) Math.sqrt(array.length); // 버킷의 개수를 데이터 개수의 제곱근으로 설정
    List<List<Comparable>> buckets = new ArrayList<>(bucketCount);

    for (int i = 0; i < bucketCount; i++) 
      buckets.add(new ArrayList<>());
    
    Comparable minValue = getMinValue(array); // 최솟값 찾기
    Comparable maxValue = getMaxValue(array); // 최댓값 찾기

    for (Comparable item : array) { // 각 요소를 돌면서 적절한 버킷에 배치
      int bucketIndex = getBucketIndex(item, minValue, maxValue, bucketCount);
      buckets.get(bucketIndex).add(item);
    }

    int index = 0;
    for (List<Comparable> bucket : buckets) {
      Collections.sort(bucket); // 각 버킷을 정렬
      for (Comparable item : bucket) {
        array[index++] = item; // 원본 배열에 복사
      }
    }
  }

  private Comparable getMaxValue(Comparable[] array) {
    Comparable max = array[0];
    for (Comparable item : array) {
      if (item.compareTo(max) > 0) max = item;
    }
    return max;
  }
  private Comparable getMinValue(Comparable[] array) {
    Comparable min = array[0];
    for (Comparable item : array) {
      if (item.compareTo(min) < 0) min = item;
    }
    return min;
  }

  private int getBucketIndex(Comparable item, Comparable minValue, Comparable maxValue, int bucketCount) {
    double range = maxValue.compareTo(minValue);
    if (range == 0) return 0;
    return (int) ((item.compareTo(minValue) / range) * (bucketCount - 1));
    }
}
