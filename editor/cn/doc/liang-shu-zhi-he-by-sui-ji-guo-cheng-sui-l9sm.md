### 解题思路1
&emsp;&emsp;直接进行暴力解题，遍历数组中的每一个元素，检查数组中是否有一个数能够使得两数之和满足要求。这样是通过时间换空间的方法进行的，时间复杂度高达O(*n^2*)。如何能够提高代码的运行速度————使用哈希表。

### 代码1

```java
class Solution {
    public int[] twoSum(int[] nums, int target) {
        int n = nums.length;
        for(int i = 0; i < n; i++){
            for (int j = i + 1; j < n; j++){
                if(nums[i] + nums[j] == target){
                    return new int[]{i,j};
                }
            }
        }
        return new int[0];
    }
}
```

### 解题思路2
&emsp;&emsp;使用哈希表，使用哈希表能够降低时间复杂度，具体思路如下，将数组中的每个元素作为key，索引值作为value，对哈希表中的元素进行遍历，判断是否存在这样两个数满足题意。此时，时间复杂度为O(N)，空间复杂度也为O(N)。

### 代码2

```java
class Solution {
    public int[] twoSum(int[] nums, int target) {
        int n = nums.length;
        // 用HashMap来存储数据，其中key是数组中的元素，value为数组中元素的索引值
        HashMap<Integer, Integer> index = new HashMap<>();
        for(int i = 0; i < n; i++) {
            index.put(nums[i], i);
        }
        for(int i = 0; i < n; i++) {
            int other = target - nums[i];
            if(index.containsKey(other) && index.get(other) != i) {
                return new int[]{i, index.get(other)};
            }
        }
        return new int[0];
    }
}
```

### 解后反思
&emsp;&emsp;写了两种方法，一种是暴力解法，另外一种是使用哈希表这种数据结构。程序看起来都是进行了遍历，检查是否存在这样两个元素，为何要使用哈希表呢？注意一个前提：

- **数组是无序的**

&emsp;&emsp;在这种情况下，使用哈希表能够为我们提供一种新的解决思路，不需要过高的时间复杂度（暴力解法的双层循环），也可以理解为通过哈希表进行**时间复杂度和空间复杂度的一个均衡**。