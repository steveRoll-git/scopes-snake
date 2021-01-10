import .state

import .sdl

import .vector2

import .prng
global rng : prng.random.RNG 0

using import array

using import struct

struct game < state
    mapWidth : i32 = 16
    mapHeight : i32 = 16

    tileSize : i32 = 32

    snakeHeadPos : vector2

    bodyParts : (Array vector2)

    moveDir : vector2
    futureDir : vector2

    foodPos : vector2
    
    initialLength : i32 = 3
    currentLength : i32

    fn init(self)
        self.running = true

        self.bodyParts = ((Array vector2))

        self.snakeHeadPos =
            vector2
                x =
                    self.mapWidth // 2
                y = 
                    self.mapHeight // 2

        self.moveDir = (vector2 (x = 1) (y = 0))

        self.futureDir = self.moveDir

        self.currentLength = self.initialLength

        'randomizeFood self;

    fn randomizeFood(self)
        self.foodPos.x = (((rng) % self.mapWidth) as i32)
        self.foodPos.y = (((rng) % self.mapWidth) as i32)

    fn tick(self)
        if (self.futureDir != -self.moveDir)
            self.moveDir = self.futureDir
        'append self.bodyParts self.snakeHeadPos
        if ((countof self.bodyParts) > self.currentLength)
            'remove self.bodyParts 0
        self.snakeHeadPos.x += self.moveDir.x
        self.snakeHeadPos.y += self.moveDir.y
        if (self.snakeHeadPos == self.foodPos)
            self.currentLength += 1
            'randomizeFood self;
        
        if
            or
                (self.snakeHeadPos.x < 0)
                (self.snakeHeadPos.x >= self.mapWidth)
                (self.snakeHeadPos.y < 0)
                (self.snakeHeadPos.y >= self.mapHeight)
            
            'fail self
            return

        for pos in self.bodyParts
            if (self.snakeHeadPos == pos)
                'fail self
                break

    fn keyDown(self key)
        switch key
        case sdl.SDLK_DOWN
            self.futureDir = (vector2 (x = 0) (y = 1))
        case sdl.SDLK_UP
            self.futureDir = (vector2 (x = 0) (y = -1))
        case sdl.SDLK_RIGHT
            self.futureDir = (vector2 (x = 1) (y = 0))
        case sdl.SDLK_LEFT
            self.futureDir = (vector2 (x = -1) (y = 0))
        case sdl.SDLK_r
            'init self
        default
            ;
        
    running : bool = true
    tickTimer : f32 = 0
    tickInterval : f32 = 0.2

    fn update(self dt)
        if self.running
            self.tickTimer += dt
            if (self.tickTimer >= self.tickInterval)
                self.tickTimer = 0
                'tick self;

    fn drawTile(self surface x y color)
        local tempRect = 
            sdl.Rect
                x * self.tileSize
                y * self.tileSize
                self.tileSize
                self.tileSize
        sdl.FillRect
            surface
            &tempRect
            color as u32

    fn windowInfo(self)
        returning i32 i32 string
        _
            self.mapWidth * self.tileSize
            self.mapHeight * self.tileSize
            "Snake"

    fn draw(self surface)
        #clear the screen to black
        sdl.FillRect surface null 0

        #draw head
        'drawTile self surface self.snakeHeadPos.x self.snakeHeadPos.y 0x00ff00

        #draw body
        for pos in self.bodyParts
            'drawTile self surface pos.x pos.y 0x00ff00
        
        #draw food
        'drawTile self surface self.foodPos.x self.foodPos.y 0xff0000

    fn fail(self)
        self.running = false
        sdl.ShowSimpleMessageBox
            sdl.MESSAGEBOX_INFORMATION
            "You lose!"
            "Score: " .. (tostring (self.currentLength - self.initialLength)) .. "\nPress R to restart"
            null
