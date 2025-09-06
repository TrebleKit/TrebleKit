import 'package:flutter/material.dart';

import 'capsule_placeholder.dart';

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
      bottomNavigationBar: BottomAppBar(),
      body: Column(
        children: [
          Card.filled(
            color: Theme.of(context).colorScheme.primaryContainer,
            margin: const EdgeInsets.only(
              left: 16,
              top: 16,
              right: 16,
              bottom: 8,
            ),
            child: Tooltip(
              message: '',
              child: ListTile(
                leading: const Icon(Icons.check_circle_outline),
                title: Text('111'),
                subtitle: Column(
                  mainAxisSize: MainAxisSize.min,
                  mainAxisAlignment: MainAxisAlignment.start,
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [Text('111'), Text('111'), Text('111')],
                ),
                shape: RoundedRectangleBorder(
                  borderRadius: BorderRadius.circular(12),
                ),
                contentPadding: const EdgeInsets.symmetric(
                  vertical: 12,
                  horizontal: 24,
                ),
              ),
            ),
          ),
        ],
      ),
    );
  }
}
