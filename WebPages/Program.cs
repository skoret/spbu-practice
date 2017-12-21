using System;

namespace WebPages
{
    internal class Program
    {
        public static void Main(string[] args)
        {
            var address = Console.ReadLine();
            
            Downloader dl = new Downloader();
            
            dl.GetCharCount(address, 2);
            
            foreach (var page in dl.GetPages())
            {
                Console.Write(page.Key + " | " + page.Value.Length + "\n");
            }
        }
    }
}