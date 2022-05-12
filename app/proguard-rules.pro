# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
#-keep class umqg.asbs.xazpr.abs.Plsza
#-keep class umqg.asbs.xazpr.aas.Bpals

#数据库定义类不混淆
-keepclasseswithmembers class replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_abs.replaceflag_Pnnmjk {<fields>;<methods>;}

#保活方法名不混淆
-keepclassmembers public class cn.rs.keepalive.NativeWatcher{
*** onProcessDie();
}

-obfuscationdictionary zidian2.txt
-classobfuscationdictionary zidian2.txt
-packageobfuscationdictionary zidian2.txt

#所有view子类不混淆
-keep class * extends android.view.View {public *; protected *;}
#activity子类public和protected不混淆
-keep class * extends android.app.Activity {public *; protected *;}

#指定压缩级别
-optimizationpasses 5

#不跳过非公共的库的类成员
-dontskipnonpubliclibraryclassmembers

#混淆时采用的算法
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*

#把混淆类中的方法名也混淆了
-useuniqueclassmembernames

#优化时允许访问并修改有修饰符的类和类的成员
-allowaccessmodification

#将文件来源重命名为“SourceFile”字符串
-renamesourcefileattribute SourceFile
#保留行号
-keepattributes SourceFile,LineNumberTable
#保持泛型
-keepattributes Signature

#保持所有实现 Serializable 接口的类成员
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}


-dontwarn com.tencent.bugly.**
-keep public class com.tencent.bugly.**{*;}