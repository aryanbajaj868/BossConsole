package ai.rever.boss.plugin.repository

import java.io.InputStream
import java.io.InputStreamReader

const val MAX_MANIFEST_BYTES = 512 * 1024 // 512 KB

fun InputStream.readBoundedText(limitBytes: Int = MAX_MANIFEST_BYTES): String {
    val reader = InputStreamReader(this, Charsets.UTF_8)
    val buffer = CharArray(8192)
    val builder = StringBuilder()
    var totalRead = 0

    while (true) {
        val charsRead = reader.read(buffer)
        if (charsRead == -1) break
        
        totalRead += charsRead
        if (totalRead > limitBytes) {
            throw RuntimeException("Manifest entry exceeds maximum allowed size of $limitBytes bytes") 
        }
        
        builder.append(buffer, 0, charsRead)
    }
    
    return builder.toString()
}
