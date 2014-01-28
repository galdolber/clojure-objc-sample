//
//  AppDelegate.m
//  sample
//
//  Created by Gal Dolber on 1/24/14.
//  Copyright (c) 2014 clojure-objc. All rights reserved.
//

#import "AppDelegate.h"
#import "clojure/lang/RT.h"
#import "clojure/lang/Var.h"
#import "clojure/lang/ISeq.h"
#import "clojure/lang/ObjC.h"
#import "clojure/lang/Selector.h"
#import "clojure/lang/PersistentHashMap.h"
#import "java/lang/Character.h"
#import "java/lang/Boolean.h"
#import "java/lang/Integer.h"
#import "java/lang/Double.h"
#import "java/lang/Float.h"
#import "java/lang/Long.h"
#import "java/lang/Short.h"
#import <UIKit/UIKit.h>
#import "NSProxyImpl.h"

@implementation AppDelegate

- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions
{
    [ClojureLangObjC setObjC];
    [ClojureLangRT load__WithNSString:@"clojure/core"];
    [ClojureLangRT load__WithNSString:@"clojure_objc_sample/core"];
    [[ClojureLangRT varWithNSString:@"clojure-objc-sample.core" withNSString:@"main"] invoke];
    return YES;
}

@end
