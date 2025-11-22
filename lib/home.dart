import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

import 'capsule_placeholder.dart';
import 'platform_image.dart';
import 'platform_invoker.dart';

class MyHomePage extends StatefulWidget {
  const MyHomePage({super.key});

  @override
  State<MyHomePage> createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  List<PluginDetails> details = [];

  final EcosedBridge _bridge = const EcosedBridge();
  final EbKit _ebKit = const EbKit();

  @override
  void initState() {
    super.initState();
    _ebKit
        .getPluginList()
        .then((result) {
          // 缓存数据
          final List<PluginDetails> temp = [];
          // 遍历数据列表
          for (var data in result) {
            temp.add(
              PluginDetails.formJSON(
                json: jsonDecode(data), // 反序列化数据
                type: PluginType.normal,
              ),
            );
          }
          // 更新视图
          setState(() => details = temp);
        })
        .catchError((error) {
          // 处理异常
          debugPrint(error);
        });
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
                _bridge.invokeMethod('ebkit_platform', 'hello');
              },
              child: Text("Hello"),
            ),

            ...details.map(
              (e) => Card(
                child: ListTile(
                  title: Text('${e.title} (${e.channel})'),
                  subtitle: Text(e.description),
                ),
              ),
            ),
          ],
        ),
      ),
    );
  }
}

class EbKit {
  const EbKit();

  static const String _platformChannel = 'ebkit_platform';

  static const EcosedBridge _bridge = EcosedBridge();

  Future<List<String>> getPluginList() async {
    var result = await _bridge.invokeMethod<List<dynamic>>(
      _platformChannel,
      'getPluginList',
    );
    return result?.cast<String>() ?? List<String>.empty();
  }
}

class EcosedBridge {
  const EcosedBridge();

  static const String _channelName = 'ecosed_bridge';
  static const MethodChannel _channel = MethodChannel(_channelName);

  Future<T?> invokeMethod<T>(
    String channel,
    String method, [
    dynamic arguments,
  ]) async {
    return await _channel.invokePlatform<T>(method, {
      'channel': channel,
      ...?arguments,
    });
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
