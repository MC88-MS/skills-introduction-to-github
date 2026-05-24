package com.supplescan.di

import android.content.Context
import androidx.room.Room
import com.supplescan.data.database.IngredientDao
import com.supplescan.data.database.ScannedSupplementDao
import com.supplescan.data.database.SupplementDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): SupplementDatabase {
        return Room.databaseBuilder(
            context,
            SupplementDatabase::class.java,
            SupplementDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    fun provideScannedSupplementDao(db: SupplementDatabase): ScannedSupplementDao =
        db.scannedSupplementDao()

    @Provides
    fun provideIngredientDao(db: SupplementDatabase): IngredientDao =
        db.ingredientDao()
}
