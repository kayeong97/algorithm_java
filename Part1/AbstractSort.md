# Java를 이용한 정렬 알고리즘 구현

## Comparable 인터페이스

구현하는 클래스의 객체들간에 natural order를 정의<br>
**int compareTo()**를 구현해야 함<br>
a.compareTo(b)를 쓴다고 가정하였을 때 뭐가 더 작고 뭐가 더 큰건지를 알려줘야 함<br>

### 왜 Comparable 인터페이스를 쓸까?


## AbstaractSort
```java
public abstract class AbstractSort{
  public static void sort(Comparablep[] aJ) {};
