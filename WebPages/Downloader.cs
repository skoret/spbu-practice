using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Net;
using System.Text.RegularExpressions;
using System.Threading.Tasks;

namespace WebPages
{
    public class Downloader
    {
        private Dictionary<string, string> pages = new Dictionary<string, string>();

        public Dictionary<string, string> GetPages()
        {
            return pages;
        }
        
        private async Task DownloadPageTaskAsync(string address)
        {
            using (var client = new WebClient())
            {
                var uri = new Uri(address);
                
                client.DownloadDataCompleted += Callback;
                client.Headers["address"] = address;
                await client.DownloadDataTaskAsync(uri);
            }
        }
            
        private void Callback(object obj, DownloadDataCompletedEventArgs e)
        {
            if (obj is WebClient client && !e.Cancelled && e.Error == null)
            {
                var address = client.Headers["address"];

                if (!pages.ContainsKey(address))
                    pages.Add(address, client.Encoding.GetString(e.Result));
            };
        }
        
        private List<string> GetLinks(string address)
        {
            if (!pages.ContainsKey(address)) return null;
            
            var regex = new Regex(@"<a.*? href=""(?<url>http(s)?[\w\.:?&-_=#/]*)""+?");
            MatchCollection matches = regex.Matches(pages[address]);

            var links = new List<string>();
            for (var j = 0; j < matches.Count; j++)
            {
                var link = matches[j].Groups["url"].Value;
                if (!pages.ContainsKey(link) && !links.Contains(link))
                    links.Add(link);
            }
            
            return links;
        }

        public void GetCharCount(string address, int depth)
        {
            Task task = DownloadPageTaskAsync(address);

            try
            {
                task.Wait();
            }
            catch
            {
                return;
            }
            
            var links = GetLinks(address);
            while (depth > 0)
            {
                depth--;
                
                if (links != null)
                {
                    try
                    {
                        Task.WaitAll(links.Select(DownloadPageTaskAsync).ToArray());
                    }
                    catch {}

                    if (depth > 0)
                    {
                        var tmp = new List<string>();
                        foreach (var link in links)
                        {
                            var l = GetLinks(link);
                            if (l != null)
                                tmp.AddRange(l);
                        }
                        links = tmp;
                    }
                }
            }
        }
    }
}