//
//  LMTools.swift
//  Runner
//
//  Created by yunxiwangluo on 2020/5/8.
//  Copyright © 2020 The Chromium Authors. All rights reserved.
//

import UIKit
import AVFoundation
import AVKit

class LMTools {

    //获取视频某一时间的画面
    static func getVideoImageWithTime(time: TimeInterval,videopath: String)->UIImage?{
        
        let videoUrl = URL(fileURLWithPath: videopath);
    
            let asset: AVURLAsset = AVURLAsset(url: videoUrl)
            let assetGen: AVAssetImageGenerator = AVAssetImageGenerator(asset: asset)
            assetGen.appliesPreferredTrackTransform = true;
            
            let imageRef: CGImage? = try? assetGen.copyCGImage(at: CMTimeMakeWithSeconds(time, preferredTimescale: asset.duration.timescale), actualTime: nil)
            if let imgRef = imageRef{
                
                return UIImage(cgImage: imgRef);
                
            }else{
                print("没有截取到画面")
            }
        
        return nil;
    }
    
}
