import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

class HomeScreen extends StatefulWidget {
  const HomeScreen({Key? key}) : super(key: key);

  @override
  State<HomeScreen> createState() => _HomeScreenState();
}

class _HomeScreenState extends State<HomeScreen> {
  static const batteryChannel = MethodChannel('saqrelfirgany/battery');
  String batteryLevel = 'Waiting for battery level...';

  Future getBatteryLevel() async {
    final arguments = {'name': 'saqrelfirgany'};
    // Use invokeMethod instead of invokeListMethod
    dynamic newBatteryLevel = await batteryChannel.invokeMethod('getBatteryLevel', arguments);
    setState(() {
      batteryLevel = '$newBatteryLevel';
    });
  }


  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Battery'),
      ),
      body: Center(child: Text(batteryLevel)),
      bottomNavigationBar: Padding(
        padding: const EdgeInsetsDirectional.all(16),
        child: ElevatedButton(
          onPressed: getBatteryLevel,
          style: ElevatedButton.styleFrom(
            minimumSize: const Size(120, 50),
          ),
          child: const Text('Battery'),
        ),
      ),
    );
  }
}
