using System;
using System.Diagnostics;
using System.Threading.Tasks;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;

namespace BinarySearchTreeParallel
{
    internal class Program
    {
        public static void Main(string[] args)
        {
            var treeParallel = new TreeParallel<int, int>();
            var tasks = new List<Task>();
            const string help = "choose action:\n" +
                                  "1 - insert pairs <key value> // for stop enter ' . '\n" +
                                  "2 - find values by keys\n" +
                                  "3 - delete values by keys\n" +
                                  "4 - print tree // and perfom all operations\n" +
                                  "5 - compare simple bst and parallel bst\n" +
                                  "help - show cmds\n" +
                                  "exit - end work with tree";
            
            Console.WriteLine(help);
            
            while (true)
            {
                Console.Write("> ");
                var choice = Console.ReadLine();
                        
                switch (choice)
                {
                    case "1":

                        var str = Console.ReadLine();
                            
                        while (str != null && str != ".")
                        {
                            var tmp = str.Split();

                            if (int.TryParse(tmp[0], out var keyInsert)
                                && int.TryParse(tmp[1], out var valueInsert))
                            {
                                tasks.Add(new Task(() =>
                                {
                                    Console.WriteLine("insert " + keyInsert);
                                    treeParallel.Insert(keyInsert, valueInsert);
                                }));
                            }
                            else
                            {
                                Console.WriteLine("invalid input, try again");
                            }

                            str = Console.ReadLine();
                        }

                        break;

                    case "2":

                        str = Console.ReadLine();
                        
                        while (str != ".")
                        {
                            if (int.TryParse(str, out var keySearch))
                            {
                                tasks.Add(new Task(() =>
                                {
                                    var node = treeParallel.Search(keySearch);
                                    Console.WriteLine(node != null ? "node found: " + node : "doesn't found");
                                }));
                            }
                            else
                            {
                                Console.WriteLine("invalid input, try again");
                            }
                            
                            str = Console.ReadLine();
                        }
                        
                        break;

                    case "3":

                        str = Console.ReadLine();
                        
                        while (str != null && str != ".")
                        {
                            if (int.TryParse(str, out var keyDelete))
                            {
                                tasks.Add(new Task(() => { 
                                    Console.WriteLine("delete " + keyDelete);
                                    treeParallel.Delete(keyDelete); 
                                }));
                            }
                            else
                            {
                                Console.WriteLine("invalid input, try again");
                            }
                            
                            str = Console.ReadLine();
                        }
                        
                        break;

                    case "4":
                        foreach (var task in tasks)
                        {
                            task.Start();
                        }
                        Task.WaitAll(tasks.ToArray());
                        tasks.Clear();
                        treeParallel.Print();
                        break;
                        
                    case "5":
                        var rn = new Random();
                        var sw = new Stopwatch();
                        var tree0 = new Tree<int, int>();
                        var tree1 = new TreeParallel<int, int>();
                        var tree2 = new TreeParallel<int, int>();
                        var tree3 = new TreeParallelAsync<int, int>();

                        str = Console.ReadLine();
                        if (str == null || !int.TryParse(str, out var amount))
                        {
                            Console.WriteLine("invalid input, try again");
                            break;
                        }
                        
                        Console.WriteLine("compare insertion on {0} keys:", amount);

                        var keys = new int[amount];
                        for (var i = 0; i < amount; i++)
                        {
                            keys[i] = rn.Next(3*amount);
                        }
                        
                        sw.Start();
                        foreach (var i in keys)
                        {
                            tree0.Insert(i, i);
                        }
                        sw.Stop();
                        Console.WriteLine("seq insertion time in simple bst: {0}", sw.Elapsed);
                        
                        sw.Start();
                        foreach (var i in keys)
                        {
                            tree1.Insert(i, i);
                        }
                        sw.Stop();
                        Console.WriteLine("seq insertion time in locked bst: {0}", sw.Elapsed);

                        var tasksList = keys.Select(arg => new Task(() => { tree2.Insert(arg, arg); })).ToList();
                        sw.Restart();
                        foreach (var task in tasksList)
                        {
                            task.Start();   
                        }
                        Task.WaitAll(tasksList.ToArray());
                        sw.Stop();
                        tasksList.Clear();
                        Console.WriteLine("conc insertion time in locked bst: {0}", sw.Elapsed);
                        
                        tasksList = keys.Select(arg => new Task(async () => { await tree3.Insert(arg, arg); })).ToList();
                        sw.Restart();
                        foreach (var task in tasksList)
                        {
                            task.Start();   
                        }
                        Task.WaitAll(tasksList.ToArray());
                        sw.Stop();
                        tasksList.Clear();
                        Console.WriteLine("conc insertion time in async locked bst: {0}", sw.Elapsed);
                        
                        Console.WriteLine("compare deletion:");

                        sw.Start();
                        for (var i = 0; i < amount; i += 3)
                        {
                            tree0.Delete(keys[i]);
                        }
                        sw.Stop();
                        Console.WriteLine("seq deletion time in simple bst: {0}", sw.Elapsed);
                        
                        sw.Start();
                        for (var i = 0; i < amount; i += 3)
                        {
                            tree1.Delete(keys[i]);
                        }
                        sw.Stop();
                        Console.WriteLine("seq deletion time in locked bst: {0}", sw.Elapsed);

                        for (var i = 0; i < amount; i += 3)
                        {
                            var arg = i;
                            tasksList.Add(new Task(() => { tree2.Delete(keys[arg]); }));
                        }
                        
                        sw.Restart();
                        foreach (var task in tasksList)
                        {
                            task.Start();   
                        }
                        Task.WaitAll(tasksList.ToArray());
                        sw.Stop();
                        tasksList.Clear();
                        Console.WriteLine("conc deletion time in locked bst: {0}", sw.Elapsed);
                        
                        for (var i = 0; i < amount; i += 3)
                        {
                            var arg = i;
                            tasksList.Add(new Task(async () => { await tree3.Delete(keys[arg]); }));
                        }
                        
                        sw.Restart();
                        foreach (var task in tasksList)
                        {
                            task.Start();   
                        }
                        Task.WaitAll(tasksList.ToArray());
                        sw.Stop();
                        tasksList.Clear();
                        Console.WriteLine("conc deletion time in async locked bst: {0}", sw.Elapsed);
                        
                        
                        
                        // tree1.Print();
                        // tree2.Print();
                        // tree3.Print();
                        
                        break;
                        
                    case "help":
                        Console.WriteLine(help);
                        break;
                        
                    case null:
                    case "exit":
                        Console.WriteLine("uhadi");
                        return;
                        
                    default:
                        Console.WriteLine("invalid input, try again");
                        break;
                }
            }
        }
    }
}