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
  List<String>? plugins;

  @override
  void initState() {
    super.initState();
    const MethodChannel("ecosed_bridge")
        .invokeListMethod<String>('getPluginList', {'channel': 'ecosed_engine'})
        .then((value) => setState(() => plugins = value))
        .catchError((error) => debugPrint(error));
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('TrebleKit'),
        leading: IconButton(onPressed: () {}, icon: Icon(Icons.menu)),
        actions: const [CapsulePlaceholder()],
      ),
      body: SingleChildScrollView(
        child: Column(
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
                channel.invokeMethod('hello', {'channel': 'ecosed_engine'});
              },
              child: Text("Hello"),
            ),

            Text(plugins.toString()),
          ],
        ),
      ),
    );
  }
}

class EcosedBridge {
  static const MethodChannel _channel = MethodChannel('ecosed_bridge');

  void a() {
    _channel.setMethodCallHandler((call) async {
      switch (call.method) {}
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
