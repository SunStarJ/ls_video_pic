//
//  VideoCropImgSupport.swift
//  Runner
//
//  Created by yunxiwangluo on 2020/5/8.
//  Copyright © 2020 The Chromium Authors. All rights reserved.
//

import Foundation
import Flutter

class VideoCropImgSupport {

    var description: String
    
    var channelResult: FlutterResult!
    
    init(mController:FlutterViewController) {
        
        buildSupport(mController: mController);
        
    }
    
    func buildSupport(mController:FlutterViewController){
           
        let _imageChannel = FlutterMethodChannel(name: "lsvideopic", binaryMessenger: mController.binaryMessenger) //注册方法监听

        _imageChannel.setMethodCallHandler { (call:FlutterMethodCall,myResult: @escaping FlutterResult) in
            
            self.channelResult = myResult;
            
            switch call.method{
                case "getThumbPic":
                    self.cropVideo(argument: call.arguments ?? {})
                    break;
                default :
                    break;
                
            }
        }
       }
    
    
     func cropVideo(argument:Any){
        
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
        
        self.channelResult?(path);
        
    }
    
    
}
