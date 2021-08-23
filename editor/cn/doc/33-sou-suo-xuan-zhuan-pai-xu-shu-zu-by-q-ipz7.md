方法一：遍历数组
```
var search = function(nums, target) {
    for(var i = 0;i < nums.length;i++){
        if(nums[i] === target){
            return i;
        }
    }
    return -1;
};
```
时间复杂度：O(n), 空间复杂度：O(1)。

方法二：二分查找
思路：
根据题目可知，该数组在**旋转之前**是**升序排列**的。
因此，将该数组任意分割成两段，其中一段**一定是升序**的，而另外一段可能是升序的，也可能是部分升序。
如果target在升序的那一部分里，则把搜索范围缩小到这一部分里面，直到left === right
最后再判断nums[left] === target
过程：
- 定义 `left = 0, right = nums.length - 1, mid = (left + right) / 2`
- 若 `nums[mid] === target`，直接返回mid
- 若 `nums[left] <= nums[mid]`，说明左边部分是升序的
    - 若 `nums[left] <= target && target <= nums[mid]`，说明target在升序这部分里面，那么 `right = mid - 1` 缩小范围
    - 否则 `left = mid + 1` 缩小范围
- 若是右边部分升序的
    - 若 `nums[mid] <= target && target <= nums[right]` 说明target在升序这部分里面，那么 `left = mid + 1` 缩小范围
    - 否则 `right = mid - 1` 缩小范围
- 若最后 `left === right` 跳出循环，则判断是否等于target： `nums[left] === target ? left : -1`
```
var search = function(nums, target) {
    if(nums.length == 0){
        return -1;
    }
    if(nums.length == 1){
        return nums[0] == target? 0 : -1;
    }

    var left = 0, right = nums.length - 1;
    while(left < right){
        var mid = parseInt((left + right) / 2);
        if(nums[mid] === target){
            return mid;
        }else{
            if(nums[left] <= nums[mid]){
                if(nums[left] <= target && target <= nums[mid]){
                    right = mid - 1;
                }else{
                    left = mid + 1;
                }
            }else{
                if(nums[mid + 1] <= target && target <= nums[right]){
                    left = mid + 1;
                }else {
                    right = mid - 1;
                }
            }
        }
    }
    return nums[left] === target ? left : -1;
};
```
时间复杂度：O(logn)
空间复杂度：O(1)

`说实在的，这两个方法打败人数的百分比没啥差别，甚至方法二还会比方法一还低，LeetCode的迷之后台`