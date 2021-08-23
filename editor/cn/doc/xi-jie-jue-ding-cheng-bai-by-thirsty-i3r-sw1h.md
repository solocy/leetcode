### 解题思路
递归：
![image.png](https://pic.leetcode-cn.com/1629354781-XNGpve-image.png)

for循环：
![image.png](https://pic.leetcode-cn.com/1629354917-Isqwcm-image.png)

思路非常简单 先处理 第一层和第二层 都只需要简单的存入1就ok
然后处理二层之后的 获取 List<List<Integer>> 里的最后一个List 将里面元素两两相加存入新List中 然后头尾插入1 获得一层结果 然后循环 到层数达到 要求 返回List<List<Integer>>

### 代码
递归：
```java
class Solution {
    public List<List<Integer>> generate(int numRows) {
       return No2(numRows,1,new ArrayList<>());
    }

    public static List<List<Integer>> No2(int numRows, int mark, List<List<Integer>> lists) {
        if (mark > numRows){
            return lists;
        }else {
            List<Integer> list = new ArrayList<>();
            if (mark <= 2){
                for (int j = 0; j < mark; j++) {
                    list.add(1);
                }
                lists.add(list);
            }else {
                List<Integer> integerList = lists.get(lists.size() - 1);
                list.add(1);
                for (int j = 0; j < integerList.size() - 1; j++) {
                    list.add(integerList.get(j) + integerList.get(j+1));
                }
                list.add(1);
                lists.add(list);               
            }
            return No2(numRows ,++mark ,lists);
        }
    }
}
```
for循环：
```java
List<List<Integer>> lists = new ArrayList<>();
        for (int i = 1; i <= numRows; i++) {
            if (lists.size() < 2){
                List<Integer> list = new ArrayList<>();
                for (int j = 0; j < i; j++) {
                    list.add(1);
                }
                lists.add(list);
            }else {
                List<Integer> integerList = lists.get(lists.size() - 1);
                List<Integer> list = new ArrayList<>();
                list.add(1);
                for (int j = 0; j < integerList.size() - 1; j++) {
                    list.add(integerList.get(j) + integerList.get(j+1));
                }
                list.add(1);
                lists.add(list);
            }
        }
        return lists;
```