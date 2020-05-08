import Flutter
import UIKit


public class SwiftLsvideopicPlugin: NSObject, FlutterPlugin {
    
   static var channelResult: FlutterResult!
    
  public static func register(with registrar: FlutterPluginRegistrar) {
    let channel = FlutterMethodChannel(name: "lsvideopic", binaryMessenger: registrar.messenger())
    let instance = SwiftLsvideopicPlugin()
    registrar.addMethodCallDelegate(instance, channel: channel)
    
    
    channel.setMethodCallHandler { (call:FlutterMethodCall,myResult: @escaping FlutterResult) in

        channelResult = myResult;

        switch call.method{
            case "getThumbPic":
                self.cropVideo(argument: call.arguments ?? {})
                break;
            default :
                break;

        }
    }
    
  }
    
    
    static func cropVideo(argument:Any){
        
        let dic: Dictionary<String,Any> = argument as! Dictionary<String,Any>;
        let second = dic["second"] as? Float64 ?? 0;
        
        let time = TimeInterval(second);
        let videopath = dic["videoPath"] ?? "";
        let image = LMTools.getVideoImageWithTime(time: time, videopath: videopath as! String)
        
        let imgData: Data = image!.pngData() ?? Data();
        var path: String = ClearCacheTool.getImgCachePath();
        
        let dateformate = DateFormatter()
        dateformate.dateFormat = "yyyyMMddHHmmss"
        
        let imgName = dateformate.string(from: Date()).appending(".png");
        
        try? FileManager.default.createDirectory(atPath: path, withIntermediateDirectories: true, attributes: nil)
        path.append("/")
        path.append(imgName)
        
        try? imgData.write(to: URL(fileURLWithPath: path));
        
        channelResult?(path);
        
    }
    

  public func handle(_ call: FlutterMethodCall, result: @escaping FlutterResult) {
    result("iOS " + UIDevice.current.systemVersion)
  }
}
