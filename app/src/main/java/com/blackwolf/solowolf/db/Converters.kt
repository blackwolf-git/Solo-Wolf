import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.Date
import com.blackwolf.solowolf.models.Sport
import com.blackwolf.solowolf.models.MuscleGroup

class Converters {
    private val gson = Gson()

    // For List<String>
    @TypeConverter
    fun fromStringList(value: String?): List<String>? {
        if (value == null) return null
        val listType = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(value, listType)
    }

    @TypeConverter
    fun toStringList(list: List<String>?): String? {
        return gson.toJson(list)
    }

    // For Date
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    // For List<Sport>
    @TypeConverter
    fun fromSportList(value: String?): List<Sport>? {
        if (value == null) return null
        val listType = object : TypeToken<List<Sport>>() {}.type
        return gson.fromJson(value, listType)
    }

    @TypeConverter
    fun sportListToString(list: List<Sport>?): String? {
        return gson.toJson(list)
    }

    // For List<MuscleGroup>
    @TypeConverter
    fun fromMuscleGroupList(value: String?): List<MuscleGroup>? {
        if (value == null) return null
        val listType = object : TypeToken<List<MuscleGroup>>() {}.type
        return gson.fromJson(value, listType)
    }

    @TypeConverter
    fun muscleGroupListToString(list: List<MuscleGroup>?): String? {
        return gson.toJson(list)
    }
}
