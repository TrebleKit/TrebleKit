import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

import 'global.dart';

enum ImageDrawable {
  treblekit(method: 'treblekit'),
  freefeos(method: 'freefeos'),
  ecosedkit(method: 'ecosedkit'),
  ebkit(method: 'ebkit');

  final String method;

  const ImageDrawable({required this.method});
}

abstract class _PlatformImage extends StatefulWidget {
  const _PlatformImage({super.key, this.size = 56, this.radius = 12});

  final double size;
  final double radius;

  ImageDrawable get image;

  @override
  State<_PlatformImage> createState() => _PlatformImageState();
}

class _PlatformImageState extends State<_PlatformImage> {
  _PlatformImageState();

  Uint8List? image;

  @override
  void initState() {
    super.initState();
    if (!Global.kSingleMode) {
      const MethodChannel("platform_resources")
          .invokeMethod<Uint8List>(widget.image.method)
          .then((value) => setState(() => image = value))
          .catchError((error) => debugPrint(error));
    }
  }

  @override
  Widget build(BuildContext context) {
    return ClipRRect(
      borderRadius: .circular(widget.radius),
      child: SizedBox(
        width: widget.size,
        height: widget.size,
        child: Container(
          color: Theme.of(context).colorScheme.primaryContainer,
          child: !Global.kSingleMode && image != null
              ? Image.memory(image!, fit: .fill)
              : Padding(padding: .all(8), child: const FlutterLogo()),
        ),
      ),
    );
  }
}

class TrebleKitLogo extends _PlatformImage {
  const TrebleKitLogo({super.key, super.size, super.radius});

  @override
  ImageDrawable get image => ImageDrawable.treblekit;
}

class FreeFEOSLogo extends _PlatformImage {
  const FreeFEOSLogo({super.key, super.size, super.radius});

  @override
  ImageDrawable get image => ImageDrawable.freefeos;
}

class EcosedKitLogo extends _PlatformImage {
  const EcosedKitLogo({super.key, super.size, super.radius});

  @override
  ImageDrawable get image => ImageDrawable.ecosedkit;
}

class EbKitLogo extends _PlatformImage {
  const EbKitLogo({super.key, super.size, super.radius});

  @override
  ImageDrawable get image => ImageDrawable.ebkit;
}
