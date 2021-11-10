#### 方法：栈

**思路与算法**

让我们在处理数据时保持栈上每个有效回合的值。栈是理想的，因为我们只处理涉及最后或倒数第二轮的操作。

```java [hfYY8zrG-Java]
class Solution {
    public int calPoints(String[] ops) {
        Stack<Integer> stack = new Stack();

        for(String op : ops) {
            if (op.equals("+")) {
                int top = stack.pop();
                int newtop = top + stack.peek();
                stack.push(top);
                stack.push(newtop);
            } else if (op.equals("C")) {
                stack.pop();
            } else if (op.equals("D")) {
                stack.push(2 * stack.peek());
            } else {
                stack.push(Integer.valueOf(op));
            }
        }

        int ans = 0;
        for(int score : stack) ans += score;
        return ans;
    }
}
```
```python [hfYY8zrG-Python]
class Solution(object):
    def calPoints(self, ops):
        stack = []
        for op in ops:
            if op == '+':
                stack.append(stack[-1] + stack[-2])
            elif op == 'C':
                stack.pop()
            elif op == 'D':
                stack.append(2 * stack[-1])
            else:
                stack.append(int(op))

        return sum(stack)
```


**复杂度分析**

* 复杂度分析：*O(N)*，其中 *N* 是 `ops` 的长度。我们解析给定数组中的每个元素，然后每个元素执行 *O(1)* 的工作。

* 空间复杂度：*O(N)*，用于存储 `stack` 的空间。