typedef state < Struct
    fn init(self)
        ;
    
    fn windowInfo(self) #width, height, title
        returning i32 i32 string

    fn update(self dt)
        ;
    
    fn draw(self surface)
        ;
    
    fn keyDown(self key)
        ;