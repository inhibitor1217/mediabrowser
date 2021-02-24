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

public class MediaBrowserPlugin implements FlutterPlugin, MethodCallHandler, ActivityAware {
  private MethodChannel channel;
  private MediaBrowserDelegate delegate;

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
      Method method = Method.fromName(call.method);

      switch (method) {
        case GetMediaList:
          delegate.listMedias(call, result);
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
    setup(activityPluginBinding.getActivity());
  }

  @Override
  public void onDetachedFromActivityForConfigChanges() {
    teardown();
  }

  @Override
  public void onReattachedToActivityForConfigChanges(ActivityPluginBinding activityPluginBinding) {
    setup(activityPluginBinding.getActivity());
  }

  @Override
  public void onDetachedFromActivity() {
    teardown();
  }

  private void setup(Activity activity) {
    delegate = new MediaBrowserDelegate(activity, new Logger("mediabrowser"));
  }

  private void teardown() {
    delegate = null;
  }
}
