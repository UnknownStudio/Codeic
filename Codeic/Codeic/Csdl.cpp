#include "Header.h"
#include "csdl.h"
/*This source code copyrighted by Lazy Foo' Productions (2004-2015)
and may not be redistributed without written permission.*/

//Using SDL and standard IO


#define DEBUG true

//#include<stdio.h>

int SCREEN_WIDTH = 640;
int SCREEN_HEIGHT = 480;
int main(int argc, char* args[])
{
	Debug("CSDL loading...");
	Csdl* c = new Csdl();
	c->Init("Test Window", 600, 800);
	Csdl* d = new Csdl();
	d->Init("Test Window2", 600, 800);
	c->close();
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
Csdl::~Csdl()
{
	close();
}
/*
	释放CSDL的内存
*/
void Csdl::close()
{
	SDL_FreeSurface(screenSurface);
	screenSurface = NULL;

	SDL_DestroyRenderer(renderer);
	renderer = NULL;

	SDL_DestroyWindow(window);
	window = NULL;
}
/*
	初始化CSDL，一举将window,screenSurface和renderer都初始化了
*/
void Csdl::Init(char* title,int width,int height)
{
	Debug("CSDL Initialize");
	if (SDL_Init(SDL_INIT_VIDEO) < 0)
	{
		char* mes = new char[200];
		sprintf_s(mes,200, "SDL fail to initialize:%s", SDL_GetError());
		Error(mes);
	}
	window = SDL_CreateWindow(title, SDL_WINDOWPOS_UNDEFINED, SDL_WINDOWPOS_UNDEFINED, width, height, SDL_WINDOW_SHOWN);
	if (this->window == NULL)
	{
		char* mes = new char[ERROR_BUFFER];
		sprintf_s(mes, ERROR_BUFFER, "Fail to create SDL window.");
		Error(mes);
		return;
	}
	else
	{
		//Initialize PNG loading
		int imgFlags = IMG_INIT_PNG;
		if (!(IMG_Init(imgFlags) & imgFlags))
		{
			char* mes = new char[ERROR_BUFFER];
			sprintf_s(mes, ERROR_BUFFER, "SDL_image could not initialize! SDL_image Error: %s", IMG_GetError());
			Error(mes);
			return;
		}
		else
		{
			//Get window surface
			screenSurface = SDL_GetWindowSurface(window);
		}


		//Create renderer for window
		renderer = SDL_CreateRenderer(window, -1, SDL_RENDERER_ACCELERATED);
		if (renderer == NULL)
		{
			char* mes = new char[ERROR_BUFFER];
			sprintf_s(mes, ERROR_BUFFER, "Renderer could not be created! SDL Error: %s", SDL_GetError());
			Error(mes);
			return;
		}
		else
		{
			//Initialize renderer color
			SDL_SetRenderDrawColor(renderer, 0xFF, 0xFF, 0xFF, 0xFF);

			//Initialize PNG loading
			int imgFlags = IMG_INIT_PNG;
			if (!(IMG_Init(imgFlags) & imgFlags))
			{
				char* mes = new char[ERROR_BUFFER];
				sprintf_s(mes, ERROR_BUFFER, "SDL_image could not initialize! SDL_image Error: %s\n", IMG_GetError());
				Error(mes);
				return;
			}
		}
	}
	screenSurface = SDL_GetWindowSurface(window);
	SDL_FillRect(screenSurface, NULL, SDL_MapRGB(screenSurface->format, 0xFF, 0xFF, 0xFF));
}
int textureID = 0;
/*
	读取材质
*/
Texture* Csdl::loadTexture(char* path,char* name)
{
	//Load image at specified path
	SDL_Surface* optimizedSurface = NULL;
	SDL_Surface* loadedSurface = IMG_Load("pic/A.png");
	if (loadedSurface == NULL)
	{
		//printf("Unable to load image %s! SDL_image Error: %s\n", "pic/A.png", IMG_GetError());
		char* mes = new char[200];
		sprintf_s(mes, 200, "Unable to load image %s! SDL_image Error: %s", path, IMG_GetError());
		Error(mes);
	}
	else
	{
		//Convert surface to screen format
		optimizedSurface = SDL_ConvertSurface(loadedSurface, screenSurface->format, NULL);
		if (optimizedSurface == NULL)
		{
			//printf("Unable to optimize image %s! SDL Error: %s\n", "pic/A.png", SDL_GetError());
			char* mes = new char[200];
			sprintf_s(mes, 200, "Unable to optimize image %s!SDL Error : %s\n", "pic / A.png", SDL_GetError());
			Error(mes);
		}

		//Get rid of old loaded surface
		SDL_FreeSurface(loadedSurface);
	}
	Texture* texture = new Texture(textureID++);
	texture->name = name;
	return texture;
}