//
//  CViewController.h
//  sample
//
//  Created by Gal Dolber on 1/26/14.
//  Copyright (c) 2014 clojure-objc. All rights reserved.
//

#import <UIKit/UIKit.h>

// UIViewController cannot be nsproxy'd
// http://stackoverflow.com/questions/17927639/adding-a-root-view-controller-ocmockobjectuiviewcontroller-as-a-child-view-con

@interface UIKitController : UIViewController

-initWithView:(UIView*)view withScope:(id)scope;

@end
