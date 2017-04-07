#include "Header.h"
#include "csdl.h"

Texture::Texture()
{

}

Texture::Texture(int id)
{
	this->id = id;
}

Texture * Texture::setName(char * name)
{
	this->name = name;
	return this;
}

Texture * Texture::setColorKey(Uint32 colorKey)
{
	this->colorKey = colorKey;
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
