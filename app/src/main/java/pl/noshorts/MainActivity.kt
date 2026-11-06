package pl.noshorts

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.Settings

/** Po kliknięciu ikony otwiera ustawienia ułatwień dostępu, żeby łatwo włączyć usługę. */
class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(
            Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        )
        finish()
    }
}
