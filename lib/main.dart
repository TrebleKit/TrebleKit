import 'package:flutter/material.dart';

import 'app.dart';

@pragma('vm:entry-point')
void main() => runApp(const TrebleKitApp(mode: AppMode.normal));

@pragma('vm:entry-point')
void mainEmbed() => runApp(const TrebleKitApp(mode: AppMode.embed));

@pragma('vm:entry-point')
void mainFloat() => runApp(const TrebleKitApp(mode: AppMode.float));
