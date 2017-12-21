﻿using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Threading;
using System.Threading.Tasks;

namespace BoundedSingleWriter
{
    
    internal class Program
    {
        public static void Main(string[] args)
        {
            var bsw = new BSW(2);
            var rd = new Random();
            var tasks = new Task[2];

            for (var i = 0; i < 22; i++)
            {
                var id = i % 2;
                var value = rd.Next(1000);
                tasks[id] = Task.Run(() =>
                {
                    Console.WriteLine("write value = {0} in #{1} register", value, id);
                    bsw.Update(id, value);
                });

                if (i % 3 == 0)
                {
                    var count = i;
                    Task.Run(() =>
                    {
                        Console.WriteLine("read from {0} thread on {1} interation: ({2})", id, count, string.Join(", ", bsw.Scan(id)));
                    });
                }

                if (i % 2 == 1)
                {
                    Task.WaitAll(tasks);
                }
            }

            bsw.Print();
            
        }
    }
}