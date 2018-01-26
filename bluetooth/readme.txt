注册设备流程

---receivedCheckAPPSuccess
---onBonded 写入一组密码
---receivedUpdateInnerPasswordInitSuccess 写入内置密码
---receivedUpdateInnerPasswordSuccess 注册后更新绑定密码
---receivedUpdateBondPasswordSuccess --更新绑定密码成功--写入服务注册成功


连接流程

-- onDeviceConnecting 开始连接
-- onDeviceConnected 连接成功
-- onDeviceReady  --校验
-- receivedCheckAPPSuccess 判断密码连接是否成功
-- receivedElectricityAndVersionSuccess--获取电量
   BOND_BONDED  发送一条无关命令 又返回说明连接成功，无返回连接失败
   BOND_BONDING
   BOND_NO      非绑定 向服务器获取绑定码从新绑定



