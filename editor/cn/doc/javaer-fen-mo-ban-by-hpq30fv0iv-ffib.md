### 解题思路
这是一道非常简单的二分模板。
我们先来查找左边界，左边界很容易查找，也就是**从左往右找到的第一个不小于x的数**
①首先确定分界点`mid`，`mid`为区间的中点`mid = l + r >> 1;`
②然后再判断`nums[mid]`是否小于`target`，小于则代表答案在右边且不包含`mid`这个点，否则就代表答案在左边且包含`mid`这个点
③接下来继续下一次查找，直到`l >= r`，跳出循环
查找右边界和左边界差不多，也就是**从左往右找到的第一个不大于x的数**
①首先确定分界点`mid`，`mid`为区间的中点`mid = l + r + 1 >> 1;`（加1为了防止死循环）
②然后判断`nums[mid]`是否小于等于`target`，小于等于则代表答案在右边且包含`mid`这个点，否则就代表答案在左边且不包含`mid`这个点
③最后继续下一次查找，直到`l >= r`，跳出循环
### 代码

```java
class Solution {
    public int[] searchRange(int[] nums, int target) {
        if(nums.length == 0)
            return new int[]{-1, -1};
        int l = 0, r = nums.length - 1;
        while(l < r) {
            int mid = r + l >> 1;
            if(nums[mid] >= target) 
                r = mid;        //代表答案在左边且包含mid这个点
            else
                l = mid + 1;    //代表答案在右边且不包含mid这个点
        }
        int left = l;
        if(nums[left] != target)    //如果target在数组不存在
            return new int[]{-1, -1};
        else {
            l = 0;
            r = nums.length - 1;
            while(l < r) {
                int mid = r + l + 1 >> 1;   //加1为了防止死循环
                if(nums[mid] <= target)
                    l = mid;        //代表答案在右边且包含mid这个点
                else 
                    r = mid - 1;    //代表答案在左边且不包含mid这个点
            }
            return new int[]{left, r};
        }
    }
}  
```
### 复杂度分析
- 时间复杂度：O(logn)
- 空间复杂度：O(1)