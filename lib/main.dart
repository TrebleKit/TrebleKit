import 'package:flutter/material.dart';

import 'app.dart';
import 'app_mode.dart';

@pragma('vm:entry-point')
void main() => runApp(const TrebleKitApp(mode: AppMode.normal));

@pragma('vm:entry-point')
void embed() => runApp(const TrebleKitApp(mode: AppMode.embed));
