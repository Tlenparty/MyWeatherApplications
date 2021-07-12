package com.geekbrains.myweatherapplicatinons.framework.contentprovider

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri

import com.geekbrains.myweatherapplicatinons.model.database.*
import java.lang.IllegalArgumentException


private const val URI_ALL = 1 // URI для всех записей
private const val URI_ID = 2 // URI для конкретной записи
private const val ENTITY_PATH =
    "HistoryEntity" // Часть пути (будем определять путь до HistoryEntity)

class EducationContentProvider : ContentProvider() {
    private var authorities: String? = null // Адрес URL
    private lateinit var uriMatcher: UriMatcher // Помогает определить тип адреса URI(URI_ALL or URI_ID)

    // Типы данных
    private var entityContentType: String? = null //Набор строк
    private var entityContentItemType: String? = null // Одна строка

    private lateinit var contentUri: Uri // Адрес URI Provider

    override fun onCreate(): Boolean {
        // Прочитаем часть пути из ресурсов
        authorities = "geekbrains.provider"
        // Вспомогательный класс для определения типа запроса
        uriMatcher = UriMatcher(UriMatcher.NO_MATCH)
        // Если нас интересуют все объекты
        uriMatcher.addURI(authorities, ENTITY_PATH, URI_ALL)
        // Если нас интересует только один объект
        uriMatcher.addURI(authorities, "$ENTITY_PATH/#", URI_ID) // вместо # будет URI_ID
        // Тип содержимого - все объекты (ссылка на курсор)
        entityContentType = "vnd.android.cursor.dir/vnd.$authorities.$ENTITY_PATH"
        // Типа содержимого - один объект
        entityContentType = "vnd.android.cursor.item/vnd.$authorities.$ENTITY_PATH"
        // Строка для доступа к Provider
        contentUri = Uri.parse("content://$authorities/$ENTITY_PATH")
        return true
    }

    // Получаем данные (запрсоы)
    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?, // доп условия для чтения данных
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor {
        // Получаем доступ к данным
        val historyDao: HistoryDao = Database.db.historyDao()
        // При помощи UriMatcher определяем все элементы запрашиваются или один
        val cursor = when (uriMatcher.match(uri)) {
            URI_ALL -> historyDao.getHistoryCursor()  // Запрос к безе всех элементов
            URI_ID -> {
                // Определяем id из URI адреса. Класс ContentUris помогает это сделать
                val id = ContentUris.parseId(uri)
                // Запрос к базе данных для одного элемента
                historyDao.getHistoryCursor(id)
            }
            else -> throw IllegalArgumentException("Wrong URI: $uri")
        }
        // Устанавливаем нотификацию при изменении данных в contentUri
        cursor.setNotificationUri(context?.contentResolver, contentUri)
        return cursor
    }

    // Провайдер требует переопределение этого метода ,чтобв понимать тип запроса
    override fun getType(uri: Uri): String? {
        when (uriMatcher.match(uri)) {
            URI_ALL -> return entityContentType
            URI_ID -> return entityContentItemType
        }
        return null
    }

    // Добавляем новую запись которую хотим получить
    override fun insert(uri: Uri, values: ContentValues?): Uri {
        require(uriMatcher.match(uri) == URI_ALL) { "Wrong URI:$uri " }
        // Получаем доступ к данным
        val historyDao = Database.db.historyDao()
        // Добавляем запись о городе
        val entity = map(values)
        val id: Long = entity.id
        historyDao.insert(entity)
        val resultUri = ContentUris.withAppendedId(contentUri, id)
        // Уведомляем ContentResolver, что данные по адерсу resultUri изменились
        context?.contentResolver?.notifyChange(resultUri, null)
        return resultUri
    }

    // Удаляем запись
    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        // Получаем доступ к данным
        val historyDao = Database.db.historyDao()
        // Получаем индификатор записи по адресу
        val id = ContentUris.parseId(uri)
        // Удаляем запись по индификатору
        historyDao.deleteById(id)
        // Нотификация на изменение курсора
        context?.contentResolver?.notifyChange(uri, null)
        return 1 // возвращаем 1 потому, что удалили 1 запись
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        require(uriMatcher.match(uri) == URI_ID) { "Wrong URI: $uri" }
        // Получаем доступ к данным
        val historyDao = Database.db.historyDao()
        historyDao.update(map(values))
        context?.contentResolver?.notifyChange(uri, null)
        return 1
    }

    // Перводим ContentValues в HistoryEntity
    private fun map(values: ContentValues?): HistoryEntity {
        return if (values == null) {
            HistoryEntity()
        } else {
            val id = if (values.containsKey(ID)) values[ID] as Long else 0
            val city = values[CITY] as String
            val temperature = values[TEMPERATURE] as Int
            HistoryEntity(id, city, temperature)
        }
    }
}