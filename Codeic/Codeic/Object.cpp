#include "csdl.h"

Object::Object()
{

}

Object* Object::setTexture(Texture* texture)
{
	this->texture = texture;
	return this;
}

Object * Object::setName(char * name)
{
	this->name = name;
	return this;
}

void Object::render(Csdl* csdl)
{
	SDL_SetTextureColorMod(texture->texture, colorMod->r, colorMod->g, colorMod->b);
	SDL_SetTextureBlendMode(texture->texture, SDL_BLENDMODE_BLEND);
	SDL_SetTextureAlphaMod(texture->texture, colorMod->a);
	SDL_RenderCopyEx(csdl->renderer , texture->texture, srcRect, dstRect, angle, center, flip);
}
