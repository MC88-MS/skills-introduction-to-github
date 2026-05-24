# Add project specific ProGuard rules here.

# Keep data classes for Gson serialization
-keep class com.supplescan.data.model.** { *; }

# Keep Room entities
-keep class com.supplescan.data.database.** { *; }

# Hilt
-keepattributes *Annotation*

# ML Kit
-keep class com.google.mlkit.** { *; }
