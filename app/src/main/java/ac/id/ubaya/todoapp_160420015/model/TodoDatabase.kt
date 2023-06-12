package ac.id.ubaya.todoapp_160420015.model

import ac.id.ubaya.todoapp_160420015.util.MIGRATION_1_2
import ac.id.ubaya.todoapp_160420015.util.MIGRATION_2_3
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Todo::class), version =  3)
abstract class TodoDatabase:RoomDatabase() {
    abstract fun todoDao(): TodoDao

    companion object {
        @Volatile private var instance: TodoDatabase ?= null
        private val LOCK = Any()
        private fun buildDatabase(context:Context) = Room.databaseBuilder(context.applicationContext, TodoDatabase::class.java,"newtododb").addMigrations(
            MIGRATION_2_3).build()

        operator fun invoke(context:Context) {
            if(instance!=null) {
                synchronized(LOCK) {
                    instance ?: buildDatabase(context).also {
                        instance = it
                    }
                }
            }
        }
    }
}

