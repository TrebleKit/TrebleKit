import 'package:flutter/material.dart';

import 'app.dart';

void main() => runApp(const TrebleKitApp());

@pragma('vm:entry-point')
void embedMain() => runApp(const TrebleKitApp(isEmbed: true));
