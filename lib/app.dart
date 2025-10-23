import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:freefeos/freefeos.dart';
import 'package:multi_builder/multi_builder.dart';

import 'app_mode.dart';
import 'home.dart';
import 'theme.dart';

class TrebleKitApp extends StatelessWidget {
  const TrebleKitApp({super.key, required this.mode});

  final AppMode mode;

  /// 主题
  final TrebleKitTheme theme = const TrebleKitTheme();

  /// 主题
  ThemeData customTheme(BuildContext context, ThemeData origin) {
    return origin.copyWith(
      appBarTheme: AppBarTheme(
        systemOverlayStyle: mode == AppMode.embed
            ? SystemUiOverlayStyle(
                systemNavigationBarColor: Colors.transparent,
                systemNavigationBarDividerColor: Colors.transparent,
                systemNavigationBarIconBrightness: Brightness.light,
                systemNavigationBarContrastEnforced: false,
                statusBarColor: Colors.transparent,
                statusBarBrightness: Brightness.light,
                statusBarIconBrightness: Brightness.light,
                systemStatusBarContrastEnforced: false,
              )
            : null,
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'TrebleKit',
      theme: customTheme(context, theme.light()),
      darkTheme: customTheme(context, theme.dark()),
      builder: <TransitionBuilder>[FreeFEOS.builder].toBuilder,
      home: const MyHomePage(),
      debugShowCheckedModeBanner: false,
    );
  }
}
