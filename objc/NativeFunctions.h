//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: Users/admin/github/clojure-objc-sample/jvm/objc/NativeFunctions.java
//
//  Created by admin on 1/26/14.
//

#import <UIKit/UIKit.h>
#import <Foundation/Foundation.h>

#ifndef _ObjcNativeFunctions_H_
#define _ObjcNativeFunctions_H_

@interface NativeFunctions : NSObject {
}

+ (CGPoint)cgpointmake:(float)x y:(float)y;
+ (float)cgrectx:(id)cgrect;
+ (float)cgrecty:(id)cgrect;
+ (float)cgrectw:(id)cgrect;
+ (float)cgrecth:(id)cgrect;
+ (id)cgrectmake:(float)x
                y:(float)y
                w:(float)w
                h:(float)h;

@end

#endif // _ObjcNativeFunctions_H_