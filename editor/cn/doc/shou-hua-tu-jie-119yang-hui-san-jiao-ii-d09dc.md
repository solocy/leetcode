#### DP 思路
如下图，外层循环从上往下一层层求，复用一维数组 res（滚动数组），计算每个位置上的元素，只取决于上一行的值。

内层遍历的递推式为`res[j] = res[j] + res[j-1]`，要保证等号右边的两个，是上一行的“旧值”。

![image.png](https://pic.leetcode-cn.com/1613063846-QBYcBY-image.png)


如果内层遍历是从左往右，会出现`res[j-1]`是本行的上一轮迭代求出的新值，不是上一行的旧值。

![image.png](https://pic.leetcode-cn.com/1613067394-UdEFGV-image.png)


所以，内层遍历的方向取从右往左。这样保证了计算新值时，等号右边都是旧值。

#### DP 代码

```golang []
func getRow(rowIndex int) []int {
    // 第rowIndex行的数组长度为rowIndex+1
    res := make([]int, rowIndex+1)   
    // 每一行的第一项都确定是1
    res[0] = 1                     
    // 从第二行开始遍历
    for i := 1; i < rowIndex+1; i++ {
        // 第i行的首尾项确定是1
        res[0] = 1
        res[i] = 1   
        // 从第i行的倒数第二个开始遍历到第二个      
        for j := i-1; j >= 1; j-- {   
            // 用上一行的值求出当前res[j]
            res[j] = res[j] + res[j-1] 
        }
    }
    // 返回出第rowIndex行的数组
    return res 
}
```
```javascript []
var getRow = function(rowIndex) {
    const res = new Array(rowIndex + 1);
    res[0] = 1;
    
    for (let i = 1; i < rowIndex + 1; i++) { 
        res[0] = res[i] = 1;
        for (let j = i - 1; j >= 1; j--) { 
            res[j] = res[j] + res[j - 1];
        }
    }
    return res;
};
```
#### 递归（超时）
定义递归函数：返回出第 i 行，第 j 列的元素值。
递归的公式是 `cal(i,j) = cal(i-1, j-1) + cal(i-1, j)`
递归的结束条件是：当 j == 0 || i == j，元素值为1，返回1，它其实就是 dp 的 base case。
```golang []
func getRow(rowIndex int) []int {
    res := make([]int, rowIndex+1)
	for j := 0; j < rowIndex+1; j++ { // 计算res数组的每一项
		res[j] = cal(rowIndex, j)
	}
	return res 
}

func cal(i int, j int) int {
    if j == 0 || i == j { // 每一行的首尾两项都是1
        return 1
    }
	return cal(i-1, j-1) + cal(i-1, j) // 递归公式  
}
```
```javascript []
var getRow = function(rowIndex) {
    const res = new Array(rowIndex + 1);
    for (let j = 0; j < rowIndex + 1; j++) { 
        res[j] = cal(rowIndex, j);
    }
    return res
};

var cal = function(i, j) {
    if (j == 0 || i == j) {
        return 1;
    }
    return cal(i-1, j-1) + cal(i-1, j);
}
```

但遇到 30 这个case 就超时了

![image.png](https://pic.leetcode-cn.com/1613096808-gXWJCo-image.png)

用 3 测试一下，打印输出 cal 函数接收到的 i 和 j，发现进入了重复的递归：

3 0
3 1
2 0
2 1
1 0
1 1
3 2
2 1  重复
1 0  重复
1 1  重复
2 2
3 3   

**具体是如何落入重复的递归的，画出递归图看看：**

![image.png](https://pic.leetcode-cn.com/1613099435-mfMzKP-image.png)


#### 记忆化递归
用一个二维数组memo，存计算过的子问题的结果，当遇到相同的递归子问题时，直接返回缓存值，避免落入重复的递归。基于上面代码轻轻一改就有了：
```golang []
func getRow(rowIndex int) []int {
	memo := make([][]int, rowIndex+1)
	for i := range memo {
		memo[i] = make([]int, i+1) // 构建memo二维数组
	}
    
	for j := 0; j < rowIndex+1; j++ { // 计算第rowIndex行的每个元素
		memo[rowIndex][j] = cal(rowIndex, j, memo)
	}
	return memo[rowIndex] // 返回第rowIndex行的子数组
}

func cal(i int, j int, memo [][]int) int {
    if j == 0 || i == j { // 每一行的首尾两项都是1
        return 1
    }
    
	if memo[i][j] > 0 { // 如果memo[i][j]有值，返回它，避免落入重复的递归
		return memo[i][j]
	}
    // 其他情况，有下面的递归公式      
	memo[i][j] = cal(i-1, j-1, memo) + cal(i-1, j, memo)
    
	return memo[i][j] 
}
```
```javascript []
var getRow = function(rowIndex) {
    const memo = new Array(rowIndex + 1);
    for (let i = 0; i < memo.length; i++) { 
        memo[i] = new Array(i + 1);
    }
    for (let j = 0; j < rowIndex + 1; j++) { 
        memo[rowIndex][j] = cal(rowIndex, j, memo);
    }
    return memo[rowIndex]
};

var cal = function(i, j, memo) {
    if (j == 0 || i == j) {
        return 1;
    }
    if (memo[i][j] > 0) {
        return memo[i][j];
    }
    memo[i][j] = cal(i-1, j-1, memo) + cal(i-1, j, memo);
    
    return memo[i][j] 
}
```


![image.png](https://pic.leetcode-cn.com/1613097579-GvMzCU-image.png)

#### 感谢阅读，新年快乐。欢迎留言回复。
![0411_2.jpg](https://pic.leetcode-cn.com/1612928375-DCnicz-image.png)
