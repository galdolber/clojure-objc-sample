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
#import "clojure/lang/ObjC.h"
#import "ReplClient.h"
#import "sample/core_main.h"

@implementation AppDelegate

- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions
{
    [ClojureLangObjC setObjC];
    [ClojureLangRT load__WithNSString:@"clojure/core"];

    // to start repl: uncomment, start jvm repl and call (remote-repl)
    [ReplClient connect:@"localhost"];
    return YES;
    
    // to run app
    [ClojureLangRT load__WithNSString:@"sample/core"];
    [Samplecore_main_get_VAR_() invoke];
    return YES;
}

@end
