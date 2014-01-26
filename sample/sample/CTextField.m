//
//  MyTextField.m
//  Zuldi
//
//  Created by Gal Dolber on 9/28/13.
//  Copyright (c) 2013 zuldi. All rights reserved.
//

#import "CTextField.h"

@implementation CTextField

- (id)initWithFrame:(CGRect)frame
{
    self = [super initWithFrame:frame];
    if (self) {
        self.delegate = self;
        
        self.contentVerticalAlignment = UIControlContentVerticalAlignmentCenter;
        
        // Without this we get EXC_BAD_ACCESS on the simulator
        self.autocorrectionType = UITextAutocorrectionTypeNo;
    }
    return self;
}

-(BOOL)textFieldShouldReturn:(UITextField *)textField {
    [self resignFirstResponder];
    return YES;
}

@end
