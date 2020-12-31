import .sdl

import .state

import .game

local curState : state = (game)

local event : sdl.Event
local shouldQuit : bool = false

let screenWidth screenHeight winTitle = ('windowInfo curState)

sdl.Init sdl.INIT_VIDEO

let window = 
    sdl.CreateWindow
        winTitle
        sdl.WINDOWPOS_UNDEFINED
        sdl.WINDOWPOS_UNDEFINED
        screenWidth
        screenHeight
        sdl.WINDOW_SHOWN

let surface = (sdl.GetWindowSurface window)

'init curState

while (not shouldQuit)
    while ((sdl.PollEvent &event) != 0)
        if (event.type == sdl.QUIT)
            shouldQuit = true
        elseif (event.type == sdl.KEYDOWN)
            'keydown curState event.key.keysym.sym
    
    'draw curState surface

    sdl.UpdateWindowSurface window

    'update curState (1 / 60) #fake deltatime at the moment

    sdl.Delay 16

sdl.DestroyWindow window

sdl.Quit;