import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:freefeos/freefeos.dart';
import 'package:multi_builder/multi_builder.dart';

import 'global.dart';
import 'home.dart';
import 'theme.dart';

class TrebleKitApp extends StatelessWidget {
  const TrebleKitApp({super.key});

  /// 主题
  final TrebleKitTheme theme = const TrebleKitTheme();

  /// 主题
  ThemeData customTheme(BuildContext context, ThemeData origin) {
    if (Global.kSingleMode) return origin;
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
      builder: <TransitionBuilder?>[FreeFEOS.builder.osContained].toBuilder,
      home: const MyHomePage(),
      debugShowCheckedModeBanner: false,
    );
  }
}

extension OSContainerExtension on TransitionBuilder {
  TransitionBuilder get osContained {
    return OSContainer(os: this).builder;
  }
}

class OSContainer with ChangeNotifier {
  OSContainer({required this.os});

  final TransitionBuilder os;

  TransitionBuilder get builder => os;
}
