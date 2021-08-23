//给你两个二进制字符串，返回它们的和（用二进制表示）。 
//
// 输入为 非空 字符串且只包含数字 1 和 0。 
//
// 
//
// 示例 1: 
//
// 输入: a = "11", b = "1"
//输出: "100" 
//
// 示例 2: 
//
// 输入: a = "1010", b = "1011"
//输出: "10101" 
//
// 
//
// 提示： 
//
// 
// 每个字符串仅由字符 '0' 或 '1' 组成。 
// 1 <= a.length, b.length <= 10^4 
// 字符串如果不是 "0" ，就都不含前导零。 
// 
// Related Topics 位运算 数学 字符串 模拟 
// 👍 638 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public String addBinary(String a, String b) {
        if (a.equals("0")) {
            return b;
        } else if (b.equals("0")) {
            return a;
        }
        StringBuilder sb = new StringBuilder();
        int x = a.length() > b.length() ? a.length() : b.length();
        int y = 0;
        for (int i = 0; i < x; i++) {
            if (a.length() > i) {
                y += a.charAt(a.length() - 1 - i) - '0';
            }
            if (b.length() > i) {
                y += b.charAt(b.length() - i - 1) - '0';
            }
            sb.append(y % 2);
            y = y / 2;
        }
        if (y == 1) {
            sb.append(1);
        }
        return sb.reverse().toString();
    }
}
//leetcode submit region end(Prohibit modification and deletion)
