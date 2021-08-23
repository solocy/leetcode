### 解题思路
![image.png](https://pic.leetcode-cn.com/1622087727-LBqoju-image.png)

从尾部开始遍历一下
### 代码

```java
class Solution {
    public int lengthOfLastWord(String s) {
        int length = 0;
        for(int i = s.length() - 1; i >= 0; i--){
            if(length != 0 && s.charAt(i) == ' ') break;
            else if(s.charAt(i) == ' ') ;
            else length++;
        }
        return length;
    }
}
```