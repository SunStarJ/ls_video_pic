# lsvideopic

Flutter 视频裁图功能

## 如何试用
**1.导入**<br>
`
import 'package:lsvideopic/ls_video_pic.dart';
`<br>
**2.使用**<br>
```
 ///@param videoPath 视频路径
 ///@param milliseconds 毫秒值
 LsVideoPic.videoFrameImagePath(videoPath, timeStemp)
           .then((data) {
                //图片路径
                print(data);
            }).catchError((error){
                (error as PlatformException).details;//错误信息
                 print(error);
            });
```

