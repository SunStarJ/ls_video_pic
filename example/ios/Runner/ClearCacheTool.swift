//
//  ClearCacheTool.swift
//  Runner
//
//  Created by yunxiwangluo on 2020/4/20.
//  Copyright © 2020 The Chromium Authors. All rights reserved.
//

import UIKit

class ClearCacheTool {
    
    //获取图片缓存路径
    static func getImgCachePath()->String{
        
        var path: String = NSSearchPathForDirectoriesInDomains(FileManager.SearchPathDirectory.cachesDirectory, FileManager.SearchPathDomainMask.userDomainMask, true).last ?? ""
        path.append("/yytCacheImg")
        return path;
    }
    
    //获取路径文件大小
    static func getCacheSize(path: String)->String{
        
        let subPaths = FileManager.default.subpaths(atPath: path);
        
        var filePath = "";
        var totleSize: NSInteger = 0
        
        if let a = subPaths{
         
            for subpath in a {
             
                
                filePath = path.appending("/\(subpath)");
                
                let isExist = FileManager.default.fileExists(atPath: filePath)
                
                if (!isExist || filePath.contains(".DS")){
                    continue;
                }
                
                let dict = try? FileManager.default.attributesOfItem(atPath: filePath)
                
                if let d = dict{
                
                    let size = d[FileAttributeKey.size] as! UInt64
                    totleSize += Int(size);
                }
            }
        }
        
        var totleStr = ""
        if (totleSize > 1000*1000){
            totleStr = String(format: "%.2fM", Double(totleSize)/1000.00/1000.00);
        }else if (totleSize > 1000){
            totleStr = String(format: "%.2fKB", Double(totleSize)/1000.00);
        }else{
            totleStr = String(format: "%.2fB", Double(totleSize));
        }
        return totleStr;
        
    }
    
    //清除目标路径下文件
    static func clearCache(path: String){
        
        let subPathArr = try? FileManager.default.contentsOfDirectory(atPath: path)
        var filePath = "";
        
        if let arr = subPathArr{
            
            for subpath in arr {
                
                filePath = path.appending("/\(subpath)");
                
                try? FileManager.default.removeItem(atPath: filePath)

            }
        }
    }
}
