import 'dart:async';

import 'package:flutter/services.dart';

class LsVideoPic {
  static const MethodChannel _channel = const MethodChannel('lsvideopic');

  static Future<String> videoFrameImagePath(String videoPath,num second) async =>
      await _channel.invokeMethod("getThumbPic", {"videoPath": videoPath,"second":second});
}
