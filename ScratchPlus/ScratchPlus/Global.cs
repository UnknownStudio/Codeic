using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ScratchPlus
{
    internal class Global
    {
        /// <summary>  
        /// execute dos command.return dos output.  
        /// </summary>  
        /// <param name="dosCommand">dos command</param>  
        /// <param name="milliseconds">waiting for the command execution time(unit：millisecond)，  
        /// If set to 0, then wait infinity</param>  
        /// <returns>return dos output</returns>  
        public static string Execute(string command, int seconds = 0)
        {
            string output = ""; 
            if (command != null && !command.Equals(""))
            {
                Process process = new Process();
                ProcessStartInfo startInfo = new ProcessStartInfo();
                startInfo.FileName = "cmd.exe"; 
                startInfo.Arguments = "/C " + command;//"/C" mean exit immediately after execution of the order
                startInfo.UseShellExecute = false;//Do not use the system shell startup  
                startInfo.RedirectStandardInput = false;//Do not use redirectStandardInput  
                startInfo.RedirectStandardOutput = true; //use redirectStandardOutput  
                startInfo.CreateNoWindow = true;//do not create Window  
                process.StartInfo = startInfo;
                try
                {
                    if (process.Start())//start process  
                    {
                        if (seconds == 0)
                        {
                            process.WaitForExit();//Here the infinite waiting for the end of the process  
                        }
                        else
                        {
                            process.WaitForExit(seconds); //Waiting for the end of the process, the waiting time for the specified milliseconds
                        }
                        output = process.StandardOutput.ReadToEnd();//The output of the reading process
                    }
                }
                catch
                {
                }
                finally
                {
                    if (process != null)
                        process.Close();
                }
            }
            return output;
        }
    }
}
