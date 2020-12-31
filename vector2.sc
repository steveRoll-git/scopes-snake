using import struct

struct vector2 plain
    x : i32
    y : i32

    inline __== (lhsT rhsT)
        # compile time if, works on constant expressions
        static-if (lhsT == rhsT)
            inline (self other)
                and 
                    self.x == other.x
                    self.y == other.y

        else
            # if an op metamethod returns nothing, it will error with op not
              defined for these types.
            ;
    
    inline __neg (val)
        this-type
            x = (- val.x)
            y = (- val.y)