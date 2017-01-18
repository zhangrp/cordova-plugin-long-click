//
//  LongClickPlugin.h
//
//  Created by 张锐平 zhangrp on 17/1/13.
//

#import <Cordova/CDVPlugin.h>
#import <Cordova/CDV.h>

@interface LongClickPlugin : CDVPlugin

@property (nonatomic,strong) UILongPressGestureRecognizer *lpgr;
@property (nonnull,strong) NSString *longClickDoScript;
@end
