import .state

import .sdl

import .game

using import struct

struct splash < game
    fn windowInfo(self)
        returning i32 i32 string
        _
            self.mapWidth * self.tileSize
            self.mapHeight * self.tileSize
            "Snake"