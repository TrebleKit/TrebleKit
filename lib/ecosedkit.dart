import 'package:flutter/material.dart';

class EcosedkitPage extends StatefulWidget {
  const EcosedkitPage({super.key});

  @override
  State<EcosedkitPage> createState() => _EcosedkitPageState();
}

class _EcosedkitPageState extends State<EcosedkitPage> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Ecosedkit'),
      ),
      bottomNavigationBar: BottomAppBar(
        child: Row(
          children: [
            Center(
              child: Text('点击FAB按钮跳转EcosedKit'),
            )
          ],
        ),
      ),
    );
  }
}
