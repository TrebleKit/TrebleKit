import 'package:flutter/material.dart';

import 'app.dart';

void main() => runApp(const TrebleKitApp(isEmbed: false));

@pragma('vm:entry-point')
void embed() => runApp(const TrebleKitApp(isEmbed: true));
