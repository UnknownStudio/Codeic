#include "Header.h"
#include "csdl.h"

Texture::Texture()
{

}

Texture::Texture(int id)
{
	this->id = id;
}

Texture::~Texture()
{
	close();
}

void Texture::close()
{
	SDL_DestroyTexture(texture);
	texture = NULL;
}

Texture * Texture::setName(char * name)
{
	this->name = name;
	return this;
}

Texture * Texture::setColorMod(Uint32 colorMod)
{
	this->colorMod = colorMod;
	return this;
}

Texture * Texture::setAlphaMod(Uint8 alphaMod)
{
	this->alphaMod = alphaMod;
	return this;
}

int Texture::getID()
{
	return this->id;
}
