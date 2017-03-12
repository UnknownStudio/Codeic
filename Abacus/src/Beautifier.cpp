/*
 * Beautifier.cpp
 * 
 * This file is part of the "Abacus" project (Copyright (c) 2015 by Lukas Hermanns)
 * See "LICENSE.txt" for license information.
 */

#include "Beautifier.h"

#include <sstream>
#include <algorithm>


namespace Ac
{


void BeautifyLiteral(std::string& s, std::size_t maxExp)
{
    if (s.empty())
        return;

    /* Check and adjust sign */
    std::size_t signChars = 0;

    if (s.front() == '+')
        s.erase(s.begin());
    else if (s.front() == '-')
        signChars = 1;

    /* Find 'E' */
    auto posE = s.find('E');
    if (posE == std::string::npos)
        return;

    /* Get exponent value */
    auto expStr = s.substr(posE + 1);
    auto posDot = s.find('.');

    if (expStr.size() < 4)
    {
        /* Get exponent as integer */
        int exp = 0;
        std::istringstream stream(expStr);
        stream >> exp;

        if (static_cast<std::size_t>(std::abs(exp)) < maxExp)
        {
            /* Remove last characters "E0", "E-1", "E1" etc. */
            s.resize(s.size() - expStr.size() - 1);

            if (exp > 0)
            {
                if (posDot == std::string::npos)
                    s += std::string(exp, '0');
                else
                {
                    while (exp > 0)
                    {
                        /* Move '.' right */
                        if (posDot + 1 < s.size())
                            std::swap(s[posDot], s[posDot + 1]);

                        ++posDot;
                        --exp;

                        /* Remove '.' if end has reached and append remaining zeros */
                        if (s.back() == '.')
                        {
                            s.pop_back();
                            s += std::string(exp, '0');
                            break;
                        }
                    }
                }
            }

            /* Insert missing '.' */
            if (exp < 0)
            {
                if (posDot == std::string::npos)
                {
                    posDot = posE;
                    s.insert(s.begin() + posDot, '.');
                }

                while (exp < 0)
                {
                    if (posDot > 1 + signChars)
                    {
                        /* Move '.' left */
                        std::swap(s[posDot - 1], s[posDot]);
                        --posDot;
                    }
                    else if (posDot == 1 + signChars)
                    {
                        /* Move '.' left and insert zero after '.' */
                        std::swap(s[posDot - 1], s[posDot]);
                        s.insert(s.begin() + signChars, '0');
                        --posDot;
                    }
                    else
                    {
                        /* Insert zero after '.' */
                        s.insert(s.begin() + 2 + signChars, '0');
                    }

                    ++exp;
                }
            }

            return;
        }
    }

    /* Replace 'E' by " * 10^" */
    s.replace(posE, 1, " * 10^");
}


} // /namespace Ac



// ================================================================================