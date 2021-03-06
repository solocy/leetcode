
**说明**：这篇题解专治二分疑难杂症。

大家不要看篇幅多，这是因为我做了很多问题，所以总结出了这么多需要注意的地方。掌握了二分查找算法的朋友，也绝对不是因为看了我写的二分才真正掌握的，更关键的原因是他们把二分查找的思想进行了足够多的应用。事实上，二分查找法需要学习的、和需要注意的事项并不多，而且很多细节完全不需要记忆。和学习绝大多数算法与数据结构知识一样，多练习、多思考，就能很好地掌握它们。

为了讲清楚我想说的部分，文字部分有点多。实在不想看那么多文字的朋友，可以只看第 3 部分「把待搜索区间分成两个部分」，并且把第 7 部分给出的「练习」做一下。相信会对大家有帮助。

> **掌握二分查找（甚至是其它的算法和数据结构）不可以只看别人写的题解，一定要自己多加练习，配合上自己的思考和总结，才可能做到融会贯通，举一三反**。

想看「第 35 题」代码的朋友，可以直接跳到「4. 例题讲解：「力扣」第 35 题：搜索插入位置」。

---

### 0. 前言

「二分查找」作为一种基础算法，本不该很难，所以希望借这道题的讲解和大家谈谈如何学习算法：

+ 「二分查找」就那么几行代码，我们完全有理由充分掌握它，而不可以用记忆模板、背例题的方式；
+ 「二分查找」虽然看起来有很多种写法，「递归」和「非递归」，「非递归」又有好几种写法：`while (left <= right)`、`while (left < right)`、`while (left + 1 < right)`。但核心的思想就一个：逐渐缩小问题规模。我们在学习和练习的时候需要 **首先着眼于掌握算法的思想，而不该去纠结二分的几种写法的区别和细节，这样会让自己更乱**；
+ 在面对问题的时候，应该将主要精力放在 **如何分析，利用单调性（绝大多数二分查找问题利用的是单调性，也有一些例外）或者题目本身蕴含的可以逐渐缩小问题规模的特性解决问题**，而不应该纠结在「二分查找」该怎么写。

下面开始正文。

---

### 1. 二分查找的基本问题

二分查找的基本问题是「力扣」第 704 题：[二分查找](https://leetcode-cn.com/problems/binary-search/)。

**参考代码 1**：（第 704 题代码）

```Java []
class Solution {
    public int search(int[] nums, int target) {
        int len = nums.length;
        int left = 0;
        int right = len - 1;
        // 在 [left..right] 里查找 target
        while (left <= right) {
            // 为了防止 left + right 整形溢出，写成这样
            int mid = left + (right - left) / 2;
            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] > target) {
                // 下一轮搜索区间：[left..mid - 1]
                right = mid - 1;
            } else {
                // 此时：nums[mid] < target，下一轮搜索区间：[mid + 1..right]
                left = mid + 1;
            }
        }
        return -1;
    }
}
```

**说明**：

二分查找的思路是：根据待搜索区间里的中间元素 `nums[mid]` 与 `target` 的值的大小关系，判断下一轮搜索需要在哪个区间里查找，进而设置 `left` 和 `right` 的值。分为如下三种情况：
+ 如果 `nums[mid] == target`，运气很好，找到了目标元素，返回 `mid` ；
+ 如果 `nums[mid] > target`，说明 `mid` 以及 `mid` 的 **右边** 的所有元素一定都比 `target` 大，下一轮搜索需要在区间 `[left..mid - 1]` 里查找，此时设置 `right = mid - 1`；
+ 如果 `nums[mid] < target`，说明 `mid` 以及 `mid` 的 **左边** 的所有元素一定都比 `target` 小，下一轮搜索需要在区间 `[mid + 1..right]` 里查找，此时设置 `left = mid + 1`。

退出循环的时候，说明区间里不存在目标元素，返回 *-1*。

---

### 2. 二分查找的问题变种

事实上，「力扣」上的「二分查找」问题没有那么简单。例如，让我们找：

+ 大于等于 `target` 的下标最小的元素；
+ 小于等于 `target` 的下标最大的元素。

这样的问题有一个特点：**当看到了 `nums[mid]` 恰好等于 `target` 的时候，还得继续查找**，继续看看左边的元素的值，或者继续看看右边元素的值。如果不小心，很可能逻辑写错。如果还用「1. 二分查找的基本问题」介绍的方式编写代码，就没有那么容易：

+ `while` 里面的 `if` 、`else` 该怎么写，有没有什么固定的思路？
+ 退出循环以后，`right` 在左，`left` 在右，返回 `left` 还是 `right` 需要分类讨论。

本题解要介绍的「二分查找」的思想其实不是什么新鲜的事儿。如下图所示，它像极了「双指针」算法，`left` 和 `right` 向中间走，直到它们重合在一起。

![image.png](https://pic.leetcode-cn.com/1617955110-eWCqKj-image.png)


这种二分查找的思考路径，不是我发明的（「参考资料」在题解最后）。我一开始看到这种写法也觉得很惊讶，也搞不明白到底怎么回事，但是我看到的解释就只有「这是模板」，但没有看到为什么有这个模板。
因此我 **尝试去了解它，并使用它**，然后整理成这篇题解。用这种二分查找的思路，可以解决「力扣」上 **所有的** 「二分查找」问题。

---

### 3. 把待搜索区间分成两个部分（重点、最重要的部分在这里）

根据看到的中间位置的元素的值 `nums[mid]` 可以把待搜索区间分为两个部分：

+ **一定不存在** 目标元素的区间：下一轮搜索的时候，不用考虑它；
+ **可能存在** 目标元素的区间：下一轮搜索的时候，需要考虑它。

由于 `mid` 只可能被分到这两个区间的其中一个，即：`while` 里面的 `if` 和 `else` 就两种写法：

+ 如果 `mid` 分到左边区间，即区间分成 `[left..mid]` 与 `[mid + 1..right]`，此时分别设置 `right = mid` 与 `left = mid + 1`；
+ 如果 `mid` 分到右边区间，即区间分成 `[left..mid - 1]` 与 `[mid..right]`，此时分别设置 `right = mid - 1` 与 `left = mid`。

并且把 **循环可以继续的条件** 写成 `while (left < right)`。**在上面把待搜索区间分成两个部分的情况下，退出循环以后一定会有 `left == right` 成立**，因此在退出循环以后，不需要考虑到底返回 `left` 还是返回 `right`。

这里介绍一个 「**重要的经验**」：

> 在 **写 `if` 语句的时候，通常把容易想到的，不容易出错的逻辑写在 `if` 的里面**，这样就把复杂的、容易出错的情况放在了 `else` 的部分，这样编写代码不容易出错。

什么情况是容易想到的，不容易出错的呢？我的经验是：题目要我们找符合条件 a 的元素，我们就对条件 a 取反面，这样分析不容易出错。

例如本题（「力扣」第 35 题），题目要我们找出第一个大于等于 `target` 的元素的下标，那么小于 `target` 的元素就一定不是我们要找的。因此 `if` 语句就是

```java
if (nums[mid] < target) {
	// 下一轮搜索区间是 [mid + 1..right]
	left = mid + 1;
}
```

剩下的情况放在 `else` 中，我们 **甚至可以不用分析 `else` 是什么情况**。`if` 的区间是 `[mid + 1..right]`，它的反面区间就是 `[left..mid]`，此时 `else` 就应该设置 `right = mid`。

因此完整的逻辑就是：

```java
if (nums[mid] < target) {
	// 下一轮搜索区间是 [mid + 1..right]
	left = mid + 1;
} else {
	right = mid;
}
```

上面的叙述，总结起来就一句话：我们 **总是在区间 `[left..right]` 里查找元素目标**。

**注意**：我们说的是 **左闭右闭区间**。为什么不是「左闭右开」呢？「左闭右开」当然可以，但是我们 **不想把精力花在思考「右边界是不是可以取到」这件事情上**，这是因为 **任意一个「左闭右开 `[left..right)` 」区间一定唯一对应一个「左闭右闭 `[left..right - 1]`」区间**，所以到底是开区间还是闭区间，前后保持一致就可以。

根据 `mid` 位置是不是目标元素，进而判断 `mid` 的左边是否存在目标元素，`mid` 的右边是否存在目标元素，只把搜索区间分为两个部分，然后设置 `left` 和 `right`，**在设置 `left` 和 `right` 的时候，左闭右闭区间的形式是最直观的，这是因为如果是开区间，还需要在脑子里反应一下，右端点不包括**。如果我们觉得二分问题复杂，复杂问题应该简单做。

我们再说说把区间分成两个部分的好处：

在代码层面，只可能有以下两种情况：

+ `while(left < right)` 与 `left = mid + 1` 、 `right = mid` 的搭配；
+ `while(left < right)` 与 `left = mid` 、 `right = mid - 1` 的搭配；

**只有在这两种把区间分成两个部分的划分下，退出循环以后有 `left == right` 成立，我们不用去讨论返回 `left` 还是 `right`（这句话非常重要，大家可以在做题的过程中慢慢体会）**。

> 补充说明：有的朋友觉得把区间分为三个部分更清晰，但是一旦分成三个部分，有 `mid + 1`、`mid - 1` 出现，退出循环以后就不一定有 `left` 和 `right` 重合。我们完全可以这样做，分类讨论的时候分成三个部分，然后把它们的逻辑合并起来。


在我们的讲解中 `while(left < right)` 与「定义的区间是 `[left..right)` 」没有任何关系，请大家不要做多余的解读，我们不讲循环不变量是 `[left..right)` 的情况，我们只认为区间是「左闭右闭」区间，理由上面也说了，每一个位置的值是不是可以取到，我们需要非常清楚，而不想看到一个开区间，我们在脑子里需要想一下「右端点不包括」。

**至于为什么是 `left = mid + 1` 、 `right = mid` 搭配使用，而 `left = mid` 、 `right = mid - 1` 搭配使用，这一点完全不用记忆**，我们画图说明。

![image.png](https://pic.leetcode-cn.com/1617857363-gTiArD-image.png)

因此我们再次和大家强调：永远去思考下一轮搜索应该在哪个区间里，就能考虑清楚到底下一轮更新的是 `left` 还是 `right` ，到底加不加 `1`，到底减不减 `1`。

---


### 4. 例题讲解：「力扣」第 35 题：搜索插入位置

**思路分析**：

根据示例 3：

```
输入: [1, 3, 5, 6], 7
输出: 4
```

如果目标元素大于输入数组中的最后一个元素，返回数组的最后一个元素的下标 + 1。

根据示例 2：

```
输入: [1,3,5,6], 2
输出: 1
```

需要返回第 1 个 **大于等于（等于的情况可以看示例 1，这里省略）** 目标元素 2 的下标，因此输出 1。因此 **如果当前 `mid` 看到的数值严格小于 `target`，那么 `mid` 以及 `mid` 左边的所有元素就一定不是题目要求的输出，就根据这一点可以写出本题二分查找算法的完整逻辑**。

**参考代码 2**：（第 35 题代码）

```Java []
public class Solution {

    public int searchInsert(int[] nums, int target) {
        int len = nums.length;
        // 特殊判断
        if (nums[len - 1] < target) {
            return len;
        }

        // 程序走到这里一定有 target <= nums[len - 1]
        int left = 0;
        int right = len - 1;
        // 在区间 nums[left..right] 里查找第 1 个大于等于 target 的元素的下标
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] < target){
                // 下一轮搜索的区间是 [mid + 1..right]
                left = mid + 1;
            } else {
                // 下一轮搜索的区间是 [left..mid]
                right = mid;
            }
        }
        return left;
    }
}
```

值得一提的是：由于执行到最后 `nums[left..right]` 里一定存在插入元素的位置，并且退出循环的时候一定有 `left == right` 成立，因此直接返回 `left` 或者 `right` 均可。

这样的思路还可以完成第 704 题。

**参考代码 3**：（第 704 题代码）

```Java []
class Solution {
    public int search(int[] nums, int target) {
        int len = nums.length;
        int left = 0;
        int right = len - 1;
        // 在 nums[left..right] 里查找 target
        while (left < right) {
            // 为了防止 left + right 整形溢出，写成这样
            int mid = left + (right - left) / 2;
            if (nums[mid] < target) {
                // 下一轮搜索区间：[mid + 1..right]
                left = mid + 1;
            } else {
                // 下一轮搜索区间：[left..mid]
                right = mid;
            }
        }
        
        if (nums[left] == target){
            return left;
        }
        return -1;
    }
}
```
```Java []
class Solution {
    public int search(int[] nums, int target) {
        int len = nums.length;
        int left = 0;
        int right = len - 1;
        // 在 nums[left..right] 里查找 target
        while (left < right) {
            // 为了防止 left + right 整形溢出，写成这样
            int mid = left + (right - left + 1) / 2;
            if (nums[mid] > target) {
                // 下一轮搜索区间：[left..mid - 1]
                right = mid - 1;
            } else {
                // 下一轮搜索区间：[mid..right]
                left = mid;
            }
        }
        
        if (nums[left] == target){
            return left;
        }
        return -1;
    }
}
```

+ 退出循环的时候，我们不能确定 `nums[left]` 是否等于 `target`，因此还需要单独做一次判断；
+ 「选项卡二」我们还给出了一版代码，具体细节大家可以在阅读完本题解以后再来理解它。

---

### 5. 两种写法对比

**写法 1**： `while(left <= right)` 这种写法可以认为我们在循环体内部直接查找元素，把当前搜索区间分为三个部分。

程序开始的时候，我们对输入数组一无所知。

![image.png](https://pic.leetcode-cn.com/1617784079-KQhLcv-image.png)

我们选择 `nums[mid]` 的值看一下。

![image.png](https://pic.leetcode-cn.com/1617784103-DNDPJy-image.png)

如果中间位置的元素的值等于目标元素就直接返回。如果中间位置的元素不是目标元素，可以根据中间位置元素的值决定在中间位置的左边还是右边继续查找。这种查找方式把待搜索区间分为三个部分：左、中、右。

![image.png](https://pic.leetcode-cn.com/1617784162-XnzfeM-image.png)

重要经验：**如果我们要找的元素性质非常明确、并且简单，通常这样写就可以。例如「力扣」第 704 题（[二分查找](https://leetcode-cn.com/problems/binary-search)），第 633 题（[平方数之和](https://leetcode-cn.com/problems/sum-of-square-numbers)）**。


**写法 2**：`while(left < right)` 这种写法表示在循环体内部排除元素，把当前搜索区间分为两个部分。

**这种思路可以很形象地理解为「两边夹」，在解决复杂问题的时候，会使得思考的过程变得简单**。

程序开始的时候，我们对输入数组一无所知（这一步和写法 1 一样）。

![image.png](https://pic.leetcode-cn.com/1617784079-KQhLcv-image.png)

我们选择 `nums[mid]` 的值看一下（这一步和写法 1 一样）。

![image.png](https://pic.leetcode-cn.com/1617784103-DNDPJy-image.png)

然后我们只分析：**在什么情况下，目标元素位于哪个区间里**，把区间分成「一定不存在目标元素的区间」和「可能存在目标元素的区间」。而 `mid` 只可能位于这两个区间的其中一个，可以分为下面 4 种情况。

![image.png](https://pic.leetcode-cn.com/1617786936-PtTKbf-image.png)

虽然看起来比较多，但是归结起来就 2 种情况：`mid` 在左边区间和 `mid` 在右边区间。即：根据 `mid` 的值，可以判断 `nums[mid]` 是「一定不是目标元素」还是「有可能是目标元素」，进而判断 `mid` 的左右两边的区间的性质。

**重要经验**：

如果我们要找的元素的性质比较复杂：例如需要满足「条件 1」并且「条件 2」。我们在编写 `if` 语句的时候，就可以把其中一个条件取反，就可以达到缩减搜索区间的目的。

这一点也不难想明白，「条件 1」并且「条件 2」的反面就是「取反条件 1」**或者**「取反条件 2」。再举一个具体的例子：例如我们要找一个数 *x*，需要满足 ![4\lex\le9 ](./p__4_le_x_le_9_.png) ，即 ![x\ge4 ](./p__x_ge_4_.png)  并且 ![x\le9 ](./p__x_le_9_.png) 。如果我们看到一个数小于 *4*，我们就知道此时需要在当前位置的右边继续查找，可以得到缩减问题区间的目的。

「力扣」第 4 题（[寻找两个正序数组的中位数](https://leetcode-cn.com/problems/median-of-two-sorted-arrays/)）的 [视频题解](https://leetcode-cn.com/problems/median-of-two-sorted-arrays/solution/xun-zhao-liang-ge-you-xu-shu-zu-de-zhong-wei-s-114/)，我们详细介绍了用这种思路分析问题的方法，并给出了 2 版等价的代码。

---

### 6. 一些细节（以问答形式呈现）

#### 细节一：为什么有些时候取 `mid` 的时候需要上取整？

**回答**：是否需要上取整，只和区间划分的逻辑有关。如果不调整，会出现死循环。

![image.png](https://pic.leetcode-cn.com/1617778514-OzKyCg-image.png)

初次接触的时候，很多朋友觉得这件事情非常难以理解，我们的建议是：**一旦遇到死循环，可以在程序中输出 `left`、`right` 和 `mid` 的值看一下就很清楚了**。「力扣」第 69 题：[x 的平方根](https://leetcode-cn.com/problems/sqrtx/) 的 [题解](https://leetcode-cn.com/problems/sqrtx/solution/er-fen-cha-zhao-niu-dun-fa-python-dai-ma-by-liweiw/) ，我们演示了如何打印变量、观察到死循环。

**结论**：当区间只剩下两个元素的时候，`left = mid` 和 `right = mid - 1` 这种划分方式，如果 `mid` 使用默认下取整的方式，在数值上 `left = mid`，而它对应的其中一个区间是 `[mid..right]`，在这种情况下，下一轮搜索区间还是 `[left..right]`，搜索区间没有减少，会进入死循环。

**提示**：「**看到边界设置的代码是 `left = mid` 时，需要把 `mid` 的取法调整为上取整，以避免死循环**」，这件事情也完全不用记忆，题目做得多了，自然而然就记住了。还是我们在题解中和大家多次强调的一件事情：**遇到代码出错的时候，一定要耐心调试，把变量打印出来看一下，是最好的学习的方法**。

#### 细节二：有些学习资料上说 `while (left < right)` 表示区间是 `[left..rihgt)` ，为什么你这里是 `[left..rihgt]`？

**回答**：区间的右端点到底是开还是闭，完全由编写代码的人决定，不需要固定。只要编码的人逻辑前后一致，并且保持不变（这件事情叫遵守「循环不变量」），就是正确的。

我们通篇讲的都是 **左闭右闭** 区间，理由是这样定义更直接。

#### 细节三：有些学习资料给出了三种模板，例如「力扣」推出的 LeetBook 之 「[二分查找专题](https://leetcode-cn.com/leetbook/detail/binary-search/)」，应该如何看待它们？

**回答**：我在学习的时候，LeetBook 也是我重要的学习材料之一。三种模板其实区别仅在于退出循环的时候，区间 `[left..right]` 里有几个数。

+ `while (left <= right)` ：退出循环的时候，`right` 在左，`left` 在右，区间为空区间，所以要讨论返回 `left` 和 `right`；
+ `while (left < right)` ：退出循环的时候，`left` 与 `right` 重合，区间里只有一个元素，这一点是我们很喜欢的；
+ `while (left + 1< right)` ：退出循环的时候，`left` 在左，`right` 在右，区间里有 2 个元素，需要编写专门的逻辑。这种写法在设置 `left` 和 `right` 的时候不需要加 1 和减 1。看似简化了思考的难度，但实际上屏蔽了我们应该且完全可以分析清楚的细节。退出循环以后一定需要编写专门的逻辑，讨论返回哪一个，也增加了编码的难度。

我个人的经验是：完全不用第三个，理由是不会使得问题变得更简单，反而很累赘。

我常用头两个，第一个在简单问题中用，第二个在复杂问题中用。在题解的第 5 部分「5. 两种写法对比」的「重要经验」里也叙述了用它们的理由和场景。

---

### 7. 练习

**提示**：这些问题都不应该当做模板问题来看待，逻辑严密的分析与思考是更关键的。我们针对这些问题都提供了题解，重点分析了应该如何思考，讲解了如何编写正确的代码，希望能够对大家有所帮助。

#### 题型一：二分下标（在数组中查找符合条件的元素的下标）

一般而言这个数组是有序的，也可能是半有序的（旋转有序数组或者山脉数组）。

| 题目                                                         | 题解                                                         | 说明                                                         |
| ------------------------------------------------------------ | ------------------------------------------------------------ | ------------------------------------------------------------ |
| [704. 二分查找（简单）](https://leetcode-cn.com/problems/binary-search/) |                                                              | 二分查找的最原始问题，使用本题解介绍的方法就要注意，需要后处理。 |
| [34. 在排序数组中查找元素的第一个和最后一个位置（中等）](https://leetcode-cn.com/problems/find-first-and-last-position-of-element-in-sorted-array/) | [文字题解](https://leetcode-cn.com/problems/find-first-and-last-position-of-element-in-sorted-array/solution/si-lu-hen-jian-dan-xi-jie-fei-mo-gui-de-er-fen-cha/)、[视频题解](https://leetcode-cn.com/problems/find-first-and-last-position-of-element-in-sorted-array/solution/zai-pai-xu-shu-zu-zhong-cha-zhao-yuan-su-de-di-3-4/) | 查找边界问题。                                               |
| [33. 搜索旋转排序数组（中等）](https://leetcode-cn.com/problems/search-in-rotated-sorted-array/) | [文字题解](https://leetcode-cn.com/problems/search-in-rotated-sorted-array/solution/er-fen-fa-python-dai-ma-java-dai-ma-by-liweiwei141/) | 利用局部单调性，逐步缩小搜索区间（其它问题类似）。           |
| [81. 搜索旋转排序数组 II（中等）](https://leetcode-cn.com/problems/search-in-rotated-sorted-array-ii/) | [文字题解](https://leetcode-cn.com/problems/search-in-rotated-sorted-array-ii/solution/er-fen-cha-zhao-by-liweiwei1419/) |                                                              |
| [153. 寻找旋转排序数组中的最小值（中等）](https://leetcode-cn.com/problems/find-minimum-in-rotated-sorted-array/) | [文字题解](https://leetcode-cn.com/problems/find-minimum-in-rotated-sorted-array/solution/er-fen-fa-fen-zhi-fa-python-dai-ma-java-dai-ma-by-/) |                                                              |
| [154. 寻找旋转排序数组中的最小值 II（中等）](https://leetcode-cn.com/problems/find-minimum-in-rotated-sorted-array-ii/) | [文字题解](https://leetcode-cn.com/problems/find-minimum-in-rotated-sorted-array-ii/solution/er-fen-fa-fen-zhi-fa-python-dai-ma-by-liweiwei1419/) |                                                              |
| [300. 最长上升子序列（中等）](https://leetcode-cn.com/problems/longest-increasing-subsequence/) | [文字题解](https://leetcode-cn.com/problems/longest-increasing-subsequence/solution/dong-tai-gui-hua-er-fen-cha-zhao-tan-xin-suan-fa-p/)。 | 特别经典的一道「动态规划」，二分查找的思路基于「动态规划」的状态定义得到，代码很像第 35 题。 |
| [275. H指数 II（中等）](https://leetcode-cn.com/problems/h-index-ii/) | [文字题解](https://leetcode-cn.com/problems/h-index-ii/solution/jian-er-zhi-zhi-er-fen-cha-zhao-by-liweiwei1419-2/) | 这个问题题目的描述让人迷惑，可以跳过不做。                   |
| [852. 山脉数组的峰顶索引（简单）](https://leetcode-cn.com/problems/peak-index-in-a-mountain-array/) |                                                              | 利用局部单调性，逐步缩小搜索区间。                           |
| [1095. 山脉数组中查找目标值（中等）](https://leetcode-cn.com/problems/find-in-mountain-array/) | [文字题解](https://leetcode-cn.com/problems/find-in-mountain-array/solution/shi-yong-chao-hao-yong-de-er-fen-fa-mo-ban-python-/)、[视频题解](https://leetcode-cn.com/problems/find-in-mountain-array/solution/shan-mai-shu-zu-zhong-cha-zhao-mu-biao-zhi-by-leet/) |                                                              |
| [4. 寻找两个有序数组的中位数（困难）](https://leetcode-cn.com/problems/median-of-two-sorted-arrays/) | [文字题解](https://leetcode-cn.com/problems/median-of-two-sorted-arrays/solution/he-bing-yi-hou-zhao-gui-bing-guo-cheng-zhong-zhao-/)、[视频题解](https://leetcode-cn.com/problems/median-of-two-sorted-arrays/solution/xun-zhao-liang-ge-you-xu-shu-zu-de-zhong-wei-s-114/) |                                                              |
| [658. 找到 K 个最接近的元素（中等）](https://leetcode-cn.com/problems/find-k-closest-elements/) | [文字题解](https://leetcode-cn.com/problems/find-k-closest-elements/solution/pai-chu-fa-shuang-zhi-zhen-er-fen-fa-python-dai-ma/) | 这个问题二分的写法需要做复杂的分类讨论，可以放在以后做。     |



#### 题型二：二分答案（在一个有范围的区间里搜索一个整数）

定位一个有范围的整数，这件事情也叫「二分答案」或者叫「二分结果」。如果题目要求的是一个整数，这个整数有明确的范围，可以考虑使用二分查找。

事实上，二分答案是我们最早接触的二分查找的场景。「幸运 52」里猜价格游戏，就是「二分查找」算法的典型应用：先随便猜一个数，如果猜中，游戏结束。如果猜大了，往小猜；如果猜小了，往大猜。


| 题目                                                         | 题解                                                         | 说明                                                         |
| ------------------------------------------------------------ | ------------------------------------------------------------ | ------------------------------------------------------------ |
| [69. 平方根（简单）](https://leetcode-cn.com/problems/sqrtx/) | [文字题解](https://leetcode-cn.com/problems/sqrtx/solution/er-fen-cha-zhao-niu-dun-fa-python-dai-ma-by-liweiw/) | 在一个整数范围里查找一个整数，也是二分查找法的应用场景。     |
| [287. 寻找重复数（中等）](https://leetcode-cn.com/problems/find-the-duplicate-number/) | [文字题解](https://leetcode-cn.com/problems/find-the-duplicate-number/solution/er-fen-fa-si-lu-ji-dai-ma-python-by-liweiwei1419/) | 在一个整数范围里查找一个整数。这个问题二分查找的解法很反常规（不应该用时间换空间，这么做太傻了），知道即可。 |
| [374. 猜数字大小（简单）](https://leetcode-cn.com/problems/guess-number-higher-or-lower/) | [文字题解](https://leetcode-cn.com/problems/guess-number-higher-or-lower/solution/shi-fen-hao-yong-de-er-fen-cha-zhao-fa-mo-ban-pyth/) |                                                              |
| [1300. 转变数组后最接近目标值的数组和](https://leetcode-cn.com/problems/sum-of-mutated-array-closest-to-target/) | [文字题解](https://leetcode-cn.com/problems/sum-of-mutated-array-closest-to-target/solution/er-fen-cha-zhao-by-liweiwei1419-2/) |                                                              |

#### 题型三：二分答案的升级版：**判别条件需要遍历数组**

**说明**：这里给出的问题解法都一样，会一题等于会其它题。问题的场景会告诉我们：**目标变量和另一个变量有相关关系（一般是线性关系），目标变量的性质不好推测，但是另一个变量的性质相对容易推测（满足某种意义上的单调性）**。这样的问题的判别函数通常会写成一个函数的形式。

这一类问题可以统称为「 **最大值极小化** 」问题，最原始的问题场景是木棍切割问题，这道题的原始问题是「力扣」第 410 题（[分割数组的最大值（困难）](https://leetcode-cn.com/problems/split-array-largest-sum/)）。

思路是这样的：

+ 分析出题目要我们找一个整数，这个整数有范围，所以可以用二分查找；
+ 分析出 **单调性**，一般来说是一个变量 `a` 的值大了，另一个变量 `b` 的值就变小，而「另一个变量的值」 `b` 有限制，因此可以通过调整 `a` 的值达到控制 `b` 的效果；
+ 这一类问题的题目条件一定会给出 **连续**、**正整数** 这样的关键字。如果没有，问题场景也一定蕴含了这两个关键信息。

以下给出的问题无一例外。


| 题目                                                         | 提示与题解                                                   | 说明                   |
| ------------------------------------------------------------ | ------------------------------------------------------------ | ---------------------- |
| [410. 分割数组的最大值（困难）](https://leetcode-cn.com/problems/split-array-largest-sum/) | [文字题解](https://leetcode-cn.com/problems/split-array-largest-sum/solution/er-fen-cha-zhao-by-liweiwei1419-4/) |                        |
| [875. 爱吃香蕉的珂珂（中等）](https://leetcode-cn.com/problems/koko-eating-bananas/) | [文字题解](https://leetcode-cn.com/problems/koko-eating-bananas/solution/er-fen-cha-zhao-ding-wei-su-du-by-liweiwei1419/) |                        |
| [LCP 12. 小张刷题计划（中等）](https://leetcode-cn.com/problems/xiao-zhang-shua-ti-ji-hua/) |                                                              | 题解在第 410 题题解里  |
| [1482. 制作 m 束花所需的最少天数（中等）](https://leetcode-cn.com/problems/minimum-number-of-days-to-make-m-bouquets/) |                                                              | 题解在第 1300 题题解里 |
| [1552. 两球之间的磁力（中等）](https://leetcode-cn.com/problems/magnetic-force-between-two-balls/) |                                                              |                        |

--- 

### 8. 总结

1. 首先想清楚这道问题为什么可以用二分查找解决（而不应该先纠结二分查找该怎么写），利用题目中给出的单调性或者可以缩减问题规模的特点：已知某个猜测的答案的结果，就可以推测出比当前猜测小的时候结果如何，比当前猜测大的时候结果如何。常见应用为：有序或者半有序数组中找下标，确定一个有范围的整数。

2. 首先确定搜索的范围，如果搜索的范围就把正确答案排除在外，那么是无论如何也搜不出正确结果的；

3. 可以从「看到的中间元素什么时候不是解」开始思考 `if` 的语句怎么写，`if` 的逻辑越简单越好，这样才能保证不会错，剩下的复杂的情况留给 `else`，`else` 的区间就是剩下的区间；

4. 只把区间分成两个部分，代码也写成两个部分，这样，在 `while (left < right)` 的循环体退出以后，`left == right` 才成立（理解这一点非常重要，理解的基础是做适当的练习，进行必要的调试）；

5. 看到 `if` 和 `else` 里有 `left = mid` 的时候，需要将 `mid` 调整为上取整，原因是当区间里只剩下两个元素的时候，`mid` 看到右边元素，这样落入 `left = mid` 的时候，区间才会缩减。如果觉得这一点很难理解的朋友，打印变量看一下就非常清楚了；

6. 如果搜索区间里一定存在目标元素，退出 `while (left < right)` 以后，返回 `left` 或者 `left` 代表的值就可以，否则还需要单独做一次判断；

7. 不要纠结左闭右闭区间和左闭右开区间。如果你觉得左闭右开区间理解一点问题都没有，当然可以用左闭右开区间定义你的循环不变量。我们通篇介绍的二分查找算法的循环不变量是 `[left..right]` 里可能存在目标元素，`left` 包括、`right` 也包括。

8. 一旦掌握了二分查找算法的思想，用哪种二分查找的写法就是习惯的问题了。模板也是人设计出来的，而且就算是模板，也要有理由。生搬硬套模板是不可以的，一定要明白原理，有一些模板声称是捷径，很有可能是弯路。写算法问题，不可以靠模板。


---


### 9.参考资料

+ 李煜东 著《算法竞赛进阶指南》（河南电子音像出版社）第 0x04 章《二分》
+ 胡凡、曾磊 主编 《算法笔记》（机械工业出版社）第 4.5 节《二分》

