### 解题思路
很简单，找规律即可有思路

### 代码

```java
class Solution {
    public List<List<Integer>> generate(int numRows) {
        List<List<Integer>> externalList = new ArrayList<List<Integer>>();
        for(int i = 0;i < numRows;i++){
            List<Integer> internalList = new ArrayList<Integer>();
            for(int j = 0;j <= i;j++){
                if(j == 0 || j == i){
                    internalList.add(1);
                }
                else{
                    internalList.add(externalList.get(i-1).get(j-1)+externalList.get(i-1).get(j));
                }
            }
            externalList.add(internalList);
        }
        return externalList;
    }
}
```