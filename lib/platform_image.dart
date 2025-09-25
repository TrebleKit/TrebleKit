import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

class FlutterPlatformImage {
  static const MethodChannel _channel = MethodChannel(
    "flutter_platform_image",
  );

  static Future<Uint8List?> drawableMipmap(String name, bool isDrawable) async {
    return await _channel.invokeMethod("drawableMipmap", {
      "name": name,
      "is_drawable": isDrawable,
    });
  }
}

class BasePlatformImage extends StatefulWidget {
  const BasePlatformImage({
    super.key,
    required this.name,
    required this.isDrawable,
  });

  final String name;

  final bool isDrawable;

  @override
  State<BasePlatformImage> createState() => _BasePlatformImageState();
}

class _BasePlatformImageState extends State<BasePlatformImage> {
  Uint8List? image;

  @override
  void initState() {
    super.initState();
    getDrawableMipmap();
  }

  Future<void> getDrawableMipmap() async {
    FlutterPlatformImage.drawableMipmap(widget.name, widget.isDrawable)
        .then((value) => setState(() => image = value))
        .catchError((error) => debugPrint(error));
  }

  @override
  Widget build(BuildContext context) {
    return image == null ? Container() : Image.memory(image!);
  }
}

class DrawableImage extends StatelessWidget {
  const DrawableImage({super.key, required this.name});

  final String name;

  @override
  Widget build(BuildContext context) {
    return BasePlatformImage(name: name, isDrawable: true);
  }
}

class MipmapImage extends StatelessWidget {
  const MipmapImage({super.key, required this.name});

  final String name;

  @override
  Widget build(BuildContext context) {
    return BasePlatformImage(name: name, isDrawable: false);
  }
}
