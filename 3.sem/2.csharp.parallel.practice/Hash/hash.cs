using System;
using System.IO;
using System.Text;
using System.Diagnostics;
using System.Threading.Tasks;
using System.Collections.Generic;
using System.Security.Cryptography;

public static class DirHasher
{
    static string Hash2Str(byte[] hash)
    {
        StringBuilder sb = new StringBuilder();
        foreach (byte b in hash)
        {
            sb.Append(b.ToString("x2"));
        }
        return sb.ToString();
    }

    static byte[] GetFileHash(MD5 md5, string file)
    {
        return md5.ComputeHash(File.OpenRead(file));
    }


    static string GetHash(string str)
    {
        return Hash2Str(GetHashInternal(str));
    }

    static byte[] GetHashInternal(string dir)
    {
        StringBuilder sb = new StringBuilder(dir.Substring(dir.LastIndexOf('/') + 1));

        using (MD5 md5 = MD5.Create())
        {
            foreach (string file in Directory.EnumerateFiles(dir))
            {
                sb.Append(Hash2Str(GetFileHash(md5, file)));
            }
        }

        List<Task<byte[]>> tasks = new List<Task<byte[]>>();

        foreach (string subdir in  Directory.EnumerateDirectories(dir))
        {
            if (Task.CurrentId < 1000) // if (t_id < 'max amount tasks')
            {
                Task<byte[]> task = Task<byte[]>.Run(() => GetHashInternal(subdir));
                tasks.Add(task);
            }
            else
            {
                sb.Append(Hash2Str(GetHashInternal(subdir)));
            }
        }

        Task.WaitAll(tasks.ToArray());

        foreach (Task<byte[]> task in tasks)
        {
            sb.Append(Hash2Str(task.Result));
        }

        using (MD5 md5 = MD5.Create())
        {
            return md5.ComputeHash(Encoding.UTF8.GetBytes(sb.ToString()));
        }

        throw new Exception("wow, smth is wrong, try again/n");
    }

    static void Main(string[] args)
    {
        if (args == null || args.Length == 0)
        {
            Console.WriteLine("Oops, there's no path, try again");
            return;
        }

        string str = "";
        Stopwatch time = Stopwatch.StartNew();

        try 
        {
            str = GetHash(args[0]);
        } 
        catch (Exception e)
        {
            Console.WriteLine(e);
            return;
        }
        time.Stop();

        Console.WriteLine("Hash: " + str + " calculated in ");
        Console.WriteLine(" time: {0}", time.Elapsed);

    }
}