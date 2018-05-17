//
//  RNFyberUser.m
//  RNFyberUser
//
//  Created by Heiko Weber <heiko@wecos.de> on 5/17/18.
//
#import "RNFyberUser.h"

@implementation RNFyberUser {
}

// Run on the main thread
- (dispatch_queue_t)methodQueue
{
  return dispatch_get_main_queue();
}

RCT_EXPORT_MODULE();

// set Fyber user properties
RCT_EXPORT_METHOD(set:(NSDictionary *)uprops)
{
    id obj = uprops[@"age"];
    if (obj != (id)[NSNull null]) {
        [[FyberSDK instance].user setAge:[obj integerValue]];
    }
    obj = uprops[@"custom"];
    if (obj != (id)[NSNull null]) {
        [[FyberSDK instance].user setCustomParameters:obj];
    }
}

@end
