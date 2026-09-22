-keepattributes SourceFile,LineNumberTable
-renamesourcefileattribute SourceFile
-keepattributes *Annotation*, InnerClasses, EnclosingMethod, Signature, Exceptions

-keep class com.p95ea315e.complete_first_called_neon.SyndicateApp { *; }
-keep class com.p95ea315e.complete_first_called_neon.MainActivity { *; }
-keep class com.p95ea315e.complete_first_called_neon.glaze.GlyphPeg { *; }
-keepclassmembers class com.p95ea315e.complete_first_called_neon.glaze.GlyphPeg {
    @android.webkit.JavascriptInterface <methods>;
}
-keep class com.p95ea315e.complete_first_called_neon.anvil.RuneCask { *; }
-dontwarn com.google.android.gms.**
-dontwarn kotlin.**
-dontwarn kotlinx.coroutines.**
