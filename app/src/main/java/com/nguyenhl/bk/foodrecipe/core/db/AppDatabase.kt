package com.nguyenhl.bk.foodrecipe.core.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

//@Database(
//    entities = [],
//    version = 1,
//    exportSchema = true
//)
//abstract class AppDatabase : RoomDatabase() {
//    abstract val userDao: UserDao
//
//    companion object {
//        @Volatile
//        private var INSTANCE: AppDatabase? = null
//
//        val executorService: ExecutorService = Executors.newFixedThreadPool(4)
//
//        fun getDatabase(context: Context): AppDatabase {
//            if (INSTANCE == null) {
//                synchronized(AppDatabase::class.java) {
//                    INSTANCE = Room
//                        .databaseBuilder(context, AppDatabase::class.java, "database.db")
//                        .fallbackToDestructiveMigration()
//                        .build()
//                }
//            }
//            return INSTANCE!!
//        }
//    }
//}
