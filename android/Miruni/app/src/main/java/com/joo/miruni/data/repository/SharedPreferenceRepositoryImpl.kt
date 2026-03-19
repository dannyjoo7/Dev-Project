package com.joo.miruni.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.joo.miruni.domain.repository.SharedPreferenceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import javax.inject.Inject

class SharedPreferenceRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>,
) : SharedPreferenceRepository {

    companion object {
        private val FORMATTER: DateTimeFormatter = DateTimeFormatter.ofPattern("a hh시 mm분")

        private val TIME_KEY = stringPreferencesKey("ALARM_TIME")
        private val COMPLETE_VISIBILITY_KEY = booleanPreferencesKey("SETTING_COMPLETED_ITEMS_VISIBILITY")
        private val UNLOCK_STATE_KEY = booleanPreferencesKey("SETTING_UNLOCK_STATE")
    }

    private val _settingCompletedItemsVisibility = MutableStateFlow(true)
    private val _settingUnlockState = MutableStateFlow(true)

    override suspend fun saveAlarmTime(time: LocalTime) {
        val formattedTime = time.format(FORMATTER)
        dataStore.edit { preferences ->
            preferences[TIME_KEY] = formattedTime
        }
    }

    override suspend fun getAlarmTime(): LocalTime? {
        val preferences = dataStore.data.first()
        val timeString = preferences[TIME_KEY] ?: return null
        return try {
            LocalTime.parse(timeString, FORMATTER)
        } catch (e: DateTimeParseException) {
            null
        }
    }

    override suspend fun settingUpdateCompletedItemsVisibility() {
        dataStore.edit { preferences ->
            val current = preferences[COMPLETE_VISIBILITY_KEY] ?: true
            preferences[COMPLETE_VISIBILITY_KEY] = !current
            _settingCompletedItemsVisibility.value = !current
        }
    }

    override suspend fun settingActiveUnlockScreen() {
        dataStore.edit { preferences ->
            val current = preferences[UNLOCK_STATE_KEY] ?: true
            preferences[UNLOCK_STATE_KEY] = !current
            _settingUnlockState.value = !current
        }
    }

    override fun getSettingCompletedItemsVisibilityState(): Boolean {
        return _settingCompletedItemsVisibility.value
    }

    override fun getSettingUnlockScreenState(): Boolean {
        return _settingUnlockState.value
    }

    override fun observeSettingCompletedItemsVisibility(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[COMPLETE_VISIBILITY_KEY] ?: true
        }
    }

    override fun observeSettingUnlockState(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[UNLOCK_STATE_KEY] ?: true
        }
    }
}
