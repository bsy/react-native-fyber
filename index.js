//
// react-native-fyber
//
import React, {Component} from 'react';
import {NativeModules, Text, View, requireNativeComponent} from 'react-native'

//
class FyberBanner extends Component {

  constructor(props) {
    super(props)
  }

  render() {
    return (
      <View>
        <Text>Fyber Banner Ad - WIP</Text>
      </View>
    );
  }
}

const FyberUser = NativeModules['RNFyberUser'];
import FyberRewardedVideo from './RNFyberRewardedVideo';
import FyberOfferWall from './RNFyberOfferWall';

module.exports = {FyberBanner, FyberOfferWall, FyberRewardedVideo, FyberUser};
