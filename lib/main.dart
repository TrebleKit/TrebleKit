import 'package:flutter/material.dart';

import 'app.dart';
import 'global.dart';

void main() {
  Global.kSingleMode = true;
  runApp(const TrebleKitApp());
}

@pragma('vm:entry-point')
void embedMain() {
  Global.kSingleMode = false;
  runApp(const TrebleKitApp());
}
