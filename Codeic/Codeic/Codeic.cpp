// Codeic.cpp : Defines the entry point for the console application.
//

#include "stdafx.h"
#include "Header.h"
using namespace std;
extern "C" {
#include <lua.h>  
#include <lualib.h>  
#include <lauxlib.h>  
}

//int main()
//{
//    return 0;
//}

//#pragma comment(lib, "lua5.1.lib")  
lua_State* L;
static int average(lua_State *L)
{
	//����ջ��Ԫ�صĸ���  
	int n = lua_gettop(L);
	double sum = 0;
	int i;
	for (i = 1; i <= n; i++)
	{
		if (!lua_isnumber(L, i))
		{
			lua_pushstring(L, "Incorrect argument to 'average'");
			lua_error(L);
		}
		sum += lua_tonumber(L, i);
	}
	/* push the average */
	lua_pushnumber(L, sum / n);
	/* push the sum */
	lua_pushnumber(L, sum);

	/* return the number of results */
	return 2;
}
int main(int argc, char*argv[])
{
	/* initialize Lua */
	// ����һ��ָ��Lua��������ָ�롣
	L = luaL_newstate();
	//��������Lua��
	luaL_openlibs(L);
	/* load Lua libraries */
	luaL_openlibs(L);
	/* register our function */
	lua_register(L, "average", average);
	/* run the script */
	luaL_dofile(L, "e15.lua");

	//lua_getglobal(L, "avg");
	//cout << "avg is:" << lua_tointeger(L, -1) << endl;
	//lua_pop(L, 1);
	//lua_getglobal(L, "sum");
	//cout << "sum is:" << lua_tointeger(L, -1) << endl;
	/* cleanup Lua */
	lua_close(L);
	cin.get();
	return 0;
}