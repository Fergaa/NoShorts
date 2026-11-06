package pl.noshorts

import android.accessibilityservice.AccessibilityService
import android.os.SystemClock
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo

/**
 * Gdy w aplikacji YouTube pojawi się odtwarzacz Shorts, usługa wykonuje "wstecz".
 *
 * Zużycie baterii: usługa reaguje tylko na zdarzenia z YouTube (patrz
 * accessibility_service_config.xml), więc poza YouTube nie robi nic.
 */
class ShortsBlockerService : AccessibilityService() {

    private var lastBackAt = 0L
    private var lastCheckAt = 0L

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        if (event?.packageName != YOUTUBE) return

        val now = SystemClock.uptimeMillis()
        // Dodatkowe ograniczenie: sprawdzamy ekran najwyżej co 300 ms.
        if (now - lastCheckAt < CHECK_INTERVAL_MS) return
        lastCheckAt = now
        // Po cofnięciu chwilę czekamy, żeby nie cofnąć dwa razy.
        if (now - lastBackAt < BACK_COOLDOWN_MS) return

        val root = rootInActiveWindow ?: return
        if (isShortsOnScreen(root)) {
            lastBackAt = now
            performGlobalAction(GLOBAL_ACTION_BACK)
        }
    }

    private fun isShortsOnScreen(root: AccessibilityNodeInfo): Boolean =
        SHORTS_VIEW_IDS.any { id ->
            root.findAccessibilityNodeInfosByViewId("$YOUTUBE:id/$id")
                .any { it.isVisibleToUser }
        }

    override fun onInterrupt() = Unit

    companion object {
        private const val YOUTUBE = "com.google.android.youtube"
        private const val CHECK_INTERVAL_MS = 300L
        private const val BACK_COOLDOWN_MS = 1200L

        /**
         * Identyfikatory widoków odtwarzacza Shorts. Kilka naraz, bo YouTube
         * zmienia je między wersjami. Jeśli blokada przestanie działać po
         * aktualizacji YouTube, wystarczy dopisać tu nowy identyfikator.
         */
        private val SHORTS_VIEW_IDS = listOf(
            "reel_player_page_container",
            "reel_watch_player",
            "reel_watch_fragment_root",
            "reel_player_overlay_container",
            "shorts_player_container",
        )
    }
}
