//
//  CViewController.m
//  sample
//
//  Created by Gal Dolber on 1/26/14.
//  Copyright (c) 2014 clojure-objc. All rights reserved.
//

#import "CViewController.h"
#import "clojure/lang/AFn.h"
#import "clojure/lang/Var.h"
#import "clojure/lang/RT.h"

static ClojureLangAFn* dealloc;

@implementation CViewController {
    id scope;
}

+(void)initialize {
    dealloc = [[ClojureLangRT varWithNSString:@"clojure-objc-sample.uikit" withNSString:@"dealloc"] deref];
}

-initWithView:(UIView*)view withScope:(id)s {
    self = [super init];
    if (self) {
        [self setView:[view retain]];
        scope = [s retain];
    }
    return self;
}

-(void)dealloc {
    [dealloc invokeWithId:scope];
    [scope release];
    [self.view release];
    [super dealloc];
}

@end
