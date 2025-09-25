import 'package:flutter/material.dart';
import 'package:freefeos/freefeos.dart';
import 'package:multi_builder/multi_builder.dart';
import 'package:treblekit/platform_image.dart';

import 'capsule_placeholder.dart';
import 'theme.dart';

void main() => runApp(const MyApp());

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  final MaterialTheme theme = const MaterialTheme();

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'TrebleKit',
      theme: theme.light(),
      darkTheme: theme.dark(),
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
          children: [
            Text('AppID: xxxxxx'),
            Text('版本: 1.0.0'),
          ],
        ),
        contentPadding: EdgeInsets.symmetric(vertical: 16, horizontal: 20),
      ),
    );
  }
}
