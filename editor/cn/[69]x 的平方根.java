//实现 int sqrt(int x) 函数。 
//
// 计算并返回 x 的平方根，其中 x 是非负整数。 
//
// 由于返回类型是整数，结果只保留整数的部分，小数部分将被舍去。 
//
// 示例 1: 
//
// 输入: 4
//输出: 2
// 
//
// 示例 2: 
//
// 输入: 8
//输出: 2
//说明: 8 的平方根是 2.82842..., 
//     由于返回类型是整数，小数部分将被舍去。
// 
// Related Topics 数学 二分查找 
// 👍 711 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public int mySqrt(int x) {
        if (x == 0) return 0;
        if (x < 4) {
            return 1;
        }

        int a = x / 2;
        int b = 2;
        while (b < a) {
            int tmp = (a + b) / 2;
            if ((long)tmp * tmp < (long)x) {
                if ((long)(tmp + 1) * (tmp + 1) > (long)x) {
                    return tmp;
                }
                b = tmp;
            } else if ((long)tmp * tmp == (long)x) {
                return tmp;
            } else {
                a = tmp;
            }


        }
        return b;

    }
}
//leetcode submit region end(Prohibit modification and deletion)
