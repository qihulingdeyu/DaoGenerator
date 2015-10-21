在android studio下使用需将src-template目录下的所有.ftl文件拷贝到 /build/classes/main/ 目录下，
并且在生成文件时，目录不用带“../”，如下：
AndroidStudio：new DaoGenerator().generateAll(schema, "TargetProject/src-gen");
Eclipse：new DaoGenerator().generateAll(schema, "../TargetProject/src-gen");