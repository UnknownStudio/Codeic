#pragma once

#include<tchar.h>
#include<Windows.h>

char *localeToUTF8(char *src);
struct Point
{
	float x;
	float y;
	Point();
	Point(float x, float y);
};