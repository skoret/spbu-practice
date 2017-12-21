using System;
using System.Threading;
using System.Threading.Tasks;
using System.Diagnostics;
using System.Collections.Generic;

public static class CheckPrimeInRange
{   

    public static List<int> CheckInThreads(int l, int r, int thr_amount)
    {
        int thrId = Thread.CurrentThread.ManagedThreadId;
        List<int> res = new List<int>();

        if (thrId < thr_amount)
        {
            int p = (l+r)/2;

            List<int> res1 = new List<int>();
            List<int> res2 = new List<int>();

            Thread thr1 = new Thread(() => { res1.AddRange(CheckInThreads(l, p, thr_amount)); });
            Thread thr2 = new Thread(() => { res2.AddRange(CheckInThreads(p+1, r, thr_amount)); });
                
            thr1.Start();
            thr2.Start();

            thr1.Join();
            thr2.Join();

            res.AddRange(res1);
            res.AddRange(res2);
        } 
        else
        {
            for (int i = l; i <= r; i++)
            {
                if (IsPrime(i)) res.Add(i);
            }
        }
        return res;
    }

    public static List<int> CheckInTasks(int l, int r)
    {
        List<int> res = new List<int>();

        if (r-l > 500)
        {
            int p = (l+r)/2;

            Task<List<int>> task1 = Task<List<int>>.Run(() => CheckInTasks(l, p));
            Task<List<int>> task2 = Task<List<int>>.Run(() => CheckInTasks(p+1, r));

            Task.WaitAll(task1, task2);

            res.AddRange(task1.Result);
            res.AddRange(task2.Result);
        } 
        else
        {
            for (int i = l; i <= r; i++)
            {
                if (IsPrime(i)) res.Add(i);
            }
        }
        return res;
    }

    public static List<int> CheckInThreadPool(int l, int r)
    {
        List<int> res = new List<int>();

        var resEvent = new ManualResetEvent(false);
        ThreadPool.QueueUserWorkItem(delegate
        {
            for (int i = l; i <= r; i++)
            {
                if (IsPrime(i)) res.Add(i);
            }
            resEvent.Set();
        });      
        resEvent.WaitOne();

        return res;
    }


    public static bool IsPrime(int num)
    {
        if (num <= 1) return false;

        int sqrt_num = (int)Math.Sqrt(num);
        for (int i = 2; i <= sqrt_num; i++)
        {
            if (num % i == 0) return false;
        }
        return true;
    }

    public static void Main()
    {
        int start = 1;
        int end = 10000000;
        Console.WriteLine("Check range [{0}, {1}M]:", start, end/1000000);

        Stopwatch time = Stopwatch.StartNew();
        List<int> nums = new List<int>();
        for (int i = start; i <= end; i++)
        {
            if (IsPrime(i)) nums.Add(i);
        }
        time.Stop();
        Console.WriteLine("Without parallel time: {0}",time.Elapsed);
        
        time.Restart();
        nums = CheckInThreads(start, end, 8);
        time.Stop();
        Console.WriteLine("On {0} threads time: {1}", 8, time.Elapsed);

        time.Restart();
        nums = CheckInTasks(start, end);
        time.Stop();
        Console.WriteLine("On tasks time: {0}", time.Elapsed);

        time.Restart();
        nums = CheckInThreadPool(start, end);
        time.Stop();
        Console.WriteLine("On thread pool time: {0}", time.Elapsed);

        // nums.ForEach(Console.WriteLine);
    }

}