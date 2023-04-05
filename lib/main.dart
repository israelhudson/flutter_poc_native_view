import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: MyHomePage(),
    );
  }
}

class MyHomePage extends StatefulWidget {
  @override
  _MyHomePageState createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  static const platform = const MethodChannel('com.example.my_flutter_app/native_view');
  late int _viewId;

  @override
  void initState() {
    super.initState();
    _createView();
  }

  Future<void> _createView() async {
    final int viewId = await platform.invokeMethod('createView');
    setState(() {
      _viewId = viewId;
    });
  }

  @override
  Widget build(BuildContext context) {
    if (_viewId == null) {
      return Scaffold(body: Center(child: CircularProgressIndicator()));
    }
    return Scaffold(
      appBar: AppBar(title: Text('Native View Example')),
      body: const Center(child: AndroidView(viewType: 'native_view')),
    );
  }
}
