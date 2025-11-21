import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

import 'capsule_placeholder.dart';
import 'platform_image.dart';

class MyHomePage extends StatefulWidget {
  const MyHomePage({super.key});

  @override
  State<MyHomePage> createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  List<PluginDetails> details = [];

  @override
  void initState() {
    super.initState();
    const MethodChannel("ecosed_bridge")
        .invokeListMethod<String>('getPluginList', {
          'channel': 'ebkit_platform',
        })
        .then((result) {
          List<PluginDetails> temp = [];
          for (var element in result ?? []) {
            temp.add(
              PluginDetails.formJSON(
                json: jsonDecode(element),
                type: PluginType.normal,
              ),
            );
          }
          setState(() => details = temp);
        }, onError: (error) => debugPrint(error));
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('TrebleKit'),
        leading: IconButton(onPressed: () {}, icon: const Icon(Icons.menu)),
        actions: const [CapsulePlaceholder()],
      ),
      body: SingleChildScrollView(
        child: Column(
          crossAxisAlignment: .center,
          children: [
            Header(),
            StateCard(),

            TrebleKitLogo(),
            FreeFEOSLogo(),
            EcosedKitLogo(),
            EbKitLogo(),

            ElevatedButton(
              onPressed: () {
                final MethodChannel channel = MethodChannel('ecosed_bridge');
                channel.invokeMethod('hello', {'channel': 'ebkit_platform'});
              },
              child: Text("Hello"),
            ),

            ...details.map(
              (e) => Card(
                child: ListTile(
                  title: Text('${e.title} (${e.description})'),
                  subtitle: Text(e.channel),
                ),
              ),
            ),
          ],
        ),
      ),
    );
  }
}

class EbKit {}

class EcosedBridge {
  static const String _channelName = 'ecosed_engine';
  static const MethodChannel _channel = MethodChannel(_channelName);

  void invokeMethod({required String channel, required String method}) {
    _channel.invokeMethod(method, {'channel': channel});
  }
}

class Header extends StatelessWidget {
  const Header({super.key});

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: const .symmetric(vertical: 16, horizontal: 30),
      child: Row(
        children: <Widget>[
          EcosedKitLogo(),
          Padding(
            padding: const .only(left: 16),
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
      margin: const .all(16),
      color: Theme.of(context).colorScheme.primaryContainer,
      child: ListTile(
        leading: const Icon(Icons.check_circle_outline),
        title: Text('一切正常'),
        subtitle: Column(
          crossAxisAlignment: .start,
          children: [Text('AppID: xxxxxx'), Text('版本: 1.0.0')],
        ),
        contentPadding: const .symmetric(vertical: 16, horizontal: 20),
      ),
    );
  }
}

enum PluginType { normal, unknown }

final class PluginDetails {
  const PluginDetails({
    required this.channel,
    required this.title,
    required this.description,
    required this.type,
  });

  /// 插件通道,插件的唯一标识符
  final String channel;

  /// 插件标题
  final String title;

  /// 插件描述
  final String description;

  /// 插件类型
  final PluginType type;

  /// 使用Map解析插件详细信息
  factory PluginDetails.formMap({
    required Map<String, dynamic> map,
    required PluginType type,
  }) {
    return PluginDetails(
      channel: map['channel'],
      title: map['title'],
      description: map['description'],
      type: type,
    );
  }

  /// 使用JSON解析插件详细信息
  factory PluginDetails.formJSON({
    required Map<String, dynamic> json,
    required PluginType type,
  }) {
    return PluginDetails(
      channel: json['channel'],
      title: json['title'],
      description: json['description'],
      type: type,
    );
  }
}
