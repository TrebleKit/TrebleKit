import 'package:flutter/services.dart';

import 'global.dart';

/// 平台调用扩展
extension PlatformInvoker on MethodChannel {
  /// 增加判断, 在模块独立调试时直接返回空值
  Future<T?> invokePlatform<T>(String method, [dynamic arguments]) async {
    if (Global.kSingleMode) return await null;
    return await invokeMethod<T>(method, arguments);
  }
}
