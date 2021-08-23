```
class Solution {
    public List<List<Integer>> generate(int numRows) {
        List<List<Integer>> list = new ArrayList<>();
        List<Integer> l = new ArrayList<>();
        for (Integer i = 1; i <= numRows; i++) {
            List<Integer> li = new ArrayList<>();
            for (Integer j = 1; j <= i; j++) {
                if (j == 1 || j.equals(i)) {
                    li.add(1);
                } else {
                    li.add(l.get(j - 2) + l.get(j - 1));
                }
            }
            l = li;
            list.add(li);
        }
        return list;
    }
}
```

![image.png](https://pic.leetcode-cn.com/1628586585-rLSxGF-image.png)

1. 每行的列数与第几行相同，可以写出框架（i为行数，j为列数<即为每行的第几个数>）
```
        for (Integer i = 1; i <= numRows; i++) {
            for (Integer j = 1; j <= i; j++) {
            
        }
```

2. 每行第一个数与最后一个数都是1，其余数为上行对应两数之和（每次遍历完一行之后，将数组li的值赋值给数组l，以便下行调用）
```
        if (j == 1 || j.equals(i)) {
            li.add(1);
        } else {
            li.add(l.get(j - 2) + l.get(j - 1));
        }
```

3. 然后···我发现就可以了