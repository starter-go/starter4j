# starter4j
用 Java 编写的 Starter 框架


### git 工作流程

         -------<---- prepare ----<---- main ----<-------
        |                                                |
    develop ---->---- debug ---->---- test ---->---- release


| 分支    | 本版号格式              | 本版号示例        | 用途                                             |
| ------- | ----------------------- | ----------------- | ------------------------------------------------ |
| main    | a.b.c                   | 1.2.3             | 仓库的默认分支                                   |
| prepare | 0.0.0-r0-dev-snapshot   | 固定为全 0        | 把 main 中的版本号恢复为 0 ， 准备合并到 develop |
| develop | 0.0.0-r0-dev-snapshot   | 固定为全 0        | 在此分支开发主要的功能                           |
| debug   | a.b.c-rd-snapshot       | 1.2.3-r4-snapshot | 处于调试阶段的那些快照版本                       |
| test    | a.b.c-rd-alpha(or.beta) | 1.2.3-r4-beta     | 内测 or 公测的版本                               |
| release | a.b.c                   | 1.2.3             | 通过测试后, 正式发布的稳定本版                   |
