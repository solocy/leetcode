//编写一个函数来查找字符串数组中的最长公共前缀。 
//
// 如果不存在公共前缀，返回空字符串 ""。 
//
// 
//
// 示例 1： 
//
// 
//输入：strs = ["flower","flow","flight"]
//输出："fl"
// 
//
// 示例 2： 
//
// 
//输入：strs = ["dog","racecar","car"]
//输出：""
//解释：输入不存在公共前缀。 
//
// 
//
// 提示： 
//
// 
// 0 <= strs.length <= 200 
// 0 <= strs[i].length <= 200 
// strs[i] 仅由小写英文字母组成 
//  nbn b
// Related Topics 字符串 
// 👍 1583 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public String longestCommonPrefix(String[] strs) {

        if (strs == null || strs.length == 0) {
            return "";
        }
        char s='0';
        int length = 0;
        while (true) {
            for (String str : strs) {
                if (str.length() <=length ){
                    if (length == 0 ) {
                        return "";
                    }
                    return strs[0].substring(0,length);
                }else if (s != '0'){
                    if (str.charAt(length) == s) {
                        s= str.charAt(length);
                    } else {
                        if (length == 0 ) {
                            return "";
                        }
                        return strs[0].substring(0,length);
                    }
                } else {
                    s = str.charAt(length);
                }
            }
            length++;
            s='0';
        }
    }
}
//leetcode submit region end(Prohibit modification and deletion)
