import java.util.ArrayList;

class Solution
{
    ArrayList<Integer> select_set = new ArrayList<>();//存放点的集合
    ArrayList<Integer> unselect_set = new ArrayList<>();//没选择的点
    double value;
    double getValue()
    {
        double ans = 0;
        ArrayList<Integer> new_set = new ArrayList<>();
        new_set.addAll(select_set);
        while(new_set.size() != 0)
        {
            int i = new_set.get(0);
            for(int j=1;j<=new_set.size()-1;j++)
            {
                ans += main.cost_mariax[i][j];
            }
            new_set.remove(0);
        }
        ans = ans / this.select_set.size();
        return ans; // 返回目标函数值
    }
    double improve_value(int i)//将这个点转到另一个集合后，value值的改变
    {
        double ans;
        double last_ans = this.value;
        if(this.select_set.contains(i))
        {
            this.select_set.remove(i);
            this.unselect_set.add(i);
            ans = this.getValue() - last_ans;
            this.unselect_set.remove(i);
            this.select_set.add(i);
        }
        else
        {
            this.unselect_set.remove(i);
            this.select_set.add(i);
            ans = this.getValue() - last_ans;
            this.select_set.remove(i);
            this.unselect_set.add(i);
        }
        return ans;
    }


}
abstract class Strategy//策略类，存放算法
{

    static Solution ini_solution()//初始化一个解，采用贪婪的思想，最开始所有解都在select_set中，随后逐渐减少，每次计算去除点的价值，由大到小，不再有改进
    {
        Solution solution = new Solution();
        for(int i=1;i<=main.CODE_NUMBER;i++)
            solution.select_set.add(i);//开始时所有解都在select_set中
        solution.value = solution.getValue();//获得当前解
        double best = 0;
        int id = 0;
        while(true) {
            for (int i : solution.select_set) {
                if (best < solution.improve_value(i)) {
                    best = solution.improve_value(i);
                    id = i;
                }
            }
            if(best == 0)//说明不再提高了
            {
                break;
            }
            solution.select_set.remove(id);//不断改进
            solution.unselect_set.add(id);
        }
        return solution;
    }

    static Solution exchange(Solution solution)//第一种邻域算子：交换i,j st i在solution中，j不在
    {
        return solution;
    }
    static Solution insert(Solution solution)//第二种邻域算子：将j从没选择的集合中加入到solution中
    {
        return solution;
    }
    static Solution remove(Solution solution)//第三种邻域算子：将j从选择的集合中删除
    {
        return solution;
    }
}
public class main {
    static double[][] cost_mariax;//距离矩阵
    static int CODE_NUMBER;

}
