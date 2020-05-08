import 'dart:io';

import 'package:file_picker/file_picker.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter/widgets.dart';
import 'package:lsvideopic/lsvideopic.dart';

void main() => runApp(MyApp());

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  TextEditingController _control = TextEditingController();
  String videoPath = "";
  List<String> pathList = List();
  num milliseconds = 0;

  @override
  void initState() {
    super.initState();
    _control.addListener(() {
      milliseconds = num.parse(_control.text) * 1000;
    });
  }

  @override
  Widget build(BuildContext context) {
    print(pathList.length);
    pathList.forEach((data) {
      print(data);
    });
    return MaterialApp(
      home: Scaffold(
          appBar: AppBar(
            title: const Text('Plugin example app'),
          ),
          body: Column(
            children: <Widget>[
              Padding(
                padding: EdgeInsets.symmetric(horizontal: 20, vertical: 30),
                child: Text(videoPath),
              ),
              Row(
                children: <Widget>[
                  FlatButton(
                      onPressed: () async {
                        File file =
                            await FilePicker.getFile(type: FileType.video);
                        pathList.clear();
                        print(file.path);
                        setState(() {
                          videoPath = file.path;
                        });
                      },
                      child: Text("选吧老铁")),
                  Expanded(
                      child: TextField(
                    controller: _control,
                  )),
                  FlatButton(
                      onPressed: () async {
                        LsVideoPic.videoFrameImagePath(videoPath, milliseconds)
                            .then((data) {
                          setState(() {
                            pathList.add(data);
                          });
                        }).catchError((error){

                          (error as PlatformException).details;
                          print(error);
                        });
                      },
                      child: Text("裁吧老弟"))
                ],
              ),
              Expanded(
                  child: GridView.builder(
                itemCount: pathList.length,
                itemBuilder: (ctx, index) =>
                    Container(child: Image.file(File(pathList[index])),color: Colors.green,),
                gridDelegate: SliverGridDelegateWithFixedCrossAxisCount(
                    crossAxisCount: 4,childAspectRatio: 9/16,crossAxisSpacing: 2,mainAxisSpacing: 2),
              ))
            ],
          )),
    );
  }
}
