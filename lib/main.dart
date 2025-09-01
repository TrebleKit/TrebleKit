import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_mixed/flutter_mixed.dart';
import 'package:freefeos/freefeos.dart';
import 'package:multi_builder/multi_builder.dart';

void main() => runApp(const MyApp());

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  ThemeData _getTheme(Brightness brightness) {
    return ThemeData(
      brightness: brightness,
      colorScheme: ColorScheme.fromSeed(
        seedColor: Colors.blue,
        brightness: brightness,
      ),
      appBarTheme: const AppBarTheme(
        systemOverlayStyle: SystemUiOverlayStyle(
          statusBarIconBrightness: Brightness.light,
          systemNavigationBarIconBrightness: Brightness.light,
        ),
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'TrebleKit',
      theme: _getTheme(Brightness.light),
      darkTheme: _getTheme(Brightness.dark),
      builder: <TransitionBuilder>[
        FreeFEOS.builder,
        FlutterMixed.builder,
      ].toBuilder,
      home: const MyHomePage(),
      debugShowCheckedModeBanner: false,
    );
  }
}

class MyHomePage extends StatefulWidget {
  const MyHomePage({super.key});

  @override
  State<MyHomePage> createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('feOS'),
        actions: [const CapsulePlaceholder()],
      ),
    );
  }
}

final class CapsulePlaceholder extends StatelessWidget {
  const CapsulePlaceholder({super.key});

  @override
  Widget build(BuildContext context) {
    return const Padding(
      padding: EdgeInsets.all(12.0),
      child: SizedBox(width: 87.0, height: 32.0),
    );
  }
}
