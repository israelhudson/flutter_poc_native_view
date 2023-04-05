import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Native View Counter Example',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: MyHomePage(title: 'Native View Counter Example'),
    );
  }
}

class MyHomePage extends StatefulWidget {
  MyHomePage({Key? key, required this.title}) : super(key: key);

  final String title;

  @override
  _MyHomePageState createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  static const platform = const MethodChannel('com.example.my_flutter_app/native_view');
  late int _viewId;
  int _counter = 0;

  @override
  void initState() {
    super.initState();
    _createView();
  }

  Future<void> _createView() async {
    final int viewId = await platform.invokeMethod('createView');
    platform.setMethodCallHandler((MethodCall call) async {
      if (call.method == "onCounterChanged") {
        final int newCounterValue = call.arguments;
        setState(() {
          _counter = newCounterValue;
        });
      }
    });

    setState(() {
      _viewId = viewId;
    });
  }

  @override
  Widget build(BuildContext context) {
    if (_viewId == null) {
      return CircularProgressIndicator();
    }

    return Scaffold(
      appBar: AppBar(title: Text(widget.title)),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Expanded(
              child: AndroidView(viewType: 'native_view'),
            ),
            Text(
              'Counter value in Flutter: $_counter',
              style: Theme.of(context).textTheme.headline5,
            ),
          ],
        ),
      ),
    );
  }
}
