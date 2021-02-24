package io.inhibitor.mediabrowser;

import android.app.Activity;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;
import io.inhibitor.mediabrowser.util.Logger;
import io.inhibitor.mediabrowser.util.ThumbnailExtractor;

public class MediaBrowserPlugin implements FlutterPlugin, MethodCallHandler, ActivityAware {
  private MethodChannel channel;
  private MediaBrowserDelegate delegate;
  private ActivityPluginBinding activityPluginBinding;

  @Override
  public void onAttachedToEngine(FlutterPluginBinding flutterPluginBinding) {
    channel = new MethodChannel(flutterPluginBinding.getFlutterEngine().getDartExecutor(), "mediabrowser");
    channel.setMethodCallHandler(this);
  }

  public static void registerWith(Registrar registrar) {
    final MethodChannel channel = new MethodChannel(registrar.messenger(), "mediabrowser");
    channel.setMethodCallHandler(new MediaBrowserPlugin());
  }

  @Override
  public void onMethodCall(MethodCall call, Result result) {
    try {
      MediaBrowserPluginMethod method = MediaBrowserPluginMethod.fromName(call.method);

      switch (method) {
        case GetMediaList:
          delegate.listMedias(call, result);
          break;
        case RequestThumbnail:
          delegate.requestThumbnail(call, result);
          break;
      }
    } catch (IllegalArgumentException e) {
      result.notImplemented();
    }
  }

  @Override
  public void onDetachedFromEngine(FlutterPluginBinding binding) {
    channel.setMethodCallHandler(null);
  }

  @Override
  public void onAttachedToActivity(ActivityPluginBinding activityPluginBinding) {
    this.activityPluginBinding = activityPluginBinding;
    setup();
  }

  @Override
  public void onDetachedFromActivityForConfigChanges() {
    this.activityPluginBinding = null;
    teardown();
  }

  @Override
  public void onReattachedToActivityForConfigChanges(ActivityPluginBinding activityPluginBinding) {
    this.activityPluginBinding = activityPluginBinding;
    setup();
  }

  @Override
  public void onDetachedFromActivity() {
    this.activityPluginBinding = null;
    teardown();
  }

  private void setup() {
    Activity activity = activityPluginBinding.getActivity();

    delegate = new MediaBrowserDelegate(activity,
                                        new Logger("mediabrowser"),
                                        new ThumbnailExtractor());
    activityPluginBinding.addRequestPermissionsResultListener(delegate);
  }

  private void teardown() {
    activityPluginBinding.removeRequestPermissionsResultListener(delegate);
    delegate = null;
  }
}
