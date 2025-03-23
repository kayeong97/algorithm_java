# Java를 이용한 정렬 알고리즘 구현

## Comparable 인터페이스

구현하는 클래스의 객체들간에 natural order를 정의<br>
**int compareTo()**를 구현해야 함<br>
a.compareTo(b)를 쓴다고 가정하였을 때 뭐가 더 작고 뭐가 더 큰건지를 알려줘야 함<br>
![Comparable 인터페이스](https://github.com/kayeong97/algorithm_java/blob/main/Part1/internalsort/Comparable.png)
[Comparable 인터페이스 공식 문서](https://docs.oracle.com/en/java/javase/14/docs/api/java.base/java/lang/Comparable.html)
여기에 보면 Comparable<T> 인터페이스에 대한 자세한 설명이 나옴<br>
<T>는 제네럴 타입을 의미하는데 원래는 각 타입별로 sort함수를 구현해야 했지만 제네럴 타입을 쓰게 되면서 하나의 함수만 정의하면 됨

## AbstaractSort
```java
public abstract class AbstractSort{
  // 정렬 알고리즘 구현
  public static void sort(Comparablep[] a) {};

  // 작은지 비교하는 함수
  protected static boolean less(Comparable v, Comparable w) 
  { return v.compareTo(w) < 0; }

  // 배열 내에서 위치를 swap해주는 함수
  protected static void exch(Comparable[] a, int i, int j) 
  { Comparable t = a[i]; a[i] = a[j];a[j] = t; }

  // 배열을 보여주는 함수
  protected static void show(Comparable[] a) {
   for (int i = 0; i < a.length; i++) System.out.print(a[i] + " ");
   System.out.println();
 }

 // 배열이 정렬되어 있는지 확인하는 함수
 protected static boolean isSorted(Comparable[] a) {
   for (int i = 1; i < a.length; i++)
     if (less(a[i], a[i-1])) return false;
   return true;
 }
```
우리가 구현 해야 하는건 sort 저 부분.
