//
//  RNFyberOfferWall.m
//  RNFyberOfferWall
//
//  Created by Ben Yee <benyee@gmail.com> on 5/20/16.
//
#import "RNFyberOfferWall.h"

NSString *const kOfferWallAvailable = @"offerWallAvailable";
NSString *const kOfferWallUnavailable = @"offerWallUnavailable";
NSString *const kOfferWallFailedToLoad = @"offerWallFailedToLoad";
NSString *const kOfferWallDidStart = @"offerWallDidStart";
NSString *const kOfferWallClosed = @"offerWallClosed";

@implementation RNFyberOfferWall {
}

@synthesize bridge = _bridge;

// Run on the main thread
- (dispatch_queue_t)methodQueue
{
  return dispatch_get_main_queue();
}

RCT_EXPORT_MODULE();

// Initialize Fyber before showing the offer wall
RCT_EXPORT_METHOD(initializeOfferWall:(NSString *)appId securityToken:(NSString *)securityToken userId:(NSString *)userId)
{
  FYBSDKOptions *options = [FYBSDKOptions optionsWithAppId:appId
                                                    userId:userId
                                             securityToken:securityToken];
  [FyberSDK startWithOptions:options];
}

// Request the video before we need to display it
RCT_EXPORT_METHOD(requestOfferWall:(RCTResponseSenderBlock)callback)
{
    NSLog(@"requestOfferWall!");
    // Fyber iOS seems to have always Offers ...
    [self sendEventWithName:kOfferWallAvailable body:nil];
    callback(@[[NSNull null]]);
}

//
// Show the Offer Wall
//
RCT_EXPORT_METHOD(showOfferWall)
{

  FYBOfferWallViewController *offerWallViewController = [FyberSDK offerWallViewController];
  [offerWallViewController presentFromViewController:[UIApplication sharedApplication].delegate.window.rootViewController animated:YES completion:^{

      NSLog(@"Offer Wall presented");
      [self sendEventWithName:kOfferWallDidStart body:nil];

  } dismiss:^(NSError *error) {

    //
    // Code executed when the Offer Wall is dismissed
    // If an error occurred, the error parameter describes the error otherwise this value is nil
    //
    if (error) {
      NSLog(@"Offer Wall error");
    }

    [self sendEventWithName:kOfferWallClosed body:nil];
  }];
}

@end
