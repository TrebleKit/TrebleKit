import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:freefeos/freefeos.dart';
import 'package:multi_builder/multi_builder.dart';

import 'home.dart';
import 'theme.dart';

class TrebleKitApp extends StatelessWidget {
  const TrebleKitApp({super.key, this.isEmbed = false});

  final bool isEmbed;

  /// 主题
  final TrebleKitTheme theme = const TrebleKitTheme();

  /// 主题
  ThemeData customTheme(BuildContext context, ThemeData origin) {
    if (!isEmbed) return origin;
    return origin.copyWith(
      appBarTheme: AppBarTheme(
        systemOverlayStyle: SystemUiOverlayStyle(
          systemNavigationBarColor: Colors.transparent,
          systemNavigationBarDividerColor: Colors.transparent,
          systemNavigationBarIconBrightness: .light,
          systemNavigationBarContrastEnforced: false,
          statusBarColor: Colors.transparent,
          statusBarBrightness: .light,
          statusBarIconBrightness: .light,
          systemStatusBarContrastEnforced: false,
        ),
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
