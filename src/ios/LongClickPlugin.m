//
//  LongClickPlugin.m
//
//  Created by 张锐平 zhangrp on 17/1/13.


#import "LongClickPlugin.h"

@interface LongClickPlugin ()

@end

@implementation LongClickPlugin

-(void)pluginInitialize {
    self.lpgr = [[UILongPressGestureRecognizer alloc] initWithTarget:self action:@selector(handleLongPressGestures:)];
    self.lpgr.minimumPressDuration = 0.45f;

    // 0.45 is ok for 'regular longpress', 0.05-0.08 is required for '3D Touch longpress',
    // but since this will also kill onclick handlers (not ontouchend), I made it optional
    if ([self.commandDelegate.settings objectForKey:@"suppress3dtouch"] && [[self.commandDelegate.settings objectForKey:@"suppress3dtouch"] boolValue]) {
        self.lpgr.minimumPressDuration = 0.05f;
    }
    self.lpgr.allowableMovement = 100.0f;



    self.longClickDoScript = [[self.commandDelegate settings] objectForKey:@"longclickdoscript"];
    NSLog(@"longclickdoscript=%@", self.longClickDoScript);

    NSArray *views = self.webView.subviews;
    if (views.count == 0) {
        NSLog(@"No webview subviews found, not applying the longClick fix");
        return;
    }
    for (int i=0; i<views.count; i++) {
        UIView *webViewScrollView = views[i];
        if ([webViewScrollView isKindOfClass:[UIScrollView class]]) {
            NSArray *webViewScrollViewSubViews = webViewScrollView.subviews;
            UIView *browser = webViewScrollViewSubViews[0];
            [browser addGestureRecognizer:self.lpgr];
            NSLog(@"Applied longClick fix");
            break;
        }
    }
}

- (void) handleLongPressGestures:(UILongPressGestureRecognizer *)gestureRecognizer {
    if (gestureRecognizer.state ==
        UIGestureRecognizerStateBegan) {
        NSLog(@"long click begin");
    }
    if (gestureRecognizer.state ==
        UIGestureRecognizerStateChanged) {
        NSLog(@"long click change");
    }

    if (gestureRecognizer.state == UIGestureRecognizerStateEnded) {
        NSLog(@"long click end");
        NSLog(@"LongClickPlugin doJavascript [%@]", self.longClickDoScript);
        NSString *js = [NSString stringWithFormat:@"%@", self.longClickDoScript];
        [self.webViewEngine evaluateJavaScript:js completionHandler:nil];
        NSLog(@"LongClickPlugin doJavascript [%@] success", self.longClickDoScript);
    }
}

@end

