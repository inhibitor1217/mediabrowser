#import "MediabrowserPlugin.h"
#if __has_include(<mediabrowser/mediabrowser-Swift.h>)
#import <mediabrowser/mediabrowser-Swift.h>
#else
// Support project import fallback if the generated compatibility header
// is not copied when this plugin is created as a library.
// https://forums.swift.org/t/swift-static-libraries-dont-copy-generated-objective-c-header/19816
#import "mediabrowser-Swift.h"
#endif

@implementation MediabrowserPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftMediabrowserPlugin registerWithRegistrar:registrar];
}
@end
