#include "Header.h"
/*This source code copyrighted by Lazy Foo' Productions (2004-2015)
and may not be redistributed without written permission.*/

//Using SDL and standard IO
#include <SDL.h>
#include <stdio.h>
#include "csdl.h"

#define DEBUG true

//#include<stdio.h>


int main(int argc, char* args[])
{
	Debug("CSDL loading...");
	if(DEBUG)
		system("pause");
	return 0;
}

void Error(char * message=NULL)
{
	if (!message) printf("[ERROR]Some errors happened!\n");
	printf("[ERROR]%s\n", message);
}

void Debug(char * message)
{
	printf("[DEBUG]%s\n", message);
}

void Csdl::Init()
{
	if (SDL_Init(SDL_INIT_VIDEO) < 0)
	{
		char* mes = new char[200];
		sprintf_s(mes,200, "SDL fail to initialize:%s", SDL_GetError());
		Error(mes);
	}
}
