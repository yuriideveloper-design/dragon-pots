-keepattributes SourceFile,LineNumberTable
-renamesourcefileattribute SourceFile
-keepattributes *Annotation*, InnerClasses, EnclosingMethod, Signature, Exceptions

-keep class com.p95ea315e.complete_first_called_neon.SyndicateApp { *; }
-keep class com.p95ea315e.complete_first_called_neon.MainActivity { *; }
-keep class com.p95ea315e.complete_first_called_neon.hull.GlyphPeg { *; }
-keepclassmembers class com.p95ea315e.complete_first_called_neon.hull.GlyphPeg {
    @android.webkit.JavascriptInterface <methods>;
}
-keep class com.p95ea315e.complete_first_called_neon.cask.RuneCask { *; }
-dontwarn com.google.android.gms.**
-dontwarn kotlin.**
-dontwarn kotlinx.coroutines.**

# WorkManager starts via InitializationProvider and reflects WorkDatabase_Impl.<init>().
-keep class * extends androidx.room.RoomDatabase { <init>(); }
-keep class androidx.work.impl.WorkDatabase_Impl { <init>(); }
