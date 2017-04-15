#pragma once
#include "SDL.h"
#include "util.h"
#include "iostream"
#include "vector"

#define ERROR_BUFFER 200
//Screen dimension constants
extern int SCREEN_WIDTH;
extern int SCREEN_HEIGHT;
void Error(char* message);
void Debug(char* message);
void Debug(const char * message);
void Debug(std::string message);
//TODO: 
class Texture
{
public:
	Texture();
	Texture(int id);
	~Texture();
	void close();
	SDL_Texture* texture;
	char* name = NULL;
	Uint32 colorMod = 0;//颜色调整
	Uint8 alphaMod = 10;//透明度
	Texture* setName(char* name);
	Texture* setColorMod(Uint32 colorMod);
	Texture* setAlphaMod(Uint8 alphaMod);
	int getID();
private:
	int id = 0;
};
class Csdl
{
public:
	std::vector<Texture*> texturePool;
	~Csdl();
	void close();
	SDL_Window* window = NULL;
	SDL_Surface* screenSurface = NULL;
	SDL_Renderer* renderer = NULL;
	void Init(char* title, int width, int height);
	Texture* Csdl::loadTexture(char* path, char* name, Uint8 colorkey_r = NULL, Uint8 colorkey_g = NULL, Uint8 colorkey_b = NULL);
	void Run();
};
class Object
{
public:
	Object();
	Texture* texture = NULL;
	char* name = "Object";
	SDL_Rect* srcRect = NULL;
	SDL_Rect* dstRect = NULL;
	SDL_Color* colorMod = NULL;
	float angle;
	SDL_Point* center = NULL;
	SDL_RendererFlip flip = SDL_FLIP_NONE;
	Object* setTexture(Texture* texture);
	Object* setName(char* name);
	void render(Csdl* csdl);
};