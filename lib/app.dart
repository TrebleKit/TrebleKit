import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_mixed/flutter_mixed.dart';
import 'package:freefeos/freefeos.dart';
import 'package:multi_builder/multi_builder.dart';

import 'ecosedkit.dart';
import 'home.dart';
import 'theme.dart';

class TrebleKitApp extends StatelessWidget {
  const TrebleKitApp({super.key});

  ThemeData _getTheme(Brightness brightness) {
    final MaterialTheme theme = const MaterialTheme(TextTheme());
    final bool isLight = brightness == Brightness.light;
    final ThemeData themeData = isLight ? theme.light() : theme.dark();
    return themeData.copyWith(
      appBarTheme: const AppBarTheme(
        centerTitle: true,
        systemOverlayStyle: SystemUiOverlayStyle(
          systemNavigationBarColor: Colors.transparent,
          systemNavigationBarDividerColor: Colors.transparent,
          systemNavigationBarIconBrightness: Brightness.light,
          systemNavigationBarContrastEnforced: false,
          statusBarColor: Colors.transparent,
          statusBarBrightness: Brightness.light,
          statusBarIconBrightness: Brightness.light,
          systemStatusBarContrastEnforced: false,
        ),
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      onGenerateTitle: (context) {
        return 'TrebleKit';
      },
      theme: _getTheme(Brightness.light),
      darkTheme: _getTheme(Brightness.dark),
      builder: <TransitionBuilder>[
        FreeFEOS.builder,
        FlutterMixed.builder,
      ].toBuilder,
      routes: {
        '/': (_) => const MyHomePage(),
        '/ecosedkit': (_) => const EcosedkitPage(),
      },
      debugShowCheckedModeBanner: false,
    );
  }
}
