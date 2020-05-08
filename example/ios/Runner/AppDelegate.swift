import UIKit
import Flutter

@UIApplicationMain
@objc class AppDelegate: FlutterAppDelegate {
  override func application(
    _ application: UIApplication,
    didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?
  ) -> Bool {
    GeneratedPluginRegistrant.register(with: self)
    
    let controller :FlutterViewController = window?.rootViewController as! FlutterViewController;
    let _imageChannel = FlutterMethodChannel(name: "lsvideopic", binaryMessenger: controller.binaryMessenger) //注册方法监听

    _imageChannel.setMethodCallHandler { (call:FlutterMethodCall,myResult: @escaping FlutterResult) in

        print(call.method,call.arguments);

    }
    
    
    return super.application(application, didFinishLaunchingWithOptions: launchOptions)
  }
}
