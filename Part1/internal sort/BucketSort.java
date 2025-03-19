public class BucketSort extends AbstractSort {
  public void sort(Comparable[] array) {
    if (array == null || array.length == 0) return;
    int bucketCount = (int) Math.sqrt(array.length);
    List<List<Comparable>> buckets = new ArrayList<>(bucketCount);

    for (int i = 0; i < bucketCount; i++) 
      buckets.add(new ArrayList<>());
    
    Comparable minValue = getMinValue(array);
    Comparable maxValue = getMaxValue(array);

    for (Comparable item : array) {
      int bucketIndex = getBucketIndex(item, minValue, maxValue, bucketCount);
      buckets.get(bucketIndex).add(item);
    }

    int index = 0;
    for (List<Comparable> bucket : buckets) {
      Collections.sort(bucket);
      for (Comparable item : bucket) {
        array[index++] = item;
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
