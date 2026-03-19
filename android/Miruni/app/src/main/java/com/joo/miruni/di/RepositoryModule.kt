package com.joo.miruni.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.joo.miruni.data.database.AppDatabase
import com.joo.miruni.data.database.TaskDao
import com.joo.miruni.data.repository.SharedPreferenceRepositoryImpl
import com.joo.miruni.data.repository.TaskRepositoryImpl
import com.joo.miruni.domain.repository.SharedPreferenceRepository
import com.joo.miruni.domain.repository.TaskRepository
import com.joo.miruni.service.notification.ReminderManagerUtil
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings_preferences")

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {


    /*
    * DataStore
    * */
    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return context.dataStore
    }

    @Provides
    @Singleton
    fun provideSharedPreferenceRepository(dataStore: DataStore<Preferences>): SharedPreferenceRepository {
        return SharedPreferenceRepositoryImpl(dataStore)
    }


    /*
    * RoomDB
    * */
    @Provides
    @Singleton
    fun provideTaskRepository(
        taskDao: TaskDao,
        reminderManagerUtil: ReminderManagerUtil,
    ): TaskRepository {
        return TaskRepositoryImpl(taskDao, reminderManagerUtil)
    }

    @Provides
    @Singleton
    fun provideTaskDao(database: AppDatabase): TaskDao {
        return database.taskDao()
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app_database"
        ).build()
    }
}
