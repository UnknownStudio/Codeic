#include "Header.h"
#include "csdl.h"

using namespace std;
#define DEBUG true

Csdl* csdl;

void preRender(Csdl* csdl)
{
	//Debug("Update");
}

void preRun(Csdl* csdl)
{
	Debug("Called preRun Event");
	Texture* test = csdl->loadTexture(".\\pic\\test.png", "test");
	Object* testo = new Object();
	testo->preRenderEvent = preRender;
	testo = testo->setName("TEST")->setTexture(test);
	//testo->srcRect = setRect(testo->srcRect, 0, 0, 100, 100);
	testo->dstRect = setRect(testo->dstRect, 0, 0, 100, 100);
	csdl->addObject(testo);
}

void thread_main()
{
	Debug("Main thread Loading...");
	csdl = new Csdl();
	csdl->preRunEvent = preRun;
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