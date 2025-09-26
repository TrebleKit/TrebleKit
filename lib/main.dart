import 'dart:async';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:freefeos/freefeos.dart';
import 'package:multi_builder/multi_builder.dart';

import 'capsule_placeholder.dart';
import 'platform_image.dart';
import 'theme.dart';

void main() => TrebleRunnable()();


class AndroidToFlutter {
  final EventChannel eventStream = const EventChannel("android_to_flutter");

  StreamSubscription<dynamic> listenNativeData({
    required Function(String data) callback,
  }) {
    return eventStream.receiveBroadcastStream().listen((dynamic event) {
      if (event is Map && event["type"] != null) {
        switch (event["type"]) {
          case "takeString":
            callback(event["data"]);
            break;
          default:
            debugPrint("未知事件类型: ${event["type"]}");
            break;
        }
      } else {
        debugPrint("未知事件: $event");
      }
    }, onError: (error) => debugPrint(error.toString()));
  }
}

class TrebleRunnable {

  void call() {
    runApp(const TrebleKitApp());
  }
}

class TrebleKitApp extends StatefulWidget {
  const TrebleKitApp({super.key});

  @override
  State<TrebleKitApp> createState() => _TrebleKitAppState();
}

class _TrebleKitAppState extends State<TrebleKitApp> {
  final MaterialTheme theme = const MaterialTheme();

  bool isLight = true;

  @override
  void didChangeDependencies() {
    super.didChangeDependencies();

    debugPrint('didChangeDependencies');
  }

  @override
  void initState() {
    super.initState();

    AndroidToFlutter().listenNativeData(callback: (data){
      debugPrint(data);
      if (data == 'dark_icon') {
        setState(() {
          isLight = false;
        });
      } else if (data == 'light_icon') {
        setState(() {
          isLight = true;
        });
      }
    });
  }

  Brightness get getBrightness {
    if (isLight) {
      return Brightness.light;
    } else {
      return Brightness.dark;
    }
  }

  ThemeData getTheme(Brightness brightness) {
    ThemeData origin;

    if (brightness == Brightness.light) {
      origin = theme.light();
    } else {
      origin = theme.dark();
    }

    ThemeData now = origin.copyWith(
      appBarTheme: AppBarTheme(
        centerTitle: true,
        systemOverlayStyle: SystemUiOverlayStyle(
          systemNavigationBarColor: Colors.transparent,
          systemNavigationBarDividerColor: Colors.transparent,
          systemNavigationBarIconBrightness: getBrightness,
          systemNavigationBarContrastEnforced: false,
          statusBarColor: Colors.transparent,
          statusBarBrightness: getBrightness,
          statusBarIconBrightness: getBrightness,
          systemStatusBarContrastEnforced: false,
        ),
      ),
    );

    return now;
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'TrebleKit',
      theme: getTheme(Brightness.light),
      darkTheme: getTheme(Brightness.dark),
      builder: <TransitionBuilder>[FreeFEOS.builder].toBuilder,
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
        title: Text('EcosedKit'),
        actions: const [CapsulePlaceholder()],
      ),
      body: Column(children: [Header(), StateCard()]),
    );
  }
}

class Header extends StatelessWidget {
  const Header({super.key});

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: EdgeInsets.symmetric(vertical: 16, horizontal: 30),
      child: Row(
        children: [
          //Image.asset('assets/logo.png', width: 56)
          ClipRRect(
            borderRadius: BorderRadius.circular(12),
            child: SizedBox(
              width: 56,
              height: 56,
              child: MipmapImage(name: 'ic_launcher'),
            ),
          ),
          Padding(
            padding: EdgeInsets.only(left: 16),
            child: Text(
              'EcosedKit',
              style: Theme.of(context).textTheme.titleLarge,
            ),
          ),
        ],
      ),
    );
  }
}

class StateCard extends StatelessWidget {
  const StateCard({super.key});

  @override
  Widget build(BuildContext context) {
    return Card(
      margin: EdgeInsets.all(16),
      color: Theme.of(context).colorScheme.primaryContainer,
      child: ListTile(
        leading: Icon(Icons.check_circle_outline),
        title: Text('一切正常'),
        subtitle: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [Text('AppID: xxxxxx'), Text('版本: 1.0.0')],
        ),
        contentPadding: EdgeInsets.symmetric(vertical: 16, horizontal: 20),
      ),
    );
  }
}
