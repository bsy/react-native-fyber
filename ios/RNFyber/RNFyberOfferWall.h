//
//  RNFyberOfferWall.h
//  RNFyberOfferWall
//
//  Created by Ben Yee <benyee@gmail.com> on 5/20/16.
//
#if __has_include(<React/RCTBridgeModule.h>)
#import <React/RCTBridgeModule.h>
#else
#import "RCTBridgeModule.h"
#endif

#if __has_include(<React/RCTEventEmitter.h>)
#import <React/RCTEventEmitter.h>
#else
#import "RCTEventEmitter.h"
#endif

#import "FyberSDK.h"

@interface RNFyberOfferWall : RCTEventEmitter <RCTBridgeModule, FYBRewardedVideoControllerDelegate>
@end
