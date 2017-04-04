#pragma once
#include "SDL.h"
#include "util.h"

#define ERROR_BUFFER 200
//Screen dimension constants
extern int SCREEN_WIDTH;
extern int SCREEN_HEIGHT;
void Error(char* message);
void Debug(char* message);
class Csdl
{
public:
	SDL_Window* window = NULL;
	SDL_Surface* screenSurface = NULL;
	SDL_Renderer* renderer = NULL;
	void Init(char * title, int width, int height, Uint32 backGroundColor);
};
//TODO: 
class Texture
{
public:
	SDL_Texture* texture;
	char* name = NULL;
	static void loadTexture(char* path);
	Uint32 colorKey = 0;//将被透明的颜色
	Uint32 colorMod = 0;//颜色调整
	Uint8 alphaMod = 10;//透明度
	Texture* setName(char* name);
	Texture* setColorKey(Uint32 colorKey);
	Texture* setColorMod(Uint32 colorMod);
	Texture* setAlphaMod(Uint8 alphaMod);
	int getID();
private:
	int id = 0;
};