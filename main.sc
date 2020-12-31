import .sdl

import .state

import .game

using import enum

inline gen-method (name)
    inline (self args...) 
        'apply self 
            inline (T self) 
                name self args...

enum State
    gameState : game

    let init = (gen-method 'init)
    let update = (gen-method 'update)
    let keyDown = (gen-method 'keyDown)
    let draw = (gen-method 'draw)
    let windowInfo = (gen-method 'windowInfo)

local curState : State = (State.gameState (game))

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
            'keyDown curState event.key.keysym.sym
    
    'draw curState surface

    sdl.UpdateWindowSurface window

    'update curState (1 / 60) #fake deltatime at the moment

    sdl.Delay 16

sdl.DestroyWindow window

sdl.Quit;