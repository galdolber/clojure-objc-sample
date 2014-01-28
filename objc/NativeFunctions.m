#include "NativeFunctions.h"

@implementation NativeFunctions

+ (CGPoint)cgpointmake:(float)x y:(float)y {
    return CGPointMake(x, y);
}

+ (float)cgrectx:(id)cgrect {
  return [(NSValue*)cgrect CGRectValue].origin.x;
}

+ (float)cgrecty:(id)cgrect {
  return [(NSValue*)cgrect CGRectValue].origin.y;
}

+ (float)cgrectw:(id)cgrect {
  return [(NSValue*)cgrect CGRectValue].size.width;
}

+ (float)cgrecth:(id)cgrect {
  return [(NSValue*)cgrect CGRectValue].size.height;
}

+ (id)cgrectmake:(float)x
               y:(float)y
               w:(float)w
               h:(float)h {
  return [NSValue valueWithCGRect:CGRectMake(x, y, w, h)];
}

@end
