using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ScratchPlus
{
    class Program
    {
        static void Main(string[] args)
        {
            log("Codeic-ScratchPlus [Author:Robert(github.com/RobertIndie)]");
            string str = Global.Execute("python test.py");
            log(str);
            Console.ReadLine();
        }
        static void log(string messsage)
        {
            Console.WriteLine(string.Format("[{0}][DEBUG]{1}",DateTime.Now,messsage));
        }
    }
}
