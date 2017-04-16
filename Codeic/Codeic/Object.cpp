#include "csdl.h"

Object::Object()
{
	if(createEvent!=NULL)createEvent(csdl);
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

void Object::render()
{
	if(preRenderEvent!=NULL)preRenderEvent(csdl);
	if(colorMod!=NULL)
		SDL_SetTextureColorMod(texture->texture, colorMod->r, colorMod->g, colorMod->b);
	SDL_SetTextureBlendMode(texture->texture, SDL_BLENDMODE_BLEND);
	if (colorMod != NULL)
		SDL_SetTextureAlphaMod(texture->texture, colorMod->a);
	SDL_RenderCopyEx(csdl->renderer , texture->texture, srcRect, dstRect, angle, center, flip);
	if(postRenderEvent!=NULL)postRenderEvent(csdl);
}
