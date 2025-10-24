import 'package:flutter/material.dart';

import 'app.dart';
import 'app_mode.dart';

@pragma('vm:entry-point')
void main() => run(AppMode.normal);

@pragma('vm:entry-point')
void embed() => run(AppMode.embed);

// 启动应用
void run(AppMode mode) {
  runApp(TrebleKitApp(mode: mode));
}
