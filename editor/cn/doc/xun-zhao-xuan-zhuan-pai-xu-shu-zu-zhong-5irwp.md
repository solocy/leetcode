#### 方法一：二分查找

**思路与算法**

一个不包含重复元素的升序数组在经过旋转之后，可以得到下面可视化的折线图：

![fig1](https://assets.leetcode-cn.com/solution-static/153/1.png)

其中横轴表示数组元素的下标，纵轴表示数组元素的值。图中标出了最小值的位置，是我们需要查找的目标。

我们考虑**数组中的最后一个元素 *x***：在最小值右侧的元素（不包括最后一个元素本身），它们的值一定都严格小于 *x*；而在最小值左侧的元素，它们的值一定都严格大于 *x*。因此，我们可以根据这一条性质，通过二分查找的方法找出最小值。

在二分查找的每一步中，左边界为 ![\itlow ](./p__it_low_.png) ，右边界为 ![\ithigh ](./p__it_high_.png) ，区间的中点为 ![\itpivot ](./p__it_pivot_.png) ，最小值就在该区间内。我们将中轴元素 ![\textit{nums}\[\textit{pivot}\] ](./p__textit{nums}_textit{pivot}__.png)  与右边界元素 ![\textit{nums}\[\textit{high}\] ](./p__textit{nums}_textit{high}__.png)  进行比较，可能会有以下的三种情况：

第一种情况是 ![\textit{nums}\[\textit{pivot}\]<\textit{nums}\[\textit{high}\] ](./p__textit{nums}_textit{pivot}____textit{nums}_textit{high}__.png) 。如下图所示，这说明 ![\textit{nums}\[\textit{pivot}\] ](./p__textit{nums}_textit{pivot}__.png)  是最小值右侧的元素，因此我们可以忽略二分查找区间的右半部分。

![fig2](https://assets.leetcode-cn.com/solution-static/153/2.png)

第二种情况是 ![\textit{nums}\[\textit{pivot}\]>\textit{nums}\[\textit{high}\] ](./p__textit{nums}_textit{pivot}____textit{nums}_textit{high}__.png) 。如下图所示，这说明 ![\textit{nums}\[\textit{pivot}\] ](./p__textit{nums}_textit{pivot}__.png)  是最小值左侧的元素，因此我们可以忽略二分查找区间的左半部分。

![fig3](https://assets.leetcode-cn.com/solution-static/153/3.png)

由于数组不包含重复元素，并且只要当前的区间长度不为 *1*，![\itpivot ](./p__it_pivot_.png)  就不会与 ![\ithigh ](./p__it_high_.png)  重合；而如果当前的区间长度为 *1*，这说明我们已经可以结束二分查找了。因此不会存在 ![\textit{nums}\[\textit{pivot}\]=\textit{nums}\[\textit{high}\] ](./p__textit{nums}_textit{pivot}__=_textit{nums}_textit{high}__.png)  的情况。

当二分查找结束时，我们就得到了最小值所在的位置。

```C++ [sol1-C++]
class Solution {
public:
    int findMin(vector<int>& nums) {
        int low = 0;
        int high = nums.size() - 1;
        while (low < high) {
            int pivot = low + (high - low) / 2;
            if (nums[pivot] < nums[high]) {
                high = pivot;
            }
            else {
                low = pivot + 1;
            }
        }
        return nums[low];
    }
};
```

```Java [sol1-Java]
class Solution {
    public int findMin(int[] nums) {
        int low = 0;
        int high = nums.length - 1;
        while (low < high) {
            int pivot = low + (high - low) / 2;
            if (nums[pivot] < nums[high]) {
                high = pivot;
            } else {
                low = pivot + 1;
            }
        }
        return nums[low];
    }
}
```

```Python [sol1-Python3]
class Solution:
    def findMin(self, nums: List[int]) -> int:    
        low, high = 0, len(nums) - 1
        while low < high:
            pivot = low + (high - low) // 2
            if nums[pivot] < nums[high]:
                high = pivot 
            else:
                low = pivot + 1
        return nums[low]
```

```C [sol1-C]
int findMin(int* nums, int numsSize) {
    int low = 0;
    int high = numsSize - 1;
    while (low < high) {
        int pivot = low + (high - low) / 2;
        if (nums[pivot] < nums[high]) {
            high = pivot;
        } else {
            low = pivot + 1;
        }
    }
    return nums[low];
}
```

```golang [sol1-Golang]
func findMin(nums []int) int {
    low, high := 0, len(nums) - 1
    for low < high {
        pivot := low + (high - low) / 2
        if nums[pivot] < nums[high] {
            high = pivot
        } else {
            low = pivot + 1
        }
    }
    return nums[low]
}
```

```JavaScript [sol1-JavaScript]
var findMin = function(nums) {
    let low = 0;
    let high = nums.length - 1;
    while (low < high) {
        const pivot = low + Math.floor((high - low) / 2);
        if (nums[pivot] < nums[high]) {
            high = pivot;
        } else {
            low = pivot + 1;
        }
    }
    return nums[low];
};
```

**复杂度分析**

* 时间复杂度：时间复杂度为 ![O(\logn) ](./p__O_log_n__.png) ，其中 *n* 是数组 ![\textit{nums} ](./p__textit{nums}_.png)  的长度。在二分查找的过程中，每一步会忽略一半的区间，因此时间复杂度为 ![O(\logn) ](./p__O_log_n__.png) 。

* 空间复杂度：*O(1)*。