#include "Header.h"
#include "csdl.h"

using namespace std;
#define DEBUG true

Csdl* csdl;

void thread_main()
{
	Debug("Main thread Loading...");
	csdl = new Csdl();
	csdl->Init("CSDL DEBUG WINDOW", 800, 600);
	csdl->Run();
}

int main(int argc, char* args[])
{
	Debug("CSDL loading...");
	thread mainThread(thread_main);
	mainThread.join();
	if (DEBUG)
		system("pause");
	return 0;
}