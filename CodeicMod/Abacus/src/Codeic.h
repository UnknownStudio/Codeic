#pragma once

#define MYLIBAPI  extern   "C++"     __declspec( dllexport )

MYLIBAPI std::string Compute(std::string expr);

MYLIBAPI bool Run();