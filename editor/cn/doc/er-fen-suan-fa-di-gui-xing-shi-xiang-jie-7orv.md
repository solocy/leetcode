### 解题思路
寻找target元素的最左位置和最右位置
在当前数组寻找两个位置，可以划分为在左子数组寻找或在右子数组寻找

1、首先判断当前nums[mid]是否等于target，若等于，则与当前记录的left，right比较

- 比left小,则更新left
- 比right大,则更新right

2、若nums[mid]小于等于target

- nums[mid] < target ,则需要在右子数组更新left,right
- nums[mid] == target ,因为不清楚右侧是否还有与target相同的元素，也需要在右子数组更新left,right

3、若nums[mid]大于等于target
- nums[mid] > target ,则需要在左子数组更新left,right
- nums[mid] == target ,因为不清楚左侧是否还有与target相同的元素，也需要在左子数组更新left,right

最终返回结果


### 代码

```java
class Solution {
    int left = Integer.MAX_VALUE;
    int right = Integer.MIN_VALUE;;
    public int[] searchRange(int[] nums, int target) {

        int len = nums.length;
        if(len == 0) return new int[]{-1,-1};

        searchIndex(nums, target, 0, len-1);

        if(left == Integer.MAX_VALUE){
            left = -1;
        }

        if(right == Integer.MIN_VALUE){
            right = -1;
        }

        return new int[]{left,right};

    }

    public void searchIndex(int[] nums, int target,int start,int end){

        int mid = start + ((end-start)>>1);
        //返回条件
        if(start > end){
            return;
        }

        if(nums[mid] == target){
            //若比left小，更新left
            if(mid < left){
                left = mid;
            }
            //若比right大,更新right
            if(mid > right){
                right = mid;
            }
        }

        //在右子数组
        if(nums[mid] <= target){
            searchIndex(nums, target, mid+1, end);
        }
        //在左子数组
        if(nums[mid] >= target){
            searchIndex(nums, target, start, mid-1);
        }
    
        return;
    }
}
```