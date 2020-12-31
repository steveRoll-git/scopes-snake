load-library (module-dir .. "/SDL2.dll")

let sdl = 
    include
        "SDL_include/SDL.h"

# vvv bind [name] is like let [name] =  but saves one indentation. The vvv decorator
# allows us to target the expression right below it. Just memorize it for now.
vvv bind sdl 
do
    using sdl.extern
    using sdl.const
    using sdl.define
    using sdl.typedef

    inline WINDOWPOS_UNDEFINED_DISPLAY (X) 
        SDL_WINDOWPOS_UNDEFINED_MASK | X
    let WINDOWPOS_UNDEFINED = (WINDOWPOS_UNDEFINED_DISPLAY 0)

    locals;

let sdl = 
    # we start with the original scope, and iterate on its keys
    fold (scope = sdl) for k v in sdl
        # k is an untyped value. We unbox it as a Symbol, then convert to string
        let name = ((k as Symbol) as string)
        # pattern match the prefix. Could use the start end stuff here, this
          is an older snippet before that was added.
        if ('match? "^SDL_" name)
            # create a new name without the prefix
            let new-name = (rslice name (countof "SDL_"))
            # bind returns a new scope that is identical to the original, but
              with the new name added.
            'bind scope (Symbol new-name) v
        else
            # if no match, just proceed without change
            scope