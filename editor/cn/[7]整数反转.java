//给你一个 32 位的有符号整数 x ，返回将 x 中的数字部分反转后的结果。 
//
// 如果反转后整数超过 32 位的有符号整数的范围 [−231, 231 − 1] ，就返回 0。 
//假设环境不允许存储 64 位整数（有符号或无符号）。
//
// 
//
// 示例 1： 
//
// 
//输入：x = 123
//输出：321
// 
//
// 示例 2： 
//
// 
//输入：x = -123
//输出：-321
// 
//
// 示例 3： 
//
// 
//输入：x = 120
//输出：21
// 
//
// 示例 4： 
//
// 
//输入：x = 0
//输出：0
// 
//
// 
//
// 提示： 
//
// 
// -231 <= x <= 231 - 1 
// 
// Related Topics 数学 
// 👍 2736 👎 0

import java.util.Stack;

//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public int reverse(int x) {
        if (Math.abs(x) == 2L << 32) {
            return 0;
        }
        boolean abs = false;
        if (x < 0) {
            abs = true;
            x = Math.abs(x);
        }
        Stack stack = new Stack();
        String s = String.valueOf(x);
        for (char aByte : s.toCharArray()) {
            stack.push(aByte);
        }
        String val = "";
        while (!stack.isEmpty()) {
            char b = (char) stack.pop();
            val = val + b;
        }
        if (Long.valueOf(val).compareTo(2L << 31) >= 0) {
            return 0;
        }
        int result = Integer.parseInt(val);
        if (abs) {
            result = -result;
        }
        return result;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
