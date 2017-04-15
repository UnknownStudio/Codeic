#include "Header.h"
#include "csdl.h"
//Using SDL and standard IO
using namespace std;
#define DEBUG true

//#include<stdio.h>
int lo = 0;
std::string ar = "Hleo";
int SCREEN_WIDTH = 640;
int SCREEN_HEIGHT = 480;
void run()
{
	Debug("1");
	ar = "00000";
	Debug(std::string("1:")+ ar);
	std::this_thread::sleep_for(std::chrono::seconds(3));
	Debug("000");
	//while (true)
	//{
	//	if(lo==1)	Debug("111111111111");
	//}
}
void run2()
{
	Debug("2");
	Debug(string("2:") + ar);
	ar = "jojoj";
	Debug(string("2:") + ar);
	std::this_thread::sleep_for(std::chrono::seconds(10));
	//while (true)
	//{
	//	if (lo == 2)	Debug("2222222222");
	//}
}
void test()
{
	std::thread t(run);
	std::thread t2(run2);
	//while (true)Debug("1.1");
	lo++;
	lo++;
	lo--;
	lo--;
	Debug("Main");
	Debug(ar);
	t.join();
	t2.join();

}
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
void Debug(const char * message)
{
	printf("[DEBUG]%s\n", message);
}
void Debug(std::string message)
{
	Debug(message.c_str());
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
		char* mes = new char[ERROR_BUFFER];
		sprintf_s(mes, ERROR_BUFFER, "SDL fail to initialize:%s", SDL_GetError());
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
Texture* Csdl::loadTexture(
	char* path,
	char* name,
	Uint8 colorkey_r, 
	Uint8 colorkey_g,
	Uint8 colorkey_b
)
{
	SDL_Texture* loadedTexture = NULL;
	SDL_Surface* loadedSurface = IMG_Load("pic/A.png");
	if (loadedSurface == NULL)
	{
		//printf("Unable to load image %s! SDL_image Error: %s\n", "pic/A.png", IMG_GetError());
		char* mes = new char[ERROR_BUFFER];
		sprintf_s(mes, ERROR_BUFFER, "Unable to load image %s! SDL_image Error: %s", path, IMG_GetError());
		Error(mes);
	}
	else
	{
		if (colorkey_r != NULL&&colorkey_g != NULL&&colorkey_b != NULL) {
			//Color key image
			SDL_SetColorKey(loadedSurface, SDL_TRUE, SDL_MapRGB(loadedSurface->format, colorkey_r, colorkey_g, colorkey_b));
		}

		//Create texture from surface pixels
		loadedTexture = SDL_CreateTextureFromSurface(renderer, loadedSurface);
		if (loadedTexture == NULL)
		{
			char* mes = new char[ERROR_BUFFER];
			sprintf_s(mes, ERROR_BUFFER, "Unable to create texture from %s! SDL Error: %s\n", path, SDL_GetError());
			Error(mes);
		}

		//Get rid of old loaded surface
		SDL_FreeSurface(loadedSurface);
	}
	Texture* texture = new Texture(textureID++);
	texture->texture = loadedTexture;
	texture->name = name;
	texturePool.push_back(texture);
	return texture;
}

Object * Csdl::addObject(Object * object)
{
	objectPool.push_back(object);
}

void Csdl::Run()
{
	preRunEvent(this);
	bool quit = false;
	SDL_Event e;
	while (!quit)
	{
		preUpdateEvent(this);
		//Handle events on queue
		while (SDL_PollEvent(&e))
		{
			//User requests quit
			if (e.type == SDL_QUIT)
			{
				quit = true;
			}
			for each (Object* obj in objectPool)
			{
				obj->render();
			}
		}
		postUpdateEvent(this);
	}
}
