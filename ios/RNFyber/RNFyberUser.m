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
    if (uprops[@"age"] != nil && [uprops[@"age"] respondsToSelector:@selector(integerValue)]) {
        [[FyberSDK instance].user setAge:[uprops[@"age"] integerValue]];
    }
    if (uprops[@"custom"] != nil && [uprops[@"custom"] isKindOfClass:[NSDictionary class]]) {
        [[FyberSDK instance].user setCustomParameters:uprops[@"custom"]];
    }

    if (uprops[@"userid"] != nil && [uprops[@"userid"] isKindOfClass:[NSString class]]) {
        [FyberSDK instance].userId = uprops[@"userid"];
    }
}

@end
