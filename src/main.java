import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

class Solution
{
    ArrayList<Integer> select_set = new ArrayList<>();//存放点的集合
    ArrayList<Integer> unselect_set = new ArrayList<>();//没选择的点
    double value;
    double getValue()
    {
        double ans = 0;
        ArrayList<Integer> new_set = new ArrayList<>();
        new_set.addAll(this.select_set);
        while(new_set.size()!=0)
        {
            int i = new_set.get(0);
            new_set.remove(0);
            for(Integer j:new_set)
            {
                ans+=main.cost_mariax[i][j];
            }
        }
        ans = ans / this.select_set.size();
        return ans; // 返回目标函数值
    }
    double improve_value(int i)//将这个点转到另一个集合后，value值的改变
    {
        double ans;
        double last_ans = this.value;
        Integer I = Integer.valueOf(i);
        Solution new_solution = new Solution();
        new_solution.select_set.addAll(this.select_set);
        if(this.select_set.contains(i))
        {

            new_solution.select_set.remove(I);
            new_solution.unselect_set.add(I);
            ans = new_solution.getValue() - last_ans;
        }
        else
        {
            new_solution.select_set.remove(I);
            new_solution.unselect_set.add(I);
            ans = new_solution.getValue() - last_ans;

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
            best = 0;
            for (int i : solution.select_set) {
                //System.out.println(solution.improve_value(i));
                if (best < solution.improve_value(i)) {
                    best = solution.improve_value(i);
                    id = i;
                }
                //System.out.println(solution.select_set.size());
            }
            if(best == 0)
                break;
            solution.select_set.remove(Integer.valueOf(id));//不断改进
            solution.unselect_set.add(Integer.valueOf(id));
            solution.value = solution.getValue();

        }
        solution.value = solution.getValue();
        return solution;
    }

    static Solution exchange(Solution solution)//第一种邻域算子：交换i,j st i在solution中，j不在
    {
        Random r = new Random();
        int i = r.nextInt(solution.select_set.size()-1);
        int j = r.nextInt(solution.unselect_set.size()-1);//在i,j中随机找两点;
        Integer mid = solution.select_set.get(i);
        solution.select_set.remove(i);
        solution.unselect_set.add(Integer.valueOf(mid));
        solution.value = solution.getValue();
        return solution;
    }
    static Solution insert(Solution solution)//第二种邻域算子：将j从没选择的集合中加入到solution中
    {
        Random r = new Random();
        int j =  r.nextInt(solution.unselect_set.size()-1);
        int mid = solution.unselect_set.get(j);
        solution.unselect_set.remove(j);
        solution.select_set.add(Integer.valueOf(mid));
        solution.value  = solution.getValue();
        return solution;
    }
    static Solution remove(Solution solution)//第三种邻域算子：将j从选择的集合中删除
    {
        Random r = new Random();
        int j = r.nextInt(solution.select_set.size()-1);
        int mid = solution.select_set.get(j);
        solution.unselect_set.add(Integer.valueOf(mid));
        solution.value = solution.getValue();
        return solution;
    }
}
public class main {
    static double[][] cost_mariax;//距离矩阵
    static int CODE_NUMBER;
    public static void main(String[] args)
    {
        CODE_NUMBER = 5;
        cost_mariax = new double[CODE_NUMBER+1][CODE_NUMBER+1];
        for(int i=1;i<=CODE_NUMBER;i++)
            for(int j=1;j<=CODE_NUMBER;j++)
            {
                if(i == j)
                {
                    cost_mariax[i][j] = 0;
                    continue;
                }
                if(cost_mariax[i][j]==0 && cost_mariax[j][i] ==0)//都为0
                {
                    Random r = new Random();
                    double d = r.nextDouble()*8 + (-4);//获得4到-4中的随机数
                    cost_mariax[i][j] = d;
                    cost_mariax[j][i] = d;
                }
            }
            Solution solution = Strategy.ini_solution();
           for(int i=1;i<=1000;i++)
           {
               Solution new_solution = new Solution();
               new_solution.select_set.addAll(solution.select_set);
               new_solution.unselect_set.addAll(solution.unselect_set);
               new_solution.value = solution.value;
               new_solution = Strategy.exchange(solution);
               if(solution.value<new_solution.value)
                   solution = new_solution;
               System.out.printlnn;

           }

    }
}
