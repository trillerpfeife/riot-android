/*
 * Copyright 2018 New Vector Ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package im.vector.receiver

import android.content.*
import android.preference.PreferenceManager
import android.util.Log
import im.vector.util.lsFiles

/**
 * Receiver to handle some command from ADB
 */
class DebugReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {
            DEBUG_ACTION_DUMP_FILESYSTEM -> lsFiles(context)
            DEBUG_ACTION_DUMP_PREFERENCES -> dumpPreferences(context)
        }
    }

    private fun dumpPreferences(context: Context) {
        logPrefs("DefaultSharedPreferences", PreferenceManager.getDefaultSharedPreferences(context))
        logPrefs("Vector.LoginStorage", context.getSharedPreferences("Vector.LoginStorage", Context.MODE_PRIVATE))
        logPrefs("GcmRegistrationManager", context.getSharedPreferences("GcmRegistrationManager", Context.MODE_PRIVATE))
    }

    private fun logPrefs(name: String, sharedPreferences: SharedPreferences?) {
        Log.d(LOG_TAG, "SharedPreferences $name:")

        sharedPreferences?.let { prefs ->
            prefs.all.keys.forEach { key ->
                Log.d(LOG_TAG, "$key : ${prefs.all[key]}")
            }
        }
    }

    companion object {
        private const val LOG_TAG = "DebugReceiver"

        private const val DEBUG_ACTION_DUMP_FILESYSTEM = "im.vector.receiver.DEBUG_ACTION_DUMP_FILESYSTEM"
        private const val DEBUG_ACTION_DUMP_PREFERENCES = "im.vector.receiver.DEBUG_ACTION_DUMP_PREFERENCES"

        fun getIntentFilter() = IntentFilter().apply {
            addAction(DEBUG_ACTION_DUMP_FILESYSTEM)
            addAction(DEBUG_ACTION_DUMP_PREFERENCES)
        }
    }
}
