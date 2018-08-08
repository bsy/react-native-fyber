import { NativeModules, NativeEventEmitter } from 'react-native';

const RNFyberOfferWall = NativeModules.RNFyberOfferWall;
const RNFyberOfferWallEventEmitter = new NativeEventEmitter(RNFyberOfferWall);

const eventHandlers = {
    offerWallAvailable: new Map(),
    offerWallUnavailable: new Map(),
    offerWallFailedToLoad: new Map(),
    offerWallDidStart: new Map(),
    offerWallClosed: new Map()
};

const addEventListener = (type, handler) => {
    switch (type) {
        case 'offerWallAvailable':
            eventHandlers[type].set(handler, RNFyberOfferWallEventEmitter.addListener(type, handler));
            break;
        case 'offerWallUnavailable':
            eventHandlers[type].set(handler, RNFyberOfferWallEventEmitter.addListener(type, handler));
            break;
        case 'offerWallFailedToLoad':
            eventHandlers[type].set(handler, RNFyberOfferWallEventEmitter.addListener(type, type, (error) => { handler(error); }));
            break;
        case 'offerWallDidStart':
            eventHandlers[type].set(handler, RNFyberOfferWallEventEmitter.addListener(type, handler));
            break;
        case 'offerWallClosed':
            eventHandlers[type].set(handler, RNFyberOfferWallEventEmitter.addListener(type, handler));
            break;
        default:
            console.log(`Event with type ${type} does not exist.`);
    }
}

const removeEventListener = (type, handler) => {
    if (!eventHandlers[type].has(handler)) {
        return;
    }
    eventHandlers[type].get(handler).remove();
    eventHandlers[type].delete(handler);
}

const removeAllListeners = () => {
    RNFyberOfferWallEventEmitter.removeAllListeners('offerWallAvailable');
    RNFyberOfferWallEventEmitter.removeAllListeners('offerWallUnavailable');
    RNFyberOfferWallEventEmitter.removeAllListeners('offerWallFailedToLoad');
    RNFyberOfferWallEventEmitter.removeAllListeners('offerWallDidStart');
    RNFyberOfferWallEventEmitter.removeAllListeners('offerWallClosed');
};

module.exports = {
    ...RNFyberOfferWall,
    requestOfferWall: (cb = () => {}) => RNFyberOfferWall.requestOfferWall(cb),
    showOfferWall: () => RNFyberOfferWall.showOfferWall(),
    addEventListener,
    removeEventListener,
    removeAllListeners
};
